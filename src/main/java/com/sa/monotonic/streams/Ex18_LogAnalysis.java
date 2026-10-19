package com.sa.monotonic.streams;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h2>Exercise 18 - Real-world: log file analysis</h2>
 *
 * Line format: {@code <timestamp> <LEVEL> <METHOD> <path> <latencyMs> <user>}.
 * Lines that do not match must be skipped (not crash!). Use LOG.lines().
 */
public class Ex18_LogAnalysis {

    static final String LOG = """
            2026-10-19T09:01:12 INFO GET /api/orders 120 ann
            2026-10-19T09:03:40 INFO GET /api/users 80 bob
            2026-10-19T09:15:02 WARN GET /api/orders 950 cid
            2026-10-19T09:47:55 ERROR POST /api/orders 30 ann
            2026-10-19T10:02:10 INFO GET /api/orders 200 bob
            2026-10-19T10:05:33 INFO POST /api/login 60 dee
            2026-10-19T10:20:00 ERROR GET /api/users 15 eve
            2026-10-19T10:59:59 INFO GET /api/orders 110 ann
            2026-10-19T11:00:00 INFO POST /api/login 40 ann
            2026-10-19T11:30:45 ERROR POST /api/orders 25 cid
            this line is garbage and must be skipped
            2026-10-19T11:45:12 INFO GET /api/users 90 cid
            """;

    record LogEntry(LocalDateTime timestamp, String level, String method, String path, int latencyMs, String user) {
    }

    /** Parse one line; empty Optional if it doesn't have 6 fields or any field fails to parse. */
    static Optional<LogEntry> parse(String line) {
        // TODO: implement
        return Check.todo();
    }

    static List<LogEntry> entries() {
        // TODO: implement
        return Check.todo();
    }

    static long invalidLineCount() {
        // TODO: implement
        return Check.todo();
    }

    static TreeMap<String, Long> countPerLevel() {
        // TODO: implement
        return Check.todo();
    }

    /** "METHOD path=count", count DESC then key ASC, top n. */
    static List<String> topEndpoints(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** path -> average latency, rounded with Math.round. */
    static TreeMap<String, Long> averageLatencyPerPath() {
        // TODO: implement
        return Check.todo();
    }

    /** hour of day -> number of requests */
    static TreeMap<Integer, Long> requestsPerHour() {
        // TODO: implement
        return Check.todo();
    }

    static Optional<LogEntry> slowestRequest() {
        // TODO: implement
        return Check.todo();
    }

    /** path -> sorted distinct users */
    static TreeMap<String, Set<String>> usersPerPath() {
        // TODO: implement
        return Check.todo();
    }

    /** Sorted users that produced at least one ERROR. */
    static List<String> usersWithErrors() {
        // TODO: implement
        return Check.todo();
    }

    /** Nearest-rank percentile: sort latencies, take the value at position ceil(p/100 * n) (1-based). */
    static int percentileLatency(int p) {
        // TODO: implement
        return Check.todo();
    }

    /** User with the most requests. */
    static Optional<String> busiestUser() {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex18 - Log analysis");

        Check.equal("entries", 11, () -> entries().size());
        Check.equal("invalidLineCount", 1L, Ex18_LogAnalysis::invalidLineCount);
        Check.equal("parse garbage", Optional.empty(), () -> parse("2026-99-99T00:00:00 INFO GET /x 1 u"));
        Check.equal("parse bad latency", Optional.empty(), () -> parse("2026-10-19T09:01:12 INFO GET /x fast u"));
        Check.equal("countPerLevel", "{ERROR=3, INFO=7, WARN=1}", () -> countPerLevel().toString());
        Check.equal("topEndpoints(3)", List.of("GET /api/orders=4", "GET /api/users=3", "POST /api/login=2"), () -> topEndpoints(3));
        Check.equal("averageLatencyPerPath", "{/api/login=50, /api/orders=239, /api/users=62}", () -> averageLatencyPerPath().toString());
        Check.equal("requestsPerHour", "{9=4, 10=4, 11=3}", () -> requestsPerHour().toString());
        Check.equal("slowestRequest", Optional.of("cid 950"), () -> slowestRequest().map(e -> e.user() + " " + e.latencyMs()));
        Check.equal("usersPerPath", "{/api/login=[ann, dee], /api/orders=[ann, bob, cid], /api/users=[bob, cid, eve]}",
                () -> usersPerPath().toString());
        Check.equal("usersWithErrors", List.of("ann", "cid", "eve"), Ex18_LogAnalysis::usersWithErrors);
        Check.equal("p90 latency", 200, () -> percentileLatency(90));
        Check.equal("p50 latency", 80, () -> percentileLatency(50));
        Check.equal("busiestUser", Optional.of("ann"), Ex18_LogAnalysis::busiestUser);

        Check.summary();
    }
}
