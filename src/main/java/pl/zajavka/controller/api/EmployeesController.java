package pl.zajavka.controller.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zajavka.controller.dao.PetDAO;
import pl.zajavka.controller.dto.EmployeeDTO;
import pl.zajavka.controller.dto.EmployeesDTO;
import pl.zajavka.controller.dto.EmployeeMapper;
import pl.zajavka.infrastructure.database.entity.EmployeeEntity;
import pl.zajavka.infrastructure.database.entity.PetEntity;
import pl.zajavka.infrastructure.database.petstore.Pet;
import pl.zajavka.infrastructure.database.repository.EmployeeRepository;
import pl.zajavka.infrastructure.database.repository.PetRepository;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping(EmployeesController.BASE_PATH)
@AllArgsConstructor
class EmployeesController {

    public static final String BASE_PATH = "/employees";
    public static final String EMPLOYEE_ID = "/{employeeId}";
    public static final String EMPLOYEE_ID_RESULT = "/%s";
    public static final String EMPLOYEE_UPDATE_SALARY = "/{employeeId}/salary";
    public static final String EMPLOYEE_UPDATE_PET = "/{employeeId}/pet/{petId}";
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private PetDAO petDAO;
    private PetRepository petRepository;


    @GetMapping
    public EmployeesDTO employeesList() {
        return EmployeesDTO.of(employeeRepository.findAll().stream()
                .map(employeeMapper::map)
                .toList());
    }

    @GetMapping(
            value = EMPLOYEE_ID, produces =
            {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public EmployeeDTO showEmployeeDetails(@PathVariable Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::map)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .salary(employeeDTO.getSalary())
                .phone(employeeDTO.getPhone())
                .email(employeeDTO.getEmail())
                .build();
        EmployeeEntity created = employeeRepository.save(newEmployee);
        return ResponseEntity
                .created(URI.create(BASE_PATH + EMPLOYEE_ID_RESULT.formatted(created.getEmployeeId())))
                .build();
    }

    @PutMapping(EMPLOYEE_ID)
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer employeeId,
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSurname(employeeDTO.getSurname());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setPhone(employeeDTO.getPhone());
        existingEmployee.setEmail(employeeDTO.getEmail());
        employeeRepository.save(existingEmployee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(EMPLOYEE_ID)
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
        employeeRepository.deleteById(existingEmployee.getEmployeeId());
        return ResponseEntity.ok().build();
//        try {
//            employeeRepository.deleteById(employeeId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
    }

    @PatchMapping(EMPLOYEE_UPDATE_SALARY)
    public ResponseEntity<?> updateEmployeeSalary(
            @PathVariable Integer employeeId,
            @RequestParam(required = true) BigDecimal newSalary
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
        existingEmployee.setSalary(newSalary);
        employeeRepository.save(existingEmployee);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "test-header")
    public ResponseEntity<?> testHeader(
            @RequestHeader(value = HttpHeaders.ACCEPT) MediaType accept,
            @RequestHeader(value = "httpStatus", required = true) int httpStatus
    ) {
        return ResponseEntity
                .status(httpStatus)
                .header("x-my-header", accept.toString())
                .body("Accepted: " + accept);
    }
    @PatchMapping(EMPLOYEE_UPDATE_PET)
    public ResponseEntity<?> updatePet(
            @PathVariable Integer employeeId,
            @PathVariable Integer petId)
    {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
        Pet petFromStore = petDAO.getPet(Long.valueOf(petId))
                .orElseThrow(() -> new RuntimeException(
                        String.format("Pet with id: [%s] could not be retrieved.", petId)));
        PetEntity newPet = PetEntity.builder()
                .petStorePetId(petFromStore.getId())
                .name(petFromStore.getName())
                .status(petFromStore.getStatus())
                .employee(existingEmployee)
                .build();
        petRepository.save(newPet);
        return ResponseEntity.ok().build();
    }


}

