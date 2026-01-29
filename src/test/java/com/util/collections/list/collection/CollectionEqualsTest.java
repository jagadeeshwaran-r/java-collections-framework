package com.util.collections.list.collection;

import com.util.collections.list.LinkedList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LinkedListEqualsTest {

    @Test
    void equals_shouldBeReflexive() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("A", "B", "C"));

        assertEquals(list, list);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithNull() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("A", "B"));

        assertNotEquals(list, null);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithDifferentType() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("A", "B"));

        assertNotEquals(list, "A,B");
    }

    @Test
    void equals_shouldReturnTrue_forSameElementsSameOrder() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", "B", "C"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("A", "B", "C"));

        assertEquals(a, b);
        assertEquals(b, a); // symmetry
    }

    @Test
    void equals_shouldReturnFalse_whenSizesDiffer() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", "B"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("A", "B", "C"));

        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenOrderDiffers() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", "B", "C"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("C", "B", "A"));

        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldHandleNullElements() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", null, "C"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("A", null, "C"));

        assertEquals(a, b);
    }

    @Test
    void equals_shouldReturnFalse_whenElementsDiffer() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", "B", "C"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("A", "X", "C"));

        assertNotEquals(a, b);
    }

    @Test
    void equals_shouldBeTransitive() {
        LinkedList<String> a = new LinkedList<>(Arrays.asList("A", "B"));
        LinkedList<String> b = new LinkedList<>(Arrays.asList("A", "B"));
        LinkedList<String> c = new LinkedList<>(Arrays.asList("A", "B"));

        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }
}

