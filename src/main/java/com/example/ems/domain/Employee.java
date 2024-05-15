package com.example.ems.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Nonnull
    private String name;

    @Nonnull
    private Department department;

    @Nonnull
    private BigDecimal salary;

    @Nonnull
    private LocalDate startDate;

    @ManyToMany(cascade = {PERSIST, REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> projects;

    @Override
    public String toString() {
        return String.format("Employee: id - " + getId()
        + ", name - " + getName() + ", department - " + getDepartment().name()
                + ", salary " + getSalary() + ", startDate - " + getStartDate()
        );
    }
}
