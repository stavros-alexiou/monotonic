package com.sa.monotonic.streams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <h2>Exercise 15 - Checked exceptions and lambdas</h2>
 *
 * java.util.function interfaces can't throw checked exceptions. Three strategies:
 * (1) wrap into an unchecked exception, (2) turn failures into values (a Result type),
 * (3) handle locally and filter out.
 *
 * <pre>
 * Q1: Why can't Function.apply throw IOException? What would happen to stream code if it could?
 * A1:
 *
 * Q2: When is a Result/Either type better than throwing?
 * A2:
 * </pre>
 */
public class Ex15_ExceptionsInLambdas {

    static final Integer PREDICT_ELEMENTS_SEEN_BEFORE_FAILURE = null;
    static final String PREDICT_EXCEPTION_ESCAPING_THE_STREAM = "???";

    @FunctionalInterface
    interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }

    /** Java 21 sealed interface + records: a value OR an error. */
    sealed interface Result<T> permits Success, Failure {
    }

    record Success<T>(T value) implements Result<T> {
    }

    record Failure<T>(String input, String error) implements Result<T> {
    }

    record ParseReport(List<Integer> values, List<String> errors) {
    }

    /** GIVEN: a method with a CHECKED exception. */
    static String readResource(String name) throws IOException {
        if (name.startsWith("missing")) throw new IOException("not found: " + name);
        return "content of " + name;
    }

    /** Wrap: checked exceptions become RuntimeException(cause = original). RuntimeExceptions pass through. */
    static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Never throws: Success(value) or Failure(input, exception message). */
    static <R> Function<String, Result<R>> attempt(ThrowingFunction<String, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** readResource on each name using unchecked(...). */
    static List<String> readAll(List<String> names) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Parse every string with Integer::parseInt via attempt(...); collect values and
     * "input: message" errors. Use a pattern-matching switch over Result (Java 21).
     */
    static ParseReport parseAll(List<String> inputs) {
        // TODO: implement
        return Check.todo();
    }

    /** Only the readable resources' contents; failures silently dropped (strategy 3, mapMulti or flatMap). */
    static List<String> readWhatYouCan(List<String> names) {
        // TODO: implement
        return Check.todo();
    }

    static int seen;

    public static void main(String[] args) {
        Check.header("Ex15 - Exceptions in lambdas");

        Check.equal("readAll ok", List.of("content of a", "content of b"), () -> readAll(List.of("a", "b")));
        Check.equal("readAll wraps IOException as cause", "IOException", () -> {
            try {
                readAll(List.of("a", "missing.txt"));
                return "nothing thrown";
            } catch (Check.TodoException todo) {
                throw todo;
            } catch (RuntimeException e) {
                return e.getCause() == null ? "no cause" : e.getCause().getClass().getSimpleName();
            }
        });
        Check.throwsA("unchecked lets RuntimeExceptions through unchanged", ArithmeticException.class,
                () -> unchecked((Integer x) -> 10 / x).apply(0));

        Check.equal("attempt success", new Success<>(42), () -> Ex15_ExceptionsInLambdas.<Integer>attempt(Integer::parseInt).apply("42"));
        Check.equal("attempt failure", new Failure<Integer>("x", "For input string: \"x\""),
                () -> Ex15_ExceptionsInLambdas.<Integer>attempt(Integer::parseInt).apply("x"));
        Check.equal("parseAll", new ParseReport(List.of(1, 3), List.of("two: For input string: \"two\"")),
                () -> parseAll(List.of("1", "two", "3")));
        Check.equal("readWhatYouCan", List.of("content of a", "content of c"),
                () -> readWhatYouCan(List.of("a", "missing-b", "c")));

        Check.predict("elements seen before the exception", PREDICT_ELEMENTS_SEEN_BEFORE_FAILURE, () -> {
            seen = 0;
            try {
                Stream.of("1", "2", "x", "4").peek(s -> seen++).map(Integer::parseInt).toList();
            } catch (RuntimeException ignored) {
            }
            return seen;
        });
        Check.predict("exception type escaping the stream", PREDICT_EXCEPTION_ESCAPING_THE_STREAM,
                () -> Check.thrownBy(() -> Stream.of("1", "x").map(Integer::parseInt).toList()));

        Check.summary();
    }
}
