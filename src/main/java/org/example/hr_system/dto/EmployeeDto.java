package org.example.hr_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.hr_system.enums.Position;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
public class EmployeeDto {
    @Schema(description = "Employee's id", nullable = true, hidden = true)
    private UUID id;

    @Schema(description = "Employee's name", minLength = 4, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 4, max = 100)
    private String name;

    @Schema(description = "Employee's age", maximum = "99", minimum = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 15, max = 99)
    private Integer age;

    @Schema(description = "Employee's position" , requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank()
    private Position position;
}

