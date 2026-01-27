package com.util.collections.list.collection;

import com.util.collections.list.LinkedList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionHashCodeTest {

    @Test
    void equalListsMustHaveSameHashCode() {
        com.util.collections.list.List<String> a = new LinkedList<>(Arrays.asList("A", "B", "C"));
        com.util.collections.list.List<String> b = new LinkedList<>(Arrays.asList("A", "B", "C"));
        assertEquals(a.hashCode(), b.hashCode(), "Equal lists must have same hashCode");
    }

    @Test
    void differentOrderShouldProduceDifferentHashCode() {
        com.util.collections.list.List<String> a = new LinkedList<>(Arrays.asList("A", "B", "C"));
        com.util.collections.list.List<String> b = new LinkedList<>(Arrays.asList("C", "B", "A"));

        assertNotEquals(a, b, "Lists with different order should not be equal");

        // Hash collision is technically possible, but extremely unlikely
        assertNotEquals(a.hashCode(), b.hashCode(), "Lists with different order should produce different hashCodes");
    }

    @Test
    void nullElementsContributeZeroHash() {
        com.util.collections.list.List<String> a = new LinkedList<>(Arrays.asList("A", null, "C"));
        com.util.collections.list.List<String> b = new LinkedList<>(Arrays.asList("A", null, "C"));

        assertEquals(a.hashCode(), b.hashCode(), "Lists with null elements must produce consistent hashCodes");
    }

    @Test
    void hashCodeIsConsistent() {
        com.util.collections.list.List<Integer> list = new LinkedList<>(Arrays.asList(1, 2, 3));

        int h1 = list.hashCode();
        int h2 = list.hashCode();

        assertEquals(h1, h2, "hashCode must be consistent across multiple calls");
    }

    @Test
    void equalsImpliesSameHashCode() {
        com.util.collections.list.List<Integer> a = new LinkedList<>(Arrays.asList(1, 2, 3));
        com.util.collections.list.List<Integer> b = new LinkedList<>(Arrays.asList(1, 2, 3));

        assertEquals(a.hashCode(), b.hashCode(), "Equal lists must produce same hashCode");
    }

    @Test
    void matchesJdkListHashCode() {
        List<String> jdk = Arrays.asList("A", "B", "C");
        com.util.collections.list.List<String> mine = new LinkedList<>(Arrays.asList("A", "B", "C"));

        assertEquals(jdk.hashCode(), mine.hashCode(), "Custom list should match JDK List hashCode");
    }
}

