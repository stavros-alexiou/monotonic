package com.sa.monotonic.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h2>Exercise 18 - Build your own Multimap and BiMap</h2>
 *
 * <pre>
 * Q: Why should get() of a Multimap return an unmodifiable VIEW instead of the internal set or a copy?
 * A:
 * </pre>
 */
public class Ex18_MultimapAndBiMap {

    /** key -> set of values. Use LinkedHashMap / LinkedHashSet so iteration order is predictable. */
    static final class Multimap<K, V> {
        private final Map<K, Set<V>> map = new LinkedHashMap<>();

        /** true if the (key, value) pair was not present before. */
        boolean put(K key, V value) {
            // TODO: implement
            return Check.todo();
        }

        /** Remove one pair. If the key has no values left, the KEY must disappear too. */
        boolean remove(K key, V value) {
            // TODO: implement
            return Check.todo();
        }

        /** Unmodifiable live view of the values for an existing key; an empty set for a missing key. */
        Set<V> get(K key) {
            // TODO: implement
            return Check.todo();
        }

        boolean containsEntry(K key, V value) {
            // TODO: implement
            return Check.todo();
        }

        /** Total number of (key, value) pairs. */
        int size() {
            // TODO: implement
            return Check.todo();
        }

        Set<K> keySet() {
            // TODO: implement
            return Check.todo();
        }
    }

    /**
     * Bidirectional map: keys AND values are unique. inverse() is a live BiMap<V, K> over the SAME
     * data - changes through either side are visible on the other. The constructors are given.
     */
    static final class BiMap<K, V> {
        private final Map<K, V> forward;
        private final Map<V, K> backward;
        private final BiMap<V, K> inverse;

        BiMap() {
            this.forward = new HashMap<>();
            this.backward = new HashMap<>();
            this.inverse = new BiMap<>(backward, forward, this);
        }

        private BiMap(Map<K, V> forward, Map<V, K> backward, BiMap<V, K> inverse) {
            this.forward = forward;
            this.backward = backward;
            this.inverse = inverse;
        }

        /**
         * Put a pair. If {@code value} is already bound to a DIFFERENT key -> IllegalArgumentException.
         * If {@code key} already had another value, that old value must be unbound in the inverse.
         * Returns the previous value of the key, or null.
         */
        V put(K key, V value) {
            // TODO: implement
            return Check.todo();
        }

        V get(K key) {
            // TODO: implement
            return Check.todo();
        }

        /** Remove the key and its value from BOTH directions; returns the old value or null. */
        V remove(K key) {
            // TODO: implement
            return Check.todo();
        }

        boolean containsKey(K key) {
            return forward.containsKey(key);
        }

        int size() {
            return forward.size();
        }

        BiMap<V, K> inverse() {
            return inverse;
        }
    }

    public static void main(String[] args) {
        Check.header("Ex18 - Multimap and BiMap");

        Check.equal("Multimap put / duplicate put", List.of(true, true, false, true), () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            return List.of(mm.put("a", 1), mm.put("a", 2), mm.put("a", 1), mm.put("b", 3));
        });
        Check.equal("Multimap get / size", List.of(Set.of(1, 2), 3), () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            mm.put("a", 1);
            mm.put("a", 2);
            mm.put("b", 3);
            return List.of(mm.get("a"), mm.size());
        });
        Check.equal("Multimap get missing -> empty", Set.of(), () -> new Multimap<String, Integer>().get("zzz"));
        Check.throwsA("Multimap get is unmodifiable", UnsupportedOperationException.class, () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            mm.put("a", 1);
            mm.get("a").add(9);
        });
        Check.isTrue("Multimap get is a live view", () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            mm.put("a", 1);
            Set<Integer> view = mm.get("a");
            mm.put("a", 5);
            return view.contains(5);
        });
        Check.equal("Multimap remove drops empty keys", List.of(true, false, Set.of("a")), () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            mm.put("a", 1);
            mm.put("b", 3);
            return List.of(mm.remove("b", 3), mm.remove("b", 3), mm.keySet());
        });
        Check.equal("Multimap containsEntry", List.of(true, false), () -> {
            Multimap<String, Integer> mm = new Multimap<>();
            mm.put("a", 2);
            return List.of(mm.containsEntry("a", 2), mm.containsEntry("a", 3));
        });

        Check.equal("BiMap get / inverse get", List.of(1, "two"), () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.put("one", 1);
            bm.put("two", 2);
            return List.of(bm.get("one"), bm.inverse().get(2));
        });
        Check.throwsA("BiMap duplicate value", IllegalArgumentException.class, () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.put("one", 1);
            bm.put("uno", 1);
        });
        Check.equal("BiMap re-put same pair is fine", 1, () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.put("one", 1);
            bm.put("one", 1);
            return bm.size();
        });
        Check.equal("BiMap replacing a key's value unbinds the old value", List.of(false, "one"), () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.put("one", 1);
            bm.put("one", 11);
            return List.of(bm.inverse().containsKey(1), bm.inverse().get(11));
        });
        Check.equal("BiMap put via inverse", 3, () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.inverse().put(3, "three");
            return bm.get("three");
        });
        Check.equal("BiMap remove both directions", List.of(2, false, 0), () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            bm.put("two", 2);
            Integer removed = bm.remove("two");
            return List.of(removed, bm.inverse().containsKey(2), bm.inverse().size());
        });
        Check.isTrue("inverse().inverse() is the same object", () -> {
            BiMap<String, Integer> bm = new BiMap<>();
            return bm.inverse().inverse() == bm;
        });

        Check.summary();
    }
}
