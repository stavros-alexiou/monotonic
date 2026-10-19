package com.sa.monotonic.collections;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * <h2>Exercise 20 - Capstone: a limit order book + a benchmark</h2>
 *
 * <b>Part 1 - OrderBook</b>
 * <ul>
 *   <li>bids: TreeMap&lt;price, Deque&lt;Order&gt;&gt; with REVERSE order (best = highest first)</li>
 *   <li>asks: TreeMap&lt;price, Deque&lt;Order&gt;&gt; natural order (best = lowest first)</li>
 *   <li>index: HashMap&lt;id, Order&gt; for O(1) cancel lookup</li>
 * </ul>
 * submit(): match against the best opposite price while prices cross (BUY price &gt;= best ask,
 * SELL price &lt;= best bid). Within a price level FIFO. Trades execute at the RESTING order's price.
 * Partial fills allowed; any remainder rests in the book. Empty price levels must be removed.
 *
 * <p><b>Part 2 - benchmark()</b>: with System.nanoTime (or JMH if you like) compare
 * ArrayList vs LinkedList for get(i), insert in the middle, full iteration; and HashMap with
 * {@link BadKey} (constant hashCode) vs {@link GoodKey} at 100k keys. Write 3 conclusions below.
 *
 * <pre>
 * Conclusion 1:
 * Conclusion 2:
 * Conclusion 3:
 * </pre>
 */
public class Ex20_OrderBookCapstone {

    enum Side { BUY, SELL }

    record Order(long id, Side side, BigDecimal price, int qty) {
        Order withQty(int newQty) {
            return new Order(id, side, price, newQty);
        }
    }

    record Trade(long buyId, long sellId, BigDecimal price, int qty) {
    }

    static final class OrderBook {
        private final NavigableMap<BigDecimal, Deque<Order>> bids = new TreeMap<>(Comparator.reverseOrder());
        private final NavigableMap<BigDecimal, Deque<Order>> asks = new TreeMap<>();
        private final Map<Long, Order> index = new HashMap<>();

        List<Trade> submit(Order order) {
            // TODO: implement
            return Check.todo();
        }

        /** true if the order was resting and has been removed. */
        boolean cancel(long id) {
            // TODO: implement
            return Check.todo();
        }

        Optional<BigDecimal> bestBid() {
            // TODO: implement
            return Check.todo();
        }

        Optional<BigDecimal> bestAsk() {
            // TODO: implement
            return Check.todo();
        }

        /** Best price first, each level as "price x totalQty", e.g. "101.00 x 8". */
        List<String> depth(Side side) {
            // TODO: implement
            return Check.todo();
        }
    }

    /** Terrible hashCode on purpose: every key collides. */
    record BadKey(int id) {
        @Override
        public int hashCode() {
            return 42;
        }
    }

    record GoodKey(int id) {
    }

    static void benchmark() {
        // TODO: implement
        Check.todo();
    }

    // TODO: implement

    private static BigDecimal p(String s) {
        return new BigDecimal(s);
    }

    public static void main(String[] args) {
        Check.header("Ex20 - Order book capstone");

        Check.equal("no cross -> no trades, both sides rest", List.of(List.of(), Optional.of(p("99.00")), Optional.of(p("100.50"))), () -> {
            OrderBook b = new OrderBook();
            List<Trade> all = new ArrayList<>();
            all.addAll(b.submit(new Order(1, Side.SELL, p("101.00"), 10)));
            all.addAll(b.submit(new Order(2, Side.SELL, p("100.50"), 5)));
            all.addAll(b.submit(new Order(3, Side.SELL, p("100.50"), 5)));
            all.addAll(b.submit(new Order(4, Side.BUY, p("99.00"), 7)));
            return List.of(all, b.bestBid(), b.bestAsk());
        });

        Check.equal("aggressive BUY sweeps two levels, FIFO within a level",
                List.of(new Trade(5, 2, p("100.50"), 5), new Trade(5, 3, p("100.50"), 5), new Trade(5, 1, p("101.00"), 2)),
                () -> scenario().submit(new Order(5, Side.BUY, p("101.00"), 12)));

        Check.equal("partial fill leaves remainder; empty levels removed", List.of("101.00 x 8"), () -> {
            OrderBook b = scenario();
            b.submit(new Order(5, Side.BUY, p("101.00"), 12));
            return b.depth(Side.SELL);
        });

        Check.equal("cancel", List.of(true, false, false, Optional.empty()), () -> {
            OrderBook b = scenario();
            b.submit(new Order(5, Side.BUY, p("101.00"), 12));
            return List.of(b.cancel(1), b.cancel(1), b.cancel(999), b.bestAsk());
        });

        Check.equal("SELL crosses the bid, remainder rests on the ask side",
                List.of(List.of(new Trade(4, 6, p("99.00"), 7)), Optional.empty(), List.of("98.00 x 3")), () -> {
                    OrderBook b = scenario();
                    b.submit(new Order(5, Side.BUY, p("101.00"), 12));
                    b.cancel(1);
                    List<Trade> trades = b.submit(new Order(6, Side.SELL, p("98.00"), 10));
                    return List.of(trades, b.bestBid(), b.depth(Side.SELL));
                });

        Check.equal("bid depth sorted best (highest) first", List.of("99.50 x 3", "99.00 x 7", "98.00 x 1"), () -> {
            OrderBook b = new OrderBook();
            b.submit(new Order(1, Side.BUY, p("99.00"), 4));
            b.submit(new Order(2, Side.BUY, p("98.00"), 1));
            b.submit(new Order(3, Side.BUY, p("99.50"), 3));
            b.submit(new Order(4, Side.BUY, p("99.00"), 3));
            return b.depth(Side.BUY);
        });

        Check.isTrue("cancel the middle order of a level keeps FIFO of the rest", () -> {
            OrderBook b = new OrderBook();
            b.submit(new Order(1, Side.SELL, p("10.00"), 1));
            b.submit(new Order(2, Side.SELL, p("10.00"), 1));
            b.submit(new Order(3, Side.SELL, p("10.00"), 1));
            b.cancel(2);
            List<Trade> t = b.submit(new Order(9, Side.BUY, p("10.00"), 2));
            Set<Long> sellers = new HashSet<>();
            for (Iterator<Trade> it = t.iterator(); it.hasNext(); ) sellers.add(it.next().sellId());
            return t.size() == 2 && t.get(0).sellId() == 1 && sellers.equals(Set.of(1L, 3L));
        });

        Check.demo("benchmark", Ex20_OrderBookCapstone::benchmark);

        Check.summary();
    }

    /** Asks: 100.50 [#2 x5, #3 x5], 101.00 [#1 x10]. Bids: 99.00 [#4 x7]. */
    private static OrderBook scenario() {
        OrderBook b = new OrderBook();
        b.submit(new Order(1, Side.SELL, p("101.00"), 10));
        b.submit(new Order(2, Side.SELL, p("100.50"), 5));
        b.submit(new Order(3, Side.SELL, p("100.50"), 5));
        b.submit(new Order(4, Side.BUY, p("99.00"), 7));
        return b;
    }
}
