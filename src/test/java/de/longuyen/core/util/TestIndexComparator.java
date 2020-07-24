package de.longuyen.core.util;

import de.longuyen.core.statistics.IndexComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIndexComparator {
    @Test
    void testIndexComparator(){
        Integer[] array = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        IndexComparator<Integer> comparator = new IndexComparator<>(array);
        Integer[] sortedIndex = comparator.sortedIndexArray();
        Integer[] expected = new Integer[]{8, 7, 6, 5, 4, 3, 2, 1, 0};
        Assertions.assertEquals(sortedIndex.length, expected.length);
        Assertions.assertArrayEquals(sortedIndex, expected);
    }
}
