package com.util.collections.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A singly-linked list implementation with explicit head and tail references.
 *
 * <p><strong>Design Overview:</strong>
 * This implementation maintains direct references to both the first ({@code head})
 * and last ({@code tail}) nodes to support efficient insertion at both ends.
 * The list does not use sentinel (dummy) nodes; all structural logic operates
 * directly on real data nodes.
 *
 * <p><strong>Structural Invariants:</strong>
 * <ul>
 *   <li>{@code head == null ⇔ tail == null}</li>
 *   <li>If the list is non-empty, {@code tail.next == null}</li>
 *   <li>{@code count} accurately reflects the number of nodes reachable from {@code head}</li>
 * </ul>
 *
 * <p><strong>Null Handling Policy:</strong>
 * Whether {@code null} elements are permitted is governed by the {@code isNullable}
 * flag inherited from {@link AbstractList}. All mutating operations enforce this
 * policy consistently.
 *
 * <p><strong>Performance Characteristics:</strong>
 * <ul>
 *   <li>Insertion at head or tail: O(1)</li>
 *   <li>Removal by value: O(n)</li>
 *   <li>Indexed access: O(n)</li>
 * </ul>
 *
 * <p><strong>Iterator Semantics:</strong>
 * The iterator reflects the list state at the time of creation and is not fail-fast.
 * Concurrent structural modifications during iteration result in undefined behavior.
 *
 * <p><strong>Design Philosophy:</strong>
 * This class deliberately separates low-level node manipulation (unlinking)
 * from list-level invariant management (head, tail, size). This mirrors the
 * internal architecture of the JDK collections and improves correctness,
 * readability, and maintainability.
 * @author Jagadeesh Waran
 * @author Sasi Prakash
 * @author Prasanth
 */
public class LinkedList<T> extends AbstractList<T> {

    /**
     * Represents a single node in the singly linked list.
     *
     * <p><strong>Role:</strong>
     * A {@code Node} is a low-level structural element that stores a single
     * data value and a reference to the next node in the list.
     *
     * <p><strong>Encapsulation:</strong>
     * This class is declared {@code static} and {@code final} to:
     * <ul>
     *   <li>Prevent accidental coupling with the enclosing {@link LinkedList} instance</li>
     *   <li>Disallow subclassing, preserving predictable memory layout and behavior</li>
     * </ul>
     *
     * <p><strong>Linking Semantics:</strong>
     * <ul>
     *   <li>{@code next} references the successor node in the list</li>
     *   <li>A node is considered <em>linked</em> while it is reachable from
     *       {@code LinkedList.head}</li>
     *   <li>A node is considered <em>unlinked</em> once its {@code next} reference
     *       is cleared</li>
     * </ul>
     *
     * <p><strong>Lifecycle:</strong>
     * Nodes are created by the enclosing list during insertion operations and
     * explicitly detached via {@link LinkedList#unlink(Node)} to ensure that all
     * references are cleared and eligible for garbage collection.
     *
     * <p><strong>Invariants:</strong>
     * <ul>
     *   <li>{@code next == null} for the tail node</li>
     *   <li>Once unlinked, both {@code data} and {@code next} are cleared</li>
     * </ul>
     *
     * @param <T> the type of element stored in this node
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

    public LinkedList(boolean isNullable) {
        super(isNullable);
    }

    //==================== Removal Operations ====================================================//

    /**
     * Detaches the specified node from the linked structure and returns its successor.
     *
     * <p><strong>Contract:</strong>
     * This method performs a <em>local unlink</em> operation only. It:
     * <ul>
     *   <li>Severs the given node from the list by clearing its {@code next} reference</li>
     *   <li>Returns the node that originally followed the given node</li>
     * </ul>
     *
     * <p><strong>Responsibilities deliberately NOT handled here:</strong>
     * <ul>
     *   <li>Updating {@code head} or {@code tail} references</li>
     *   <li>Maintaining list size ({@code count})</li>
     *   <li>Reconnecting predecessor nodes</li>
     * </ul>
     * These concerns are intentionally delegated to the caller in order to keep
     * this method a low-level, reusable primitive.
     *
     * <p><strong>Preconditions:</strong>
     * <ul>
     *   <li>The supplied {@code node} must be non-null</li>
     *   <li>The caller must already hold any references required to maintain list invariants</li>
     * </ul>
     *
     * <p><strong>Postconditions:</strong>
     * <ul>
     *   <li>The returned node is the original {@code node.next}</li>
     *   <li>The unlinked node is fully detached and eligible for garbage collection</li>
     *   <li>No list-level invariants are modified by this method</li>
     * </ul>
     *
     * <p><strong>Design Rationale:</strong>
     * Centralizing unlink logic ensures consistent detachment semantics across all
     * structural modifications (e.g., {@code remove}, {@code clear}, future
     * {@code removeAt}). This mirrors the internal design approach used by the JDK
     * collections, where unlinking is treated as a primitive operation.
     *
     * @param node the non-null node to be detached from the list
     * @return the node that originally followed {@code node}, or {@code null} if none
     */
    private Node<T> unlink(Node<T> node) {
        Objects.requireNonNull(node, "node");
        Node<T> next = node.next;
        node.data = null; // Help GC
        node.next = null;
        return next;
    }

