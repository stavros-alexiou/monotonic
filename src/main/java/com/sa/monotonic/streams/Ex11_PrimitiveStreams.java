package com.sa.monotonic.streams;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h2>Exercise 11 - IntStream / LongStream / DoubleStream</h2>
 *
 * Avoid boxing: stay on primitive streams until you really need objects (boxed / mapToObj).
 *
 * <pre>
 * Q1: Why do primitive streams exist? What does boxing cost?
 * A1:
 *
 * Q2: range vs rangeClosed? mapToObj vs boxed?
 * A2:
 * </pre>
 */
public class Ex11_PrimitiveStreams {

    static final String PREDICT_AVERAGE_TOSTRING = "???";
    static final Boolean PREDICT_INT_SUM_OF_SQUARES_IS_CORRECT = null;

    static int sumRange(int from, int toInclusive) {
        // TODO: implement
        return Check.todo();
    }

    /** Trial division with IntStream.rangeClosed(2, sqrt(n)).noneMatch(...). */
    static boolean isPrime(int n) {
        // TODO: implement
        return Check.todo();
    }

    static List<Integer> primesUpTo(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Count vowels (aeiou, case-insensitive) using chars(). */
    static long vowelCount(String s) {
        // TODO: implement
        return Check.todo();
    }

    static OptionalDouble average(int[] values) {
        // TODO: implement
        return Check.todo();
    }

    /** 1..n as "1", "2", "Fizz", "4", "Buzz", ... "FizzBuzz" using mapToObj. */
    static List<String> fizzBuzz(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Sum of the decimal digits: 9045 -> 18. Use String.valueOf(n).chars(). */
    static int digitSum(long n) {
        // TODO: implement
        return Check.todo();
    }

    /** 1^2 + 2^2 + ... + n^2 WITHOUT overflow (use mapToLong / LongStream). */
    static long sumOfSquares(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Index of the maximum value (first on ties), -1 for an empty array. Use IntStream.range over indexes. */
    static int indexOfMax(int[] values) {
        // TODO: implement
        return Check.todo();
    }

    /** Dot product of two equally sized double arrays. */
    static double dot(double[] a, double[] b) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex11 - Primitive streams");

        Check.equal("sumRange(1, 100)", 5050, () -> sumRange(1, 100));
        Check.equal("isPrime", List.of(false, false, true, true, false, true),
                () -> List.of(isPrime(0), isPrime(1), isPrime(2), isPrime(97), isPrime(91), isPrime(7919)));
        Check.equal("primesUpTo(30)", List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), () -> primesUpTo(30));
        Check.equal("vowelCount", 6L, () -> vowelCount("Lambda Expressions"));
        Check.equal("average", OptionalDouble.of(2.5), () -> average(new int[]{1, 2, 3, 4}));
        Check.equal("average(empty)", OptionalDouble.empty(), () -> average(new int[0]));
        Check.equal("fizzBuzz(15)", List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"),
                () -> fizzBuzz(15));
        Check.equal("digitSum", 18, () -> digitSum(9045));
        Check.equal("sumOfSquares(100_000)", 333_338_333_350_000L, () -> sumOfSquares(100_000));
        Check.equal("indexOfMax", 2, () -> indexOfMax(new int[]{3, 7, 9, 9, 1}));
        Check.equal("indexOfMax(empty)", -1, () -> indexOfMax(new int[0]));
        Check.equal("dot", 32.0, () -> dot(new double[]{1, 2, 3}, new double[]{4, 5, 6}));

        Check.predict("IntStream.rangeClosed(1,5).average().toString()", PREDICT_AVERAGE_TOSTRING,
                () -> IntStream.rangeClosed(1, 5).average().toString());
        Check.predict("int sum of squares 1..100_000 is correct", PREDICT_INT_SUM_OF_SQUARES_IS_CORRECT,
                () -> (long) IntStream.rangeClosed(1, 100_000).map(i -> i * i).sum() == 333_338_333_350_000L);

        Check.demo("boxed vs primitive timing (10M)", () -> {
            long t = System.nanoTime();
            long s1 = Stream.iterate(1, i -> i + 1).limit(10_000_000).mapToLong(Integer::longValue).sum();
            long boxed = System.nanoTime() - t;
            t = System.nanoTime();
            long s2 = IntStream.rangeClosed(1, 10_000_000).asLongStream().sum();
            long prim = System.nanoTime() - t;
            System.out.printf("    boxed %d ms, primitive %d ms (sums equal: %b)%n", boxed / 1_000_000, prim / 1_000_000, s1 == s2);
        });

        Check.summary();
    }
}
