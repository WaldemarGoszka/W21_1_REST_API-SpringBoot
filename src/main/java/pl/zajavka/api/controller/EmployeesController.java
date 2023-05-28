package pl.zajavka.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zajavka.api.dto.EmployeeDTO;
import pl.zajavka.api.dto.EmployeesDTO;
import pl.zajavka.api.mapper.EmployeeMapper;
import pl.zajavka.infrastructure.database.entity.EmployeeEntity;
import pl.zajavka.infrastructure.database.repository.EmployeeRepository;

import java.net.URI;

@RestController
@RequestMapping(EmployeesController.BASE_PATH)
@AllArgsConstructor
class EmployeesController {

    public static final String BASE_PATH = "/employees";
    public static final String EMPLOYEE_ID = "/{employeeId}";
    public static final String EMPLOYEE_ID_RESULT = "/%s";
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

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


}

