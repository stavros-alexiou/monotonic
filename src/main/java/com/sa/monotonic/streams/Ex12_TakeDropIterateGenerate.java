package com.sa.monotonic.streams;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <h2>Exercise 12 - takeWhile / dropWhile / iterate / generate</h2>
 *
 * <pre>
 * Q1: takeWhile vs filter on [1, 2, 5, 1, 2] with x &lt; 3?
 * A1:
 *
 * Q2: Why are takeWhile/dropWhile only meaningful on ORDERED streams?
 * A2:
 * </pre>
 */
public class Ex12_TakeDropIterateGenerate {

    static final String PREDICT_TAKEWHILE = "???";
    static final String PREDICT_FILTER = "???";
    static final String PREDICT_DROPWHILE = "???";

    /** Elements from the start while they are positive. */
    static List<Integer> leadingPositives(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    /** Lines AFTER the first "---" separator line (the separator itself excluded). */
    static List<String> body(List<String> lines) {
        // TODO: implement
        return Check.todo();
    }

    /** Collatz sequence of n ending with 1: 6 -> [6, 3, 10, 5, 16, 8, 4, 2, 1]. 3-arg iterate + concat. */
    static List<Integer> collatz(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** First n Fibonacci numbers starting 0, 1, ... Iterate over pairs (long[] or a record). */
    static List<Long> fibonacci(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** 1, 2, 4, 8, ... strictly below limit. */
    static List<Long> powersOfTwoBelow(long limit) {
        // TODO: implement
        return Check.todo();
    }

    /** A Supplier producing "PREFIX-1", "PREFIX-2", ... (stateful; thread-safe counter). */
    static Supplier<String> idGenerator(String prefix) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Running balance after each transaction, starting at 0: [10, -3, 5] -> [10, 7, 12].
     * Streams dislike state. Use a one-element array as accumulator inside map(), then answer:
     * why would this break with .parallel()? (Bonus: rewrite with a plain loop or Arrays.parallelPrefix.)
     */
    static List<Integer> runningTotals(List<Integer> transactions) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex12 - takeWhile / dropWhile / iterate / generate");

        List<Integer> data = List.of(1, 2, 5, 1, 2);
        Check.predict("takeWhile(x < 3)", PREDICT_TAKEWHILE, () -> data.stream().takeWhile(x -> x < 3).toList().toString());
        Check.predict("filter(x < 3)", PREDICT_FILTER, () -> data.stream().filter(x -> x < 3).toList().toString());
        Check.predict("dropWhile(x < 3)", PREDICT_DROPWHILE, () -> data.stream().dropWhile(x -> x < 3).toList().toString());

        Check.equal("leadingPositives", List.of(3, 1), () -> leadingPositives(List.of(3, 1, -2, 4)));
        Check.equal("body", List.of("hello", "world"), () -> body(List.of("Subject: hi", "From: me", "---", "hello", "world")));
        Check.equal("body without separator", List.of(), () -> body(List.of("a", "b")));
        Check.equal("collatz(6)", List.of(6, 3, 10, 5, 16, 8, 4, 2, 1), () -> collatz(6));
        Check.equal("collatz(1)", List.of(1), () -> collatz(1));
        Check.equal("fibonacci(10)", List.of(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L), () -> fibonacci(10));
        Check.equal("fibonacci(90) last", 1_779_979_416_004_714_189L, () -> fibonacci(90).getLast());
        Check.equal("powersOfTwoBelow(100)", List.of(1L, 2L, 4L, 8L, 16L, 32L, 64L), () -> powersOfTwoBelow(100));
        Check.equal("idGenerator with Stream.generate", List.of("ORD-1", "ORD-2", "ORD-3"),
                () -> Stream.generate(idGenerator("ORD")).limit(3).toList());
        Check.equal("idGenerators are independent", List.of("A-1", "B-1", "A-2"), () -> {
            Supplier<String> a = idGenerator("A"), b = idGenerator("B");
            return List.of(a.get(), b.get(), a.get());
        });
        Check.equal("runningTotals", List.of(10, 7, 12), () -> runningTotals(List.of(10, -3, 5)));

        Check.summary();
    }
}
