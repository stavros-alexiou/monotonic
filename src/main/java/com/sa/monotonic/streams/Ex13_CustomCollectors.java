package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h2>Exercise 13 - Writing your own Collectors</h2>
 *
 * Use {@code Collector.of(supplier, accumulator, combiner, finisher, characteristics...)} and, once,
 * implement the {@link Collector} interface by hand. Every collector must also work in PARALLEL,
 * which means a correct combiner!
 *
 * <pre>
 * Q1: What are the 4 functions of a Collector, and when is the combiner called?
 * A1:
 *
 * Q2: What do the characteristics IDENTITY_FINISH, UNORDERED and CONCURRENT promise?
 * A2:
 * </pre>
 */
public class Ex13_CustomCollectors {

    record Stats(int min, int max, long count) {
    }

    /** "" | "a" | "a and b" | "a, b and c" */
    static Collector<String, ?, String> joiningWithAnd() {
        // TODO: implement
        return Check.todo();
    }

    /** The n greatest elements by comparator, greatest first. Use a bounded PriorityQueue as container. */
    static <T> Collector<T, ?, List<T>> topN(int n, Comparator<? super T> comparator) {
        // TODO: implement
        return Check.todo();
    }

    /** min, max, count of Integers in one pass. Empty stream -> Stats(MAX_VALUE, MIN_VALUE, 0). */
    static Collector<Integer, ?, Stats> stats() {
        // TODO: implement
        return Check.todo();
    }

    /** Collect into an UNMODIFIABLE SortedSet (natural order). */
    static <T extends Comparable<? super T>> Collector<T, ?, SortedSet<T>> toUnmodifiableSortedSet() {
        // TODO: implement
        return Check.todo();
    }

    /** Implement Collector BY HAND: concatenates with a separator into a StringBuilder. */
    static final class ConcatCollector implements Collector<CharSequence, StringBuilder, String> {
        private final String separator;

        ConcatCollector(String separator) {
            this.separator = separator;
        }

        @Override
        public Supplier<StringBuilder> supplier() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public BiConsumer<StringBuilder, CharSequence> accumulator() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public BinaryOperator<StringBuilder> combiner() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public Function<StringBuilder, String> finisher() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public Set<Characteristics> characteristics() {
            // TODO: implement
            return Check.todo();
        }
    }

    public static void main(String[] args) {
        Check.header("Ex13 - Custom collectors");

        Check.equal("joiningWithAnd", List.of("", "a", "a and b", "a, b and c"), () -> List.of(
                Stream.<String>empty().collect(joiningWithAnd()),
                Stream.of("a").collect(joiningWithAnd()),
                Stream.of("a", "b").collect(joiningWithAnd()),
                Stream.of("a", "b", "c").collect(joiningWithAnd())));
        Check.equal("joiningWithAnd parallel", "0, 1, 2, 3, 4, 5, 6, 7, 8 and 9",
                () -> IntStream.range(0, 10).parallel().mapToObj(String::valueOf).collect(joiningWithAnd()));

        Check.equal("topN(3)", List.of(99, 42, 17),
                () -> Stream.of(5, 42, 1, 99, 17, 3).collect(topN(3, Comparator.naturalOrder())));
        Check.equal("topN by length", List.of("lambda", "java"),
                () -> Stream.of("go", "java", "lambda", "c").collect(topN(2, Comparator.comparingInt(String::length))));
        Check.equal("topN(5) parallel over 100k", List.of(99_999, 99_998, 99_997, 99_996, 99_995),
                () -> IntStream.range(0, 100_000).boxed().parallel().collect(topN(5, Comparator.<Integer>naturalOrder())));

        Check.equal("stats", new Stats(-4, 12, 5), () -> Stream.of(3, 12, -4, 0, 7).collect(stats()));
        Check.equal("stats parallel", new Stats(1, 1_000, 1_000),
                () -> IntStream.rangeClosed(1, 1_000).boxed().parallel().collect(stats()));
        Check.equal("stats empty", new Stats(Integer.MAX_VALUE, Integer.MIN_VALUE, 0), () -> Stream.<Integer>empty().collect(stats()));

        Check.equal("toUnmodifiableSortedSet", "[a, b, c]", () -> Stream.of("c", "a", "b", "a").collect(toUnmodifiableSortedSet()).toString());
        Check.throwsA("toUnmodifiableSortedSet is unmodifiable", UnsupportedOperationException.class,
                () -> Stream.of("a").collect(toUnmodifiableSortedSet()).add("z"));

        Check.equal("ConcatCollector", "x|y|z", () -> Stream.of("x", "y", "z").collect(new ConcatCollector("|")));
        Check.equal("ConcatCollector parallel == joining",
                IntStream.range(0, 1_000).mapToObj(String::valueOf).collect(Collectors.joining("|")),
                () -> IntStream.range(0, 1_000).parallel().mapToObj(String::valueOf).collect(new ConcatCollector("|")));

        Check.summary();
    }
}
