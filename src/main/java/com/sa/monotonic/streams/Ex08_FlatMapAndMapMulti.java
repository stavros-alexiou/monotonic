package com.sa.monotonic.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <h2>Exercise 08 - flatMap and mapMulti (Java 16)</h2>
 *
 * <pre>
 * Q1: map vs flatMap - draw the "shape" of the stream before and after.
 * A1:
 *
 * Q2: When is mapMulti nicer than flatMap?
 * A2:
 * </pre>
 */
public class Ex08_FlatMapAndMapMulti {

    static final Long PREDICT_COUNT_AFTER_MAP = null;
    static final Long PREDICT_COUNT_AFTER_FLATMAP = null;

    /** All words (lower-case, split on whitespace) of all sentences, in order. */
    static List<String> words(List<String> sentences) {
        // TODO: implement
        return Check.todo();
    }

    /** Distinct letters of all words, sorted and joined: ["hello","world"] -> "dehlorw". */
    static String distinctLetters(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** Cartesian product as strings: [a,b] x [1,2] -> [a1, a2, b1, b2]. */
    static List<String> cartesian(List<String> left, List<Integer> right) {
        // TODO: implement
        return Check.todo();
    }

    static <T> List<T> flatten(List<List<T>> nested) {
        // TODO: implement
        return Check.todo();
    }

    /** Sorted distinct product names bought by the customer with that name (Shop data). */
    static List<String> productsBoughtBy(String customerName) {
        // TODO: implement
        return Check.todo();
    }

    /** ["1-3", "5", "7-8"] -> [1, 2, 3, 5, 7, 8] using mapMulti. */
    static List<Integer> expandRanges(List<String> ranges) {
        // TODO: implement
        return Check.todo();
    }

    /** [1, [2, [3, 4]], 5] -> [1, 2, 3, 4, 5]. Recursive mapMulti (write a helper). */
    static List<Object> deepFlatten(List<?> nested) {
        // TODO: implement
        return Check.todo();
    }

    // your recursive helper goes here

    /** Each n repeated n times: [1, 2, 3] -> [1, 2, 2, 3, 3, 3]. Use IntStream.flatMap. */
    static int[] repeatEach(int[] nums) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex08 - flatMap and mapMulti");

        Check.equal("words", List.of("streams", "are", "lazy", "flatmap", "flattens"),
                () -> words(List.of("Streams are lazy", "  flatMap   flattens ")));
        Check.equal("distinctLetters", "dehlorw", () -> distinctLetters(List.of("hello", "world")));
        Check.equal("cartesian", List.of("a1", "a2", "b1", "b2"), () -> cartesian(List.of("a", "b"), List.of(1, 2)));
        Check.equal("flatten", List.of(1, 2, 3), () -> flatten(List.of(List.of(1), List.of(), List.of(2, 3))));
        Check.equal("productsBoughtBy Ann", List.of("Java Book", "Laptop", "Mouse", "Novel"), () -> productsBoughtBy("Ann"));
        Check.equal("productsBoughtBy Eve", List.of(), () -> productsBoughtBy("Eve"));
        Check.equal("expandRanges", List.of(1, 2, 3, 5, 7, 8), () -> expandRanges(List.of("1-3", "5", "7-8")));
        Check.equal("deepFlatten", List.of(1, 2, 3, 4, 5),
                () -> deepFlatten(List.of(1, List.of(2, List.of(3, 4)), 5)));
        Check.equal("repeatEach", new int[]{1, 2, 2, 3, 3, 3}, () -> repeatEach(new int[]{1, 2, 3}));

        Check.predict("count after map(List::stream)", PREDICT_COUNT_AFTER_MAP,
                () -> Stream.of(List.of(1, 2), List.of(3)).map(List::stream).count());
        Check.predict("count after flatMap(List::stream)", PREDICT_COUNT_AFTER_FLATMAP,
                () -> Stream.of(List.of(1, 2), List.of(3)).flatMap(List::stream).count());

        Check.summary();
    }
}
