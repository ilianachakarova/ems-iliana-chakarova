package com.example.ems.service;

import com.example.ems.domain.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(long id);

    List<Employee>fetchAllEmployees();

    Employee fetchEmployeeByID(long id);

    BigDecimal calculateBonus(Employee employee);
}
