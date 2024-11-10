package org.example.hr_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.hr_system.enums.Position;

@Data
@AllArgsConstructor
public class CountPositionWithAverageAgeDto {
    @Schema(description = "Employee's position" )
    @NotBlank()
    private Position position;

    @Schema(description = "Employees count")
    @NotNull()
    private Integer countOfEmployees;

    @Schema(description = "Employees average age" )
    @NotNull()
    private Double averageAge;
}
