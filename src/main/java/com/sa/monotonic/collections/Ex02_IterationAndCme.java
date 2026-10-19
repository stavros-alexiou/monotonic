package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * <h2>Exercise 02 - Iteration and ConcurrentModificationException</h2>
 *
 * <p>Explain in your own words:
 * <pre>
 * Q1: How does ArrayList detect the modification? (hint: modCount / expectedModCount)
 * A1:
 *
 * Q2: Why does removing the second-to-last element inside a for-each NOT throw?
 * A2:
 * </pre>
 */
public class Ex02_IterationAndCme {

    static final String PREDICT_EXCEPTION_FROM_BROKEN_LOOP = "???";

    /** GIVEN (broken on purpose): removes even numbers inside a for-each loop. Do not fix it. */
    static void brokenRemoveEvens(List<Integer> list) {
        for (Integer n : list) {
            if (n % 2 == 0) {
                list.remove(n);
            }
        }
    }

    /** Fix #1: use an explicit Iterator and iterator.remove(). */
    static List<Integer> removeEvensWithIterator(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    /** Fix #2: use Collection.removeIf. */
    static List<Integer> removeEvensWithRemoveIf(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    /** Fix #3: classic index loop running BACKWARDS with list.remove(int). */
    static List<Integer> removeEvensBackwards(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Return a list (with at least one even number) for which brokenRemoveEvens does NOT throw,
     * but still leaves an even number behind - a silent bug, worse than an exception!
     */
    static List<Integer> inputWhereBrokenLoopSilentlyFails() {
        // TODO: implement
        return Check.todo();
    }

    /** Remove all map entries whose value is even. Hint: entrySet() / values() are live views. */
    static Map<String, Integer> removeEntriesWithEvenValues(Map<String, Integer> map) {
        // TODO: implement
        return Check.todo();
    }

    /** Double every element in place using a ListIterator and set(). */
    static List<Integer> doubleAll(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    /** Insert a copy of every element right after it ([a, b] -> [a, a, b, b]) using ListIterator.add(). */
    static List<String> duplicateEach(List<String> list) {
        // TODO: implement
        return Check.todo();
    }

    private static List<Integer> oneToTwenty() {
        return new ArrayList<>(IntStream.rangeClosed(1, 20).boxed().toList());
    }

    public static void main(String[] args) {
        Check.header("Ex02 - Iteration and CME");

        Check.predict("exception from broken loop", PREDICT_EXCEPTION_FROM_BROKEN_LOOP,
                () -> Check.thrownBy(() -> brokenRemoveEvens(oneToTwenty())));

        List<Integer> odds = IntStream.rangeClosed(1, 20).filter(n -> n % 2 == 1).boxed().toList();
        Check.equal("removeEvensWithIterator", odds, () -> removeEvensWithIterator(oneToTwenty()));
        Check.equal("removeEvensWithRemoveIf", odds, () -> removeEvensWithRemoveIf(oneToTwenty()));
        Check.equal("removeEvensBackwards", odds, () -> removeEvensBackwards(oneToTwenty()));

        Check.isTrue("silent-failure input (no throw, even number left behind)", () -> {
            List<Integer> in = new ArrayList<>(inputWhereBrokenLoopSilentlyFails());
            brokenRemoveEvens(in); // must NOT throw
            return in.stream().anyMatch(n -> n % 2 == 0);
        });

        Check.equal("removeEntriesWithEvenValues", Map.of("a", 1, "c", 3),
                () -> removeEntriesWithEvenValues(new HashMap<>(Map.of("a", 1, "b", 2, "c", 3, "d", 4))));
        Check.equal("doubleAll", List.of(2, 4, 6), () -> doubleAll(new ArrayList<>(List.of(1, 2, 3))));
        Check.equal("duplicateEach", List.of("a", "a", "b", "b"),
                () -> duplicateEach(new ArrayList<>(List.of("a", "b"))));
        Check.throwsA("for-each removal really throws", ConcurrentModificationException.class,
                () -> brokenRemoveEvens(oneToTwenty()));

        Check.summary();
    }
}
