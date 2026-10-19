package com.sa.monotonic.streams;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * <h2>Exercise 03 - Composing functions: and/or/negate, andThen/compose, currying</h2>
 *
 * <pre>
 * Q1: f.andThen(g) vs f.compose(g) - which runs first?
 * A1:
 *
 * Q2: What is currying, and what is partial application?
 * A2:
 * </pre>
 */
public class Ex03_ComposingFunctions {

    // f = x + 1, g = x * 2, applied to 3
    static final Integer PREDICT_F_AND_THEN_G = null;
    static final Integer PREDICT_F_COMPOSE_G = null;

    /** One predicate that is true only if ALL are true (empty list -> always true). Use reduce + and. */
    static <T> Predicate<T> allOf(List<Predicate<T>> predicates) {
        // TODO: implement
        return Check.todo();
    }

    /** True if ANY is true (empty list -> always false). */
    static <T> Predicate<T> anyOf(List<Predicate<T>> predicates) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Valid password: length >= 8 AND has a digit AND has an upper-case letter
     * AND does NOT contain "password" (case-insensitive). Combine small predicates; use Predicate.not.
     */
    static Predicate<String> validPassword() {
        // TODO: implement
        return Check.todo();
    }

    /** Chain all operations left-to-right into one Function (empty -> identity). Use reduce + andThen. */
    static Function<String, String> pipeline(List<UnaryOperator<String>> ops) {
        // TODO: implement
        return Check.todo();
    }

    /** f applied n times: times(x -> x * 2, 3).apply(1) == 8 */
    static <T> Function<T, T> times(Function<T, T> f, int n) {
        // TODO: implement
        return Check.todo();
    }

    /** Curried addition: curriedAdd().apply(2).apply(3) == 5 */
    static Function<Integer, Function<Integer, Integer>> curriedAdd() {
        // TODO: implement
        return Check.todo();
    }

    /** Generic curry. */
    static <A, B, R> Function<A, Function<B, R>> curry(BiFunction<A, B, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Generic uncurry. */
    static <A, B, R> BiFunction<A, B, R> uncurry(Function<A, Function<B, R>> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Fix the first argument. */
    static <A, B, R> Function<B, R> partial(BiFunction<A, B, R> f, A a) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex03 - Composing functions");

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Check.predict("f.andThen(g).apply(3)", PREDICT_F_AND_THEN_G, () -> f.andThen(g).apply(3));
        Check.predict("f.compose(g).apply(3)", PREDICT_F_COMPOSE_G, () -> f.compose(g).apply(3));

        List<Predicate<Integer>> rules = List.of(n -> n > 0, n -> n % 2 == 0, n -> n < 100);
        Check.equal("allOf", List.of(true, false, false), () -> {
            Predicate<Integer> p = allOf(rules);
            return List.of(p.test(42), p.test(43), p.test(200));
        });
        Check.equal("allOf(empty)", true, () -> allOf(List.<Predicate<Integer>>of()).test(1));
        Check.equal("anyOf", List.of(true, false), () -> {
            Predicate<Integer> p = anyOf(List.of(n -> n < 0, n -> n > 1000));
            return List.of(p.test(-5), p.test(5));
        });
        Check.equal("anyOf(empty)", false, () -> anyOf(List.<Predicate<Integer>>of()).test(1));

        Check.equal("validPassword", List.of(true, false, false, false, false), () -> {
            Predicate<String> p = validPassword();
            return List.of(p.test("Secur3Pass"), p.test("short1A"), p.test("nouppercase1"),
                    p.test("NoDigitsHere"), p.test("MyPassword123"));
        });

        Check.equal("pipeline", "HELLO WORLD!", () -> pipeline(List.of(String::trim, String::toUpperCase, s -> s + "!"))
                .apply("  hello world "));
        Check.equal("pipeline(empty)", "same", () -> pipeline(List.of()).apply("same"));
        Check.equal("times", 8, () -> times((Integer x) -> x * 2, 3).apply(1));
        Check.equal("curriedAdd", 5, () -> curriedAdd().apply(2).apply(3));
        Check.equal("curry", "ab", () -> curry((String a, String b) -> a + b).apply("a").apply("b"));
        Check.equal("uncurry", 6, () -> uncurry(curriedAdd()).apply(2, 4));
        Check.equal("partial", "Hello, Ann", () -> partial((String greet, String name) -> greet + ", " + name, "Hello").apply("Ann"));

        Check.summary();
    }
}
