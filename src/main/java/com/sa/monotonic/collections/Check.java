package com.sa.monotonic.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Tiny self-check harness so every exercise runs as a plain {@code main()} - no JUnit needed.
 *
 * <ul>
 *   <li>[PASS] - your code / prediction is right</li>
 *   <li>[FAIL] - wrong result, wrong exception, or an unexpected exception</li>
 *   <li>[TODO] - the method still calls {@link #todo()} or the PREDICT_ constant is still "???" / null</li>
 * </ul>
 */
public final class Check {

    /** Thrown by unimplemented exercise stubs. Counted as TODO, not as a failure. */
    public static final class TodoException extends RuntimeException {
        TodoException() {
            super("not implemented yet");
        }
    }

    @FunctionalInterface
    public interface Code {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface Value<T> {
        T get() throws Exception;
    }

    private static int pass, fail, todo;
    private static int totalPass, totalFail, totalTodo;

    private Check() {
    }

    /** Use in stubs: {@code return Check.todo();} or simply {@code Check.todo();} */
    public static <T> T todo() {
        throw new TodoException();
    }

    public static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    /** Passes when {@code Objects.deepEquals(expected, actual.get())}. */
    public static void equal(String name, Object expected, Value<?> actual) {
        try {
            Object a = actual.get();
            if (Objects.deepEquals(expected, a)) {
                ok(name);
            } else {
                bad(name, "expected <" + str(expected) + "> but was <" + str(a) + ">");
            }
        } catch (TodoException e) {
            pending(name);
        } catch (Throwable t) {
            bad(name, "threw " + t);
        }
    }

    public static void isTrue(String name, Value<Boolean> condition) {
        try {
            if (Boolean.TRUE.equals(condition.get())) {
                ok(name);
            } else {
                bad(name, "condition was false");
            }
        } catch (TodoException e) {
            pending(name);
        } catch (Throwable t) {
            bad(name, "threw " + t);
        }
    }

    /** Passes when the code throws an instance of {@code expected}. */
    public static void throwsA(String name, Class<? extends Throwable> expected, Code code) {
        try {
            code.run();
            bad(name, "expected " + expected.getSimpleName() + " but nothing was thrown");
        } catch (TodoException e) {
            pending(name);
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                ok(name);
            } else {
                bad(name, "expected " + expected.getSimpleName() + " but got " + t);
            }
        }
    }

    /**
     * Checks a prediction you wrote in a PREDICT_ constant.
     * {@code null} or {@code "???"} means "not answered yet" and is reported as TODO.
     */
    public static void predict(String name, Object prediction, Value<?> actual) {
        if (prediction == null || "???".equals(prediction)) {
            pending("predict: " + name + "  (fill in your PREDICT_ constant)");
            return;
        }
        equal("predict: " + name, prediction, actual);
    }

    /** Runs free-form demo / benchmark code. Prints exceptions but never counts as pass/fail. */
    public static void demo(String name, Code code) {
        System.out.println("  ~ demo: " + name);
        try {
            code.run();
        } catch (TodoException e) {
            pending(name);
        } catch (Throwable t) {
            System.out.println("    (demo threw " + t + ")");
        }
    }

    /** Simple name of the exception the code throws, or "none". Used to check predictions. */
    public static String thrownBy(Code code) {
        try {
            code.run();
            return "none";
        } catch (TodoException e) {
            throw e;
        } catch (Throwable t) {
            return t.getClass().getSimpleName();
        }
    }

    /** Prints the totals of the current exercise and resets the counters. */
    public static void summary() {
        System.out.printf("--- %d passed, %d failed, %d todo%n", pass, fail, todo);
        totalPass += pass;
        totalFail += fail;
        totalTodo += todo;
        pass = fail = todo = 0;
    }

    static void grandTotal() {
        System.out.println();
        System.out.printf("##### TOTAL: %d passed, %d failed, %d todo #####%n", totalPass, totalFail, totalTodo);
    }

    private static void ok(String name) {
        pass++;
        System.out.println("  [PASS] " + name);
    }

    private static void bad(String name, String why) {
        fail++;
        System.out.println("  [FAIL] " + name + " -> " + why);
    }

    private static void pending(String name) {
        todo++;
        System.out.println("  [TODO] " + name);
    }

    private static String str(Object o) {
        if (o instanceof Object[] arr) return Arrays.deepToString(arr);
        if (o instanceof int[] arr) return Arrays.toString(arr);
        if (o instanceof long[] arr) return Arrays.toString(arr);
        if (o instanceof double[] arr) return Arrays.toString(arr);
        return String.valueOf(o);
    }
}
