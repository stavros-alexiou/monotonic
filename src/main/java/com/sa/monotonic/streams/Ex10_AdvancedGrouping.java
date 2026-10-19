package com.sa.monotonic.streams;

import com.sa.monotonic.streams.Shop.Customer;
import com.sa.monotonic.streams.Shop.Order;
import com.sa.monotonic.streams.Shop.OrderLine;
import com.sa.monotonic.streams.Shop.Product;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <h2>Exercise 10 - Advanced grouping: multi-level, mapping, filtering, flatMapping, teeing</h2>
 *
 * Return TreeMaps where asked so the output order is predictable.
 *
 * <pre>
 * Q1: groupingBy(k, filtering(p, ...)) vs filter(p).collect(groupingBy(k)) - what's different in the result?
 * A1:
 *
 * Q2: What does Collectors.teeing (Java 12) do?
 * A2:
 * </pre>
 */
public class Ex10_AdvancedGrouping {

    record PriceRange(String cheapest, String mostExpensive) {
    }

    /** city -> customer names (in Shop order) */
    static TreeMap<String, java.util.List<String>> customersByCity() {
        // TODO: implement
        return Check.todo();
    }

    /** category -> revenue (flatten orders into lines first) */
    static TreeMap<String, Double> revenuePerCategory() {
        // TODO: implement
        return Check.todo();
    }

    /** product name -> total units sold */
    static Map<String, Integer> unitsPerProduct() {
        // TODO: implement
        return Check.todo();
    }

    /** customer name -> (YearMonth -> number of orders). Both levels TreeMaps. */
    static TreeMap<String, TreeMap<YearMonth, Long>> ordersPerCustomerPerMonth() {
        // TODO: implement
        return Check.todo();
    }

    /**
     * customer name -> ids of orders with total > 100. Customers WITH orders but no big ones must
     * still appear with an empty list. Hint: Collectors.filtering.
     */
    static TreeMap<String, java.util.List<Integer>> bigOrderIdsPerCustomer() {
        // TODO: implement
        return Check.todo();
    }

    /** customer name -> sorted set of product names bought. Hint: Collectors.flatMapping. */
    static TreeMap<String, Set<String>> productsPerCustomer() {
        // TODO: implement
        return Check.todo();
    }

    /** Cheapest and most expensive product names in ONE pass using Collectors.teeing. */
    static PriceRange priceRange() {
        // TODO: implement
        return Check.todo();
    }

    /** true -> number of orders with total >= 200, false -> the rest. */
    static Map<Boolean, Long> bigVsSmallOrderCount() {
        // TODO: implement
        return Check.todo();
    }

    /** city -> name of the customer who spent the most there (only customers with orders). */
    static TreeMap<String, String> topSpenderPerCity() {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex10 - Advanced grouping");

        Check.equal("customersByCity", "{Athens=[Ann, Cid], Patras=[Bob, Eve], Thessaloniki=[Dee]}",
                () -> customersByCity().toString());
        Check.equal("revenuePerCategory", "{Books=225.0, Electronics=2500.0, Furniture=750.0}",
                () -> revenuePerCategory().toString());
        Check.equal("unitsPerProduct", Map.of("Laptop", 2, "Mouse", 4, "Desk", 1, "Chair", 3, "Java Book", 3, "Novel", 6),
                Ex10_AdvancedGrouping::unitsPerProduct);
        Check.equal("ordersPerCustomerPerMonth",
                "{Ann={2026-01=1, 2026-02=1, 2026-03=1}, Bob={2026-01=1, 2026-03=1}, Cid={2026-02=1, 2026-03=1}, Dee={2026-02=1}}",
                () -> ordersPerCustomerPerMonth().toString());
        Check.equal("bigOrderIdsPerCustomer (Dee keeps an empty list)", "{Ann=[1], Bob=[6], Cid=[4, 8], Dee=[]}",
                () -> bigOrderIdsPerCustomer().toString());
        Check.equal("productsPerCustomer",
                "{Ann=[Java Book, Laptop, Mouse, Novel], Bob=[Chair, Java Book, Novel], Cid=[Chair, Desk, Laptop], Dee=[Mouse]}",
                () -> productsPerCustomer().toString());
        Check.equal("priceRange", new PriceRange("Novel", "Laptop"), Ex10_AdvancedGrouping::priceRange);
        Check.equal("bigVsSmallOrderCount", Map.of(true, 4L, false, 4L), Ex10_AdvancedGrouping::bigVsSmallOrderCount);
        Check.equal("topSpenderPerCity", "{Athens=Cid, Patras=Bob, Thessaloniki=Dee}", () -> topSpenderPerCity().toString());

        Check.summary();
    }
}
