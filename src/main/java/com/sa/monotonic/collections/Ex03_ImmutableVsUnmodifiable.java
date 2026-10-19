package com.sa.monotonic.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * <h2>Exercise 03 - Immutable vs unmodifiable vs fixed-size</h2>
 *
 * <p>Part A: fill in the behaviour table (true / false) BEFORE running.
 * <p>Part B: implement the defensive-copy tasks.
 *
 * <pre>
 * Q: Explain "view" vs "copy" using Collections.unmodifiableList vs List.copyOf.
 * A:
 * </pre>
 */
public class Ex03_ImmutableVsUnmodifiable {

    // ---- Part A: behaviour table. Replace null with true / false. --------------------------
    static final Boolean LIST_OF_ADD_THROWS = null;
    static final Boolean LIST_OF_ALLOWS_NULL_ELEMENTS = null;
    static final Boolean LIST_OF_CONTAINS_NULL_THROWS = null;
    static final Boolean LIST_COPY_OF_REFLECTS_SOURCE_CHANGES = null;
    static final Boolean LIST_COPY_OF_A_LIST_OF_RETURNS_SAME_INSTANCE = null;
    static final Boolean UNMODIFIABLE_ADD_THROWS = null;
    static final Boolean UNMODIFIABLE_REFLECTS_SOURCE_CHANGES = null;
    static final Boolean ARRAYS_AS_LIST_SET_THROWS = null;
    static final Boolean ARRAYS_AS_LIST_ADD_THROWS = null;
    static final Boolean ARRAYS_AS_LIST_WRITES_THROUGH_TO_ARRAY = null;
    static final Boolean STREAM_TO_LIST_ALLOWS_NULL_ELEMENTS = null;

    // ---- Part B ----------------------------------------------------------------------------

    /** Return a copy that is immutable AND unaffected by later changes to {@code in}. */
    static List<String> defensiveCopy(List<String> in) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Make this record safe: later changes to the list passed in must NOT leak into the team,
     * and team.members().add(...) must throw UnsupportedOperationException.
     * Hint: compact constructor.
     */
    record Team(String name, List<String> members) {
        Team {
            // TODO: implement
        }
    }

    /**
     * A class that owns a mutable list but exposes a READ-ONLY LIVE view of it.
     * members() must reflect later join() calls but must not allow callers to modify it.
     */
    static class Club {
        private final List<String> members = new ArrayList<>();

        void join(String name) {
            members.add(name);
        }

        List<String> members() {
            // TODO: implement
            return Check.todo();
        }
    }

    private static boolean throwsAnything(Check.Code code) {
        return !"none".equals(Check.thrownBy(code));
    }

    public static void main(String[] args) {
        Check.header("Ex03 - Immutable vs unmodifiable");

        Check.predict("List.of add throws", LIST_OF_ADD_THROWS,
                () -> throwsAnything(() -> List.of(1).add(2)));
        Check.predict("List.of allows null", LIST_OF_ALLOWS_NULL_ELEMENTS,
                () -> !throwsAnything(() -> List.of(1, null)));
        Check.predict("List.of contains(null) throws", LIST_OF_CONTAINS_NULL_THROWS,
                () -> throwsAnything(() -> List.of(1, 2, 3).contains(null)));
        Check.predict("List.copyOf reflects source", LIST_COPY_OF_REFLECTS_SOURCE_CHANGES, () -> {
            List<Integer> src = new ArrayList<>(List.of(1));
            List<Integer> copy = List.copyOf(src);
            src.add(2);
            return copy.size() == 2;
        });
        Check.predict("List.copyOf(List.of(..)) same instance", LIST_COPY_OF_A_LIST_OF_RETURNS_SAME_INSTANCE, () -> {
            List<Integer> immutable = List.of(1, 2);
            return List.copyOf(immutable) == immutable;
        });
        Check.predict("unmodifiableList add throws", UNMODIFIABLE_ADD_THROWS,
                () -> throwsAnything(() -> Collections.unmodifiableList(new ArrayList<>()).add(1)));
        Check.predict("unmodifiableList reflects source", UNMODIFIABLE_REFLECTS_SOURCE_CHANGES, () -> {
            List<Integer> src = new ArrayList<>(List.of(1));
            List<Integer> view = Collections.unmodifiableList(src);
            src.add(2);
            return view.size() == 2;
        });
        Check.predict("Arrays.asList set throws", ARRAYS_AS_LIST_SET_THROWS,
                () -> throwsAnything(() -> Arrays.asList(1, 2).set(0, 9)));
        Check.predict("Arrays.asList add throws", ARRAYS_AS_LIST_ADD_THROWS,
                () -> throwsAnything(() -> Arrays.asList(1, 2).add(3)));
        Check.predict("Arrays.asList writes through", ARRAYS_AS_LIST_WRITES_THROUGH_TO_ARRAY, () -> {
            Integer[] array = {1, 2, 3};
            Arrays.asList(array).set(0, 99);
            return array[0] == 99;
        });
        Check.predict("stream().toList() allows null", STREAM_TO_LIST_ALLOWS_NULL_ELEMENTS,
                () -> !throwsAnything(() -> Stream.of(1, null).toList()));

        Check.isTrue("defensiveCopy is detached", () -> {
            List<String> src = new ArrayList<>(List.of("a"));
            List<String> copy = defensiveCopy(src);
            src.add("b");
            return copy.equals(List.of("a"));
        });
        Check.throwsA("defensiveCopy is immutable", UnsupportedOperationException.class,
                () -> defensiveCopy(new ArrayList<>(List.of("a"))).add("x"));

        Check.isTrue("Team is detached from caller's list", () -> {
            List<String> src = new ArrayList<>(List.of("Ann"));
            Team team = new Team("A", src);
            src.add("Mallory");
            return team.members().equals(List.of("Ann"));
        });
        Check.throwsA("Team.members() is immutable", UnsupportedOperationException.class,
                () -> new Team("A", new ArrayList<>(List.of("Ann"))).members().add("x"));

        Check.isTrue("Club.members() is a live view", () -> {
            Club club = new Club();
            List<String> view = club.members();
            club.join("Ann");
            return view.equals(List.of("Ann"));
        });
        Check.throwsA("Club.members() is read-only", UnsupportedOperationException.class,
                () -> new Club().members().add("x"));

        Check.summary();
    }
}
