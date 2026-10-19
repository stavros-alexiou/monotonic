package com.sa.monotonic.streams;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h2>Exercise 06 - reduce, aggregates, and the *Match family</h2>
 *
 * <pre>
 * Q1: reduce(identity, accumulator) - what rules must identity and accumulator follow
 *     (think parallel streams)?
 * A1:
 *
 * Q2: When do you need the 3-argument reduce(identity, accumulator, combiner)?
 * A2:
 *
 * Q3: Why is allMatch on an EMPTY stream true? ("vacuous truth")
 * A3:
 * </pre>
 */
public class Ex06_ReduceAndMatch {

    static final Boolean PREDICT_ALLMATCH_EMPTY = null;
    static final Boolean PREDICT_ANYMATCH_EMPTY = null;
    static final Boolean PREDICT_NONEMATCH_EMPTY = null;
    static final String PREDICT_REDUCE_NO_IDENTITY_EMPTY = "???";
    static final String PREDICT_INTSTREAM_EMPTY_AVERAGE = "???";

    record Person(String name, int age) {
    }

    static int sum(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    static int product(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    /** Longest string; on ties keep the FIRST one. Use reduce WITHOUT identity. */
    static Optional<String> longest(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** n! as BigInteger (0! = 1), via rangeClosed + mapToObj + reduce. */
    static BigInteger factorial(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Total characters using the 3-argument reduce (identity 0, accumulator, combiner) - no map! */
    static int totalLength(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    static IntSummaryStatistics stats(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    /** Oldest person (use max with a Comparator). */
    static Optional<Person> oldest(List<Person> people) {
        // TODO: implement
        return Check.todo();
    }

    /** true if every element is >= its predecessor. Use IntStream.range + allMatch. */
    static boolean isSortedAscending(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    /** true if no two adjacent people share a name (noneMatch). */
    static boolean noAdjacentDuplicates(List<String> list) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex06 - reduce and match");

        Check.equal("sum", 15, () -> sum(List.of(1, 2, 3, 4, 5)));
        Check.equal("sum(empty)", 0, () -> sum(List.of()));
        Check.equal("product", 120, () -> product(List.of(1, 2, 3, 4, 5)));
        Check.equal("longest (tie keeps first)", Optional.of("streams"), () -> longest(List.of("java", "streams", "lambdas", "go")));
        Check.equal("longest(empty)", Optional.empty(), () -> longest(List.of()));
        Check.equal("factorial(25)", new BigInteger("15511210043330985984000000"), () -> factorial(25));
        Check.equal("factorial(0)", BigInteger.ONE, () -> factorial(0));
        Check.equal("totalLength", 11, () -> totalLength(List.of("hello", "world", "!")));
        Check.equal("stats", List.of(1, 9, 5.0, 4L), () -> {
            IntSummaryStatistics s = stats(List.of(9, 1, 5, 5));
            return List.of(s.getMin(), s.getMax(), s.getAverage(), s.getCount());
        });
        Check.equal("oldest", Optional.of(new Person("Bob", 51)),
                () -> oldest(List.of(new Person("Ann", 30), new Person("Bob", 51), new Person("Cid", 22))));
        Check.equal("isSortedAscending", List.of(true, false, true),
                () -> List.of(isSortedAscending(List.of(1, 2, 2, 5)), isSortedAscending(List.of(3, 1)), isSortedAscending(List.of())));
        Check.equal("noAdjacentDuplicates", List.of(true, false),
                () -> List.of(noAdjacentDuplicates(List.of("a", "b", "a")), noAdjacentDuplicates(List.of("a", "b", "b"))));

        Check.predict("allMatch on empty", PREDICT_ALLMATCH_EMPTY, () -> Stream.<Integer>empty().allMatch(x -> x > 100));
        Check.predict("anyMatch on empty", PREDICT_ANYMATCH_EMPTY, () -> Stream.<Integer>empty().anyMatch(x -> x > 100));
        Check.predict("noneMatch on empty", PREDICT_NONEMATCH_EMPTY, () -> Stream.<Integer>empty().noneMatch(x -> x > 100));
        Check.predict("reduce(Integer::sum) on empty -> toString", PREDICT_REDUCE_NO_IDENTITY_EMPTY,
                () -> Stream.<Integer>empty().reduce(Integer::sum).toString());
        Check.predict("IntStream.empty().average() -> toString", PREDICT_INTSTREAM_EMPTY_AVERAGE,
                () -> IntStream.empty().average().toString());

        Check.summary();
    }
}
