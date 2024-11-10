package org.example.hr_system;

import org.example.hr_system.controller.EmployeeController;
import org.example.hr_system.domain.Employee;
import org.example.hr_system.dto.EmployeeDto;
import org.example.hr_system.mappers.EmployeeMapper;
import org.example.hr_system.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
class EmployeeControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeMapper employeeMapper;

    @Test
    public void findAllShouldReturnEmployees()
            throws Exception {

        Employee employee = new Employee();
        List<Employee> employees = List.of(employee);

        given(employeeService.findAll()).willReturn(employees);

        mvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findAllShouldReturnEmptyList()
            throws Exception {

        mvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void findOneShouldReturnParticularEmployee()
            throws Exception {

        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee();

        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());

        mvc.perform(get("/employees/"+employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findOneShouldReturnNotFound()
            throws Exception {

        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());

        mvc.perform(get("/employees/"+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnParticularEmployee()
            throws Exception {

        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();

        given(employeeService.create(employeeDto)).willReturn(employee);
        when(employeeMapper.toEmployee(any(EmployeeDto.class))).thenReturn(employee);

        mvc.perform(post("/employees", employeeDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}