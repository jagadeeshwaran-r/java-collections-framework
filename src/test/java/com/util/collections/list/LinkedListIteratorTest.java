package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIteratorTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    // ===================== BASIC ITERATION =====================

    @Test
    void testIteratorOverMultipleElements() {
        list.add(10);
        list.add(20);
        list.add(30);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(30, iterator.next());

        assertFalse(iterator.hasNext());
    }

    // ===================== EMPTY LIST =====================

    @Test
    void testIteratorOnEmptyList() {
        Iterator<Integer> iterator = list.iterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // ===================== SINGLE ELEMENT =====================

    @Test
    void testIteratorSingleElement() {
        list.add(99);

        Iterator<Integer> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(99, iterator.next());
        assertFalse(iterator.hasNext());
    }

    // ===================== NEXT WITHOUT HASNEXT =====================

    @Test
    void testNextWithoutCallingHasNext() {
        list.add(1);

        Iterator<Integer> iterator = list.iterator();

        assertEquals(1, iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // ===================== ITERATION ORDER =====================

    @Test
    void testIterationOrderIsInsertionOrder() {
        list.add(5);
        list.add(15);
        list.add(25);

        Iterator<Integer> iterator = list.iterator();

        assertEquals(5, iterator.next());
        assertEquals(15, iterator.next());
        assertEquals(25, iterator.next());
    }

    // ===================== MULTIPLE ITERATORS =====================

    @Test
    void testMultipleIteratorsIndependent() {
        list.add(1);
        list.add(2);

        Iterator<Integer> it1 = list.iterator();
        Iterator<Integer> it2 = list.iterator();

        assertEquals(1, it1.next());
        assertEquals(1, it2.next());

        assertEquals(2, it1.next());
        assertEquals(2, it2.next());
    }

    // ===================== REMOVE NOT SUPPORTED =====================

    @Test
    void testIteratorRemoveNotSupported() {
        list.add(1);

        Iterator<Integer> iterator = list.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}