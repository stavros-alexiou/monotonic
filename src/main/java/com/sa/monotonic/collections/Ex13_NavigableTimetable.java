package com.sa.monotonic.collections;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <h2>Exercise 13 - NavigableMap / NavigableSet: a bus timetable</h2>
 *
 * Use floor/ceiling/lower/higher, subMap/headMap/tailMap, descendingMap. Format entries as
 * "HH:mm route", e.g. "14:15 Line 3" (LocalTime.toString() already gives "14:15").
 *
 * <pre>
 * Q: headMap/tailMap/subMap return views. What happens when you put a key outside the range
 *    through such a view, and why?
 * A:
 * </pre>
 */
public class Ex13_NavigableTimetable {

    static final Integer PREDICT_HEADMAP_SIZE_AFTER_PUT = null;
    static final String PREDICT_SUBMAP_PUT_OUT_OF_RANGE = "???";

    static TreeMap<LocalTime, String> timetable() {
        TreeMap<LocalTime, String> t = new TreeMap<>();
        t.put(LocalTime.of(6, 45), "Line 1");
        t.put(LocalTime.of(7, 30), "Line 3");
        t.put(LocalTime.of(8, 0), "Line 1");
        t.put(LocalTime.of(8, 40), "Line 7");
        t.put(LocalTime.of(9, 15), "Line 3");
        t.put(LocalTime.of(10, 0), "Line 1");
        t.put(LocalTime.of(12, 30), "Line 7");
        t.put(LocalTime.of(14, 0), "Line 1");
        t.put(LocalTime.of(14, 15), "Line 3");
        t.put(LocalTime.of(17, 45), "Line 7");
        t.put(LocalTime.of(22, 10), "Night bus");
        return t;
    }

    private static String fmt(Map.Entry<LocalTime, String> e) {
        return e.getKey() + " " + e.getValue();
    }

    /** First departure at or after {@code time}. */
    static Optional<String> nextBusAtOrAfter(NavigableMap<LocalTime, String> t, LocalTime time) {
        // TODO: implement
        return Check.todo();
    }

    /** Last departure at or before {@code time}. */
    static Optional<String> lastBusAtOrBefore(NavigableMap<LocalTime, String> t, LocalTime time) {
        // TODO: implement
        return Check.todo();
    }

    /** Departure time strictly AFTER {@code time}. */
    static Optional<LocalTime> strictlyAfter(NavigableMap<LocalTime, String> t, LocalTime time) {
        // TODO: implement
        return Check.todo();
    }

    /** All departures between from and to, BOTH inclusive, formatted, in time order. */
    static List<String> busesBetween(NavigableMap<LocalTime, String> t, LocalTime from, LocalTime to) {
        // TODO: implement
        return Check.todo();
    }

    /** All departure times, latest first. */
    static List<LocalTime> departuresDescending(NavigableMap<LocalTime, String> t) {
        // TODO: implement
        return Check.todo();
    }

    /** Remove and return (formatted) the first departure of the day. */
    static Optional<String> takeFirstBus(NavigableMap<LocalTime, String> t) {
        // TODO: implement
        return Check.todo();
    }

    /** Value in the set closest to target. On a tie prefer the LOWER one. Set is non-empty. */
    static int closest(NavigableSet<Integer> set, int target) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex13 - NavigableMap timetable");

        Check.equal("next bus at/after 14:07", Optional.of("14:15 Line 3"),
                () -> nextBusAtOrAfter(timetable(), LocalTime.of(14, 7)));
        Check.equal("next bus at/after 14:00 (exact)", Optional.of("14:00 Line 1"),
                () -> nextBusAtOrAfter(timetable(), LocalTime.of(14, 0)));
        Check.equal("next bus after 23:00", Optional.empty(),
                () -> nextBusAtOrAfter(timetable(), LocalTime.of(23, 0)));
        Check.equal("last bus at/before 14:07", Optional.of("14:00 Line 1"),
                () -> lastBusAtOrBefore(timetable(), LocalTime.of(14, 7)));
        Check.equal("last bus before 06:00", Optional.empty(),
                () -> lastBusAtOrBefore(timetable(), LocalTime.of(6, 0)));
        Check.equal("strictly after 10:00", Optional.of(LocalTime.of(12, 30)),
                () -> strictlyAfter(timetable(), LocalTime.of(10, 0)));
        Check.equal("buses 08:00-10:00",
                List.of("08:00 Line 1", "08:40 Line 7", "09:15 Line 3", "10:00 Line 1"),
                () -> busesBetween(timetable(), LocalTime.of(8, 0), LocalTime.of(10, 0)));
        Check.equal("departuresDescending", timetable().navigableKeySet().descendingSet().stream().toList(),
                () -> departuresDescending(timetable()));
        Check.equal("takeFirstBus removes it", List.of("06:45 Line 1", "07:30"), () -> {
            TreeMap<LocalTime, String> t = timetable();
            String first = takeFirstBus(t).orElseThrow();
            return List.of(first, t.firstKey().toString());
        });

        NavigableSet<Integer> scores = new TreeSet<>(List.of(10, 20, 30, 45));
        Check.equal("closest 26", 30, () -> closest(scores, 26));
        Check.equal("closest 25 (tie -> lower)", 20, () -> closest(scores, 25));
        Check.equal("closest 5", 10, () -> closest(scores, 5));
        Check.equal("closest 99", 45, () -> closest(scores, 99));

        Check.predict("headMap(10:00) size after putting 09:50 into the BACKING map", PREDICT_HEADMAP_SIZE_AFTER_PUT, () -> {
            TreeMap<LocalTime, String> t = timetable();
            SortedMap<LocalTime, String> head = t.headMap(LocalTime.of(10, 0));
            t.put(LocalTime.of(9, 50), "Express");
            return head.size();
        });
        Check.predict("subMap(08:00..10:00).put(12:00, ..)", PREDICT_SUBMAP_PUT_OUT_OF_RANGE,
                () -> Check.thrownBy(() -> timetable()
                        .subMap(LocalTime.of(8, 0), true, LocalTime.of(10, 0), true)
                        .put(LocalTime.of(12, 0), "x")));

        Check.summary();
    }
}
