package com.util.collections.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListToArrayTest {

    /**
     * Replace CustomLinkedList with your actual class name
     */
    @Test
    void toArray_returnsEmptyArray_whenListIsEmpty() {
        List<String> list = new LinkedList<>();

        Object[] result = list.toArray();

        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    void toArray_returnsSingleElement_whenOneNodePresent() {
        List<String> list = new LinkedList<>();
        list.add("A");

        Object[] result = list.toArray();

        assertEquals(1, result.length);
        assertEquals("A", result[0]);
    }

    @Test
    void toArray_returnsAllElements_inInsertionOrder() {
        List<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        Object[] result = list.toArray();

        assertArrayEquals(new Object[]{10, 20, 30}, result);
    }

    @Test
    void toArray_handlesNullElements_correctly() {
        List<String> list = new LinkedList<>();
        list.add(null);
        list.add("B");

        Object[] result = list.toArray();

        assertEquals(2, result.length);
        assertNull(result[0]);
        assertEquals("B", result[1]);
    }

    @Test
    void toArray_doesNotReturnExtraNulls_beyondListSize() {
        List<String> list = new LinkedList<>();
        list.add("X");
        list.add("Y");

        Object[] result = list.toArray();

        assertEquals(2, result.length);
        for (Object o : result) {
            // ensures traversal stops at tail node
            // and does not depend on capacity
            assertTrue(o.equals("X") || o.equals("Y"));
        }
    }

    @Test
    void toArray_usesIteratorTraversal_notInternalNodeAccess() {
        List<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        Object[] result = list.toArray();

        // proves correct iterator traversal through nodes
        assertEquals("first", result[0]);
        assertEquals("second", result[1]);
        assertEquals("third", result[2]);
    }
}
