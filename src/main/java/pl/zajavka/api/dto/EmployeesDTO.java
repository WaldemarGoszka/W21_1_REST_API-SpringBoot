package pl.zajavka.api.dto;

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
