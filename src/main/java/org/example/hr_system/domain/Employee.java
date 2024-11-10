package org.example.hr_system.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.hr_system.enums.Position;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @NotNull()
    private UUID id;

    @Column(name = "age", nullable = false)
    @Range(min = 15, max = 99)
    @NotNull(message = "Age is mandatory")
    private Integer age;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;
}
