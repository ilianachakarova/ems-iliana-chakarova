package com.example.ems.service;

import com.example.ems.domain.Department;
import com.example.ems.domain.Employee;
import com.example.ems.domain.Project;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.impl.EmployeeServiceImpl;
import jdk.jfr.Description;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
        Project project1 = Project.builder().id(1L).name("project1").build();
        Project project2 = Project.builder().id(1L).name("project1").build();
        Project project3 = Project.builder().id(1L).name("project1").build();
        employee.setProjects(List.of(project1, project2, project3));

        BigDecimal bonus = employeeService.calculateBonus(employee);

        Assertions.assertEquals(bonus, employee.getSalary().multiply(BigDecimal.valueOf(0.10)));
    }


}
