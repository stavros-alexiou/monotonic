package com.sa.monotonic.streams;

import java.time.LocalDate;
import java.util.List;

/**
 * Shared sample data (a tiny web shop) used by Ex08, Ex09, Ex10 and Ex20.
 * Do not change it - the checks depend on it.
 *
 * <pre>
 * Customers: Ann (Athens), Bob (Patras), Cid (Athens), Dee (Thessaloniki), Eve (Patras - never ordered)
 * Products : Laptop 1200, Mouse 25 (Electronics) | Desk 300, Chair 150 (Furniture) | Java Book 45, Novel 15 (Books)
 * </pre>
 */
public final class Shop {

    public record Customer(String id, String name, String city) {
    }

    public record Product(String sku, String name, String category, double price) {
    }

    public record OrderLine(Product product, int qty) {
        public double amount() {
            return product.price() * qty;
        }
    }

    public record Order(int id, Customer customer, LocalDate date, List<OrderLine> lines) {
        public double total() {
            return lines.stream().mapToDouble(OrderLine::amount).sum();
        }
    }

    public static final Customer ANN = new Customer("c1", "Ann", "Athens");
    public static final Customer BOB = new Customer("c2", "Bob", "Patras");
    public static final Customer CID = new Customer("c3", "Cid", "Athens");
    public static final Customer DEE = new Customer("c4", "Dee", "Thessaloniki");
    public static final Customer EVE = new Customer("c5", "Eve", "Patras");

    public static final Product LAPTOP = new Product("P1", "Laptop", "Electronics", 1200);
    public static final Product MOUSE = new Product("P2", "Mouse", "Electronics", 25);
    public static final Product DESK = new Product("P3", "Desk", "Furniture", 300);
    public static final Product CHAIR = new Product("P4", "Chair", "Furniture", 150);
    public static final Product JAVA_BOOK = new Product("P5", "Java Book", "Books", 45);
    public static final Product NOVEL = new Product("P6", "Novel", "Books", 15);

    public static List<Customer> customers() {
        return List.of(ANN, BOB, CID, DEE, EVE);
    }

    public static List<Product> products() {
        return List.of(LAPTOP, MOUSE, DESK, CHAIR, JAVA_BOOK, NOVEL);
    }

    public static List<Order> orders() {
        return List.of(
                new Order(1, ANN, LocalDate.of(2026, 1, 5), List.of(new OrderLine(LAPTOP, 1), new OrderLine(MOUSE, 2))),
                new Order(2, BOB, LocalDate.of(2026, 1, 20), List.of(new OrderLine(NOVEL, 3))),
                new Order(3, ANN, LocalDate.of(2026, 2, 2), List.of(new OrderLine(JAVA_BOOK, 1), new OrderLine(NOVEL, 1))),
                new Order(4, CID, LocalDate.of(2026, 2, 14), List.of(new OrderLine(DESK, 1), new OrderLine(CHAIR, 2))),
                new Order(5, DEE, LocalDate.of(2026, 2, 28), List.of(new OrderLine(MOUSE, 1))),
                new Order(6, BOB, LocalDate.of(2026, 3, 3), List.of(new OrderLine(CHAIR, 1), new OrderLine(JAVA_BOOK, 2))),
                new Order(7, ANN, LocalDate.of(2026, 3, 15), List.of(new OrderLine(MOUSE, 1), new OrderLine(NOVEL, 2))),
                new Order(8, CID, LocalDate.of(2026, 3, 30), List.of(new OrderLine(LAPTOP, 1)))
        );
    }

    private Shop() {
    }
}
