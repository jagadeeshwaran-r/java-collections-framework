import com.util.collections.list.LinkedList;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {

    @Test
    public void testRemoveFromEmptyList() {
        LinkedList<Integer> list = new LinkedList<>();
        assertFalse(list.remove(1));
    }

    @Test
    public void testRemoveHeadElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.remove(1));
        assertEquals(2, list.size());
        assertEquals(2, (int) list.get(0));
    }

    @Test
    public void testRemoveMiddleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.remove(2));
        assertEquals(2, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(3, (int) list.get(1));
    }

    @Test
    public void testRemoveTailElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.remove(3));
        assertEquals(2, list.size());
        assertEquals(2, (int) list.get(1));
    }

    @Test
    public void testRemoveNonExistentElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        assertFalse(list.remove(99));
        assertEquals(2, list.size());
    }

    @Test
    public void testRemoveSingleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        assertTrue(list.remove(1));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveWithNullValue() {
        LinkedList<String> list = new LinkedList<>(true);
        list.add("a");
        list.add(null);
        list.add("b");
        assertTrue(list.remove(null));
        assertEquals(2, list.size());
    }

    @Test
    public void testRemoveMultipleSameValues() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(1);
        assertTrue(list.remove(1));
        assertEquals(2, list.size());
        assertEquals(2, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
    }
}