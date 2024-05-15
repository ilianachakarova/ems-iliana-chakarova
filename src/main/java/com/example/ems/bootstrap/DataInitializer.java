package com.example.ems.bootstrap;

import com.example.ems.domain.Department;
import com.example.ems.domain.Employee;
import com.example.ems.domain.Project;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ems.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Log4j2
public class DataInitializer implements CommandLineRunner {

    private final EmployeeService employeeService;

    public DataInitializer (EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @Override
    public void run(String... args) throws Exception {
        Employee employee = Employee.builder()
                .name("Ivan Pertov")
                .department(Department.IT)
                .salary(BigDecimal.valueOf(4500))
                .startDate(LocalDate.of(2022,3,3))
                .projects(List.of(Project.builder().name("Project Main").build()))
                .build();


        Employee persistedEmployee = employeeService.addEmployee(employee);

        log.info("1. Saved employee: {}", persistedEmployee.toString());
        log.info(employeeService.fetchEmployeeByID(persistedEmployee.getId()));

        Employee retreivedEmployee = employeeService.fetchEmployeeByID(persistedEmployee.getId());
        log.info("Retrieved employee: {}", retreivedEmployee.toString());

        persistedEmployee.setDepartment(Department.HUMAN_RESOURCES);
        employeeService.updateEmployee(persistedEmployee);
        log.info("Updated employee: {}", persistedEmployee.toString());

        employeeService.calculateBonus(persistedEmployee);
        log.info("Updated employee: {}", persistedEmployee.toString());

        long id = persistedEmployee.getId();
        employeeService.deleteEmployee(persistedEmployee.getId());
        log.info("1. Deleted employee: {}", id);

    }
}
