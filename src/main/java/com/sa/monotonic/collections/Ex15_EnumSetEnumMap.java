package com.sa.monotonic.collections;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h2>Exercise 15 - EnumSet and EnumMap</h2>
 *
 * <pre>
 * Q: How are EnumSet and EnumMap implemented internally, and why are they faster and smaller than
 *    HashSet / HashMap with enum keys?
 * A:
 * </pre>
 */
public class Ex15_EnumSetEnumMap {

    enum Permission { READ, WRITE, DELETE, ADMIN, AUDIT }

    static final String PREDICT_ENUMSET_TOSTRING = "???";
    static final String PREDICT_ENUMMAP_PUT_NULL_KEY = "???";

    /** All permissions in either set (do not modify the inputs). */
    static EnumSet<Permission> union(Set<Permission> a, Set<Permission> b) {
        // TODO: implement
        return Check.todo();
    }

    static EnumSet<Permission> intersection(Set<Permission> a, Set<Permission> b) {
        // TODO: implement
        return Check.todo();
    }

    /** Every permission the role does NOT have. Hint: complementOf. */
    static EnumSet<Permission> missing(EnumSet<Permission> granted) {
        // TODO: implement
        return Check.todo();
    }

    /** READ..DELETE inclusive, using EnumSet.range. */
    static EnumSet<Permission> basicPermissions() {
        // TODO: implement
        return Check.todo();
    }

    static boolean canPerform(Set<Permission> granted, Permission... required) {
        // TODO: implement
        return Check.todo();
    }

    /** Encode as a bitmask: bit i set <=> permission with ordinal i present. {READ, DELETE} -> 0b101 = 5. */
    static int toBitmask(Set<Permission> perms) {
        // TODO: implement
        return Check.todo();
    }

    static EnumSet<Permission> fromBitmask(int mask) {
        // TODO: implement
        return Check.todo();
    }

    /** "Name:DAY" entries -> EnumMap day -> names (in input order). Must return an EnumMap. */
    static Map<DayOfWeek, List<String>> shifts(List<String> entries) {
        // TODO: implement
        return Check.todo();
    }

    /** Same input -> count per day, using groupingBy with an EnumMap factory. */
    static Map<DayOfWeek, Long> countPerDay(List<String> entries) {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex15 - EnumSet / EnumMap");
        EnumSet<Permission> editor = EnumSet.of(Permission.READ, Permission.WRITE);
        EnumSet<Permission> auditor = EnumSet.of(Permission.READ, Permission.AUDIT);

        Check.equal("union", "[READ, WRITE, AUDIT]", () -> union(editor, auditor).toString());
        Check.equal("intersection", "[READ]", () -> intersection(editor, auditor).toString());
        Check.equal("missing", "[DELETE, ADMIN, AUDIT]", () -> missing(editor).toString());
        Check.equal("basicPermissions", "[READ, WRITE, DELETE]", () -> basicPermissions().toString());
        Check.equal("canPerform yes", true, () -> canPerform(editor, Permission.READ, Permission.WRITE));
        Check.equal("canPerform no", false, () -> canPerform(editor, Permission.READ, Permission.ADMIN));
        Check.equal("toBitmask {READ, DELETE}", 5, () -> toBitmask(EnumSet.of(Permission.READ, Permission.DELETE)));
        Check.equal("toBitmask all", 31, () -> toBitmask(EnumSet.allOf(Permission.class)));
        Check.equal("fromBitmask 5", EnumSet.of(Permission.READ, Permission.DELETE), () -> fromBitmask(5));
        Check.equal("bitmask round trip", auditor, () -> fromBitmask(toBitmask(auditor)));

        List<String> roster = List.of("Ann:FRIDAY", "Bob:MONDAY", "Cid:FRIDAY", "Dee:WEDNESDAY");
        Check.equal("shifts in DAY order", "{MONDAY=[Bob], WEDNESDAY=[Dee], FRIDAY=[Ann, Cid]}",
                () -> shifts(roster).toString());
        Check.isTrue("shifts returns an EnumMap", () -> shifts(roster) instanceof EnumMap);
        Check.equal("countPerDay", "{MONDAY=1, WEDNESDAY=1, FRIDAY=2}", () -> countPerDay(roster).toString());
        Check.isTrue("countPerDay returns an EnumMap", () -> countPerDay(roster) instanceof EnumMap);

        Check.predict("EnumSet.of(AUDIT, READ, ADMIN).toString()", PREDICT_ENUMSET_TOSTRING,
                () -> EnumSet.of(Permission.AUDIT, Permission.READ, Permission.ADMIN).toString());
        Check.predict("EnumMap.put(null, ..)", PREDICT_ENUMMAP_PUT_NULL_KEY,
                () -> Check.thrownBy(() -> new EnumMap<DayOfWeek, String>(DayOfWeek.class).put(null, "x")));

        Check.summary();
    }
}
