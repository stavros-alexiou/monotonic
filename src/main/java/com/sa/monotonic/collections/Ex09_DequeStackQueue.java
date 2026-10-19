package com.sa.monotonic.collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <h2>Exercise 09 - ArrayDeque as a stack and as a queue</h2>
 *
 * Use ArrayDeque everywhere - never java.util.Stack.
 *
 * <pre>
 * Q1: Why is java.util.Stack discouraged? (hint: it extends Vector)
 * A1:
 *
 * Q2: offer/poll/peek vs add/remove/element - what's the difference?
 * A2:
 * </pre>
 */
public class Ex09_DequeStackQueue {

    // Write "null" for a null result, or the exception's simple name.
    static final String PREDICT_POLL_ON_EMPTY = "???";
    static final String PREDICT_REMOVE_ON_EMPTY = "???";
    static final String PREDICT_PUSH_NULL = "???";
    static final String PREDICT_TOSTRING_AFTER_PUSH_1_2_3 = "???";

    /** Brackets () [] {} must be balanced and properly nested. Ignore all other characters. */
    static boolean isBalanced(String s) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Evaluate Reverse Polish Notation with + - * / on ints, tokens separated by spaces.
     * "3 4 + 2 *" == (3 + 4) * 2 == 14
     */
    static int evalRpn(String expression) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * BFS on a grid. 'S' start, 'E' end, '#' wall, '.' free. Moves: up/down/left/right.
     * Return the number of steps of the shortest path, or -1 if E is unreachable.
     */
    static int shortestPath(String[] maze) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Maximum of every window of size k, in O(n), using a Deque of INDEXES kept in decreasing
     * value order ("monotonic deque"). [1,3,-1,-3,5,3,6,7], k=3 -> [3,3,5,5,6,7]
     */
    static List<Integer> slidingWindowMax(int[] nums, int k) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex09 - Deque as stack and queue");

        Check.predict("poll() on empty ArrayDeque", PREDICT_POLL_ON_EMPTY,
                () -> String.valueOf(new ArrayDeque<Integer>().poll()));
        Check.predict("remove() on empty ArrayDeque", PREDICT_REMOVE_ON_EMPTY,
                () -> Check.thrownBy(() -> new ArrayDeque<Integer>().remove()));
        Check.predict("push(null)", PREDICT_PUSH_NULL,
                () -> Check.thrownBy(() -> new ArrayDeque<Integer>().push(null)));
        Check.predict("toString after push 1,2,3", PREDICT_TOSTRING_AFTER_PUSH_1_2_3, () -> {
            Deque<Integer> d = new ArrayDeque<>();
            d.push(1);
            d.push(2);
            d.push(3);
            return d.toString();
        });

        Check.equal("isBalanced {[()()]}", true, () -> isBalanced("{[()()]}"));
        Check.equal("isBalanced ([)]", false, () -> isBalanced("([)]"));
        Check.equal("isBalanced ((", false, () -> isBalanced("(("));
        Check.equal("isBalanced )(", false, () -> isBalanced(")("));
        Check.equal("isBalanced empty", true, () -> isBalanced(""));
        Check.equal("isBalanced a(b[c]d)e", true, () -> isBalanced("a(b[c]d)e"));

        Check.equal("evalRpn 3 4 + 2 *", 14, () -> evalRpn("3 4 + 2 *"));
        Check.equal("evalRpn 5 1 2 + 4 * + 3 -", 14, () -> evalRpn("5 1 2 + 4 * + 3 -"));
        Check.equal("evalRpn 20 4 / 3 -", 2, () -> evalRpn("20 4 / 3 -"));

        Check.equal("shortestPath", 12, () -> shortestPath(new String[]{
                "S.#.....",
                ".##.###.",
                "....#...",
                ".##...#E"}));
        Check.equal("shortestPath unreachable", -1, () -> shortestPath(new String[]{
                "S.#",
                "###",
                "..E"}));

        Check.equal("slidingWindowMax", List.of(3, 3, 5, 5, 6, 7),
                () -> slidingWindowMax(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
        Check.equal("slidingWindowMax k=1", List.of(4, 2, 12),
                () -> slidingWindowMax(new int[]{4, 2, 12}, 1));

        Check.summary();
    }
}
