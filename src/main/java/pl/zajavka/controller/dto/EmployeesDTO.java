package pl.zajavka.controller.dto;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeesDTO {
    private List<EmployeeDTO> employees;
}
