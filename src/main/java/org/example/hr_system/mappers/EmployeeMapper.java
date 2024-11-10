package org.example.hr_system.mappers;

import org.example.hr_system.domain.Employee;
import org.example.hr_system.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toEmployeeDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEmployee(EmployeeDto employeeDto);
}
