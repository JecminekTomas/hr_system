package org.example.hr_system.repository;

import org.example.hr_system.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
    @Query(value = "SELECT employees.position, count(employees.position) as count, avg(age) as average FROM employees GROUP BY position", nativeQuery = true)
    List<Map<String, Object>> countPositionWithAverageAge();

    @Query(value = """
        SELECT ageGroup, count FROM (
            SELECT '<20' AS ageGroup, COUNT(*) AS count, 1 AS ordering FROM employees WHERE age > 0 AND age < 20
            UNION  
            SELECT '20-40' AS ageGroup, COUNT(*) AS count, 2 AS ordering FROM employees WHERE age >= 20 AND age < 40
            UNION
            SELECT '40-60' AS ageGroup, COUNT(*) AS count, 3 AS ordering FROM employees WHERE age >= 40 AND age < 60
            UNION 
            SELECT '60+' AS ageGroup, COUNT(*) AS count, 4 AS ordering FROM employees WHERE age >= 60 
        ) AS result 
        ORDER BY ordering
        """, nativeQuery = true)
    List<Map<String, Object>> countByAgeGroups();
}