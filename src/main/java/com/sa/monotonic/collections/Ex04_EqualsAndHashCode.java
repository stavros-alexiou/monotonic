package com.sa.monotonic.collections;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <h2>Exercise 04 - equals / hashCode and hash-based collections</h2>
 *
 * <pre>
 * Q1: State the equals/hashCode contract in one sentence.
 * A1:
 *
 * Q2: Why is the mutated Person still IN the set (iteration finds it) but contains() says false?
 * A2:
 *
 * Q3: Why do records make good map keys?
 * A3:
 * </pre>
 */
public class Ex04_EqualsAndHashCode {

    // ---- predictions (Integer / Boolean) -------------------------------------------------
    static final Integer PREDICT_SIZE_WITHOUT_EQUALS = null;
    static final Integer PREDICT_SIZE_WITH_EQUALS = null;
    static final Boolean PREDICT_CONTAINS_AFTER_MUTATION = null;
    static final Boolean PREDICT_REMOVE_AFTER_MUTATION_SUCCEEDS = null;
    static final Boolean PREDICT_ITERATION_STILL_FINDS_IT = null;
    static final Boolean PREDICT_BIGDECIMAL_1_0_EQUALS_1_00 = null;
    static final Integer PREDICT_HASHSET_SIZE_OF_1_0_AND_1_00 = null;
    static final Integer PREDICT_TREESET_SIZE_OF_1_0_AND_1_00 = null;

    /** GIVEN: no equals/hashCode on purpose. */
    static class PersonNoEquals {
        String name;
        int age;

        PersonNoEquals(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    /** TODO: implement equals and hashCode based on name and age. */
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public int hashCode() {
            // TODO: implement
            return Check.todo();
        }
    }

    record PersonRecord(String name, int age) {
    }

    /**
     * new BigDecimal("1.0").equals(new BigDecimal("1.00")) is FALSE (scale differs), so a record
     * holding a BigDecimal inherits that trap. Fix it in the compact constructor so that
     * Money("1.0","EUR").equals(Money("1.00","EUR")) is true. Use 2 decimals, HALF_EVEN.
     */
    record Money(BigDecimal amount, String currency) {
        Money {
            // TODO: implement
        }
    }

    public static void main(String[] args) {
        Check.header("Ex04 - equals / hashCode");

        Check.predict("HashSet size without equals", PREDICT_SIZE_WITHOUT_EQUALS, () -> {
            Set<PersonNoEquals> set = new HashSet<>();
            set.add(new PersonNoEquals("Ann", 30));
            set.add(new PersonNoEquals("Ann", 30));
            return set.size();
        });

        Check.predict("HashSet size with equals", PREDICT_SIZE_WITH_EQUALS, () -> {
            Set<Person> set = new HashSet<>();
            set.add(new Person("Ann", 30));
            set.add(new Person("Ann", 30));
            return set.size();
        });

        Check.equal("Person equals is reflexive/symmetric", true, () -> {
            Person a = new Person("Ann", 30), b = new Person("Ann", 30);
            return a.equals(a) && a.equals(b) && b.equals(a) && !a.equals(null) && !a.equals("Ann");
        });
        Check.equal("equal Persons have equal hashCodes", true,
                () -> new Person("Ann", 30).hashCode() == new Person("Ann", 30).hashCode());

        Check.predict("contains() after mutating a key", PREDICT_CONTAINS_AFTER_MUTATION, () -> {
            Set<Person> set = new HashSet<>();
            Person p = new Person("Ann", 30);
            set.add(p);
            p.age = 31;
            return set.contains(p);
        });
        Check.predict("remove() after mutating a key", PREDICT_REMOVE_AFTER_MUTATION_SUCCEEDS, () -> {
            Set<Person> set = new HashSet<>();
            Person p = new Person("Ann", 30);
            set.add(p);
            p.age = 31;
            return set.remove(p);
        });
        Check.predict("iteration still finds the mutated key", PREDICT_ITERATION_STILL_FINDS_IT, () -> {
            Set<Person> set = new HashSet<>();
            Person p = new Person("Ann", 30);
            set.add(p);
            p.age = 31;
            return set.stream().anyMatch(x -> x == p);
        });

        Check.equal("records work as map keys out of the box", "555-1234", () -> {
            Map<PersonRecord, String> phones = new HashMap<>();
            phones.put(new PersonRecord("Ann", 30), "555-1234");
            return phones.get(new PersonRecord("Ann", 30));
        });

        Check.predict("BigDecimal 1.0 equals 1.00", PREDICT_BIGDECIMAL_1_0_EQUALS_1_00,
                () -> new BigDecimal("1.0").equals(new BigDecimal("1.00")));
        Check.predict("HashSet size of {1.0, 1.00}", PREDICT_HASHSET_SIZE_OF_1_0_AND_1_00,
                () -> new HashSet<>(Set.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size());
        Check.predict("TreeSet size of {1.0, 1.00}", PREDICT_TREESET_SIZE_OF_1_0_AND_1_00,
                () -> new TreeSet<>(Set.of(new BigDecimal("1.0"), new BigDecimal("1.00"))).size());

        Check.equal("Money normalises scale", new Money(new BigDecimal("1.00"), "EUR"),
                () -> new Money(new BigDecimal("1.0"), "EUR"));
        Check.equal("Money dedups in a HashSet", 1, () -> {
            Set<Money> set = new HashSet<>();
            set.add(new Money(new BigDecimal("2.5"), "EUR"));
            set.add(new Money(new BigDecimal("2.500"), "EUR"));
            return set.size();
        });

        Check.summary();
    }
}
