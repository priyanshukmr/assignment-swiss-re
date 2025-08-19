package Test;

import org.assignment.EmployeeGraph;
import org.assignment.Metric;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EmployeeGraphTest {

    EmployeeGraph employeeGraph;

    @BeforeEach
    public void setup() throws IOException {
        employeeGraph = new EmployeeGraph(Path.of("src/main/resources/employees.csv"));;
    }

    @Test
    public void testEmployeesEarningLess() {
        List<Metric<Double>> lessEarningEmployees = employeeGraph.getEmployeesEarningLess();
        Assertions.assertNotNull(lessEarningEmployees);
        Assertions.assertEquals(1, lessEarningEmployees.size());
        Assertions.assertEquals("124", lessEarningEmployees.getFirst().getId());
    }

    @Test
    public void testEmployeesEarningMore() {
        List<Metric<Double>> moreEarningEmployees = employeeGraph.getEmployeesEarningMore();
        Assertions.assertNotNull(moreEarningEmployees);
        Assertions.assertEquals(0, moreEarningEmployees.size());
    }

    @Test
    public void testEmployeesWithLongReportingChain() {
        List<Metric<Integer>> longReportingEmployees = employeeGraph.getEmployeesWithLongReporting();
        Assertions.assertNotNull(longReportingEmployees);
        Assertions.assertEquals(0, longReportingEmployees.size());
    }

    @Test
    public void testForEmptyCSV() throws IOException {
        employeeGraph = new EmployeeGraph(Path.of("src/main/resources/empty.csv"));
        List<Metric<Double>> lessEarningEmployees = employeeGraph.getEmployeesEarningLess();
        Assertions.assertNotNull(lessEarningEmployees);
        Assertions.assertEquals(0, lessEarningEmployees.size());
        List<Metric<Double>> moreEarningEmployees = employeeGraph.getEmployeesEarningMore();
        Assertions.assertNotNull(moreEarningEmployees);
        Assertions.assertEquals(0, moreEarningEmployees.size());
        List<Metric<Double>> longReportingEmployees = employeeGraph.getEmployeesEarningMore();
        Assertions.assertNotNull(longReportingEmployees);
        Assertions.assertEquals(0, longReportingEmployees.size());
    }

    @Test
    public void testInvalidCSV() throws IOException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmployeeGraph(Path.of("src/main/resources/invalid.csv")));
    }

}
