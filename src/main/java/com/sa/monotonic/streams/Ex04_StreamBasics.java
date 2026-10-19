package com.sa.monotonic.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h2>Exercise 04 - Stream basics: sources, intermediate and terminal operations</h2>
 *
 * <pre>
 * Q1: Intermediate vs terminal operation - name 5 of each.
 * A1:
 *
 * Q2: Why can a stream be consumed only once?
 * A2:
 * </pre>
 */
public class Ex04_StreamBasics {

    static final Integer PREDICT_PEEK_CALLS_WITHOUT_TERMINAL_OP = null;
    static final String PREDICT_REUSING_A_STREAM = "???";
    static final String PREDICT_ADD_TO_STREAM_TOLIST = "???";

    /** Squares of the even numbers, in order. */
    static List<Integer> evenSquares(List<Integer> nums) {
        // TODO: implement
        return Check.todo();
    }

    /** Distinct, sorted, first n. */
    static List<String> firstNDistinctSorted(List<String> words, int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Page number pageNo (0-based) with pageSize items, using skip + limit. */
    static <T> List<T> page(List<T> items, int pageNo, int pageSize) {
        // TODO: implement
        return Check.todo();
    }

    static long countLongWords(List<String> words, int minLength) {
        // TODO: implement
        return Check.todo();
    }

    /** Odd numbers 1, 3, 5 ... below n, using the 3-argument Stream.iterate (Java 9). */
    static List<Integer> oddsBelow(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** n values from the supplier, using Stream.generate. */
    static <T> List<T> generate(Supplier<T> supplier, int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Distinct characters of s, sorted, joined: "banana" -> "abn". Use s.chars(). */
    static String distinctSortedChars(String s) {
        // TODO: implement
        return Check.todo();
    }

    /** Sum of an int[] with Arrays.stream. */
    static int sum(int[] values) {
        // TODO: implement
        return Check.todo();
    }

    /** "a-b-c" from Stream.of(...) of the arguments. */
    static String dashJoin(String... parts) {
        // TODO: implement
        return Check.todo();
    }

    static int peekCalls;

    public static void main(String[] args) {
        Check.header("Ex04 - Stream basics");

        Check.equal("evenSquares", List.of(4, 16, 36), () -> evenSquares(List.of(1, 2, 3, 4, 5, 6)));
        Check.equal("firstNDistinctSorted", List.of("apple", "banana"),
                () -> firstNDistinctSorted(List.of("cherry", "banana", "apple", "banana", "date"), 2));
        Check.equal("page 1 of size 3", List.of(4, 5, 6), () -> page(List.of(1, 2, 3, 4, 5, 6, 7), 1, 3));
        Check.equal("last partial page", List.of(7), () -> page(List.of(1, 2, 3, 4, 5, 6, 7), 2, 3));
        Check.equal("countLongWords", 2L, () -> countLongWords(List.of("a", "streams", "are", "lazy", "beasts"), 5));
        Check.equal("oddsBelow(10)", List.of(1, 3, 5, 7, 9), () -> oddsBelow(10));
        Check.equal("generate", List.of("x", "x", "x"), () -> generate(() -> "x", 3));
        Check.equal("distinctSortedChars", "abn", () -> distinctSortedChars("banana"));
        Check.equal("sum", 10, () -> sum(new int[]{1, 2, 3, 4}));
        Check.equal("dashJoin", "a-b-c", () -> dashJoin("a", "b", "c"));

        Check.predict("peek calls without a terminal op", PREDICT_PEEK_CALLS_WITHOUT_TERMINAL_OP, () -> {
            peekCalls = 0;
            Stream<Integer> s = Stream.of(1, 2, 3).peek(x -> peekCalls++).map(x -> x * 2);
            return peekCalls;
        });
        Check.predict("reusing a stream", PREDICT_REUSING_A_STREAM, () -> Check.thrownBy(() -> {
            Stream<Integer> s = Stream.of(1, 2, 3);
            s.count();
            s.count();
        }));
        Check.predict("adding to Stream.toList()", PREDICT_ADD_TO_STREAM_TOLIST,
                () -> Check.thrownBy(() -> Stream.of(1).toList().add(2)));

        Check.summary();
    }
}
