package com.sa.monotonic.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * <h2>Exercise 07 - Word frequency with the Map default methods</h2>
 *
 * <pre>
 * Q: putIfAbsent(k, expensive()) vs computeIfAbsent(k, k -> expensive()) - what's the difference?
 * A:
 * </pre>
 */
public class Ex07_WordFrequency {

    static final List<String> WORDS = List.of("to be or not to be that is the question to be".split(" "));

    static final Integer PREDICT_CALLS_PUT_IF_ABSENT_EXISTING_KEY = null;
    static final Integer PREDICT_CALLS_COMPUTE_IF_ABSENT_EXISTING_KEY = null;
    static final Boolean PREDICT_KEY_PRESENT_AFTER_MERGE_RETURNS_NULL = null;

    /** Count using getOrDefault + put. */
    static Map<String, Integer> countWithGetOrDefault(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** Count using merge. */
    static Map<String, Integer> countWithMerge(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** Count using compute. */
    static Map<String, Integer> countWithCompute(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /** Top n as "word=count", count DESC, ties broken alphabetically. */
    static List<String> topN(Map<String, Integer> counts, int n) {
        // TODO: implement
        return Check.todo();
    }

    /** First letter -> sorted set of distinct words starting with it, keys sorted. */
    static NavigableMap<Character, SortedSet<String>> indexByFirstLetter(List<String> words) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Decrement the stock of {@code item} by one. When it reaches 0 the key must DISAPPEAR.
     * Missing items are left alone. Use computeIfPresent or merge (returning null removes the key).
     */
    static Map<String, Integer> decrementStock(Map<String, Integer> stock, String item) {
        // TODO: implement
        return Check.todo();
    }

    static int expensiveCalls;

    static String expensive() {
        expensiveCalls++;
        return "computed";
    }

    public static void main(String[] args) {
        Check.header("Ex07 - Word frequency");

        Map<String, Integer> expected = Map.of("to", 3, "be", 3, "or", 1, "not", 1,
                "that", 1, "is", 1, "the", 1, "question", 1);
        Check.equal("countWithGetOrDefault", expected, () -> countWithGetOrDefault(WORDS));
        Check.equal("countWithMerge", expected, () -> countWithMerge(WORDS));
        Check.equal("countWithCompute", expected, () -> countWithCompute(WORDS));
        Check.equal("topN(3)", List.of("be=3", "to=3", "is=1"), () -> topN(expected, 3));
        Check.equal("indexByFirstLetter",
                "{b=[be], i=[is], n=[not], o=[or], q=[question], t=[that, the, to]}",
                () -> indexByFirstLetter(WORDS).toString());

        Check.equal("decrementStock 2 -> 1", Map.of("apples", 1),
                () -> decrementStock(new HashMap<>(Map.of("apples", 2)), "apples"));
        Check.equal("decrementStock 1 -> removed", Map.of(),
                () -> decrementStock(new HashMap<>(Map.of("apples", 1)), "apples"));
        Check.equal("decrementStock missing item", Map.of("apples", 1),
                () -> decrementStock(new HashMap<>(Map.of("apples", 1)), "pears"));

        Check.predict("expensive() calls: putIfAbsent on existing key", PREDICT_CALLS_PUT_IF_ABSENT_EXISTING_KEY, () -> {
            Map<String, String> m = new HashMap<>(Map.of("k", "v"));
            expensiveCalls = 0;
            m.putIfAbsent("k", expensive());
            return expensiveCalls;
        });
        Check.predict("expensive() calls: computeIfAbsent on existing key", PREDICT_CALLS_COMPUTE_IF_ABSENT_EXISTING_KEY, () -> {
            Map<String, String> m = new HashMap<>(Map.of("k", "v"));
            expensiveCalls = 0;
            m.computeIfAbsent("k", k -> expensive());
            return expensiveCalls;
        });
        Check.predict("key present after merge returns null", PREDICT_KEY_PRESENT_AFTER_MERGE_RETURNS_NULL, () -> {
            Map<String, Integer> m = new HashMap<>(Map.of("apples", 1));
            m.merge("apples", -1, (oldV, delta) -> oldV + delta == 0 ? null : oldV + delta);
            return m.containsKey("apples");
        });

        Check.demo("same counts in HashMap / LinkedHashMap / TreeMap", () -> {
            Map<String, Integer> counts = countWithMerge(WORDS);
            System.out.println("    HashMap       " + counts);
            Map<String, Integer> linked = new java.util.LinkedHashMap<>();
            WORDS.forEach(w -> linked.merge(w, 1, Integer::sum));
            System.out.println("    LinkedHashMap " + linked);
            System.out.println("    TreeMap       " + new TreeMap<>(counts));
        });

        Check.summary();
    }
}
