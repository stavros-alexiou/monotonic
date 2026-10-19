package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * <h2>Exercise 05 - Choosing a Set: HashSet vs LinkedHashSet vs TreeSet</h2>
 *
 * <pre>
 * Q1: Which Set would you choose for (a) fastest membership, (b) "remember insertion order",
 *     (c) sorted / range queries? What are the big-O costs of add/contains for each?
 * A1:
 *
 * Q2: A TreeSet with CASE_INSENSITIVE_ORDER and a HashSet can disagree on equals(). Why?
 *     (hint: TreeSet uses compareTo/compare, not equals)
 * A2:
 * </pre>
 */
public class Ex05_ChoosingASet {

    static final String TEXT = "The quick brown fox jumps over the lazy dog. The fox barks!";

    static final String PREDICT_SURVIVOR_OF_APPLE_VARIANTS = "???";
    static final Boolean PREDICT_TREESET_EQUALS_HASHSET = null;
    static final Boolean PREDICT_HASHSET_EQUALS_TREESET = null;

    /**
     * Split {@code text} into lower-case words (split on anything that is not a letter, skip empty
     * tokens) and add them to a set created by {@code factory}.
     */
    static <S extends Set<String>> S words(String text, Supplier<S> factory) {
        // TODO: implement
        return Check.todo();
    }

    /** Deduplicate ignoring case, keeping the FIRST spelling seen, sorted case-insensitively. */
    static SortedSet<String> caseInsensitiveDedup(List<String> in) {
        // TODO: implement
        return Check.todo();
    }

    // Set algebra: return NEW sets, never modify the inputs (they may be immutable!).

    static <T> Set<T> union(Set<? extends T> a, Set<? extends T> b) {
        // TODO: implement
        return Check.todo();
    }

    static <T> Set<T> intersection(Set<? extends T> a, Set<? extends T> b) {
        // TODO: implement
        return Check.todo();
    }

    static <T> Set<T> difference(Set<? extends T> a, Set<? extends T> b) {
        // TODO: implement
        return Check.todo();
    }

    /** Elements in exactly one of the two sets. */
    static <T> Set<T> symmetricDifference(Set<? extends T> a, Set<? extends T> b) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex05 - Choosing a Set");

        Check.equal("LinkedHashSet keeps first-occurrence order",
                List.of("the", "quick", "brown", "fox", "jumps", "over", "lazy", "dog", "barks"),
                () -> new ArrayList<>(words(TEXT, LinkedHashSet::new)));
        Check.equal("TreeSet is sorted",
                List.of("barks", "brown", "dog", "fox", "jumps", "lazy", "over", "quick", "the"),
                () -> new ArrayList<>(words(TEXT, TreeSet::new)));
        Check.equal("HashSet has the same 9 words", 9, () -> words(TEXT, HashSet::new).size());
        Check.demo("HashSet order (no guarantee!)", () -> System.out.println("    " + words(TEXT, HashSet::new)));

        List<String> fruits = List.of("Apple", "apple", "Banana", "APPLE", "banana", "cherry");
        Check.equal("caseInsensitiveDedup", "[Apple, Banana, cherry]",
                () -> caseInsensitiveDedup(fruits).toString());
        Check.predict("which 'apple' survives", PREDICT_SURVIVOR_OF_APPLE_VARIANTS,
                () -> {
                    TreeSet<String> s = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                    s.addAll(fruits);
                    return s.first();
                });

        Check.predict("treeSet.equals(hashSet)", PREDICT_TREESET_EQUALS_HASHSET, () -> {
            Set<String> tree = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            tree.add("Apple");
            return tree.equals(new HashSet<>(Set.of("apple")));
        });
        Check.predict("hashSet.equals(treeSet)", PREDICT_HASHSET_EQUALS_TREESET, () -> {
            Set<String> tree = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            tree.add("Apple");
            return new HashSet<>(Set.of("apple")).equals(tree);
        });

        Set<Integer> a = Set.of(1, 2, 3, 4);
        Set<Integer> b = Set.of(3, 4, 5);
        Check.equal("union", Set.of(1, 2, 3, 4, 5), () -> union(a, b));
        Check.equal("intersection", Set.of(3, 4), () -> intersection(a, b));
        Check.equal("difference", Set.of(1, 2), () -> difference(a, b));
        Check.equal("symmetricDifference", Set.of(1, 2, 5), () -> symmetricDifference(a, b));

        Check.summary();
    }
}
