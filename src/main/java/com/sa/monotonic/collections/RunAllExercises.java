package com.sa.monotonic.collections;

/** Runs every exercise and prints a grand total. Each ExNN class can also be run on its own. */
public class RunAllExercises {

    public static void main(String[] args) throws Exception {
        String[] none = new String[0];
        Ex01_ListBasics.main(none);
        Ex02_IterationAndCme.main(none);
        Ex03_ImmutableVsUnmodifiable.main(none);
        Ex04_EqualsAndHashCode.main(none);
        Ex05_ChoosingASet.main(none);
        Ex06_Comparators.main(none);
        Ex07_WordFrequency.main(none);
        Ex08_Grouping.main(none);
        Ex09_DequeStackQueue.main(none);
        Ex10_PriorityQueue.main(none);
        Ex11_SequencedCollections.main(none);
        Ex12_SequencedMapRecentSearches.main(none);
        Ex13_NavigableTimetable.main(none);
        Ex14_LruCache.main(none);
        Ex15_EnumSetEnumMap.main(none);
        Ex16_RingBuffer.main(none);
        Ex17_GenericsAndWildcards.main(none);
        Ex18_MultimapAndBiMap.main(none);
        Ex19_ConcurrentCollections.main(none);
        Ex20_OrderBookCapstone.main(none);
        Check.grandTotal();
    }
}
