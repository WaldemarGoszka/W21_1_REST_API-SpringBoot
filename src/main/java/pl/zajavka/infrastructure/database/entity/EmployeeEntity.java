package pl.zajavka.infrastructure.database.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Data
@With
@Entity
@Builder
@ToString(of = {"name", "surname", "email"})
@EqualsAndHashCode(of = "employeeId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;
}
