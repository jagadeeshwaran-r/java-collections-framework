package com.util.collections.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListContainsTest {

    @Test
    void contains_shouldReturnFalse_whenListIsEmpty() {
        LinkedList<Integer> list = new LinkedList<>();

        assertFalse(list.contains(1));
    }

    @Test
    void contains_shouldReturnFalse_whenNullNotAllowed() {
        LinkedList<String> list = new LinkedList<>(false);

        assertFalse(list.contains(null));
    }

    @Test
    void contains_shouldReturnTrue_whenValueIsAtHead() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(10));
    }

    @Test
    void contains_shouldReturnTrue_whenValueIsAtTail() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(30));
    }

    @Test
    void contains_shouldReturnTrue_whenValueIsInMiddle() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(20));
    }

    @Test
    void contains_shouldReturnFalse_whenValueNotPresent() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertFalse(list.contains(99));
    }

    @Test
    void contains_shouldHandleSingleElementList_trueCase() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(42);

        assertTrue(list.contains(42));
    }

    @Test
    void contains_shouldHandleSingleElementList_falseCase() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(42);

        assertFalse(list.contains(99));
    }

    @Test
    void contains_shouldHandleNullValue_whenNullsAllowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add(null);
        list.add("b");

        assertTrue(list.contains(null));
    }

    @Test
    void contains_shouldReturnFalse_whenNullNotPresentButAllowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add("b");

        assertFalse(list.contains(null));
    }

    @Test
    void contains_shouldNotModifyListStructure() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertTrue(list.contains(2));
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }
}
