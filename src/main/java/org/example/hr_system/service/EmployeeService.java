package org.example.hr_system.service;

import org.example.hr_system.dto.CountByAgeGroupsDto;
import org.example.hr_system.dto.CountPositionWithAverageAgeDto;
import org.example.hr_system.dto.EmployeeDto;
import org.example.hr_system.domain.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    Iterable<Employee> findAll();
    Optional<Employee> findById(UUID id);
    Employee create(EmployeeDto employee);
    void delete(UUID id);
    List<CountPositionWithAverageAgeDto> countByPositionWithAverageAge();
    List<CountByAgeGroupsDto> countByAgeGroup();

}
