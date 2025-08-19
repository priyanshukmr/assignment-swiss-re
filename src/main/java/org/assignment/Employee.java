package org.assignment;

import java.util.Arrays;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private Integer salary;
    private String managerId;

    Employee(String csvRow) {
        String[] fields = Arrays.stream(csvRow.split(",")).map(String::trim).toArray(String[]::new);
        if(fields.length < 4) {
            throw new IllegalArgumentException("Invalid CSV Row");
        }
        this.id = fields[0];
        this.firstName = fields[1];
        this.lastName = fields[2];
        this.salary = Integer.parseInt(fields[3]);
        if(fields.length>=5) {
            this.managerId = fields[4];
        }
    }

    public String getId() {
        return this.id;
    }

    public String getManagerId() {
        return this.managerId;
    }

    public Integer getSalary() {
        return this.salary;
    }
}
