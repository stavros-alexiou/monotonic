package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <h2>Exercise 14 - LRU cache, twice</h2>
 *
 * <ol>
 *   <li>{@link LruCache}: extend LinkedHashMap with accessOrder = true and override removeEldestEntry.</li>
 *   <li>{@link ManualLruCache}: build it yourself with a HashMap + doubly linked list (O(1) get/put).</li>
 * </ol>
 *
 * <pre>
 * Q: Which LinkedHashMap operations count as an "access" in access-order mode?
 * A:
 * </pre>
 */
public class Ex14_LruCache {

    static final Boolean PREDICT_CONTAINS_KEY_COUNTS_AS_ACCESS = null;
    static final Boolean PREDICT_GET_OR_DEFAULT_COUNTS_AS_ACCESS = null;

    /** Part 1. Iteration order of the map = least recently used FIRST. */
    static class LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        LruCache(int capacity) {
            // TODO: implement
            super(); // TODO: wrong constructor - you need access order!
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            // TODO: implement
            return Check.todo();
        }
    }

    /** Part 2. No LinkedHashMap allowed. */
    static class ManualLruCache<K, V> {

        private static final class Node<K, V> {
            K key;
            V value;
            Node<K, V> prev, next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private final int capacity;
        private final Map<K, Node<K, V>> index = new HashMap<>();
        // Sentinels: head.next is the MOST recently used, tail.prev the LEAST recently used.
        private final Node<K, V> head = new Node<>(null, null);
        private final Node<K, V> tail = new Node<>(null, null);

        ManualLruCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        // helper methods (unlink / addFront ...) go here

        /** Value or null; a hit makes the key most-recently-used. */
        V get(K key) {
            // TODO: implement
            return Check.todo();
        }

        /** Insert or update; the key becomes most-recently-used; evict LRU when over capacity. */
        void put(K key, V value) {
            // TODO: implement
            Check.todo();
        }

        int size() {
            return index.size();
        }

        List<K> keysMostRecentFirst() {
            // TODO: implement
            return Check.todo();
        }
    }

    public static void main(String[] args) {
        Check.header("Ex14 - LRU cache");

        Check.equal("LruCache: put A,B,C; get A; put D -> B evicted", List.of("C", "A", "D"), () -> {
            LruCache<String, Integer> c = new LruCache<>(3);
            c.put("A", 1);
            c.put("B", 2);
            c.put("C", 3);
            c.get("A");
            c.put("D", 4);
            return new ArrayList<>(c.keySet());
        });
        Check.equal("LruCache never exceeds capacity", 2, () -> {
            LruCache<Integer, Integer> c = new LruCache<>(2);
            for (int i = 0; i < 100; i++) c.put(i, i);
            return c.size();
        });
        Check.predict("containsKey counts as access", PREDICT_CONTAINS_KEY_COUNTS_AS_ACCESS, () -> {
            LruCache<String, Integer> c = new LruCache<>(3);
            c.put("A", 1);
            c.put("B", 2);
            c.containsKey("A");
            return c.keySet().iterator().next().equals("B");
        });
        Check.predict("getOrDefault counts as access", PREDICT_GET_OR_DEFAULT_COUNTS_AS_ACCESS, () -> {
            LruCache<String, Integer> c = new LruCache<>(3);
            c.put("A", 1);
            c.put("B", 2);
            c.getOrDefault("A", 0);
            return c.keySet().iterator().next().equals("B");
        });

        Check.equal("Manual: put A,B,C; get A; put D", List.of("D", "A", "C"), () -> {
            ManualLruCache<String, Integer> c = new ManualLruCache<>(3);
            c.put("A", 1);
            c.put("B", 2);
            c.put("C", 3);
            c.get("A");
            c.put("D", 4);
            return c.keysMostRecentFirst();
        });
        Check.equal("Manual: evicted key returns null", null, () -> {
            ManualLruCache<String, Integer> c = new ManualLruCache<>(1);
            c.put("A", 1);
            c.put("B", 2);
            return c.get("A");
        });
        Check.equal("Manual: update existing key moves it to front, keeps size", List.of(List.of("A", "B"), 10, 2), () -> {
            ManualLruCache<String, Integer> c = new ManualLruCache<>(2);
            c.put("A", 1);
            c.put("B", 2);
            c.put("A", 10);
            return List.of(c.keysMostRecentFirst(), c.get("A"), c.size());
        });
        Check.equal("Manual: miss returns null", null, () -> new ManualLruCache<String, Integer>(2).get("nope"));

        Check.summary();
    }
}
