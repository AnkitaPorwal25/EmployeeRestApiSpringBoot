package com.example.demo.service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    private final Map<Integer, Employee> employeeMap = new HashMap<>();
    private int currentId = 1;

    public Employee createEmployee(Employee employee) {
        employee.setId(currentId++);
        employeeMap.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    public Employee getEmployeeById(int id) {
        Employee emp = employeeMap.get(id);
        if (emp == null) throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        return emp;
    }

    public Employee updateEmployee(int id, Employee employee) {
        if (!employeeMap.containsKey(id)) {
            throw new EmployeeNotFoundException("Cannot update, employee not found with ID: " + id);
        }
        employee.setId(id);
        employeeMap.put(id, employee);
        return employee;
    }

    public void deleteEmployee(int id) {
        if (!employeeMap.containsKey(id)) {
            throw new EmployeeNotFoundException("Cannot delete, employee not found with ID: " + id);
        }
        employeeMap.remove(id);
    }

    public Employee partialUpdateEmployee(int id, Map<String, Object> updates) {
        Employee emp = getEmployeeById(id); // Reuse existing method for error handling

        if (updates.containsKey("name")) {
            emp.setName((String) updates.get("name"));
        }
        if (updates.containsKey("email")) {
            emp.setEmail((String) updates.get("email"));
        }

        // Save updated employee back
        employeeMap.put(id, emp);
        return emp;
    }

}
