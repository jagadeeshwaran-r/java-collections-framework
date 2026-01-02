package com.util.collections.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListClearTest {

    @Test
    void clear_shouldWorkOnEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void clear_shouldRemoveSingleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void clear_shouldRemoveAllElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void clear_shouldResetHeadAndTail() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);

        list.clear();

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(0));
    }

    @Test
    void clear_shouldBeIdempotent() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        list.clear();
        list.clear(); // should not throw

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void clear_shouldAllowReuseAfterClear() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);

        list.clear();
        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void clear_shouldHandleNullElementsWhenAllowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add(null);
        list.add("b");

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
}