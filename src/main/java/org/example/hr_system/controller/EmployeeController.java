package org.example.hr_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.collections4.IterableUtils;
import org.example.hr_system.dto.CountByAgeGroupsDto;
import org.example.hr_system.dto.CountPositionWithAverageAgeDto;
import org.example.hr_system.dto.EmployeeDto;
import org.example.hr_system.mappers.EmployeeMapper;
import org.example.hr_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(
        EmployeeService employeeService,
        EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @Operation(summary = "Show all employees")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmployeeDto>> findAll() {
        List<EmployeeDto> employees = IterableUtils.toList(employeeService.findAll()).stream()
                .map(employeeMapper::toEmployeeDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Get a employee by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee Not Found",
                    content = @Content)
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDto> findOne(@PathVariable UUID id) {
        try {
            EmployeeDto employeeDto = employeeService
                    .findById(id)
                    .map(employeeMapper::toEmployeeDto)
                    .orElseThrow();

            return new ResponseEntity<>(
                    employeeDto,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        }
    }

    @Operation(summary = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Wrong input values",
                    content = @Content)
    })
    @PostMapping (value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeDto> create(EmployeeDto employee) {
        EmployeeDto employeeDto = employeeMapper.toEmployeeDto(employeeService
                 .create(employee));

        return new ResponseEntity<>(
                employeeDto, HttpStatus.CREATED
        );
    }

    @Operation(summary = "Delete employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Employee Not Found",
                    content = @Content)
    })
    @DeleteMapping (value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            employeeService.delete(id);

            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        }
    }

    @Operation(summary = "Get employees position count with average age")
    @GetMapping(value = "/position_count_with_average_age")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CountPositionWithAverageAgeDto>> getPositionCountWithAverageAge() {
        List<CountPositionWithAverageAgeDto> statistics = employeeService.countByPositionWithAverageAge();

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @Operation(summary = "Get employees count by age groups")
    @GetMapping(value = "/age_group_count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CountByAgeGroupsDto>> getEmployeesCountByAgeGroups() {
        List<CountByAgeGroupsDto> statistics = employeeService.countByAgeGroup();

        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
