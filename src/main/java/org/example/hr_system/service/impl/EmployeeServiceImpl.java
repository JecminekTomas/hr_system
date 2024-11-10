package org.example.hr_system.service.impl;

import org.example.hr_system.dto.CountByAgeGroupsDto;
import org.example.hr_system.dto.CountPositionWithAverageAgeDto;
import org.example.hr_system.dto.EmployeeDto;
import org.example.hr_system.domain.Employee;
import org.example.hr_system.enums.Position;
import org.example.hr_system.mappers.EmployeeMapper;
import org.example.hr_system.repository.EmployeeRepository;
import org.example.hr_system.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee create(EmployeeDto employeeDto) {
        return employeeRepository.save(employeeMapper.toEmployee(employeeDto));
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<CountPositionWithAverageAgeDto> countByPositionWithAverageAge() {
        List<Map<String, Object>> employeesRaw = employeeRepository.countPositionWithAverageAge();
        ArrayList<CountPositionWithAverageAgeDto> employees = new ArrayList<>();
        for (var employee : employeesRaw) {
           employees.add(new CountPositionWithAverageAgeDto(
                    Position.valueOf(employee.get("position").toString()),
                    Integer.parseInt(employee.get("count").toString()),
                   Double.parseDouble(employee.get("average").toString())
           ));
        }

        return employees.stream().toList();
    }

    @Override
    public List<CountByAgeGroupsDto> countByAgeGroup() {
        List<Map<String, Object>> ageGroupsRaw = employeeRepository.countByAgeGroups();
        ArrayList<CountByAgeGroupsDto> ageGroups = new ArrayList<>();
        for (var ageGroup : ageGroupsRaw) {
            ageGroups.add(new CountByAgeGroupsDto(
                    ageGroup.get("ageGroup").toString(),
                    Integer.parseInt(ageGroup.get("count").toString())
            ));
        }
        return ageGroups;
    }
}
