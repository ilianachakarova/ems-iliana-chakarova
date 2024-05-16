package com.example.ems.domain;

import lombok.Getter;

@Getter
public enum Department {
    ENGINEERING("Engineering", 0.10),
    HUMAN_RESOURCES("Human Resources", 0.08),
    SALES("Sales", 0.15),
    MARKETING("Marketing", 012),
    IT("IT", 0.09);

    private final String name;
    private final double bonusPercentage;

    private Department(String name, double bonusPercentage) {
        this.name = name;
        this.bonusPercentage = bonusPercentage;
    }

}
