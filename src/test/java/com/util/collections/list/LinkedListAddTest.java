package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListAddTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    // ===================== ADD SINGLE ELEMENT =====================
    @Test
    void testAddSingleElement() {
        assertTrue(list.add(10), "add() should return true");
        assertEquals(1, list.size(), "Size should be 1 after adding one element");
        assertEquals(10, list.get(0), "The element added should be 10");
    }

    // ===================== ADD MULTIPLE ELEMENTS =====================
    @Test
    void testAddMultipleElements() {
        assertTrue(list.add(5));
        assertTrue(list.add(15));
        assertTrue(list.add(25));

        assertEquals(3, list.size(), "Size should be 3 after adding three elements");
        assertEquals(5, list.get(0), "First element should be 5");
        assertEquals(15, list.get(1), "Second element should be 15");
        assertEquals(25, list.get(2), "Third element should be 25");
    }

    // ===================== ADD NULL ELEMENT =====================
    @Test
    void testAddNullAllowed() {
        LinkedList<Integer> nullableList = new LinkedList<>(true); // assume constructor allows null
        assertTrue(nullableList.add(null), "add(null) should return true when nulls allowed");
        assertEquals(1, nullableList.size(), "Size should be 1 after adding null");
        assertNull(nullableList.get(0), "The element added should be null");
    }

    @Test
    void testAddNullNotAllowed() {
        LinkedList<Integer> nonNullableList = new LinkedList<>(false); // null not allowed
        assertThrows(IllegalArgumentException.class, () -> nonNullableList.add(null),
                "add(null) should throw IllegalArgumentException when nulls are not allowed");
    }

    // ===================== ADD AND GET =====================
    @Test
    void testAddAndRetrieve() {
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.size(), "Size should be 3");
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    // ===================== ADD TO EMPTY LIST =====================
    @Test
    void testAddToEmptyList() {
        assertTrue(list.add(99));
        assertEquals(1, list.size(), "Size should be 1");
        assertEquals(99, list.get(0));
    }
}