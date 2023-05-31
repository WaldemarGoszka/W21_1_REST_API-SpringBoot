package pl.zajavka.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Integer employeeId;
    private String name;
    private String surname;
    private BigDecimal salary;
    private String phone;
    private String email;
}

