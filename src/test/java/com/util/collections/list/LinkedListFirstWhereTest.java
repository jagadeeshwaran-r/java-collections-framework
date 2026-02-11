package com.util.collections.list;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListFirstWhereTest {

    @Test
    void firstWhere_shouldReturnFirstMatchingElement_inIterationOrder() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Predicate<Integer> isEven = v -> v % 2 == 0;

        // Act
        Integer result = list.firstWhere(isEven);

        // Assert
        assertEquals(2, result);
    }

    @Test
    void firstWhere_shouldReturnNull_whenNoElementMatches() {
        // Arrange
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Act
        String result = list.firstWhere(v -> v.equals("z"));

        // Assert
        assertNull(result);
    }

    @Test
    void firstWhere_onEmptyList_shouldReturnNull() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();

        // Act
        Integer result = list.firstWhere(v -> true);

        // Assert
        assertNull(result);
    }

    @Test
    void firstWhere_shouldShortCircuit_afterFirstMatch() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        int[] invocationCount = {0};

        // Act
        Integer result = list.firstWhere(v -> {
            invocationCount[0]++;
            return v == 20;
        });

        // Assert
        assertEquals(20, result);
        assertEquals(2, invocationCount[0],
                "Predicate evaluation must stop after first match");
    }

    @Test
    void firstWhere_withNullPredicate_shouldThrowNullPointerException() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> list.firstWhere(null));
    }

    @Test
    void firstWhere_withNullElement_whenNullableAllowed_shouldReturnNull() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>(true);
        list.add(null);
        list.add(1);
        list.add(2);

        // Act
        Integer result = list.firstWhere(v -> v == null);

        // Assert
        assertNull(result);
    }

    @Test
    void firstWhere_whenNullableNotAllowed_shouldIgnoreNullChecksSafely() {
        // Arrange
        LinkedList<Integer> list = new LinkedList<>(false);
        list.add(1);
        list.add(2);
        list.add(3);

        // Act
        Integer result = list.firstWhere(v -> v > 1);

        // Assert
        assertEquals(2, result);
    }
}