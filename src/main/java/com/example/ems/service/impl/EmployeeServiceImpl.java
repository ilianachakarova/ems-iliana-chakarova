package com.example.ems.service.impl;

import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;
import com.example.ems.domain.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Scope(value="prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        Employee existingByNameAndDepartment = employeeRepository.findByNameAndDepartment(employee.getName(), employee.getDepartment());
        if(existingByNameAndDepartment != null){
            throw new RuntimeException("Employee Already Exists");
        }
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee fetchEmployeeByID(long id) {
       return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public BigDecimal calculateBonus(Employee employee) {
        double percentage = employee.getDepartment().getBonusPercentage();
        return employee.getSalary().multiply(new BigDecimal(percentage)).setScale(2, RoundingMode.CEILING);
    }
}
