package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * <h2>Exercise 01 - Lambda basics and the core functional interfaces</h2>
 *
 * Every method returns a lambda. Write it as a LAMBDA (not a method reference - that's Ex02).
 *
 * <pre>
 * Q1: What makes an interface a "functional interface"? Is @FunctionalInterface required?
 * A1:
 *
 * Q2: Why must captured local variables be (effectively) final? How does int[] counter cheat?
 * A2:
 *
 * Q3: What does "this" mean inside a lambda vs inside an anonymous class?
 * A3:
 *
 * Compile puzzle - uncomment in compilePuzzle(). Does it compile? Why?
 * A4:
 * </pre>
 */
public class Ex01_LambdaBasics {

    static final String PREDICT_THIS_IN_LAMBDA = "???";
    static final String PREDICT_THIS_IN_ANONYMOUS_CLASS = "???";

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    static Predicate<Integer> isEven() {
        // TODO: implement
        return Check.todo();
    }

    static Function<String, Integer> length() {
        // TODO: implement
        return Check.todo();
    }

    /** () -> "Hello, NAME!" */
    static Supplier<String> greeting(String name) {
        // TODO: implement
        return Check.todo();
    }

    /** Adds the UPPER-CASED string to sink. */
    static Consumer<String> upperCaseInto(List<String> sink) {
        // TODO: implement
        return Check.todo();
    }

    /** base ^ exponent with ints, using a loop inside a block lambda (no Math.pow). */
    static BiFunction<Integer, Integer, Integer> power() {
        // TODO: implement
        return Check.todo();
    }

    /** Appends "!" */
    static UnaryOperator<String> shout() {
        // TODO: implement
        return Check.todo();
    }

    static BinaryOperator<Integer> maxOf() {
        // TODO: implement
        return Check.todo();
    }

    /** clamp(value, low, high) */
    static TriFunction<Integer, Integer, Integer, Integer> clamp() {
        // TODO: implement
        return Check.todo();
    }

    /** Euclid's gcd as a primitive specialisation (no boxing). */
    static IntBinaryOperator gcd() {
        // TODO: implement
        return Check.todo();
    }

    /** Each run() increments counter[0]. (Why does this compile while counter++ on an int would not?) */
    static Runnable incrementer(int[] counter) {
        // TODO: implement
        return Check.todo();
    }

    /** Compare strings by their LAST character. Write the lambda by hand (a, b) -> ... */
    static Comparator<String> byLastChar() {
        // TODO: implement
        return Check.todo();
    }

    // ---- "this" puzzle (given) -----------------------------------------------------------
    @Override
    public String toString() {
        return "outer";
    }

    Supplier<String> lambdaThis() {
        return () -> this.toString();
    }

    Supplier<String> anonymousThis() {
        return new Supplier<>() {
            @Override
            public String get() {
                return this.toString();
            }

            @Override
            public String toString() {
                return "anonymous";
            }
        };
    }

    @SuppressWarnings("unused")
    static void compilePuzzle() {
        int count = 0;
        List<String> names = new ArrayList<>();
        // names.forEach(n -> count++);
        // Runnable r = () -> System.out.println(count); count = 5;
    }

    public static void main(String[] args) {
        Check.header("Ex01 - Lambda basics");

        Check.equal("isEven", List.of(true, false), () -> List.of(isEven().test(4), isEven().test(7)));
        Check.equal("length", 5, () -> length().apply("hello"));
        Check.equal("greeting", "Hello, Stavros!", () -> greeting("Stavros").get());
        Check.equal("upperCaseInto", List.of("A", "B"), () -> {
            List<String> sink = new ArrayList<>();
            Consumer<String> c = upperCaseInto(sink);
            c.accept("a");
            c.accept("b");
            return sink;
        });
        Check.equal("power", 1024, () -> power().apply(2, 10));
        Check.equal("shout twice (andThen)", "hey!!", () -> shout().andThen(shout()).apply("hey"));
        Check.equal("maxOf", 9, () -> maxOf().apply(3, 9));
        Check.equal("clamp", List.of(5, 0, 10), () -> List.of(clamp().apply(5, 0, 10), clamp().apply(-3, 0, 10), clamp().apply(42, 0, 10)));
        Check.equal("gcd", 6, () -> gcd().applyAsInt(48, 18));
        Check.equal("incrementer", 3, () -> {
            int[] counter = {0};
            Runnable r = incrementer(counter);
            r.run();
            r.run();
            r.run();
            return counter[0];
        });
        Check.equal("byLastChar", List.of("banana", "apple", "kiwi"), () -> {
            List<String> l = new ArrayList<>(List.of("apple", "kiwi", "banana"));
            l.sort(byLastChar());
            return l;
        });

        Ex01_LambdaBasics outer = new Ex01_LambdaBasics();
        Check.predict("this inside a lambda", PREDICT_THIS_IN_LAMBDA, () -> outer.lambdaThis().get());
        Check.predict("this inside an anonymous class", PREDICT_THIS_IN_ANONYMOUS_CLASS, () -> outer.anonymousThis().get());

        Check.summary();
    }
}
