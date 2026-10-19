package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <h2>Exercise 02 - Method references (all four kinds)</h2>
 *
 * Every method must return a METHOD REFERENCE (Type::method, obj::method, Type::new, Type[]::new).
 *
 * <pre>
 * Q1: Name the four kinds of method references and give an example of each from this file.
 * A1:
 *
 * Q2: String::compareToIgnoreCase fits Comparator&lt;String&gt; although it takes ONE argument. Why?
 * A2:
 *
 * Q3: A bound method reference evaluates its receiver when? A lambda when? (see predictions)
 * A3:
 * </pre>
 */
public class Ex02_MethodReferences {

    // "creation" or "call"
    static final String PREDICT_NPE_TIMING_BOUND_METHOD_REF = "???";
    static final String PREDICT_NPE_TIMING_LAMBDA = "???";
    // "first" or "second"
    static final String PREDICT_BOUND_REF_SEES = "???";
    static final String PREDICT_LAMBDA_SEES = "???";

    record Person(String name) {
    }

    record Point(int x, int y) {
    }

    /** static method reference */
    static Function<String, Integer> parser() {
        // TODO: implement
        return Check.todo();
    }

    /** unbound instance method reference */
    static Function<String, String> upper() {
        // TODO: implement
        return Check.todo();
    }

    /** bound instance method reference on the given set */
    static Predicate<String> isIn(Set<String> allowed) {
        // TODO: implement
        return Check.todo();
    }

    /** unbound, with an extra argument: (s, i) -> s.charAt(i) */
    static BiFunction<String, Integer, Character> charAt() {
        // TODO: implement
        return Check.todo();
    }

    static Comparator<String> caseInsensitive() {
        // TODO: implement
        return Check.todo();
    }

    /** constructor reference (one arg) */
    static Function<String, Person> personFactory() {
        // TODO: implement
        return Check.todo();
    }

    /** constructor reference (two args) */
    static BiFunction<Integer, Integer, Point> pointFactory() {
        // TODO: implement
        return Check.todo();
    }

    /** constructor reference for a collection */
    static Supplier<Map<String, Integer>> sortedMapFactory() {
        // TODO: implement
        return Check.todo();
    }

    /** array constructor reference */
    static IntFunction<String[]> stringArrayFactory() {
        // TODO: implement
        return Check.todo();
    }

    /** Use Stream.map with personFactory() and Person::name ... and return names upper-cased. */
    static List<String> upperNames(List<String> names) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex02 - Method references");

        Check.equal("parser", 42, () -> parser().apply("42"));
        Check.equal("upper", "JAVA", () -> upper().apply("java"));
        Check.equal("isIn", List.of(true, false), () -> {
            Predicate<String> p = isIn(Set.of("admin", "root"));
            return List.of(p.test("root"), p.test("guest"));
        });
        Check.equal("charAt", 'v', () -> charAt().apply("java", 2));
        Check.equal("caseInsensitive", List.of("apple", "Banana", "cherry"), () -> {
            List<String> l = new ArrayList<>(List.of("cherry", "Banana", "apple"));
            l.sort(caseInsensitive());
            return l;
        });
        Check.equal("personFactory", new Person("Ann"), () -> personFactory().apply("Ann"));
        Check.equal("pointFactory", new Point(1, 2), () -> pointFactory().apply(1, 2));
        Check.equal("sortedMapFactory", "{a=1, b=2}", () -> {
            Map<String, Integer> m = sortedMapFactory().get();
            m.put("b", 2);
            m.put("a", 1);
            return m.toString();
        });
        Check.equal("stringArrayFactory with toArray", new String[]{"a", "b"},
                () -> Stream.of("a", "b").toArray(stringArrayFactory()));
        Check.equal("upperNames", List.of("ANN", "BOB"), () -> upperNames(List.of("ann", "bob")));

        Check.predict("NPE timing: bound method ref on null", PREDICT_NPE_TIMING_BOUND_METHOD_REF, () -> {
            String s = null;
            try {
                Supplier<Integer> sup = s::length;
            } catch (NullPointerException e) {
                return "creation";
            }
            return "call";
        });
        Check.predict("NPE timing: lambda on null", PREDICT_NPE_TIMING_LAMBDA, () -> {
            String s = null;
            try {
                Supplier<Integer> sup = () -> s.length();
            } catch (NullPointerException e) {
                return "creation";
            }
            return "call";
        });

        StringBuilder[] holder = {new StringBuilder("first")};
        Supplier<String> ref = holder[0]::toString;
        Supplier<String> lambda = () -> holder[0].toString();
        holder[0] = new StringBuilder("second");
        Check.predict("bound method ref after receiver swap", PREDICT_BOUND_REF_SEES, ref::get);
        Check.predict("lambda after receiver swap", PREDICT_LAMBDA_SEES, lambda::get);

        Check.summary();
    }
}
