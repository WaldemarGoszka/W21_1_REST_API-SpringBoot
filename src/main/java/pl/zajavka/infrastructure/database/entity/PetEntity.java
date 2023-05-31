package pl.zajavka.infrastructure.database.entity;

import lombok.*;
import jakarta.persistence.*;

@Data
@With
@Entity
@Builder
@ToString(of = {"name", "status"})
@EqualsAndHashCode(of = "petId")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pet")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer petId;

    @Column(name = "pet_store_pet_id")
    private Long petStorePetId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
