package com.sa.monotonic.collections;

import java.time.LocalDate;
import java.util.List;

/** Shared sample data for Ex06 and Ex08. Do not change it - the checks depend on it. */
public record Employee(String name, String dept, double salary, LocalDate hired, String manager) {

    public static List<Employee> sample() {
        return List.of(
                new Employee("Alice", "Engineering", 95_000, LocalDate.of(2018, 3, 1), null),
                new Employee("Bob", "Engineering", 72_000, LocalDate.of(2020, 6, 15), "Alice"),
                new Employee("Carol", "Engineering", 72_000, LocalDate.of(2021, 1, 10), "Alice"),
                new Employee("Dan", "Sales", 48_000, LocalDate.of(2019, 9, 23), "Eve"),
                new Employee("Eve", "Sales", 66_000, LocalDate.of(2016, 2, 2), null),
                new Employee("Frank", "Sales", 39_500, LocalDate.of(2023, 4, 4), "Eve"),
                new Employee("Grace", "HR", 51_000, LocalDate.of(2017, 11, 30), "Heidi"),
                new Employee("Heidi", "HR", 61_000, LocalDate.of(2015, 7, 7), null),
                new Employee("Ivan", "Engineering", 120_000, LocalDate.of(2014, 5, 5), null),
                new Employee("Judy", "Marketing", 45_000, LocalDate.of(2022, 8, 8), "Heidi")
        );
    }
}
