package org.example.hr_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountByAgeGroupsDto {
    @Schema(description = "Age group" )
    @NotBlank()
    private String ageGroup;

    @Schema(description = "Employees count" )
    @NotNull()
    private Integer countOfEmployees;
}
