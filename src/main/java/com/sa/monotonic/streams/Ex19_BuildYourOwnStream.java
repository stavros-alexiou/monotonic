package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * <h2>Exercise 19 - Build your own lazy Stream ("Pipe") on top of Iterator</h2>
 *
 * Every intermediate operation returns a NEW Pipe wrapping a NEW Iterator that pulls from the
 * previous one on demand. Nothing may run until a terminal operation pulls. That's how
 * java.util.stream works conceptually ("pull" model with fused stages).
 *
 * <pre>
 * Q1: Why does laziness make Pipe.iterate(...) (infinite) + limit(...) work?
 * A1:
 *
 * Q2: What's the trickiest part of filter's iterator, and why does hasNext() need look-ahead?
 * A2:
 * </pre>
 */
public class Ex19_BuildYourOwnStream {

    static final class Pipe<T> {
        private final Iterator<T> source;

        private Pipe(Iterator<T> source) {
            this.source = source;
        }

        @SafeVarargs
        static <T> Pipe<T> of(T... items) {
            return new Pipe<>(List.of(items).iterator());
        }

        static <T> Pipe<T> from(Iterable<T> iterable) {
            return new Pipe<>(iterable.iterator());
        }

        /** Infinite: seed, f(seed), f(f(seed)), ... */
        static <T> Pipe<T> iterate(T seed, UnaryOperator<T> next) {
            // TODO: implement
            return Check.todo();
        }

        <R> Pipe<R> map(Function<? super T, ? extends R> f) {
            // TODO: implement
            return Check.todo();
        }

        Pipe<T> filter(Predicate<? super T> p) {
            // TODO: implement
            return Check.todo();
        }

        Pipe<T> limit(long max) {
            // TODO: implement
            return Check.todo();
        }

        Pipe<T> takeWhile(Predicate<? super T> p) {
            // TODO: implement
            return Check.todo();
        }

        <R> Pipe<R> flatMap(Function<? super T, ? extends Iterable<? extends R>> f) {
            // TODO: implement
            return Check.todo();
        }

        Pipe<T> peek(Consumer<? super T> action) {
            // TODO: implement
            return Check.todo();
        }

        // ---- terminal operations ----

        List<T> toList() {
            // TODO: implement
            return Check.todo();
        }

        <R> R reduce(R identity, BiFunction<R, ? super T, R> accumulator) {
            // TODO: implement
            return Check.todo();
        }

        Optional<T> findFirst() {
            // TODO: implement
            return Check.todo();
        }

        long count() {
            // TODO: implement
            return Check.todo();
        }
    }

    static int calls;

    public static void main(String[] args) {
        Check.header("Ex19 - Build your own lazy stream");

        Check.equal("map + filter", List.of(4, 16), () -> Pipe.of(1, 2, 3, 4).map(x -> x * x).filter(x -> x % 2 == 0).toList());
        Check.equal("filter that matches nothing", List.of(), () -> Pipe.of(1, 3, 5).filter(x -> x % 2 == 0).toList());
        Check.equal("limit", List.of("a", "b"), () -> Pipe.of("a", "b", "c").limit(2).toList());
        Check.equal("infinite iterate + filter + limit", List.of(0, 3, 6, 9),
                () -> Pipe.iterate(0, x -> x + 1).filter(x -> x % 3 == 0).limit(4).toList());
        Check.equal("takeWhile", List.of(1, 2, 4, 8), () -> Pipe.iterate(1, x -> x * 2).takeWhile(x -> x < 10).toList());
        Check.equal("flatMap", List.of(1, 1, 2, 1, 2, 3),
                () -> Pipe.of(1, 2, 3).flatMap(n -> Pipe.iterate(1, x -> x + 1).limit(n).toList()).toList());
        Check.equal("flatMap with empty inner", List.of("x"), () -> Pipe.of(List.<String>of(), List.of("x"), List.<String>of())
                .flatMap(l -> l).toList());
        Check.equal("reduce", 10, () -> Pipe.of(1, 2, 3, 4).reduce(0, Integer::sum));
        Check.equal("count", 3L, () -> Pipe.of("a", "b", "c").count());
        Check.equal("findFirst on infinite pipe", Optional.of(100), () -> Pipe.iterate(1, x -> x + 1).map(x -> x * x).filter(x -> x >= 100).findFirst());
        Check.equal("findFirst on empty", Optional.empty(), () -> Pipe.of().findFirst());

        Check.equal("LAZY: nothing runs before the terminal op", 0, () -> {
            calls = 0;
            Pipe.of(1, 2, 3).peek(x -> calls++).map(x -> x * 2);
            return calls;
        });
        Check.equal("LAZY: findFirst pulls only what it needs", 2, () -> {
            calls = 0;
            Pipe.of(1, 2, 3, 4, 5).peek(x -> calls++).filter(x -> x % 2 == 0).findFirst();
            return calls;
        });
        Check.equal("hasNext() is idempotent in filter", List.of(2, 4), () -> {
            Pipe<Integer> p = Pipe.of(1, 2, 3, 4).filter(x -> x % 2 == 0);
            Iterator<Integer> it = p.source;
            it.hasNext();
            it.hasNext();
            List<Integer> out = new ArrayList<>();
            while (it.hasNext()) out.add(it.next());
            return out;
        });

        Check.summary();
    }
}
