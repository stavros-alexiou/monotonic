package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Exercise 01 - List basics and the remove(int) vs remove(Object) trap</h2>
 *
 * <ol>
 *   <li>Fill in every PREDICT_ constant BEFORE you run the class.</li>
 *   <li>Implement every method that currently calls {@code Check.todo()}.</li>
 *   <li>Run main() until everything is [PASS].</li>
 * </ol>
 *
 * <p>Explain in your own words:
 * <pre>
 * Q1: Why does list.remove(1) on a List&lt;Integer&gt; remove by INDEX and not the value 1?
 * A1:
 *
 * Q2: subList(1, 3).clear() changed the original list. What does that tell you about subList?
 * A2:
 * </pre>
 */
public class Ex01_ListBasics {

    // ---- Part A: predictions --------------------------------------------------------------
    // Starting list: [10, 20, 1, 30, 1]. Write the exact toString() output, e.g. "[1, 2]".
    static final String PREDICT_AFTER_REMOVE_1 = "???";
    // ... then on THAT result we call list.remove(Integer.valueOf(1))
    static final String PREDICT_AFTER_REMOVE_INTEGER_VALUE_OF_1 = "???";

    // ---- Part B: implement ----------------------------------------------------------------

    /**
     * Starting from the given list (always [a, b, c, d, e]) do, IN THIS ORDER, on the same list:
     * insert "X" at index 1, replace index 0 with "A", then remove indexes 2..3 using subList(...).clear().
     * Return the list.
     */
    static List<String> listOps(List<String> list) {
        // TODO: implement
        return Check.todo();
    }

    /** Return {indexOf(s), lastIndexOf(s)}. */
    static int[] firstAndLastIndex(List<String> list, String s) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Remove EVERY occurrence of {@code value} from the list WITHOUT removeIf / streams.
     * Careful with the remove(int) overload!
     */
    static List<Integer> removeAllOccurrences(List<Integer> list, int value) {
        // TODO: implement
        return Check.todo();
    }

    /** Reverse the list IN PLACE using only get/set/size (no Collections.reverse, no reversed()). */
    static List<Integer> reverseInPlace(List<Integer> list) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex01 - List basics");

        List<Integer> nums = new ArrayList<>(List.of(10, 20, 1, 30, 1));
        nums.remove(1);
        String afterIndex = nums.toString();
        nums.remove(Integer.valueOf(1));
        String afterValue = nums.toString();
        Check.predict("after remove(1)", PREDICT_AFTER_REMOVE_1, () -> afterIndex);
        Check.predict("after remove(Integer.valueOf(1))", PREDICT_AFTER_REMOVE_INTEGER_VALUE_OF_1, () -> afterValue);

        Check.equal("listOps", List.of("A", "X", "d", "e"),
                () -> listOps(new ArrayList<>(List.of("a", "b", "c", "d", "e"))));
        Check.equal("firstAndLastIndex(a)", new int[]{0, 4},
                () -> firstAndLastIndex(List.of("a", "b", "a", "c", "a"), "a"));
        Check.equal("firstAndLastIndex(missing)", new int[]{-1, -1},
                () -> firstAndLastIndex(List.of("a", "b"), "z"));
        Check.equal("removeAllOccurrences(1)", List.of(2, 3),
                () -> removeAllOccurrences(new ArrayList<>(List.of(1, 2, 1, 3, 1)), 1));
        Check.equal("removeAllOccurrences(0) - the overload trap", List.of(5, 5),
                () -> removeAllOccurrences(new ArrayList<>(List.of(5, 0, 5)), 0));
        Check.equal("reverseInPlace", List.of(5, 4, 3, 2, 1),
                () -> reverseInPlace(new ArrayList<>(List.of(1, 2, 3, 4, 5))));
        Check.equal("reverseInPlace(empty)", List.of(),
                () -> reverseInPlace(new ArrayList<>()));

        Check.summary();
    }
}
