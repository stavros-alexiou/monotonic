package com.sa.monotonic.streams;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <h2>Exercise 17 - Spliterators and custom stream sources</h2>
 *
 * <pre>
 * Q1: What are tryAdvance, trySplit and estimateSize for? Which one makes parallelism possible?
 * A1:
 *
 * Q2: What do the characteristics ORDERED, SIZED, SUBSIZED, IMMUTABLE, NONNULL promise?
 * A2:
 * </pre>
 */
public class Ex17_SpliteratorsAndCustomSources {

    /** Any Iterator -> sequential, ordered Stream. Hint: Spliterators.spliteratorUnknownSize + StreamSupport. */
    static <T> Stream<T> fromIterator(Iterator<T> it) {
        // TODO: implement
        return Check.todo();
    }

    /** Zip two lists by index (stops at the shorter). Use IntStream.range. */
    static <A, B, R> List<R> zip(List<A> a, List<B> b, BiFunction<A, B, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Zip two (possibly INFINITE) streams lazily via their iterators. Stops when either ends. */
    static <A, B, R> Stream<R> zip(Stream<A> a, Stream<B> b, BiFunction<A, B, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Sliding windows: [1,2,3,4], size 2 -> [[1,2],[2,3],[3,4]]. Empty if size > list size. */
    static <T> List<List<T>> slidingWindows(List<T> list, int size) {
        // TODO: implement
        return Check.todo();
    }

    /** Lazily group a (possibly infinite) stream into chunks: [1..7], 3 -> [1,2,3],[4,5,6],[7]. */
    static <T> Stream<List<T>> chunked(Stream<T> source, int size) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * A splittable spliterator over the int range [from, to). trySplit hands the FIRST half to a new
     * spliterator and keeps the second half (return null when fewer than 2 elements remain).
     */
    static final class IntRangeSpliterator implements Spliterator<Integer> {
        private int from;
        private final int to;

        IntRangeSpliterator(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Integer> action) {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public Spliterator<Integer> trySplit() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public long estimateSize() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public int characteristics() {
            // TODO: implement
            return Check.todo();
        }
    }

    public static void main(String[] args) {
        Check.header("Ex17 - Spliterators and custom sources");

        Check.equal("fromIterator", List.of("A", "B"), () -> fromIterator(List.of("a", "b").iterator()).map(String::toUpperCase).toList());
        Check.equal("zip lists", List.of("a1", "b2"), () -> zip(List.of("a", "b", "c"), List.of(1, 2), (s, i) -> s + i));
        Check.equal("zip infinite streams", List.of("0:a", "1:b", "2:c"),
                () -> zip(Stream.iterate(0, i -> i + 1), Stream.of("a", "b", "c"), (i, s) -> i + ":" + s).toList());
        Check.equal("zip with limit on both infinite", List.of(0, 2, 4),
                () -> zip(Stream.iterate(0, i -> i + 1), Stream.iterate(0, i -> i + 1), Integer::sum).limit(3).toList());
        Check.equal("slidingWindows", List.of(List.of(1, 2), List.of(2, 3), List.of(3, 4)), () -> slidingWindows(List.of(1, 2, 3, 4), 2));
        Check.equal("slidingWindows too big", List.of(), () -> slidingWindows(List.of(1, 2), 3));
        Check.equal("chunked", List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7)),
                () -> chunked(Stream.of(1, 2, 3, 4, 5, 6, 7), 3).toList());
        Check.equal("chunked infinite", List.of(List.of(0, 1), List.of(2, 3)),
                () -> chunked(Stream.iterate(0, i -> i + 1), 2).limit(2).toList());

        Check.equal("IntRangeSpliterator sequential", List.of(3, 4, 5, 6),
                () -> StreamSupport.stream(new IntRangeSpliterator(3, 7), false).toList());
        Check.equal("IntRangeSpliterator trySplit halves", List.of(5L, 5L), () -> {
            IntRangeSpliterator s = new IntRangeSpliterator(0, 10);
            Spliterator<Integer> prefix = s.trySplit();
            return List.of(prefix.estimateSize(), s.estimateSize());
        });
        Check.equal("IntRangeSpliterator parallel sum", 499_999_500_000L,
                () -> StreamSupport.stream(new IntRangeSpliterator(0, 1_000_000), true).mapToLong(Integer::longValue).sum());
        Check.equal("IntRangeSpliterator parallel keeps order", IntStream.range(0, 5_000).boxed().toList(),
                () -> StreamSupport.stream(new IntRangeSpliterator(0, 5_000), true).toList());

        Check.summary();
    }
}
