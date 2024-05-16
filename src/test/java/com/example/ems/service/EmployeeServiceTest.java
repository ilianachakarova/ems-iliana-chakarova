package com.example.ems.service;

import com.example.ems.domain.Department;
import com.example.ems.domain.Employee;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.impl.EmployeeServiceImpl;
import jdk.jfr.Description;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
@EntityScan(basePackageClasses = Employee.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .name("Test").salary(BigDecimal.valueOf(5000)).startDate(LocalDate.of(2020,6,9))
                .department(Department.SALES)
                .projects(List.of())
                .build();
    }

    @Test
    @Description("Adding an employee works correctly")
    void addEmployeeTest() {
        int employeeCountBefore = employeeRepository.findAll().size();

        employeeService.addEmployee(employee);

        Assertions.assertEquals(employeeCountBefore +1, employeeRepository.findAll().size());
    }

    @Test
    @Description("Should throw an exception if an employee with the same name and department exists already.")
    void addEmployeeTest2() {
        employeeService.addEmployee(employee);
        Employee employeeNew = Employee.builder().name("Test").salary(BigDecimal.valueOf(5000)).startDate(LocalDate.of(2020,6,9))
                .department(Department.SALES)
                .projects(List.of())
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> employeeService.addEmployee(employeeNew));
    }

    @Test
    @Description("Updating an employee works correctly")
    void updateEmployeeTest() {
        employee.setName("UPDATED");

        employeeService.updateEmployee(employee);

        Assertions.assertEquals("UPDATED", employee.getName());
    }

    @Test
    @Description("Fetching an employee works correctly")
    void fetchEmployeeTest() {
       employeeRepository.save(employee);

        Employee fetched = employeeService.fetchEmployeeByID(employee.getId());

        Assertions.assertEquals(fetched, employee);
    }

    @Test
    @Description("Fetching an employee works correctly")
    void calculateBonusEmployeeTest() {

        BigDecimal bonus = employeeService.calculateBonus(employee);

        Assertions.assertEquals(bonus, employee.getSalary().multiply(BigDecimal.valueOf(0.15)));
    }


}
