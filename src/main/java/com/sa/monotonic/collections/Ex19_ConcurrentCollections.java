package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * <h2>Exercise 19 - Concurrent collections with virtual threads</h2>
 *
 * Every counting method runs {@code tasks} tasks on a virtual-thread executor; each task
 * increments the counter of EVERY word once. Correct result: every word maps to {@code tasks}.
 * Tip: {@code try (ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor()) { ... }}
 * waits for all tasks when the try block ends (ExecutorService is AutoCloseable since Java 19).
 *
 * <pre>
 * Q1: Why does Collections.synchronizedMap still lose updates with "if containsKey then put"?
 * A1:
 *
 * Q2: Why does iterating a CopyOnWriteArrayList never throw CME? What's the cost?
 * A2:
 *
 * Q3: When is ConcurrentHashMap&lt;K, LongAdder&gt; better than merge(k, 1, Integer::sum)?
 * A3:
 * </pre>
 */
public class Ex19_ConcurrentCollections {

    static final List<String> WORDS = IntStream.range(0, 100).mapToObj(i -> "w" + i).toList();
    static final int TASKS = 2_000;

    static final Integer PREDICT_COW_ITERATIONS = null;
    static final Integer PREDICT_COW_FINAL_SIZE = null;
    static final String PREDICT_ARRAYLIST_SAME_LOOP = "???";
    static final String PREDICT_CHM_NULL_VALUE = "???";

    /** GIVEN (broken): plain HashMap shared by many threads. */
    static Map<String, Integer> brokenHashMapCount(List<String> words, int tasks) {
        Map<String, Integer> counts = new HashMap<>();
        try (ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int t = 0; t < tasks; t++) {
                ex.submit(() -> words.forEach(w -> counts.merge(w, 1, Integer::sum)));
            }
        }
        return counts;
    }

    /** GIVEN (broken): every single call is synchronized, but check-then-act is not atomic. */
    static Map<String, Integer> brokenSynchronizedCheckThenAct(List<String> words, int tasks) {
        Map<String, Integer> counts = Collections.synchronizedMap(new HashMap<>());
        try (ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int t = 0; t < tasks; t++) {
                ex.submit(() -> words.forEach(w -> {
                    if (!counts.containsKey(w)) counts.put(w, 1);
                    else counts.put(w, counts.get(w) + 1);
                }));
            }
        }
        return counts;
    }

    /** Fix A: keep Collections.synchronizedMap but make check-then-act atomic with synchronized(counts). */
    static Map<String, Integer> countWithSynchronizedBlock(List<String> words, int tasks) {
        // TODO: implement
        return Check.todo();
    }

    /** Fix B: ConcurrentHashMap.merge (atomic per key). */
    static Map<String, Integer> countWithConcurrentMerge(List<String> words, int tasks) {
        // TODO: implement
        return Check.todo();
    }

    /** Fix C: ConcurrentHashMap<String, LongAdder> with computeIfAbsent(...).increment(). */
    static Map<String, LongAdder> countWithLongAdder(List<String> words, int tasks) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Producer/consumer: ONE producer puts 1..n into a BOUNDED LinkedBlockingQueue(100), then one
     * POISON PILL (e.g. -1) per consumer. {@code consumers} consumer threads take() and sum until
     * they see the pill. Return the total of all consumers' sums (= n(n+1)/2).
     */
    static long producerConsumerSum(int n, int consumers) throws Exception {
        // TODO: implement
        return Check.todo();
    }

    private static boolean allEqualTo(Map<String, ? extends Number> counts, long expected) {
        return counts.size() == WORDS.size()
                && counts.values().stream().allMatch(v -> v.longValue() == expected);
    }

    private static long total(Map<String, ? extends Number> counts) {
        return counts.values().stream().mapToLong(Number::longValue).sum();
    }

    public static void main(String[] args) {
        Check.header("Ex19 - Concurrent collections");
        long expectedTotal = (long) WORDS.size() * TASKS;

        Check.demo("broken HashMap", () -> System.out.println(
                "    total=" + total(brokenHashMapCount(WORDS, TASKS)) + " expected=" + expectedTotal));
        Check.demo("broken synchronizedMap check-then-act", () -> System.out.println(
                "    total=" + total(brokenSynchronizedCheckThenAct(WORDS, TASKS)) + " expected=" + expectedTotal));

        Check.isTrue("countWithSynchronizedBlock", () -> allEqualTo(countWithSynchronizedBlock(WORDS, TASKS), TASKS));
        Check.isTrue("countWithConcurrentMerge", () -> allEqualTo(countWithConcurrentMerge(WORDS, TASKS), TASKS));
        Check.isTrue("countWithLongAdder", () -> allEqualTo(countWithLongAdder(WORDS, TASKS), TASKS));
        Check.equal("producerConsumerSum(10_000, 4)", 50_005_000L, () -> producerConsumerSum(10_000, 4));

        Check.predict("CopyOnWriteArrayList iterations while adding", PREDICT_COW_ITERATIONS, () -> {
            List<String> listeners = new CopyOnWriteArrayList<>(List.of("a", "b", "c"));
            int iterations = 0;
            for (String l : listeners) {
                listeners.add(l + "'");
                iterations++;
            }
            return iterations;
        });
        Check.predict("CopyOnWriteArrayList final size", PREDICT_COW_FINAL_SIZE, () -> {
            List<String> listeners = new CopyOnWriteArrayList<>(List.of("a", "b", "c"));
            for (String l : listeners) listeners.add(l + "'");
            return listeners.size();
        });
        Check.predict("same loop on ArrayList", PREDICT_ARRAYLIST_SAME_LOOP, () -> Check.thrownBy(() -> {
            List<String> listeners = new ArrayList<>(List.of("a", "b", "c"));
            for (String l : listeners) listeners.add(l + "'");
        }));
        Check.predict("ConcurrentHashMap.put(k, null)", PREDICT_CHM_NULL_VALUE,
                () -> Check.thrownBy(() -> new ConcurrentHashMap<String, String>().put("k", null)));

        Check.summary();
    }
}
