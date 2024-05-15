package com.example.ems.service.impl;

import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;
import com.example.ems.domain.Employee;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;

@Service
@Scope(value="prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
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
        BigDecimal bonus = BigDecimal.ZERO;
        long yearsWorked = YEARS.between(employee.getStartDate(), LocalDate.now());
        int size = employee.getProjects().size();
        if (yearsWorked >= 3 && size >= 3) {
            bonus = employee.getSalary().multiply(BigDecimal.valueOf(0.10));
        }
        if (yearsWorked >= 3 && size >= 4) {
            bonus = employee.getSalary().multiply((BigDecimal.valueOf(0.20)));
        }
        if (yearsWorked >= 5 && size >= 6) {
            bonus = employee.getSalary().multiply((BigDecimal.valueOf(0.30)));
        }
        return bonus;
    }
}
