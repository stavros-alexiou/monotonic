package com.sa.monotonic.collections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * <h2>Exercise 17 - Generics and wildcards (PECS)</h2>
 *
 * The signatures are given - implement the bodies.
 *
 * <pre>
 * Q1: PECS - "Producer Extends, Consumer Super". Map each parameter below to P or C.
 * A1:
 *
 * Q2: max(List&lt;LocalDate&gt;) only compiles thanks to "Comparable&lt;? super T&gt;". Why?
 *     (hint: LocalDate implements Comparable&lt;ChronoLocalDate&gt;, not Comparable&lt;LocalDate&gt;)
 * A2:
 *
 * Q3: Compile puzzles - uncomment each line in compilePuzzles() one at a time. Compiles? Why?
 * A3:
 * </pre>
 */
public class Ex17_GenericsAndWildcards {

    /** Largest element; throw NoSuchElementException if empty. Do not use Collections.max. */
    static <T extends Comparable<? super T>> T max(Collection<? extends T> c) {
        // TODO: implement
        return Check.todo();
    }

    /** Append every element of src to the end of dest. */
    static <T> void copy(List<? super T> dest, List<? extends T> src) {
        // TODO: implement
        Check.todo();
    }

    /** Split into consecutive chunks of {@code size} (last may be shorter). Chunks must be subList VIEWS. */
    static <T> List<List<T>> partition(List<T> list, int size) {
        // TODO: implement
        return Check.todo();
    }

    static double sum(Collection<? extends Number> numbers) {
        // TODO: implement
        return Check.todo();
    }

    /** Add 1..n to dest - must accept List<Integer>, List<Number> and List<Object>. */
    static void fillWithIntegers(List<? super Integer> dest, int n) {
        // TODO: implement
        Check.todo();
    }

    /** Key whose value is the largest; empty for an empty map. */
    static <K, V extends Comparable<? super V>> Optional<K> keyWithMaxValue(Map<K, V> map) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Swap two elements of a List<?>. You CANNOT call list.set(i, list.get(j)) on a List<?> directly.
     * Use the "wildcard capture" trick: delegate to a private generic helper method.
     */
    static void swapAny(List<?> list, int i, int j) {
        // TODO: implement
        Check.todo();
    }

    // your private generic helper goes here

    @SuppressWarnings("unused")
    static void compilePuzzles() {
        List<Integer> ints = new ArrayList<>(List.of(1, 2));
        List<? extends Number> producer = ints;
        List<? super Integer> consumer = new ArrayList<Number>();
        // 1) producer.add(3);
        // 2) Number n = producer.get(0);
        // 3) consumer.add(3);
        // 4) Integer i = consumer.get(0);
        // 5) List<Number> nums = ints;
        // 6) Object o = consumer.get(0);
    }

    public static void main(String[] args) {
        Check.header("Ex17 - Generics and wildcards");

        Check.equal("max ints", 9, () -> max(List.of(3, 9, 2)));
        Check.equal("max LocalDate", LocalDate.of(2026, 10, 19),
                () -> max(List.of(LocalDate.of(2020, 1, 1), LocalDate.of(2026, 10, 19), LocalDate.of(2025, 5, 5))));
        Check.throwsA("max empty", NoSuchElementException.class, () -> max(List.<Integer>of()));

        Check.equal("copy Integers into List<Object>", List.of("x", 1, 2), () -> {
            List<Object> dest = new ArrayList<>(List.of("x"));
            Ex17_GenericsAndWildcards.<Integer>copy(dest, List.of(1, 2));
            return dest;
        });

        Check.equal("partition", List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7)),
                () -> partition(List.of(1, 2, 3, 4, 5, 6, 7), 3));
        Check.equal("partition returns views", List.of(1, 2, 3, 40, 5), () -> {
            List<Integer> source = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            partition(source, 3).get(1).set(0, 40);
            return source;
        });

        Check.equal("sum mixed numbers", 6.5, () -> sum(List.of(1, 2.5, 3L)));

        Check.equal("fillWithIntegers List<Number>", List.of(1, 2, 3), () -> {
            List<Number> nums = new ArrayList<>();
            fillWithIntegers(nums, 3);
            return nums;
        });
        Check.equal("fillWithIntegers List<Object>", List.of("start", 1, 2), () -> {
            List<Object> objs = new ArrayList<>(List.of("start"));
            fillWithIntegers(objs, 2);
            return objs;
        });

        Check.equal("keyWithMaxValue", Optional.of("bob"), () -> keyWithMaxValue(Map.of("ann", 3, "bob", 7, "cid", 5)));
        Check.equal("keyWithMaxValue empty", Optional.empty(), () -> keyWithMaxValue(Map.<String, Integer>of()));

        Check.equal("swapAny", List.of("c", "b", "a"), () -> {
            List<String> l = new ArrayList<>(List.of("a", "b", "c"));
            swapAny(l, 0, 2);
            return l;
        });

        Check.summary();
    }
}
