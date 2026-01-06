package com.util.collections.list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListAddAllTest {

    @Test
    void addAll_shouldAddAllElements_andReturnTrue() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3);

        // Act
        boolean result = list.addAll(iterable);

        // Assert
        assertTrue(result, "addAll should return true");
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void addAll_withEmptyIterable_shouldReturnTrue_andNotModifyList() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        Iterable<Integer> iterable = List.of();

        // Act
        boolean result = list.addAll(iterable);

        // Assert
        assertTrue(result);
        assertTrue(list.isEmpty());
    }

    @Test
    void addAll_withNullIterable_shouldThrowNullPointerException() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> list.addAll(null));
    }

    @Test
    void addAll_shouldAppendElementsToExistingList() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);

        Iterable<Integer> iterable = Arrays.asList(30, 40);

        // Act
        boolean result = list.addAll(iterable);

        // Assert
        assertTrue(result);
        assertEquals(4, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(40, list.get(3));
    }

    @Test
    void addAll_withNullElement_whenNullableAllowed_shouldAddNull() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>(true);
        Iterable<Integer> iterable = Arrays.asList(1, null, 3);

        // Act
        boolean result = list.addAll(iterable);

        // Assert
        assertTrue(result);
        assertEquals(3, list.size());
        assertNull(list.get(1));
    }

    @Test
    void addAll_withNullElement_whenNullableNotAllowed_shouldThrowException() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>(false);
        Iterable<Integer> iterable = Arrays.asList(1, null, 3);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> list.addAll(iterable));
    }
}
