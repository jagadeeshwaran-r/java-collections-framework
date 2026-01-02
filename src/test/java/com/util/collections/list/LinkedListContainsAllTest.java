package com.util.collections.list;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkedListContainsAllTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
    }

    @Test
    @DisplayName("containsAll returns true when all elements are present")
    void shouldReturnTrueWhenAllElementsExist() {
        assertTrue(list.containsAll(List.of(10, 20)));
    }

    @Test
    @DisplayName("containsAll returns false when at least one element is missing")
    void shouldReturnFalseWhenAnyElementIsMissing() {
        assertFalse(list.containsAll(List.of(10, 40)));
    }

    @Test
    @DisplayName("containsAll is order independent")
    void shouldIgnoreOrderOfElements() {
        assertTrue(list.containsAll(List.of(30, 10)));
    }

    @Test
    @DisplayName("containsAll ignores duplicate elements in input iterable")
    void shouldIgnoreDuplicatesInIterable() {
        assertTrue(list.containsAll(List.of(10, 10, 20)));
    }

    @Test
    @DisplayName("containsAll returns true for empty iterable")
    void shouldReturnTrueForEmptyIterable() {
        assertTrue(list.containsAll(List.of()));
    }

    @Test
    @DisplayName("containsAll returns false when list is empty and iterable is not")
    void shouldReturnFalseWhenListIsEmpty() {
        LinkedList<Integer> emptyList = new LinkedList<>();
        assertFalse(emptyList.containsAll(List.of(1)));
    }

    @Test
    @DisplayName("containsAll throws NullPointerException for null iterable")
    void shouldThrowExceptionWhenIterableIsNull() {
        assertThrows(NullPointerException.class, () -> list.containsAll(null));
    }

    @Test
    @DisplayName("containsAll relies on equals() rather than reference equality")
    void shouldUseEqualsForComparison() {
        LinkedList<String> stringList = new LinkedList<>();
        stringList.add(new String("A"));
        stringList.add(new String("B"));

        assertTrue(stringList.containsAll(List.of(new String("A"))));
    }

    @Test
    @DisplayName("containsAll does not modify list state")
    void shouldNotModifyList() {
        int originalSize = list.size();

        list.containsAll(List.of(10, 20));

        assertEquals(originalSize, list.size());
        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertTrue(list.contains(30));
    }
}
