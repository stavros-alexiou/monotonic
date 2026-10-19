package com.sa.monotonic.collections;

import java.util.AbstractList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * <h2>Exercise 16 - Your own List: RingBuffer extends AbstractList</h2>
 *
 * Fixed capacity; add() appends, and when full it OVERWRITES the oldest element.
 * Implement ONLY get, size, set and add(E). Everything else (contains, indexOf, iterator, equals,
 * hashCode, toString, stream, reversed, getFirst/getLast ...) is inherited for free.
 * Make the iterator fail-fast by incrementing the inherited {@code modCount} on structural changes.
 *
 * <pre>
 * Q1: Which methods does AbstractList build on top of get/size? How does its iterator detect CME?
 * A1:
 *
 * Q2: Is set() a structural modification? Should it touch modCount?
 * A2:
 * </pre>
 */
public class Ex16_RingBuffer {

    static final String PREDICT_REMOVE_ON_RING_BUFFER = "???";

    static class RingBuffer<E> extends AbstractList<E> {
        private final Object[] items;
        private int head; // index in items of the OLDEST element
        private int size;

        RingBuffer(int capacity) {
            if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
            this.items = new Object[capacity];
        }

        /** index 0 = oldest element. Out of range -> IndexOutOfBoundsException (Objects.checkIndex). */
        @Override
        @SuppressWarnings("unchecked")
        public E get(int index) {
            // TODO: implement
            return Check.todo();
        }

        @Override
        public int size() {
            // TODO: implement
            return Check.todo();
        }

        @Override
        @SuppressWarnings("unchecked")
        public E set(int index, E element) {
            // TODO: implement
            return Check.todo();
        }

        /** Append; when full overwrite the oldest. Always returns true. Remember modCount++. */
        @Override
        public boolean add(E e) {
            // TODO: implement
            return Check.todo();
        }
    }

    private static RingBuffer<Integer> oneToFiveInCapacity3() {
        RingBuffer<Integer> rb = new RingBuffer<>(3);
        for (int i = 1; i <= 5; i++) rb.add(i);
        return rb;
    }

    public static void main(String[] args) {
        Check.header("Ex16 - RingBuffer extends AbstractList");

        Check.equal("keeps only the last 3", List.of(3, 4, 5), () -> List.copyOf(oneToFiveInCapacity3()));
        Check.equal("size", 3, () -> oneToFiveInCapacity3().size());
        Check.equal("partially filled", List.of(7), () -> {
            RingBuffer<Integer> rb = new RingBuffer<>(3);
            rb.add(7);
            return List.copyOf(rb);
        });
        Check.equal("inherited toString", "[3, 4, 5]", () -> oneToFiveInCapacity3().toString());
        Check.equal("inherited equals", true, () -> oneToFiveInCapacity3().equals(List.of(3, 4, 5)));
        Check.equal("inherited hashCode", List.of(3, 4, 5).hashCode(), () -> oneToFiveInCapacity3().hashCode());
        Check.equal("inherited contains / indexOf", List.of(true, false, 1),
                () -> {
                    RingBuffer<Integer> rb = oneToFiveInCapacity3();
                    return List.of(rb.contains(4), rb.contains(1), rb.indexOf(4));
                });
        Check.equal("inherited stream", 12, () -> oneToFiveInCapacity3().stream().mapToInt(Integer::intValue).sum());
        Check.equal("inherited reversed() (Java 21!)", List.of(5, 4, 3), () -> List.copyOf(oneToFiveInCapacity3().reversed()));
        Check.equal("inherited getFirst/getLast", List.of(3, 5), () -> {
            RingBuffer<Integer> rb = oneToFiveInCapacity3();
            return List.of(rb.getFirst(), rb.getLast());
        });
        Check.equal("set returns old value", List.of(4, List.of(3, 99, 5)), () -> {
            RingBuffer<Integer> rb = oneToFiveInCapacity3();
            Integer old = rb.set(1, 99);
            return List.of(old, List.copyOf(rb));
        });
        Check.throwsA("get out of range", IndexOutOfBoundsException.class, () -> oneToFiveInCapacity3().get(3));
        Check.throwsA("fail-fast iterator", ConcurrentModificationException.class, () -> {
            RingBuffer<Integer> rb = new RingBuffer<>(3);
            rb.add(1);
            rb.add(2);
            for (Integer i : rb) rb.add(i);
        });
        Check.isTrue("set() does NOT break an ongoing iteration", () -> {
            RingBuffer<Integer> rb = oneToFiveInCapacity3();
            int i = 0;
            for (Integer ignored : rb) rb.set(i++, 0);
            return rb.equals(List.of(0, 0, 0));
        });
        Check.predict("rb.remove(0)", PREDICT_REMOVE_ON_RING_BUFFER,
                () -> Check.thrownBy(() -> oneToFiveInCapacity3().remove(0)));

        Check.summary();
    }
}
