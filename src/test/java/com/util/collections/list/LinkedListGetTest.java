package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListGetTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
    }

    @Test
    void testGetFirstElement() {
        assertEquals(10, list.get(0));
    }

    @Test
    void testGetMiddleElement() {
        assertEquals(20, list.get(1));
    }

    @Test
    void testGetLastElement() {
        assertEquals(30, list.get(2));
    }

    @Test
    void testGetInvalidNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void testGetInvalidLargeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    void testGetSingleElementList() {
        LinkedList<Integer> single = new LinkedList<>();
        single.add(99);
        assertEquals(99, single.get(0));
    }

    @Test
    void testGetEmptyList() {
        LinkedList<Integer> empty = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.get(0));
    }
}
