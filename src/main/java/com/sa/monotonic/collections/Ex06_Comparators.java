package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <h2>Exercise 06 - Comparator and Comparable</h2>
 *
 * Uses {@link Employee#sample()}.
 *
 * <pre>
 * Q1: Comparable vs Comparator - when do you use which?
 * A1:
 *
 * Q2: Why must compareTo be "consistent with equals" if you want to put objects into a TreeSet?
 * A2:
 * </pre>
 */
public class Ex06_Comparators {

    static final String PREDICT_EXCEPTION_SORTING_BY_NULLABLE_MANAGER = "???";

    /** dept ascending, then salary DESCENDING, then name ascending - ONE comparator chain. */
    static Comparator<Employee> byDeptThenSalaryDescThenName() {
        // TODO: implement
        return Check.todo();
    }

    /** manager ascending with NULL managers LAST, then name ascending. */
    static Comparator<Employee> byManagerNullsLastThenName() {
        // TODO: implement
        return Check.todo();
    }

    /** String length ascending, then alphabetical. */
    static Comparator<String> byLengthThenAlphabetical() {
        // TODO: implement
        return Check.todo();
    }

    /** Highest-paid employee. Use Collections.max or Stream.max with a Comparator. */
    static Employee highestPaid(List<Employee> employees) {
        // TODO: implement
        return Check.todo();
    }

    /** Semantic version: implement compareTo (major, then minor, then patch - numerically!). */
    record Version(int major, int minor, int patch) implements Comparable<Version> {

        static Version parse(String s) {
            String[] p = s.split("\\.");
            return new Version(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
        }

        @Override
        public int compareTo(Version o) {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public String toString() {
            return major + "." + minor + "." + patch;
        }
    }

    private static List<String> names(List<Employee> list) {
        return list.stream().map(Employee::name).toList();
    }

    private static List<Employee> sorted(Comparator<Employee> c) {
        List<Employee> list = new ArrayList<>(Employee.sample());
        list.sort(c);
        return list;
    }

    public static void main(String[] args) {
        Check.header("Ex06 - Comparators");

        Check.equal("byDeptThenSalaryDescThenName",
                List.of("Ivan", "Alice", "Bob", "Carol", "Heidi", "Grace", "Judy", "Eve", "Dan", "Frank"),
                () -> names(sorted(byDeptThenSalaryDescThenName())));
        Check.equal("byManagerNullsLastThenName",
                List.of("Bob", "Carol", "Dan", "Frank", "Grace", "Judy", "Alice", "Eve", "Heidi", "Ivan"),
                () -> names(sorted(byManagerNullsLastThenName())));
        Check.predict("sorting with comparing(Employee::manager)", PREDICT_EXCEPTION_SORTING_BY_NULLABLE_MANAGER,
                () -> Check.thrownBy(() -> sorted(Comparator.comparing(Employee::manager))));

        Check.equal("byLengthThenAlphabetical", List.of("ox", "ant", "bee", "cat", "zebra", "giraffe"),
                () -> {
                    List<String> l = new ArrayList<>(List.of("zebra", "cat", "ant", "giraffe", "ox", "bee"));
                    l.sort(byLengthThenAlphabetical());
                    return l;
                });
        Check.equal("highestPaid", "Ivan", () -> highestPaid(Employee.sample()).name());

        Check.equal("Version natural order (List.sort(null))", "[0.9.12, 1.2.0, 1.2.9, 1.2.10, 1.10.0]", () -> {
            List<Version> versions = new ArrayList<>(
                    List.of("1.10.0", "1.2.0", "1.2.10", "1.2.9", "0.9.12").stream().map(Version::parse).toList());
            versions.sort(null); // null = natural ordering
            return versions.toString();
        });
        Check.equal("Version reversed", "1.10.0", () -> {
            List<Version> versions = new ArrayList<>(
                    List.of("1.2.0", "1.10.0", "1.9.9").stream().map(Version::parse).toList());
            versions.sort(Comparator.reverseOrder());
            return versions.getFirst().toString();
        });

        Check.summary();
    }
}
