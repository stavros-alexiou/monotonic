package com.sa.monotonic.collections;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.SequencedMap;
import java.util.TreeMap;

/**
 * <h2>Exercise 12 - SequencedMap: a "recent searches" list</h2>
 *
 * Keep the N most recent search terms, newest FIRST. Searching an existing term again moves it to
 * the front. When over capacity, evict the oldest. Use putFirst / pollLastEntry /
 * sequencedKeySet / firstEntry / lastEntry - no manual list bookkeeping.
 */
public class Ex12_SequencedMapRecentSearches {

    static final String PREDICT_TREEMAP_PUT_FIRST = "???";
    static final String PREDICT_REVERSED_FIRST_KEY = "???";
    static final String PREDICT_PUT_LAST_EXISTING = "???";

    static class RecentSearches {
        private final int capacity;
        private final LinkedHashMap<String, Instant> searches = new LinkedHashMap<>();

        RecentSearches(int capacity) {
            this.capacity = capacity;
        }

        /** Record a search: newest goes FIRST, re-searching moves to front, evict oldest beyond capacity. */
        void search(String term, Instant at) {
            // TODO: implement
            Check.todo();
        }

        /** Terms, newest first. */
        List<String> mostRecentFirst() {
            // TODO: implement
            return Check.todo();
        }

        /** Terms, oldest first - use a reversed view. */
        List<String> oldestFirst() {
            // TODO: implement
            return Check.todo();
        }

        Optional<String> newest() {
            // TODO: implement
            return Check.todo();
        }

        Optional<String> oldest() {
            // TODO: implement
            return Check.todo();
        }

        Optional<Instant> lastSearchedAt(String term) {
            // TODO: implement
            return Check.todo();
        }
    }

    public static void main(String[] args) {
        Check.header("Ex12 - SequencedMap recent searches");

        Instant t = Instant.parse("2026-10-19T12:00:00Z");
        Check.equal("a, b, c", List.of("c", "b", "a"), () -> {
            RecentSearches r = new RecentSearches(3);
            r.search("a", t);
            r.search("b", t.plusSeconds(1));
            r.search("c", t.plusSeconds(2));
            return r.mostRecentFirst();
        });
        Check.equal("re-search moves to front", List.of("a", "c", "b"), () -> {
            RecentSearches r = new RecentSearches(3);
            r.search("a", t);
            r.search("b", t);
            r.search("c", t);
            r.search("a", t);
            return r.mostRecentFirst();
        });
        Check.equal("eviction of the oldest", List.of("d", "a", "c"), () -> {
            RecentSearches r = new RecentSearches(3);
            for (String s : List.of("a", "b", "c", "a", "d")) r.search(s, t);
            return r.mostRecentFirst();
        });
        Check.equal("oldestFirst", List.of("c", "a", "d"), () -> {
            RecentSearches r = new RecentSearches(3);
            for (String s : List.of("a", "b", "c", "a", "d")) r.search(s, t);
            return r.oldestFirst();
        });
        Check.equal("newest / oldest", List.of(Optional.of("d"), Optional.of("c")), () -> {
            RecentSearches r = new RecentSearches(3);
            for (String s : List.of("a", "b", "c", "a", "d")) r.search(s, t);
            return List.of(r.newest(), r.oldest());
        });
        Check.equal("empty newest", Optional.empty(), () -> new RecentSearches(2).newest());
        Check.equal("lastSearchedAt updated", Optional.of(t.plusSeconds(5)), () -> {
            RecentSearches r = new RecentSearches(3);
            r.search("java", t);
            r.search("java", t.plusSeconds(5));
            return r.lastSearchedAt("java");
        });

        Check.predict("TreeMap.putFirst", PREDICT_TREEMAP_PUT_FIRST,
                () -> Check.thrownBy(() -> new TreeMap<String, Integer>().putFirst("a", 1)));
        Check.predict("{x=1,y=2,z=3}.reversed().firstEntry().getKey()", PREDICT_REVERSED_FIRST_KEY, () -> {
            SequencedMap<String, Integer> m = new LinkedHashMap<>();
            m.put("x", 1);
            m.put("y", 2);
            m.put("z", 3);
            return m.reversed().firstEntry().getKey();
        });
        Check.predict("{x=1,y=2,z=3}.putLast(\"x\", 9)", PREDICT_PUT_LAST_EXISTING, () -> {
            SequencedMap<String, Integer> m = new LinkedHashMap<>();
            m.put("x", 1);
            m.put("y", 2);
            m.put("z", 3);
            m.putLast("x", 9);
            return m.toString();
        });

        Check.summary();
    }
}
