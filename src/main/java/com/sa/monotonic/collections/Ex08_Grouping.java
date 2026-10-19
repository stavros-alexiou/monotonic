package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <h2>Exercise 08 - Grouping: computeIfAbsent and Collectors</h2>
 *
 * Uses {@link Employee#sample()}. Implement every method; most are a single Collector.
 *
 * <pre>
 * Q: Why does Collectors.toMap throw on duplicate keys, while groupingBy never does?
 * A:
 * </pre>
 */
public class Ex08_Grouping {

    static final String PREDICT_TOMAP_DUPLICATE_KEY_EXCEPTION = "???";

    /** dept -> employees, the "manual" way with computeIfAbsent (no streams). */
    static Map<String, List<Employee>> groupByDeptManual(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** dept -> employees, with keys SORTED (groupingBy with a map factory). */
    static TreeMap<String, List<Employee>> groupByDeptSorted(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    static Map<String, Long> countPerDept(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    static Map<String, Double> averageSalaryPerDept(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** dept -> highest earner (Optional, as maxBy returns it). */
    static Map<String, Optional<Employee>> topEarnerPerDept(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** Same, but unwrap the Optional - hint: collectingAndThen. Values are names. */
    static Map<String, String> topEarnerNamePerDept(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** true -> names with salary > threshold, false -> the rest (encounter order kept). */
    static Map<Boolean, List<String>> partitionBySalary(List<Employee> employees, double threshold) {
        // TODO: implement
        return Check.todo();
    }

    /** dept -> "Alice, Bob, ..." names sorted alphabetically, joined with ", ". */
    static Map<String, String> namesPerDeptJoined(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** dept -> total salary, using Collectors.toMap with a MERGE function and a TreeMap. */
    static TreeMap<String, Double> totalSalaryPerDept(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    private static Map<String, List<String>> namesOf(Map<String, List<Employee>> m) {
        Map<String, List<String>> r = new HashMap<>();
        m.forEach((k, v) -> r.put(k, v.stream().map(Employee::name).toList()));
        return r;
    }

    public static void main(String[] args) {
        Check.header("Ex08 - Grouping");
        List<Employee> staff = Employee.sample();

        Map<String, List<String>> expectedGroups = Map.of(
                "Engineering", List.of("Alice", "Bob", "Carol", "Ivan"),
                "Sales", List.of("Dan", "Eve", "Frank"),
                "HR", List.of("Grace", "Heidi"),
                "Marketing", List.of("Judy"));
        Check.equal("groupByDeptManual", expectedGroups, () -> namesOf(groupByDeptManual(staff)));
        Check.equal("groupByDeptSorted keys", List.of("Engineering", "HR", "Marketing", "Sales"),
                () -> new ArrayList<>(groupByDeptSorted(staff).keySet()));
        Check.equal("countPerDept", Map.of("Engineering", 4L, "Sales", 3L, "HR", 2L, "Marketing", 1L),
                () -> countPerDept(staff));
        Check.equal("averageSalaryPerDept (rounded)",
                Map.of("Engineering", 89750L, "Sales", 51167L, "HR", 56000L, "Marketing", 45000L),
                () -> {
                    Map<String, Long> rounded = new HashMap<>();
                    averageSalaryPerDept(staff).forEach((k, v) -> rounded.put(k, Math.round(v)));
                    return rounded;
                });
        Check.equal("topEarnerPerDept",
                Map.of("Engineering", "Ivan", "Sales", "Eve", "HR", "Heidi", "Marketing", "Judy"),
                () -> {
                    Map<String, String> r = new HashMap<>();
                    topEarnerPerDept(staff).forEach((k, v) -> r.put(k, v.map(Employee::name).orElse("?")));
                    return r;
                });
        Check.equal("topEarnerNamePerDept",
                Map.of("Engineering", "Ivan", "Sales", "Eve", "HR", "Heidi", "Marketing", "Judy"),
                () -> topEarnerNamePerDept(staff));
        Check.equal("partitionBySalary(50k)",
                Map.of(true, List.of("Alice", "Bob", "Carol", "Eve", "Grace", "Heidi", "Ivan"),
                        false, List.of("Dan", "Frank", "Judy")),
                () -> partitionBySalary(staff, 50_000));
        Check.equal("namesPerDeptJoined",
                Map.of("Engineering", "Alice, Bob, Carol, Ivan", "Sales", "Dan, Eve, Frank",
                        "HR", "Grace, Heidi", "Marketing", "Judy"),
                () -> namesPerDeptJoined(staff));
        Check.equal("totalSalaryPerDept", "{Engineering=359000.0, HR=112000.0, Marketing=45000.0, Sales=153500.0}",
                () -> totalSalaryPerDept(staff).toString());

        Check.predict("toMap with duplicate keys", PREDICT_TOMAP_DUPLICATE_KEY_EXCEPTION,
                () -> Check.thrownBy(() -> staff.stream().collect(Collectors.toMap(Employee::dept, Employee::name))));

        Check.summary();
    }
}
