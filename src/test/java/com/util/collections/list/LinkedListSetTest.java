package com.util.collections.list;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListSetTest {

    private LinkedList<String> list;

    @BeforeEach
    void setup() {
        list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @Test
    void set_shouldReplaceHead_andReturnOldValue() {
        String old = list.set(0, "X");

        assertEquals("A", old);
        assertEquals("X", list.get(0));
        assertEquals(3, list.size());
    }

    @Test
    void set_shouldReplaceMiddleElement() {
        String old = list.set(1, "Y");

        assertEquals("B", old);
        assertEquals("Y", list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    void set_shouldReplaceTail_usingTailOptimization() {
        String old = list.set(2, "Z");

        assertEquals("C", old);
        assertEquals("Z", list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void set_shouldThrowException_forNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.set(-1, "X"));
    }

    @Test
    void set_shouldThrowException_forIndexEqualToSize() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.set(3, "X"));
    }

    @Test
    void set_shouldThrowException_whenNullNotAllowed() {
        LinkedList<String> nonNullableList = new LinkedList<>(false);
        nonNullableList.add("A");

        assertThrows(IllegalArgumentException.class,
                () -> nonNullableList.set(0, null));
    }

    @Test
    void set_shouldNotBreakHeadTailInvariant() {
        list.set(2, "Z");

        assertNotNull(list.iterator()); // sanity
        assertEquals("Z", list.get(list.size() - 1));
        assertEquals(3, list.size());
    }


}
