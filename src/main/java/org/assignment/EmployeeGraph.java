package org.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assignment.Constants.MAX_SALARY_MULTIPLIER;
import static org.assignment.Constants.MIN_SALARY_MULTIPLIER;
import static org.assignment.Constants.MAX_MIDDLE_MANAGERS;


public class EmployeeGraph {
    private final Map<String, List<Employee>> graph;
    private final List<Metric<Double>> lessEarningEmployees;
    private final List<Metric<Double>> moreEarningEmployees;
    private final List<Metric<Integer>> longReportingEmployees;

    public EmployeeGraph(Path path) throws IOException {
        graph = new HashMap<>();
        lessEarningEmployees = new ArrayList<>();
        moreEarningEmployees = new ArrayList<>();
        longReportingEmployees = new ArrayList<>();
        List<String> rows = Files.readAllLines(path);
        Employee CEO = null;
        for(int i = 1; i<rows.size(); i++) {
            String row = rows.get(i);
            Employee employee = new Employee(row);
            if(employee.getManagerId()==null) {
                CEO = employee;
            }
            else {
                this.addSubordinate(employee.getManagerId(), employee);
            }
        }
        if(CEO!=null) {
            preprocessDfs(CEO, 1);
        }
    }

    private void preprocessDfs(Employee employee, int depth) {
        if(graph.get(employee.getId())==null) { // skip for employees with no subordinates
            return;
        }
        Double avgSubordinatesSalary = 0.0;
        for(Employee subordinate : graph.get(employee.getId())) {
            preprocessDfs(subordinate, depth+1);
            avgSubordinatesSalary += subordinate.getSalary();
        }
        avgSubordinatesSalary = avgSubordinatesSalary/graph.get(employee.getId()).size();

        if(employee.getSalary() < MIN_SALARY_MULTIPLIER*avgSubordinatesSalary) {
            lessEarningEmployees.add(new Metric<>(employee.getId(), avgSubordinatesSalary));
        }
        else if(employee.getSalary() > MAX_SALARY_MULTIPLIER*avgSubordinatesSalary) {
            moreEarningEmployees.add(new Metric<>(employee.getId(), avgSubordinatesSalary));
        }
        if(depth > MAX_MIDDLE_MANAGERS+2) {
            longReportingEmployees.add(new Metric<>(employee.getId(), depth-5));
        }
    }

    private void addSubordinate(String managerId, Employee subordinate) {
        List<Employee> subordinates = graph.getOrDefault(managerId, new ArrayList<>());
        subordinates.add(subordinate);
        graph.put(managerId, subordinates);
    }

    private <T> void printMetrics(List<Metric<T>> metrics) {
        for(Metric<T> metric: metrics) {
            System.out.println(metric);
        }
    }

    public List<Metric<Double>> getEmployeesEarningLess() {
        printMetrics(lessEarningEmployees);
        return lessEarningEmployees;
    }

    public List<Metric<Double>> getEmployeesEarningMore() {
        printMetrics(moreEarningEmployees);
        return moreEarningEmployees;
    }

    public List<Metric<Integer>> getEmployeesWithLongReporting() {
        printMetrics(longReportingEmployees);
        return longReportingEmployees;
    }
}
