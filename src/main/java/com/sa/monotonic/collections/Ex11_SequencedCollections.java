package com.sa.monotonic.collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.TreeSet;

/**
 * <h2>Exercise 11 - Java 21 SequencedCollection</h2>
 *
 * <pre>
 * Q1: Which interfaces were retrofitted to extend SequencedCollection / SequencedSet in Java 21?
 * A1:
 *
 * Q2: Why does TreeSet.addFirst throw instead of inserting?
 * A2:
 *
 * Q3: Is reversed() a copy or a view? How did you prove it?
 * A3:
 * </pre>
 */
public class Ex11_SequencedCollections {

    // Exception simple names or exact toString output.
    static final String PREDICT_TREESET_ADD_FIRST = "???";
    static final String PREDICT_LINKEDHASHSET_ADD_FIRST_EXISTING = "???";
    static final String PREDICT_LIST_OF_REVERSED_ADD = "???";
    static final String PREDICT_EMPTY_LIST_GET_FIRST = "???";
    static final String PREDICT_EMPTY_LIST_GET_0 = "???";

    /**
     * Return "first=X, last=Y, reversed=[...]" using getFirst(), getLast(), reversed().
     * For an empty collection return "empty".
     */
    static <E> String describe(SequencedCollection<E> c) {
        // TODO: implement
        return Check.todo();
    }

    /** Append s to the END of the list using ONLY list.reversed() (no add / addLast on list). */
    static List<String> appendUsingReversed(List<String> list, String s) {
        // TODO: implement
        return Check.todo();
    }

    /** The last n elements, in ORIGINAL order. Iterate the reversed() view; don't index. */
    static <E> List<E> lastN(SequencedCollection<E> c, int n) {
        // TODO: implement
        return Check.todo();
    }

    /** true if the collection reads the same forwards and backwards (use iterator() + reversed()). */
    static boolean isPalindrome(SequencedCollection<?> c) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex11 - SequencedCollection");

        Check.equal("describe ArrayList", "first=a, last=c, reversed=[c, b, a]",
                () -> describe(new ArrayList<>(List.of("a", "b", "c"))));
        Check.equal("describe LinkedHashSet", "first=c, last=b, reversed=[b, a, c]",
                () -> describe(new LinkedHashSet<>(List.of("c", "a", "b"))));
        Check.equal("describe ArrayDeque", "first=x, last=y, reversed=[y, x]",
                () -> describe(new ArrayDeque<>(List.of("x", "y"))));
        Check.equal("describe TreeSet", "first=a, last=c, reversed=[c, b, a]",
                () -> describe(new TreeSet<>(List.of("b", "c", "a"))));
        Check.equal("describe empty", "empty", () -> describe(new ArrayList<String>()));

        Check.predict("TreeSet.addFirst", PREDICT_TREESET_ADD_FIRST,
                () -> Check.thrownBy(() -> new TreeSet<>(List.of("b")).addFirst("a")));
        Check.predict("LinkedHashSet [a,b,c].addFirst(\"c\")", PREDICT_LINKEDHASHSET_ADD_FIRST_EXISTING, () -> {
            LinkedHashSet<String> s = new LinkedHashSet<>(List.of("a", "b", "c"));
            s.addFirst("c");
            return s.toString();
        });
        Check.predict("List.of(1,2,3).reversed().add(4)", PREDICT_LIST_OF_REVERSED_ADD,
                () -> Check.thrownBy(() -> List.of(1, 2, 3).reversed().add(4)));
        Check.predict("new ArrayList<>().getFirst()", PREDICT_EMPTY_LIST_GET_FIRST,
                () -> Check.thrownBy(() -> new ArrayList<String>().getFirst()));
        Check.predict("new ArrayList<>().get(0)", PREDICT_EMPTY_LIST_GET_0,
                () -> Check.thrownBy(() -> new ArrayList<String>().get(0)));

        Check.equal("appendUsingReversed", List.of("a", "b", "z"),
                () -> appendUsingReversed(new ArrayList<>(List.of("a", "b")), "z"));
        Check.equal("lastN(List, 2)", List.of(4, 5), () -> lastN(List.of(1, 2, 3, 4, 5), 2));
        Check.equal("lastN(LinkedHashSet, 10)", List.of("a", "b"), () -> lastN(new LinkedHashSet<>(List.of("a", "b")), 10));
        Check.equal("lastN(TreeSet, 1)", List.of(9), () -> lastN(new TreeSet<>(List.of(9, 1, 5)), 1));

        Check.equal("isPalindrome [1,2,1]", true, () -> isPalindrome(List.of(1, 2, 1)));
        Check.equal("isPalindrome [r,a,c,e,c,a,r] deque", true,
                () -> isPalindrome(new ArrayDeque<>(List.of("r", "a", "c", "e", "c", "a", "r"))));
        Check.equal("isPalindrome [a,b]", false, () -> isPalindrome(List.of("a", "b")));
        Check.equal("isPalindrome empty", true, () -> isPalindrome(List.of()));

        Check.summary();
    }
}
