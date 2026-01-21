package com.util.collections.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListFinishToArrayTest {

    @Test
    void finishToArray_createsNewArray_whenInputArrayIsTooSmall() {
        List<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");

        String[] input = new String[1];
        String[] result = list.toArray(input);

        assertNotSame(input, result);
        assertArrayEquals(new String[]{"A", "B"}, result);
    }

    @Test
    void finishToArray_reusesSameArray_whenInputArrayIsExactSize() {
        List<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");

        String[] input = new String[2];
        String[] result = list.toArray(input);

        assertSame(input, result);
        assertArrayEquals(new String[]{"1", "2"}, result);
    }

    @Test
    void finishToArray_setsNullAtCountIndex_whenArrayIsLarger() {
        List<String> list = new LinkedList<>();
        list.add("X");
        list.add("Y");

        String[] input = new String[5];
        input[2] = "SHOULD_BE_CLEARED";

        String[] result = list.toArray(input);

        assertSame(input, result);
        assertEquals("X", result[0]);
        assertEquals("Y", result[1]);
        assertNull(result[2]); // Java spec requirement
    }

    @Test
    void finishToArray_preservesRuntimeComponentType() {
        List<String> list = new LinkedList<>();
        list.add("A");

        String[] result = list.toArray(new String[0]);

        assertEquals(String.class, result.getClass().getComponentType());
    }

    @Test
    void finishToArray_returnsSameArray_whenListIsEmpty() {
        List<String> list = new LinkedList<>();

        String[] input = new String[3];
        String[] result = list.toArray(input);

        assertSame(input, result);
        assertNull(result[0]); // first extra element must be null
    }

    @Test
    void finishToArray_traversesLinkedList_inCorrectOrder() {
        List<String> list = new LinkedList<>();
        list.add("10");
        list.add("20");
        list.add("30");

        String[] result = list.toArray(new String[3]);

        assertArrayEquals(new String[]{"10", "20", "30"}, result);
    }
}
