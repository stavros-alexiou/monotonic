package com.sa.monotonic.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

/**
 * <h2>Exercise 14 - Functional patterns with lambdas</h2>
 *
 * memoize, lazy, strategy map, retry, Consumer-configured builder, a tiny event bus.
 *
 * <pre>
 * Q1: Which classic GoF patterns become "just a lambda" in modern Java?
 * A1:
 *
 * Q2: Why does recursive memoization with HashMap.computeIfAbsent blow up? (see prediction)
 * A2:
 * </pre>
 */
public class Ex14_FunctionalPatterns {

    static final String PREDICT_RECURSIVE_COMPUTE_IF_ABSENT = "???";

    /** Cache results: the wrapped function is called at most once per distinct input. */
    static <T, R> Function<T, R> memoize(Function<T, R> f) {
        // TODO: implement
        return Check.todo();
    }

    /** Evaluate the supplier only on the FIRST get() and cache the value. */
    static <T> Supplier<T> lazy(Supplier<T> supplier) {
        // TODO: implement
        return Check.todo();
    }

    /** Strategy map: "+", "-", "*", "/" -> IntBinaryOperator. */
    static Map<String, IntBinaryOperator> operators() {
        // TODO: implement
        return Check.todo();
    }

    /** Evaluate "3 * 4" using operators(). Unknown operator -> IllegalArgumentException. */
    static int calc(String expression) {
        // TODO: implement
        return Check.todo();
    }

    /**
     * Call action until it succeeds, at most maxAttempts times. If every attempt throws,
     * rethrow the LAST exception.
     */
    static <T> T retry(Supplier<T> action, int maxAttempts) {
        // TODO: implement
        return Check.todo();
    }

    record HttpRequest(String url, String method, Map<String, String> headers, int timeoutSeconds) {
    }

    /** GIVEN: a mutable builder. */
    static final class RequestBuilder {
        private final String url;
        String method = "GET";
        final Map<String, String> headers = new LinkedHashMap<>();
        int timeoutSeconds = 30;

        RequestBuilder(String url) {
            this.url = url;
        }

        RequestBuilder header(String name, String value) {
            headers.put(name, value);
            return this;
        }

        HttpRequest build() {
            return new HttpRequest(url, method, Map.copyOf(headers), timeoutSeconds);
        }
    }

    /** request("https://x", b -> { b.method = "POST"; b.header("A", "1"); }) */
    static HttpRequest request(String url, Consumer<RequestBuilder> configure) {
        // TODO: implement
        return Check.todo();
    }

    /** Type-safe event bus: handlers subscribed for a class receive events of that class (exact class only). */
    static final class EventBus {
        private final Map<Class<?>, List<Consumer<Object>>> handlers = new HashMap<>();

        <T> void subscribe(Class<T> type, Consumer<? super T> handler) {
            // TODO: implement
            Check.todo();
        }

        /** Returns how many handlers received the event. */
        int publish(Object event) {
            // TODO: implement
            return Check.todo();
        }
    }

    record UserRegistered(String name) {
    }

    record OrderPlaced(int id) {
    }

    static int slowCalls;

    static Map<Integer, Long> fibCache = new HashMap<>();

    static long fibWithComputeIfAbsent(int n) {
        if (n < 2) return n;
        return fibCache.computeIfAbsent(n, k -> fibWithComputeIfAbsent(k - 1) + fibWithComputeIfAbsent(k - 2));
    }

    public static void main(String[] args) {
        Check.header("Ex14 - Functional patterns");

        Check.equal("memoize calls the function once per input", List.of(4, 4, 9, 2), () -> {
            slowCalls = 0;
            Function<Integer, Integer> square = memoize(x -> {
                slowCalls++;
                return x * x;
            });
            return List.of(square.apply(2), square.apply(2), square.apply(3), slowCalls);
        });
        Check.equal("lazy evaluates once, only on demand", List.of(0, "v", "v", 1), () -> {
            slowCalls = 0;
            Supplier<String> s = lazy(() -> {
                slowCalls++;
                return "v";
            });
            int before = slowCalls;
            return List.of(before, s.get(), s.get(), slowCalls);
        });
        Check.equal("calc", List.of(12, 1, 7, 3), () -> List.of(calc("3 * 4"), calc("4 - 3"), calc("3 + 4"), calc("10 / 3")));
        Check.throwsA("calc unknown operator", IllegalArgumentException.class, () -> calc("2 ^ 3"));

        Check.equal("retry succeeds on 3rd attempt", List.of("ok", 3), () -> {
            int[] attempts = {0};
            String r = retry(() -> {
                if (++attempts[0] < 3) throw new IllegalStateException("flaky #" + attempts[0]);
                return "ok";
            }, 5);
            return List.of(r, attempts[0]);
        });
        Check.equal("retry rethrows the last exception", "flaky #2", () -> {
            int[] attempts = {0};
            try {
                retry(() -> {
                    throw new IllegalStateException("flaky #" + ++attempts[0]);
                }, 2);
                return "no exception";
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
        });

        Check.equal("request builder via Consumer",
                new HttpRequest("https://api", "POST", Map.of("Auth", "t0k3n"), 5),
                () -> request("https://api", b -> {
                    b.method = "POST";
                    b.timeoutSeconds = 5;
                    b.header("Auth", "t0k3n");
                }));
        Check.equal("request defaults", new HttpRequest("https://x", "GET", Map.of(), 30), () -> request("https://x", b -> {
        }));

        Check.equal("event bus", List.of(2, 1, 0, "welcome Ann", "audit Ann", "ship 7"), () -> {
            EventBus bus = new EventBus();
            List<String> log = new ArrayList<>();
            bus.subscribe(UserRegistered.class, e -> log.add("welcome " + e.name()));
            bus.subscribe(UserRegistered.class, e -> log.add("audit " + e.name()));
            bus.subscribe(OrderPlaced.class, e -> log.add("ship " + e.id()));
            int a = bus.publish(new UserRegistered("Ann"));
            int b = bus.publish(new OrderPlaced(7));
            int c = bus.publish("nobody listens to strings");
            List<Object> out = new ArrayList<>(List.of(a, b, c));
            out.addAll(log);
            return out;
        });

        Check.predict("recursive memo with HashMap.computeIfAbsent (fib 30)", PREDICT_RECURSIVE_COMPUTE_IF_ABSENT, () -> {
            fibCache = new HashMap<>();
            return Check.thrownBy(() -> fibWithComputeIfAbsent(30));
        });

        Check.summary();
    }
}
