package com.sa.monotonic.streams;

import com.sa.monotonic.streams.Shop.Customer;
import com.sa.monotonic.streams.Shop.Order;
import com.sa.monotonic.streams.Shop.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h2>Exercise 09 - Collectors basics (Shop data)</h2>
 *
 * Use {@code Shop.customers()}, {@code Shop.products()}, {@code Shop.orders()} and one Collector each.
 *
 * <pre>
 * Q: Collectors.toList() vs Collectors.toUnmodifiableList() vs Stream.toList() - mutability and nulls?
 * A:
 * </pre>
 */
public class Ex09_CollectorsBasics {

    static final String PREDICT_TOMAP_DUPLICATE_KEY = "???";
    static final String PREDICT_TOMAP_NULL_VALUE = "???";
    static final String PREDICT_TOUNMODIFIABLELIST_WITH_NULL = "???";

    /** Customer names sorted, into a TreeSet (toCollection). */
    static TreeSet<String> customerNames() {
        // TODO: implement
        return Check.todo();
    }

    /** sku -> product name */
    static Map<String, String> skuToName() {
        // TODO: implement
        return Check.todo();
    }

    /** customer name -> Customer (Function.identity()) */
    static Map<String, Customer> customerByName() {
        // TODO: implement
        return Check.todo();
    }

    /** product name -> price, iteration order = most expensive first. Hint: 4-arg toMap + LinkedHashMap. */
    static LinkedHashMap<String, Double> pricesMostExpensiveFirst() {
        // TODO: implement
        return Check.todo();
    }

    /** "[Novel, Mouse, ...]" names sorted by price ascending, joining with delimiter, prefix and suffix. */
    static String productNamesByPrice() {
        // TODO: implement
        return Check.todo();
    }

    static double totalRevenue() {
        // TODO: implement
        return Check.todo();
    }

    static long orderCount() {
        // TODO: implement
        return Check.todo();
    }

    static double averageOrderValue() {
        // TODO: implement
        return Check.todo();
    }

    /** Order ids as an UNMODIFIABLE list (Collectors.toUnmodifiableList). */
    static List<Integer> orderIds() {
        // TODO: implement
        return Check.todo();
    }

    /** Sorted categories wrapped as an unmodifiable SortedSet (collectingAndThen). */
    static SortedSet<String> categories() {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex09 - Collectors basics");

        Check.equal("customerNames", "[Ann, Bob, Cid, Dee, Eve]", () -> customerNames().toString());
        Check.equal("skuToName", "Desk", () -> skuToName().get("P3"));
        Check.equal("skuToName size", 6, () -> skuToName().size());
        Check.equal("customerByName", "Patras", () -> customerByName().get("Eve").city());
        Check.equal("pricesMostExpensiveFirst order", List.of("Laptop", "Desk", "Chair", "Java Book", "Mouse", "Novel"),
                () -> new ArrayList<>(pricesMostExpensiveFirst().keySet()));
        Check.equal("productNamesByPrice", "[Novel, Mouse, Java Book, Chair, Desk, Laptop]", Ex09_CollectorsBasics::productNamesByPrice);
        Check.equal("totalRevenue", 3475.0, Ex09_CollectorsBasics::totalRevenue);
        Check.equal("orderCount", 8L, Ex09_CollectorsBasics::orderCount);
        Check.equal("averageOrderValue", 434.375, Ex09_CollectorsBasics::averageOrderValue);
        Check.equal("orderIds", List.of(1, 2, 3, 4, 5, 6, 7, 8), Ex09_CollectorsBasics::orderIds);
        Check.throwsA("orderIds is unmodifiable", UnsupportedOperationException.class, () -> orderIds().add(9));
        Check.equal("categories", "[Books, Electronics, Furniture]", () -> categories().toString());
        Check.throwsA("categories is unmodifiable", UnsupportedOperationException.class, () -> categories().add("Toys"));

        Check.predict("toMap with duplicate keys", PREDICT_TOMAP_DUPLICATE_KEY, () -> Check.thrownBy(() ->
                Shop.customers().stream().collect(Collectors.toMap(Customer::city, Customer::name))));
        Check.predict("toMap with a null value", PREDICT_TOMAP_NULL_VALUE, () -> Check.thrownBy(() ->
                Stream.of("a").collect(Collectors.toMap(s -> s, s -> (String) null))));
        Check.predict("toUnmodifiableList with a null element", PREDICT_TOUNMODIFIABLELIST_WITH_NULL, () -> Check.thrownBy(() ->
                Stream.of("a", null).collect(Collectors.toUnmodifiableList())));

        Check.summary();
    }
}
