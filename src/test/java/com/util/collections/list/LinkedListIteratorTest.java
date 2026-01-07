package com.util.collections.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIteratorTest {
    @Test
    void removeHeadViaIterator() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        assertEquals(1, it.next());
        it.remove();

        assertEquals(2, list.get(0));
        assertEquals(2, list.size());
    }

    @Test
    void removeMiddleViaIterator() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        it.next(); // 1
        assertEquals(2, it.next());
        it.remove();

        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void removeTailViaIteratorUpdatesTail() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);

        Iterator<Integer> it = list.iterator();
        it.next(); // 1
        assertEquals(2, it.next());
        it.remove();

        assertEquals(1, list.size());
        assertEquals(1, list.get(0));
        assertEquals(1, list.get(list.size() - 1));
    }

    @Test
    void removeOnlyElementViaIterator() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);

        Iterator<Integer> it = list.iterator();
        assertEquals(10, it.next());
        it.remove();

        assertTrue(list.isEmpty());
    }

    @Test
    void removeWithoutNextThrowsIllegalState() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        Iterator<Integer> it = list.iterator();
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    void doubleRemoveThrowsIllegalState() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        Iterator<Integer> it = list.iterator();
        it.next();
        it.remove();

        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    void nextBeyondEndThrowsException() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        Iterator<Integer> it = list.iterator();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void failFastOnExternalModification() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);

        Iterator<Integer> it = list.iterator();
        list.add(3); // structural modification

        assertThrows(ConcurrentModificationException.class, it::next);
    }
}