    /**
     * Removes the first occurrence of the specified value from the list, if present.
     *
     * <p><strong>Behavior:</strong>
     * Traverses the list from {@code head} and removes the first node whose value
     * is equal to the supplied argument as determined by {@link Objects#equals}.
     *
     * <p><strong>Structural Effects:</strong>
     * <ul>
     *   <li>Updates {@code head} if the first node is removed</li>
     *   <li>Updates {@code tail} if the last node is removed</li>
     *   <li>Decrements {@code count} exactly once upon successful removal</li>
     * </ul>
     *
     * <p><strong>Implementation Note:</strong>
     * Actual node detachment is delegated to {@link #unlink(Node)} to ensure consistent
     * unlink semantics across all structural operations.
     *
     * @param val the value to remove
     * @return {@code true} if an element was removed, {@code false} otherwise
     */
    @Override
    public boolean remove(T val) {
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
     * <p><strong>Behavior:</strong>
     * Iteratively unlinks every node starting from {@code head}, ensuring that
     * all internal node references are cleared and eligible for garbage collection.
     *
     * <p><strong>Implementation Strategy:</strong>
     * This method delegates node detachment to {@link #unlink(Node)} in order to
     * centralize unlink semantics and guarantee consistent cleanup behavior across
     * all structural operations.
     *
     * <p><strong>Structural Effects:</strong>
     * <ul>
     *   <li>All nodes are fully detached from the list</li>
     *   <li>{@code head} is set to {@code null}</li>
     *   <li>{@code tail} is set to {@code null}</li>
     *   <li>{@code count} is reset to {@code 0}</li>
     * </ul>
     *
     * <p><strong>Performance Characteristics:</strong>
     * Runs in linear time, O(n), proportional to the number of elements in the list.
     *
     * <p><strong>Postconditions:</strong>
     * <ul>
     *   <li>The list is empty</li>
     *   <li>{@code head == null ⇔ tail == null}</li>
     *   <li>All list invariants are restored</li>
     * </ul>
     *
     * <p><strong>Design Rationale:</strong>
     * Explicitly unlinking each node (rather than simply nulling {@code head})
     * ensures prompt release of references, which is beneficial in long-lived
     * data structures and memory-sensitive environments.
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
    // ===========================================================================================//

    //==================== Search Operations ====================================================//

    /**
     * Returns the element at the specified position in this list.
     *
     * <p><strong>Index Semantics:</strong>
     * Indices are zero-based. The valid range is {@code [0, size() - 1]}.
     * Index validation is delegated to {@link #checkIndexOrElseThrow(int)}.
     *
     * <p><strong>Behavior:</strong>
     * This operation performs a forward traversal starting from {@code head}
     * and advances node-by-node until the requested position is reached.
     *
     * <p><strong>Performance Characteristics:</strong>
     * This method runs in linear time, O(n), as random access is not supported
     * by singly-linked structures.
     *
     * <p><strong>Structural Guarantees:</strong>
     * <ul>
     *   <li>Does not modify the list structure</li>
     *   <li>Preserves all list invariants</li>
     *   <li>Safe to invoke concurrently with read-only operations</li>
     * </ul>
     *
     * <p><strong>Failure Modes:</strong>
     * <ul>
     *   <li>Throws {@link IndexOutOfBoundsException} if the index is invalid</li>
     *   <li>Relies on internal invariants to ensure {@code current} is non-null
     *       once the index has been validated</li>
     * </ul>
     *
     * <p><strong>Design Rationale:</strong>
     * Explicit traversal logic is favored over recursion or auxiliary helpers
     * to minimize overhead and maintain predictable control flow.
     *
     * @param index the zero-based position of the element to return
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    @Override
    public T get(int index) {
        checkIndexOrElseThrow(index);

        Node<T> current = head;
        int pos = 0;
        while (pos != index) {
            current = current.next;
            pos++;
        }
        return current.data;
    }
    // ===========================================================================================//

    //====================== Add Operations =====================================================//

    @Override
    public boolean add(T val) {
        linkLast(val);
        return true;
    }

    @Override
    public boolean add(T val, int index) {
        insertAt(val, index);
        return true;
    }

    /**
     * Inserts the specified element at the end of this list.
     *
     * <p><strong>Behavior:</strong>
     * Creates a new node containing the given element and appends it after the
     * current {@code tail}. If the list is empty, the new node becomes both
     * {@code head} and {@code tail}.
     *
     * <p><strong>Structural Effects:</strong>
     * <ul>
     *   <li>Updates {@code tail} to reference the newly created node</li>
     *   <li>Initializes {@code head} when inserting into an empty list</li>
     *   <li>Ensures {@code tail.next == null} after insertion</li>
     *   <li>Increments {@code count} exactly once</li>
     * </ul>
     *
     * <p><strong>Null Handling Policy:</strong>
     * The supplied element is validated against the list’s nullability policy
     * via {@link #checkNullAllowed(Object)} before insertion.
     *
     * <p><strong>Performance Characteristics:</strong>
     * This operation executes in constant time, O(1), regardless of list size.
     *
     * <p><strong>Postconditions:</strong>
     * <ul>
     *   <li>{@code tail} references the newly inserted node</li>
     *   <li>If the list was previously empty, {@code head == tail}</li>
     *   <li>All list invariants remain satisfied</li>
     * </ul>
     *
     * <p><strong>Design Rationale:</strong>
     * Maintaining a dedicated {@code tail} reference allows to append operations
     * to avoid linear traversal, which is a key performance advantage of this
     * implementation over naive singly linked lists.
     *
     * @param data the element to insert at the end of the list
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
     * <p><strong>Behavior:</strong>
     * Creates a new node containing the given element and makes it the new
     * {@code head} of the list. The previously first node (if any) becomes the
     * successor of the newly inserted node.
     *
     * <p><strong>Structural Effects:</strong>
     * <ul>
     *   <li>Updates {@code head} to reference the newly created node</li>
     *   <li>Initializes {@code tail} when inserting into an empty list</li>
     *   <li>Increments {@code count} exactly once</li>
     * </ul>
     *
     * <p><strong>Null Handling Policy:</strong>
     * The supplied element is validated against the list’s nullability policy
     * via {@link #checkNullAllowed(Object)} before insertion.
     *
     * <p><strong>Performance Characteristics:</strong>
     * This operation executes in constant time, O(1), regardless of list size.
     *
     * <p><strong>Postconditions:</strong>
     * <ul>
     *   <li>{@code head} references the newly inserted node</li>
     *   <li>If the list was previously empty, {@code head == tail}</li>
     *   <li>All list invariants remain satisfied</li>
     * </ul>
     *
     * <p><strong>Design Rationale:</strong>
     * Maintaining a direct {@code tail} reference allows this method to correctly
     * initialize both ends of the list without requiring special-case logic
     * elsewhere in the implementation.
     *
     * @param data the element to insert at the front of the list
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
     * <p><strong>Behavior:</strong>
     * The element is inserted such that it will occupy the specified {@code index},
     * and all existing elements at and after that position are shifted one position
     * to the right.
     *
     * <p><strong>Index Semantics:</strong>
     * <ul>
     *   <li>{@code index == 0} — insertion at the head of the list</li>
     *   <li>{@code index == count} — insertion at the tail of the list</li>
     *   <li>{@code 0 < index < count} — insertion in the middle of the list</li>
     * </ul>
     *
     * <p><strong>Implementation Strategy:</strong>
     * Boundary insertions are delegated to {@link #linkFirst(Object)} and
     * {@link #linkLast(Object)} to centralize head and tail handling.
     * Middle insertions traverse the list up to the node preceding the target
     * position and splice the new node into the chain.
     *
     * <p><strong>Preconditions:</strong>
     * <ul>
     *   <li>The specified {@code index} must be within the insertable range
     *       {@code [0, count]}</li>
     *   <li>If this list does not allow {@code null} values, {@code val} must be non-null</li>
     * </ul>
     *
     * <p><strong>Postconditions:</strong>
     * <ul>
     *   <li>The list size is increased by one</li>
     *   <li>The element is accessible via {@code get(index)}</li>
     *   <li>All list invariants ({@code head}, {@code tail}, {@code count}) are preserved</li>
     * </ul>
     *
     * <p><strong>Performance Characteristics:</strong>
     * Runs in linear time, O(n), due to traversal in the worst case. Space complexity
     * is O(1).
     *
     * <p><strong>Design Rationale:</strong>
     * This method isolates structural concerns by reusing specialized primitives
     * for head and tail insertion, keeping the core logic minimal and reducing
     * the risk of invariant violations during future maintenance.
     *
     * @param val   the element to be inserted
     * @param index the position at which the element is to be inserted
     * @throws IndexOutOfBoundsException if the index is outside the range {@code [0, count]}
     * @throws IllegalArgumentException if {@code val} is {@code null} and nulls are not allowed
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
        Node<T> currNode = head;
        for (int i = 1; i < index; i++) {
            currNode = currNode.next;
        }
        newNode.next = currNode.next;
        currNode.next = newNode;
        count++;
    }

    /**
     * Replaces the element at the specified position in this list.
     *
     * <p>This is a non-structural operation. It mutates only the value stored at the
     * given index and does not affect the size, ordering, or topology of the list.</p>
     *
     * <p>The previous value is returned to preserve observational consistency with
     * standard {@code List} semantics.</p>
     *
     * <h3>Contractual Guarantees</h3>
     * <ul>
     *   <li>The list size remains unchanged</li>
     *   <li>The relative order of elements is preserved</li>
     *   <li>No nodes are created or removed</li>
     * </ul>
     *
     * @param index zero-based index of the element to replace
     * @param val   replacement value
     * @return the value previously stored at {@code index}
     *
     * @throws IndexOutOfBoundsException if {@code index} is outside the valid range
     * @throws NullPointerException if {@code val} violates the list's null policy
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
    // ===========================================================================================//

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Iterator implementation for {@link LinkedList}.
     *
     * <p><strong>Traversal Semantics:</strong>
     * This iterator traverses the list in forward order, starting from the
     * {@code head} node and proceeding sequentially via {@code next} references.
     *
     * <p><strong>State Model:</strong>
     * The iterator maintains a single cursor ({@code currentNode}) that always
     * points to the next node whose data will be returned by {@link #next()}.
     *
     * <p><strong>Behavior:</strong>
     * <ul>
     *   <li>{@link #hasNext()} returns {@code true} if there is at least one
     *       remaining element to iterate over</li>
     *   <li>{@link #next()} returns the current element and advances the cursor</li>
     * </ul>
     *
     * <p><strong>Exception Semantics:</strong>
     * Calling {@link #next()} when no elements remain will result in a
     * {@link NoSuchElementException}, in accordance with the {@link Iterator}
     * contract.
     *
     * <p><strong>Concurrency & Modification Semantics:</strong>
     * This iterator is <em>not fail-fast</em>. Structural modifications to the
     * underlying list during iteration may result in undefined behavior.
     * Concurrent modification detection is intentionally omitted for simplicity
     * and performance.
     *
     * <p><strong>Performance Characteristics:</strong>
     * All iterator operations run in constant time, O(1), and require no
     * additional memory allocation.
     *
     * <p><strong>Design Rationale:</strong>
     * This iterator is implemented as a lightweight inner class to allow direct
     * access to the list’s internal node structure, minimizing overhead and
     * avoiding additional indirection.
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
