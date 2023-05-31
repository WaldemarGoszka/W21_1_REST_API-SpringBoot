package pl.zajavka.infrastructure.database.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

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

    @Size(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    @Column(name = "phone")
    private String phone;
    @Email
    @Column(name = "email")
    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
    private Set<PetEntity> pets;

}
