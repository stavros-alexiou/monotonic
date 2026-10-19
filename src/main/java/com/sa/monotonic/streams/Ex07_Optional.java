package com.sa.monotonic.streams;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <h2>Exercise 07 - Optional done right</h2>
 *
 * Rule of thumb: Optional is a RETURN type. Don't use it for fields or parameters (the records
 * below use plain nullable fields on purpose - wrap them with Optional.ofNullable).
 * No isPresent()/get() pairs allowed - use map / flatMap / filter / or / orElse...
 *
 * <pre>
 * Q1: orElse(x) vs orElseGet(() -> x) - when does it matter?
 * A1:
 *
 * Q2: map vs flatMap on Optional?
 * A2:
 * </pre>
 */
public class Ex07_Optional {

    static final Integer PREDICT_CALLS_ORELSE_WHEN_PRESENT = null;
    static final Integer PREDICT_CALLS_ORELSEGET_WHEN_PRESENT = null;
    static final String PREDICT_OPTIONAL_OF_NULL = "???";
    static final String PREDICT_EMPTY_GET = "???";
    static final Boolean PREDICT_MAP_RETURNING_NULL_IS_PRESENT = null;

    record City(String name) {
    }

    record Address(String street, City city /* nullable */) {
    }

    record User(String name, Address address /* nullable */, String email /* nullable */) {
    }

    /** user -> address -> city -> name, any of them may be null (including user). */
    static Optional<String> cityName(User user) {
        // TODO: implement
        return Check.todo();
    }

    static String cityNameOrUnknown(User user) {
        // TODO: implement
        return Check.todo();
    }

    static Optional<User> findUser(List<User> users, String name) {
        // TODO: implement
        return Check.todo();
    }

    /** Domain of a user's e-mail ("ann@acme.com" -> "acme.com"); empty if null or no '@'. */
    static Optional<String> emailDomain(User user) {
        // TODO: implement
        return Check.todo();
    }

    /** Domain of the user called name, found in the list. Hint: flatMap. */
    static Optional<String> emailDomainOf(List<User> users, String name) {
        // TODO: implement
        return Check.todo();
    }

    static Optional<Integer> parseInt(String s) {
        // TODO: implement
        return Check.todo();
    }

    /** Sum of every string that parses as an int, ignoring the rest. Hint: flatMap(Optional::stream). */
    static int sumOfParsable(List<String> values) {
        // TODO: implement
        return Check.todo();
    }

    /** Look in primary, then in fallback. Use Optional.or (Java 9). */
    static Optional<String> lookup(String key, Map<String, String> primary, Map<String, String> fallback) {
        // TODO: implement
        return Check.todo();
    }

    /** "Hello, NAME" if present else "Hello, stranger" - use ifPresentOrElse to fill the StringBuilder. */
    static String greet(Optional<String> name) {
        StringBuilder sb = new StringBuilder();
        // TODO: implement
        Check.todo();
        return sb.toString();
    }

    static int calls;

    static String fallback() {
        calls++;
        return "fallback";
    }

    public static void main(String[] args) {
        Check.header("Ex07 - Optional");

        User ann = new User("Ann", new Address("Ermou 1", new City("Athens")), "ann@acme.com");
        User bob = new User("Bob", new Address("Unknown st", null), "bob-at-nowhere");
        User cid = new User("Cid", null, null);
        List<User> users = List.of(ann, bob, cid);

        Check.equal("cityName full chain", Optional.of("Athens"), () -> cityName(ann));
        Check.equal("cityName null city", Optional.empty(), () -> cityName(bob));
        Check.equal("cityName null address", Optional.empty(), () -> cityName(cid));
        Check.equal("cityName null user", Optional.empty(), () -> cityName(null));
        Check.equal("cityNameOrUnknown", "Unknown", () -> cityNameOrUnknown(cid));
        Check.equal("findUser", Optional.of(bob), () -> findUser(users, "Bob"));
        Check.equal("findUser missing", Optional.empty(), () -> findUser(users, "Zed"));
        Check.equal("emailDomain", List.of(Optional.of("acme.com"), Optional.empty(), Optional.empty()),
                () -> List.of(emailDomain(ann), emailDomain(bob), emailDomain(cid)));
        Check.equal("emailDomainOf", List.of(Optional.of("acme.com"), Optional.empty()),
                () -> List.of(emailDomainOf(users, "Ann"), emailDomainOf(users, "Zed")));
        Check.equal("parseInt", List.of(Optional.of(42), Optional.empty()), () -> List.of(parseInt(" 42 "), parseInt("4x2")));
        Check.equal("sumOfParsable", 60, () -> sumOfParsable(List.of("10", "x", "20", "", "30")));
        Check.equal("lookup", List.of(Optional.of("p"), Optional.of("f"), Optional.empty()), () -> List.of(
                lookup("a", Map.of("a", "p"), Map.of("a", "f")),
                lookup("b", Map.of(), Map.of("b", "f")),
                lookup("c", Map.of(), Map.of())));
        Check.equal("greet", List.of("Hello, Ann", "Hello, stranger"), () -> List.of(greet(Optional.of("Ann")), greet(Optional.empty())));

        Check.predict("fallback() calls: orElse on a present Optional", PREDICT_CALLS_ORELSE_WHEN_PRESENT, () -> {
            calls = 0;
            Optional.of("value").orElse(fallback());
            return calls;
        });
        Check.predict("fallback() calls: orElseGet on a present Optional", PREDICT_CALLS_ORELSEGET_WHEN_PRESENT, () -> {
            calls = 0;
            Optional.of("value").orElseGet(Ex07_Optional::fallback);
            return calls;
        });
        Check.predict("Optional.of(null)", PREDICT_OPTIONAL_OF_NULL, () -> Check.thrownBy(() -> Optional.of(null)));
        Check.predict("Optional.empty().get()", PREDICT_EMPTY_GET, () -> Check.thrownBy(() -> Optional.empty().get()));
        Check.predict("Optional.of(\"x\").map(s -> null).isPresent()", PREDICT_MAP_RETURNING_NULL_IS_PRESENT,
                () -> Optional.of("x").map(s -> null).isPresent());

        Check.summary();
    }
}
