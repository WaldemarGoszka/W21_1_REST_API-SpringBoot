package pl.zajavka.api.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zajavka.api.dto.EmployeeDTO;
import pl.zajavka.api.dto.EmployeesDTO;
import pl.zajavka.api.mapper.EmployeeMapper;
import pl.zajavka.infrastructure.database.repository.EmployeeRepository;

@RestController
@RequestMapping(EmployeesController.BASE_PATH)
@AllArgsConstructor
class EmployeesController {

    public static final String BASE_PATH = "/employees";
    public static final String EMPLOYEE_ID = "/{employeeId}";
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    @GetMapping
    public EmployeesDTO employeesList() {
        return EmployeesDTO.of(employeeRepository.findAll().stream()
                .map(employeeMapper::map)
                .toList());
    }

    @GetMapping(value = EMPLOYEE_ID)
    public EmployeeDTO showEmployeeDetails(@PathVariable Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::map)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
    }
}

