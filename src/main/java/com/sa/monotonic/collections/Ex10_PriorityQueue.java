package com.sa.monotonic.collections;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * <h2>Exercise 10 - PriorityQueue (binary heap)</h2>
 *
 * <pre>
 * Q1: Why is System.out.println(pq) NOT sorted, while polling until empty IS sorted?
 * A1:
 *
 * Q2: Cost of offer / poll / peek / contains on a PriorityQueue?
 * A2:
 *
 * Q3: Why does top-K use a MIN-heap of size K to find the K LARGEST numbers?
 * A3:
 * </pre>
 */
public class Ex10_PriorityQueue {

    // offer 5, 1, 4, 2, 3 into a natural-order PriorityQueue, then print it.
    static final String PREDICT_PQ_TOSTRING = "???";

    /** Lower priority number = more urgent. Same priority -> older (earlier created) first. */
    record Task(int priority, Instant created, String name) {
    }

    static Comparator<Task> taskOrder() {
        // TODO: implement
        return Check.todo();
    }

    /** Offer all tasks into a PriorityQueue(taskOrder()) and poll until empty; return names. */
    static List<String> drain(List<Task> tasks) {
        // TODO: implement
        return Check.todo();
    }

    /** K largest numbers, sorted DESC, using a min-heap capped at size k (do NOT sort the array). */
    static List<Integer> topK(int[] nums, int k) {
        // TODO: implement
        return Check.todo();
    }

    /** Merge k individually-sorted lists into one sorted list using a PriorityQueue. */
    static List<Integer> mergeSorted(List<List<Integer>> lists) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Minimum number of meeting rooms for intervals [start, end) - classic heap problem.
     * Hint: sort by start, keep a min-heap of end times.
     */
    static int minMeetingRooms(int[][] intervals) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex10 - PriorityQueue");

        Check.predict("PriorityQueue toString", PREDICT_PQ_TOSTRING, () -> {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int n : new int[]{5, 1, 4, 2, 3}) pq.offer(n);
            return pq.toString();
        });

        Instant t0 = Instant.parse("2026-01-01T10:00:00Z");
        List<Task> tasks = List.of(
                new Task(3, t0, "write docs"),
                new Task(1, t0.plusSeconds(60), "fix prod (late report)"),
                new Task(2, t0, "code review"),
                new Task(1, t0, "fix prod (first report)"),
                new Task(3, t0.minusSeconds(60), "refactor"));
        Check.equal("drain order",
                List.of("fix prod (first report)", "fix prod (late report)", "code review", "refactor", "write docs"),
                () -> drain(tasks));

        int[] random = new Random(42).ints(1_000_000, 0, 10_000_000).toArray();
        List<Integer> expectedTop = Arrays.stream(random).boxed().sorted(Comparator.reverseOrder()).limit(5).toList();
        Check.equal("topK(5) of 1M ints", expectedTop, () -> topK(random, 5));
        Check.equal("topK small", List.of(9, 7), () -> topK(new int[]{3, 9, 1, 7}, 2));

        Check.equal("mergeSorted", List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
                () -> mergeSorted(List.of(List.of(1, 4, 7), List.of(2, 5, 8), List.of(), List.of(3, 6, 9))));

        Check.equal("minMeetingRooms A", 2, () -> minMeetingRooms(new int[][]{{0, 30}, {5, 10}, {15, 20}}));
        Check.equal("minMeetingRooms B", 1, () -> minMeetingRooms(new int[][]{{7, 10}, {2, 4}}));
        Check.equal("minMeetingRooms C", 3, () -> minMeetingRooms(new int[][]{{1, 5}, {2, 6}, {3, 7}, {8, 9}}));

        Check.summary();
    }
}
