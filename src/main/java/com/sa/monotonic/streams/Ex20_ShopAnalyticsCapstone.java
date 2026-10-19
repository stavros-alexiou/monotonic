package com.sa.monotonic.streams;

import com.sa.monotonic.streams.Shop.Customer;
import com.sa.monotonic.streams.Shop.Order;
import com.sa.monotonic.streams.Shop.OrderLine;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <h2>Exercise 20 - Capstone: shop analytics</h2>
 *
 * Everything with streams and collectors over {@link Shop}. No loops except where you really
 * want one. Number formatting: ALWAYS {@code String.format(Locale.ROOT, ...)} - otherwise your
 * Greek locale prints "47,1" instead of "47.1" and the checks fail.
 *
 * <pre>
 * Reflection: which of these would you rather write as SQL? Which streams are hard to read,
 * and how would you refactor them (helper methods, records, named collectors)?
 * A:
 * </pre>
 */
public class Ex20_ShopAnalyticsCapstone {

    record CustomerSummary(String name, long orders, double spend, LocalDate firstOrder, String favouriteCategory) {
    }

    /** Month -> revenue. */
    static TreeMap<YearMonth, Double> revenuePerMonth() {
        // TODO: implement
        return Check.todo();
    }

    /** Names of the n biggest spenders, biggest first. */
    static List<String> topCustomers(int n) {
        // TODO: implement
        return Check.todo();
    }

    /** category -> name of the product with the most UNITS sold in that category. */
    static TreeMap<String, String> bestSellerPerCategory() {
        // TODO: implement
        return Check.todo();
    }

    /** Sorted names of customers (ALL customers, even those with no orders) who never bought from the category. */
    static List<String> neverBoughtCategory(String category) {
        // TODO: implement
        return Check.todo();
    }

    /** Total units / number of orders. */
    static double averageItemsPerOrder() {
        // TODO: implement
        return Check.todo();
    }

    /** customers with >= 2 orders / customers with >= 1 order */
    static double repeatCustomerRate() {
        // TODO: implement
        return Check.todo();
    }

    /** Month-over-month revenue change as "+118.2%" / "-47.1%" (Locale.ROOT, "%+.1f%%"), oldest first. */
    static List<String> monthOverMonthGrowth() {
        // TODO: implement
        return Check.todo();
    }

    /**
     * One summary per customer WITH orders, sorted by spend DESC. favouriteCategory = category with
     * the highest spend for that customer.
     */
    static List<CustomerSummary> customerSummaries() {
        // TODO: implement
        return Check.todo();
    }

    /**
     * A small text report, one line per month: "2026-01 | orders 2 | revenue 1295.00 | best Ann".
     * best = customer with the highest spend in that month. Use Locale.ROOT, "%.2f".
     */
    static List<String> monthlyReport() {
        // TODO: implement
        return Check.todo();
    }

    public static void main(String[] args) {
        Check.header("Ex20 - Shop analytics capstone");

        Check.equal("revenuePerMonth", "{2026-01=1295.0, 2026-02=685.0, 2026-03=1495.0}", () -> revenuePerMonth().toString());
        Check.equal("topCustomers(3)", List.of("Cid", "Ann", "Bob"), () -> topCustomers(3));
        Check.equal("bestSellerPerCategory", "{Books=Novel, Electronics=Mouse, Furniture=Chair}", () -> bestSellerPerCategory().toString());
        Check.equal("neverBoughtCategory(Electronics)", List.of("Bob", "Eve"), () -> neverBoughtCategory("Electronics"));
        Check.equal("neverBoughtCategory(Furniture)", List.of("Ann", "Dee", "Eve"), () -> neverBoughtCategory("Furniture"));
        Check.equal("averageItemsPerOrder", 2.375, Ex20_ShopAnalyticsCapstone::averageItemsPerOrder);
        Check.equal("repeatCustomerRate", 0.75, Ex20_ShopAnalyticsCapstone::repeatCustomerRate);
        Check.equal("monthOverMonthGrowth", List.of("-47.1%", "+118.2%"), Ex20_ShopAnalyticsCapstone::monthOverMonthGrowth);
        Check.equal("customerSummaries", List.of(
                        new CustomerSummary("Cid", 2, 1800.0, LocalDate.of(2026, 2, 14), "Electronics"),
                        new CustomerSummary("Ann", 3, 1365.0, LocalDate.of(2026, 1, 5), "Electronics"),
                        new CustomerSummary("Bob", 2, 285.0, LocalDate.of(2026, 1, 20), "Furniture"),
                        new CustomerSummary("Dee", 1, 25.0, LocalDate.of(2026, 2, 28), "Electronics")),
                Ex20_ShopAnalyticsCapstone::customerSummaries);
        Check.equal("monthlyReport", List.of(
                        "2026-01 | orders 2 | revenue 1295.00 | best Ann",
                        "2026-02 | orders 3 | revenue 685.00 | best Cid",
                        "2026-03 | orders 3 | revenue 1495.00 | best Cid"),
                Ex20_ShopAnalyticsCapstone::monthlyReport);

        Check.summary();
    }
}
