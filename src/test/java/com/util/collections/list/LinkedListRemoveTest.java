package com.util.collections.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListRemoveTest {

    @Test
    void remove_shouldReturnFalse_whenListIsEmpty() {
        LinkedList<Integer> list = new LinkedList<>();

        assertFalse(list.remove(10));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void remove_shouldRemoveHeadElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        boolean removed = list.remove(1);

        assertTrue(removed);
        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    void remove_shouldRemoveMiddleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        boolean removed = list.remove(2);

        assertTrue(removed);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void remove_shouldRemoveTailElement_andUpdateTail() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        boolean removed = list.remove(3);

        assertTrue(removed);
        assertEquals(2, list.size());
        assertEquals(2, list.get(1));
    }

    @Test
    void remove_shouldHandleSingleElementList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        boolean removed = list.remove(1);

        assertTrue(removed);
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void remove_shouldReturnFalse_whenElementNotPresent() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);

        boolean removed = list.remove(99);

        assertFalse(removed);
        assertEquals(2, list.size());
    }

    @Test
    void remove_shouldRemoveOnlyFirstOccurrence() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(1);

        boolean removed = list.remove(1);

        assertTrue(removed);
        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    void remove_shouldRemoveNull_whenNullsAllowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add(null);
        list.add("b");

        boolean removed = list.remove(null);

        assertTrue(removed);
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
    }

    @Test
    void remove_shouldThrowException_whenNullNotAllowed() {
        LinkedList<String> list = new LinkedList<>(false);
        list.add("a");

        assertThrows(IllegalArgumentException.class,
                () -> list.remove(null));
    }
}

