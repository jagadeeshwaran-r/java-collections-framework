package com.util.collections.list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListRemoveTest {

    /* ---------- Empty list ---------- */

    @ParameterizedTest
    @ValueSource(strings = {"a", "x", "test"})
    void remove_shouldReturnFalse_whenListIsEmpty(String value) {
        LinkedList<String> list = new LinkedList<>();

        assertFalse(list.remove(value));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    /* ---------- Head / Middle / Tail ---------- */

    static Stream<RemoveScenario> removeScenarios() {
        return Stream.of(
                new RemoveScenario(
                        new String[]{"a", "b", "c"}, "a",
                        new String[]{"b", "c"}
                ),
                new RemoveScenario(
                        new String[]{"a", "b", "c"}, "b",
                        new String[]{"a", "c"}
                ),
                new RemoveScenario(
                        new String[]{"a", "b", "c"}, "c",
                        new String[]{"a", "b"}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("removeScenarios")
    void remove_shouldRemoveCorrectElement(RemoveScenario scenario) {
        LinkedList<String> list = new LinkedList<>();
        for (String v : scenario.initial) {
            list.add(v);
        }

        boolean removed = list.remove(scenario.toRemove);

        assertTrue(removed);
        assertEquals(scenario.expected.length, list.size());

        for (int i = 0; i < scenario.expected.length; i++) {
            assertEquals(scenario.expected[i], list.get(i));
        }
    }

    /* ---------- Single element ---------- */

    @Test
    void remove_shouldHandleSingleElementList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");

        assertTrue(list.remove("a"));
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    /* ---------- Element not present ---------- */

    @Test
    void remove_shouldReturnFalse_whenElementNotPresent() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");

        assertFalse(list.remove("z"));
        assertEquals(2, list.size());
    }

    /* ---------- Duplicate handling ---------- */

    @Test
    void remove_shouldRemoveOnlyFirstOccurrence() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("a");

        assertTrue(list.remove("a"));
        assertEquals(2, list.size());
        assertEquals("b", list.get(0));
        assertEquals("a", list.get(1));
    }

    /* ---------- Null handling ---------- */

    @Test
    void remove_shouldRemoveNull_whenNullsAllowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add(null);
        list.add("b");

        assertTrue(list.remove(null));
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

    /* ---------- Helper DTO ---------- */

    private static final class RemoveScenario {
        final String[] initial;
        final String toRemove;
        final String[] expected;

        RemoveScenario(String[] initial, String toRemove, String[] expected) {
            this.initial = initial;
            this.toRemove = toRemove;
            this.expected = expected;
        }
    }
}
