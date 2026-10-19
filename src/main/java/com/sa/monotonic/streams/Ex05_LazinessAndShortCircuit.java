package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h2>Exercise 05 - Laziness, vertical processing, short-circuiting</h2>
 *
 * Mostly predictions this time: write the exact trace lists BEFORE running.
 *
 * <pre>
 * Q1: Streams process elements "vertically" (one element through the whole pipeline). Why?
 * A1:
 *
 * Q2: Why does sorted() change the trace completely?
 * A2:
 *
 * Q3: Since Java 9, Stream.of(1,2,3).map(...).count() may NOT call map at all. Why is that allowed?
 * A3:
 * </pre>
 */
public class Ex05_LazinessAndShortCircuit {

    static final String PREDICT_TRACE_FILTER_MAP_FINDFIRST = "???";
    static final String PREDICT_TRACE_WITH_SORTED = "???";
    static final Integer PREDICT_MAP_CALLS_BEFORE_COUNT = null;
    static final Integer PREDICT_MAP_CALLS_WITH_FILTER_BEFORE_COUNT = null;
    static final Integer PREDICT_ANYMATCH_EVALUATIONS = null;

    /** Smallest perfect square strictly greater than threshold. Use an INFINITE IntStream.iterate. */
    static int firstSquareAbove(int threshold) {
        // TODO: implement
        return Check.todo();
    }

    /** First n primes using an infinite stream + limit. */
    static List<Integer> firstNPrimes(int n) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Lazy logging: return the message only if enabled, otherwise null - and when disabled the
     * supplier must NOT be called (that's the whole point of passing a Supplier).
     */
    static String log(boolean enabled, Supplier<String> message) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Return how many elements the predicate x -> x > limit actually inspects when you call
     * anyMatch on the list. Count with a counter inside the predicate.
     */
    static int inspectedByAnyMatch(List<Integer> list, int limit) {
        // TODO: implement
        return Check.todo();
    }

    static int calls;

    public static void main(String[] args) {
        Check.header("Ex05 - Laziness and short-circuiting");

        Check.predict("trace filter/map/findFirst", PREDICT_TRACE_FILTER_MAP_FINDFIRST, () -> {
            List<String> trace = new ArrayList<>();
            Stream.of("a", "bb", "ccc", "dd")
                    .filter(s -> {
                        trace.add("filter " + s);
                        return s.length() > 1;
                    })
                    .map(s -> {
                        trace.add("map " + s);
                        return s.toUpperCase();
                    })
                    .findFirst();
            return trace.toString();
        });
        Check.predict("trace with sorted()", PREDICT_TRACE_WITH_SORTED, () -> {
            List<String> trace = new ArrayList<>();
            Stream.of("c", "a", "b")
                    .peek(s -> trace.add("p1 " + s))
                    .sorted()
                    .peek(s -> trace.add("p2 " + s))
                    .findFirst();
            return trace.toString();
        });
        Check.predict("map calls before count()", PREDICT_MAP_CALLS_BEFORE_COUNT, () -> {
            calls = 0;
            Stream.of(1, 2, 3).map(x -> {
                calls++;
                return x;
            }).count();
            return calls;
        });
        Check.predict("map calls with a filter before count()", PREDICT_MAP_CALLS_WITH_FILTER_BEFORE_COUNT, () -> {
            calls = 0;
            Stream.of(1, 2, 3).filter(x -> true).map(x -> {
                calls++;
                return x;
            }).count();
            return calls;
        });
        Check.predict("evaluations of anyMatch(x < 0) on [1, -2, 3, -4]", PREDICT_ANYMATCH_EVALUATIONS, () -> {
            calls = 0;
            Stream.of(1, -2, 3, -4).anyMatch(x -> {
                calls++;
                return x < 0;
            });
            return calls;
        });

        Check.equal("firstSquareAbove(50)", 64, () -> firstSquareAbove(50));
        Check.equal("firstSquareAbove(0)", 1, () -> firstSquareAbove(0));
        Check.equal("firstNPrimes(8)", List.of(2, 3, 5, 7, 11, 13, 17, 19), () -> firstNPrimes(8));
        Check.equal("log enabled", "x=42", () -> log(true, () -> "x=" + 42));
        Check.equal("log disabled never calls the supplier", 0, () -> {
            calls = 0;
            log(false, () -> {
                calls++;
                return "expensive";
            });
            return calls;
        });
        Check.equal("inspectedByAnyMatch", 3, () -> inspectedByAnyMatch(List.of(1, 5, 20, 2, 30), 10));
        Check.equal("inspectedByAnyMatch no match -> all", 4, () -> inspectedByAnyMatch(List.of(1, 2, 3, 4), 10));

        Check.summary();
    }
}
