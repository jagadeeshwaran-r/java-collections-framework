package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListLastIndexOfTest {

    private LinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    @Test
    void testLastIndexOfWithDuplicates() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("B");
        list.add("D");

        assertEquals(3, list.lastIndexOf("B"), "Should return the last index of 'B'");
        assertEquals(2, list.lastIndexOf("C"), "Should return the index of 'C'");
    }

    @Test
    void testLastIndexOfWithSingleElement() {
        list.add("X");

        assertEquals(0, list.lastIndexOf("X"), "Single-element list should return 0 for its element");
        assertEquals(-1, list.lastIndexOf("Y"), "Should return -1 if element not found");
    }

    @Test
    void testLastIndexOfWithNulls() {
        list.add("A");
        list.add(null);
        list.add("B");
        list.add(null);

        assertEquals(3, list.lastIndexOf(null), "Should find last null element");
        assertEquals(0, list.lastIndexOf("A"), "Should find 'A' at index 0");
    }

    @Test
    void testLastIndexOfNotFound() {
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals(-1, list.lastIndexOf("D"), "Element not in list should return -1");
    }

    @Test
    void testLastIndexOfEmptyList() {
        assertEquals(-1, list.lastIndexOf("X"), "Empty list should return -1 for any element");
        assertEquals(-1, list.lastIndexOf(null), "Empty list should return -1 for null");
    }
}
