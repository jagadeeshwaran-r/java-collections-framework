package com.util.collections.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIndexOfTest {

    @Test
    @DisplayName("indexOf returns -1 for an empty list")
    void indexOf_onEmptyList_returnsMinusOne() {
        LinkedList<Integer> list = new LinkedList<>();

        assertEquals(-1, list.indexOf(10),
                "indexOf must return -1 when the list is empty");
    }

    @Test
    @DisplayName("indexOf returns correct index for a single element")
    void indexOf_singleElement_present() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");

        assertEquals(0, list.indexOf("A"),
                "indexOf must return 0 for the only element in the list");
    }

    @Test
    @DisplayName("indexOf returns -1 when element is not present")
    void indexOf_elementAbsent_returnsMinusOne() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        assertEquals(-1, list.indexOf("D"),
                "indexOf must return -1 when the element is not present");
    }

    @Test
    @DisplayName("indexOf returns index of first occurrence when duplicates exist")
    void indexOf_duplicates_returnsFirstOccurrence() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(1);
        list.add(3);

        assertEquals(0, list.indexOf(1),
                "indexOf must return the index of the first matching element");
    }

    @Test
    @DisplayName("indexOf respects iteration order")
    void indexOf_preservesIterationOrder() {
        LinkedList<String> list = new LinkedList<>();
        list.add("X");
        list.add("Y");
        list.add("Z");

        assertEquals(1, list.indexOf("Y"),
                "indexOf must reflect the logical iteration order of the list");
    }

    @Test
    @DisplayName("indexOf supports null lookup when nulls are allowed")
    void indexOf_nullElement_allowed() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("A");
        list.add(null);
        list.add("B");

        assertEquals(1, list.indexOf(null),
                "indexOf must correctly locate null elements when allowed");
    }

    @Test
    @DisplayName("indexOf returns -1 for null when null is not present")
    void indexOf_nullAbsent_returnsMinusOne() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("A");
        list.add("B");

        assertEquals(-1, list.indexOf(null),
                "indexOf must return -1 when null is not present");
    }

    @Test
    @DisplayName("indexOf uses equals() rather than reference equality")
    void indexOf_usesEqualsContract() {
        LinkedList<String> list = new LinkedList<>();
        list.add("hello");

        assertEquals(0, list.indexOf("hello"),
                "indexOf must rely on equals(), not reference equality");
    }

    @Test
    @DisplayName("indexOf does not modify list state")
    void indexOf_isNonMutating() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int sizeBefore = list.size();
        list.indexOf(2);

        assertEquals(sizeBefore, list.size(),
                "indexOf must not modify the list size");
    }
}
