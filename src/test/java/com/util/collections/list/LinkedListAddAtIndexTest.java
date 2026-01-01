package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListAddAtIndexTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    // ===================== BASIC INSERTIONS =====================

    @Test
    void testAddAtIndexZeroInEmptyList() {
        assertTrue(list.add(10, 0));
        assertEquals(1, list.size());
        assertEquals(10, list.get(0));
    }

    @Test
    void testAddAtBeginning() {
        list.add(20, 0);
        list.add(10, 0);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testAddAtEnd() {
        list.add(10, 0);
        list.add(20, 1);
        list.add(30, 2);

        assertEquals(3, list.size());
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtMiddle() {
        list.add(10, 0);
        list.add(30, 1);
        list.add(20, 1);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    // ===================== SINGLE ELEMENT =====================

    @Test
    void testAddAtIndexZeroSingleElement() {
        list.add(99, 0);

        assertEquals(1, list.size());
        assertEquals(99, list.get(0));
    }

    // ===================== INVALID INDEXES =====================

    @Test
    void testAddNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(10, -1));
    }

    @Test
    void testAddIndexGreaterThanSize() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(10, 1));
    }

    @Test
    void testAddIndexFarGreaterThanSize() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(10, 100));
    }

    // ===================== MULTIPLE SEQUENTIAL INSERTIONS =====================

    @Test
    void testMultipleIndexedInsertions() {
        list.add(1, 0);
        list.add(3, 1);
        list.add(2, 1);
        list.add(4, 3);

        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
    }
}
