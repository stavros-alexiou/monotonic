package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * <h2>Exercise 16 - Parallel streams: correctness first, speed second</h2>
 *
 * <pre>
 * Q1: Name 3 reasons a parallel stream can be SLOWER than a sequential one.
 * A1:
 *
 * Q2: Why is reduce(10, Integer::sum) wrong in parallel but right sequentially?
 * A2:
 *
 * Q3: findFirst vs findAny, forEach vs forEachOrdered in parallel?
 * A3:
 *
 * Q4: Which common pool runs parallel streams? How many threads does it have?
 * A4:
 * </pre>
 */
public class Ex16_ParallelStreams {

    static final Integer PREDICT_PARALLEL_FINDFIRST_EVEN = null;
    static final Boolean PREDICT_PARALLEL_TOLIST_KEEPS_ORDER = null;

    static long parallelSum(long n) {
        // TODO: implement
        return Check.todo();
    }

    /** Count primes <= n with a parallel IntStream. */
    static long countPrimesParallel(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Squares of 0..n-1 computed in parallel, collected WITHOUT shared mutable state, in order. */
    static List<Long> squaresParallel(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Word count with groupingByConcurrent. */
    static ConcurrentMap<String, Long> wordCountConcurrent(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** Process 0..n-1 in parallel but append to the list in encounter order (forEachOrdered). */
    static List<Integer> orderedSideEffect(int n) {
        List<Integer> sink = Collections.synchronizedList(new ArrayList<>());
        // TODO: implement
        Check.todo();
        return sink;
    }

    /** Correct parallel sum with reduce: what must the identity be? */
    static int parallelReduceSum(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex16 - Parallel streams");

        Check.equal("parallelSum(10M)", 50_000_005_000_000L, () -> parallelSum(10_000_000));
        Check.equal("countPrimesParallel(1M)", 78_498L, () -> countPrimesParallel(1_000_000));
        Check.equal("squaresParallel keeps order", LongStream.range(0, 10_000).map(i -> i * i).boxed().toList(),
                () -> squaresParallel(10_000));
        Check.equal("wordCountConcurrent", Map.of("a", 3L, "b", 2L, "c", 1L),
                () -> Map.copyOf(wordCountConcurrent(List.of("a", "b", "a", "c", "b", "a"))));
        Check.equal("orderedSideEffect", IntStream.range(0, 1_000).map(i -> i * 2).boxed().toList(), () -> orderedSideEffect(1_000));
        Check.equal("parallelReduceSum", 5050, () -> parallelReduceSum(IntStream.rangeClosed(1, 100).boxed().toList()));

        Check.predict("IntStream.range(1,1000).parallel().filter(even).findFirst()", PREDICT_PARALLEL_FINDFIRST_EVEN,
                () -> IntStream.range(1, 1000).parallel().filter(i -> i % 2 == 0).findFirst().getAsInt());
        Check.predict("parallel map().toList() keeps encounter order", PREDICT_PARALLEL_TOLIST_KEEPS_ORDER,
                () -> IntStream.range(0, 10_000).parallel().boxed().map(i -> i + 1).toList()
                        .equals(IntStream.rangeClosed(1, 10_000).boxed().toList()));

        Check.demo("BROKEN: parallel forEach into a plain ArrayList", () -> {
            List<Integer> unsafe = new ArrayList<>();
            IntStream.range(0, 100_000).parallel().forEach(unsafe::add);
            System.out.println("    size = " + unsafe.size() + " (expected 100000)");
        });
        Check.demo("BROKEN: reduce(10, Integer::sum) over 1..4", () -> System.out.println(
                "    sequential = " + IntStream.rangeClosed(1, 4).boxed().reduce(10, Integer::sum)
                        + ", parallel = " + IntStream.rangeClosed(1, 4).boxed().parallel().reduce(10, Integer::sum)));
        Check.demo("BROKEN: non-associative reduce (subtraction)", () -> System.out.println(
                "    sequential = " + IntStream.rangeClosed(1, 100).reduce(0, (a, b) -> a - b)
                        + ", parallel = " + IntStream.rangeClosed(1, 100).parallel().reduce(0, (a, b) -> a - b)));
        Check.demo("timing sequential vs parallel prime count up to 3M", () -> {
            long t = System.nanoTime();
            long s = IntStream.rangeClosed(2, 3_000_000)
                    .filter(c -> IntStream.rangeClosed(2, (int) Math.sqrt(c)).noneMatch(d -> c % d == 0)).count();
            long seq = System.nanoTime() - t;
            t = System.nanoTime();
            long p = IntStream.rangeClosed(2, 3_000_000).parallel()
                    .filter(c -> IntStream.rangeClosed(2, (int) Math.sqrt(c)).noneMatch(d -> c % d == 0)).count();
            long par = System.nanoTime() - t;
            System.out.printf("    sequential %d ms, parallel %d ms, cores %d, same result %b%n",
                    seq / 1_000_000, par / 1_000_000, Runtime.getRuntime().availableProcessors(), s == p);
        });

        Check.summary();
    }
}
