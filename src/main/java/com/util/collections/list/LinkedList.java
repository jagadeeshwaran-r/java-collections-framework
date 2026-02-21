package com.util.collections.list;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A singly-linked list implementation that maintains references
 * to both the head and tail nodes.
 *
 * <p>This implementation allows efficient insertion at both
 * the beginning and the end of the list. The structure does not
 * use sentinel (dummy) nodes; all operations work directly on
 * data-bearing nodes.
 *
 * <p>Structural invariants:
 * <ul>
 *   <li>{@code head == null} if and only if {@code tail == null}</li>
 *   <li>If the list is non-empty, {@code tail.next == null}</li>
 *   <li>{@code count} reflects the number of reachable nodes</li>
 * </ul>
 *
 * <p>Null element support is governed by the {@code isNullable}
 * policy defined in {@link AbstractList}. All mutating operations
 * enforce this policy consistently.
 *
 * <p>Performance characteristics:
 * <ul>
 *   <li>Insertion at head or tail — O(1)</li>
 *   <li>Removal by value — O(n)</li>
 *   <li>Indexed access — O(n)</li>
 * </ul>
 *
 * <p>The iterator is not fail-fast. Structural modifications during
 * iteration may result in undefined behavior.
 *
 * @param <T> the type of elements maintained in this list
 * @author Jagadeesh Waran
 * @author Sasi Prakash
 * @author Prasanth
 */
public class LinkedList<T> extends AbstractList<T> {

    /**
     * Node representing a single element in the linked structure.
     */
    static final class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next=null;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        super(true);
    }

    public LinkedList(List<T> list) {
        super(true);
        addAll(list);
    }

    public LinkedList(boolean isNullable) {
        super(isNullable);
    }

    @Override
    protected com.util.collections.list.List<T> createEmptyList() {
        return new LinkedList<>();
    }

    //==================== Removal Operations ====================================================//

    /**
     * Unlinks the specified node from the chain and returns its successor.
     *
     * <p>This method clears the node’s references to assist garbage collection.
     *
     * @param node the node to unlink
     * @return the node that originally followed {@code node}, or {@code null}
     * @throws NullPointerException if {@code node} is {@code null}
     */
    private Node<T> unlink(Node<T> node) {
        Objects.requireNonNull(node, "node");
        Node<T> next = node.next;
        node.data = null; // Help GC
        node.next = null;
        return next;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * <p>Traversal proceeds from the head of the list. Equality is determined
     * using {@link Objects#equals(Object, Object)}.
     *
     * @param val element to be removed
     * @return {@code true} if the list contained the specified element
     */
    @Override
    // TODO: Need to refactor this API.
    public boolean remove(Object val) {
        checkNullAllowed(val);

        if (head == null) {
            return false;
        }

        // Case 1: remove head
        if (Objects.equals(head.data, val)) {
            head = unlink(head);
            count--;

            // list became empty
            if (head == null) {
                tail = null;
            }
            return true;
        }

        Node<T> prev = head;
        Node<T> current = head.next;

        while (current != null) {
            if (Objects.equals(current.data, val)) {
                prev.next = unlink(current);
                count--;

                // Case 2: removed tail
                if (current == tail) { // To check current node is tail
                    tail = prev;
                }
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Removes all elements from this list.
     *
     * <p>All nodes are unlinked and internal references are cleared.
     * After this call, {@link #size()} returns {@code 0}.
     */
    @Override
    public void clear() {
        Node<T> current = head;
        while (current != null) {
            current = unlink(current);
        }
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * <p>This operation runs in linear time.
     *
     * @param index zero-based index of the element to return
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T get(int index) {
        checkIndexOrElseThrow(index);
        return getNodeAt(index).data;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param val element to be appended
     * @return {@code true} (as specified by {@code Collection.add})
     */
    @Override
    public boolean add(T val) {
        linkLast(val);
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param val element to insert
     * @param index position at which the element is to be inserted
     * @return {@code true}
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public boolean add(T val, int index) {
        insertAt(val, index);
        return true;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>If the list is empty, the new node becomes both head and tail.
     * Otherwise, it is linked after the current tail.
     *
     * <p>This operation runs in constant time.
     *
     * @param data element to append
     */
    private void linkLast(T data) {
        checkNullAllowed(data);

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        count++;
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * <p>If the list is empty, the new node becomes both head and tail.
     * Otherwise, it becomes the new head and points to the previous first node.
     *
     * <p>This operation runs in constant time.
     *
     * @param data element to insert at the front
     */
    private void linkFirst(T data) {
        checkNullAllowed(data);

        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    /**
     * Inserts the specified element at the given position in this list.
     *
     * <p>Elements at and after the specified position are shifted
     * to the right.
     *
     * <p>This operation runs in linear time.
     *
     * @param val element to insert
     * @param index position at which the element is to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    private void insertAt(T val, int index) {
        checkNullAllowed(val);
        checkIsInsertable(index);

        if (index == 0) {
            linkFirst(val);
            return;
        }
        if (index == count) {
            linkLast(val);
            return;
        }

        Node<T> newNode = new Node<>(val);
        Node<T> previous = getNodeAt(index - 1);
        Node<T> previousNext = previous.next;
        previous.next = newNode;
        newNode.next = previousNext;
        count++;
    }

    /**
     * Replaces the element at the specified position in this list.
     *
     * <p>This operation does not change the size or structure of the list.
     *
     * @param index zero-based index of the element to replace
     * @param val replacement value
     * @return the element previously stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T set(int index, T val) {
        checkNullAllowed(val);
        Node<T> node = getNodeAt(index);
        T oldValue = node.data;
        node.data = val;
        return oldValue;
    }

    /**
     * Resolves a logical index into its corresponding storage node.
     *
     * <p>This method centralizes index validation and node resolution, ensuring
     * consistent failure behavior across all index-based operations.</p>
     *
     * <p>Implementations may apply localized optimizations while preserving
     * correctness and determinism.</p>
     *
     * @param index zero-based index to resolve
     * @return the node representing the specified index
     *
     * @throws IndexOutOfBoundsException if {@code index} is invalid
     */
    private Node<T> getNodeAt(int index) {
        checkIndexOrElseThrow(index);
        if (index == count - 1) {
            return tail;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the current element count
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if the list is empty, otherwise {@code false}
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns an iterator over the elements in this list.
     *
     * <p>The iterator traverses the elements from head to tail.
     * It is not fail-fast.
     *
     * @return an iterator over the elements in encounter order
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Iterator implementation for {@link LinkedList}.
     *
     * <p>Traverses the list in forward order, starting from the head
     * and following next references until all elements are visited.
     *
     * <p>The iterator maintains a cursor that points to the next node
     * whose value will be returned by {@link #next()}.
     *
     * <p>{@link #hasNext()} returns {@code true} if more elements remain.
     * {@link #next()} returns the current element and advances the cursor.
     *
     * <p>Calling {@link #next()} when no elements remain throws
     * {@link NoSuchElementException}, as defined by the {@link Iterator} contract.
     *
     * <p>This iterator is not fail-fast. Structural modifications to the
     * list during iteration may result in undefined behavior.
     *
     * <p>All operations run in constant time.
     */
    private class LinkedListIterator implements Iterator<T> {

        /** The next node to be returned by the iterator */
        Node<T> currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }
}
