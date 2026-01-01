package com.util.collections.list;

/**
 * A simplified, ordered collection (sequence) of elements.
 *
 * <p>This interface defines the core contract for list-like data structures
 * that maintain elements in a positional order and support indexed access,
 * insertion, removal, and traversal.
 *
 * <p><strong>Ordering Semantics:</strong>
 * Elements are stored and accessed in a well-defined sequence. The position
 * of each element is determined by its index, starting from {@code 0}.
 *
 * <p><strong>Null Handling:</strong>
 * Whether {@code null} elements are permitted is implementation-specific.
 * Implementations are expected to document and enforce their nullability
 * policy.
 *
 * <p><strong>Iteration:</strong>
 * This interface extends {@link Iterable}, allowing implementations to be
 * traversed using enhanced {@code for} loops.
 *
 * <p><strong>Concurrency:</strong>
 * Implementations are not required to be thread-safe. External synchronization
 * is required if concurrent access is needed.
 *
 * <p><strong>Design Scope:</strong>
 * This interface intentionally exposes a minimal API surface, focusing on
 * essential list operations without advanced features such as sublists,
 * iterators with removal, or bulk operations.
 *
 * @author Jagadeesh Waran
 */
public interface List<T> extends Iterable<T> {

    /**
     * Appends the specified element to the end of this list.
     *
     * <p><strong>Contract:</strong>
     * After this method returns successfully:
     * <ul>
     *   <li>The element is present in the list</li>
     *   <li>The size of the list is increased by one</li>
     *   <li>The relative order of existing elements is preserved</li>
     * </ul>
     *
     * <p><strong>Null Handling:</strong>
     * Whether {@code null} elements are permitted is implementation-specific.
     * Implementations must enforce and document their nullability policy.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time performance depending on their
     * internal structure.
     *
     * @param val the element to be appended
     * @return {@code true} if the list was modified as a result of this call
     * @throws IllegalArgumentException if {@code val} is {@code null} and the
     *         implementation does not permit {@code null} elements
     */
    boolean add(T val);

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * <p><strong>Contract:</strong>
     * After this method returns successfully:
     * <ul>
     *   <li>The element is present at position {@code index}</li>
     *   <li>The size of the list is increased by one</li>
     *   <li>Elements previously at {@code index} and beyond are shifted one
     *       position to the right</li>
     *   <li>The relative order of existing elements is preserved</li>
     * </ul>
     *
     * <p><strong>Index Semantics:</strong>
     * <ul>
     *   <li>{@code index == 0} inserts the element at the beginning of the list</li>
     *   <li>{@code index == size()} inserts the element at the end of the list</li>
     * </ul>
     *
     * <p><strong>Null Handling:</strong>
     * Whether {@code null} elements are permitted is implementation-specific.
     * Implementations must enforce and document their nullability policy.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide different performance characteristics depending on their internal
     * structure.
     *
     * @param val   the element to be inserted
     * @param index the position at which the element is to be inserted
     * @return {@code true} if the list was modified as a result of this call
     * @throws IndexOutOfBoundsException if {@code index} is outside the range
     *         {@code [0, size()]}
     * @throws IllegalArgumentException if {@code val} is {@code null} and the
     *         implementation does not permit {@code null} elements
     */
    boolean add(T val, int index);

    /**
     * Returns the element at the specified position in this list.
     *
     * <p><strong>Contract:</strong>
     * This method provides positional (index-based) access to elements stored
     * in this list. The returned element corresponds to the element currently
     * stored at the specified {@code index}.
     *
     * <p><strong>Index Semantics:</strong>
     * Indices are zero-based. Valid indices range from {@code 0} (inclusive)
     * to {@code size() - 1} (inclusive).
     *
     * <p><strong>Behavior:</strong>
     * <ul>
     *   <li>The list is not structurally modified by this operation</li>
     *   <li>The relative order of elements remains unchanged</li>
     * </ul>
     *
     * <p><strong>Null Semantics:</strong>
     * If {@code null} elements are permitted by the implementation, this method
     * may return {@code null}.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time access depending on their internal
     * representation.
     *
     * @param index the position of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if {@code index} is outside the valid
     *         range {@code [0, size())}
     */
    T get(int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * <p><strong>Contract:</strong>
     * After this method returns:
     * <ul>
     *   <li>If the element was present, exactly one matching element is removed</li>
     *   <li>The size of the list is decreased by one if removal occurs</li>
     *   <li>The relative order of the remaining elements is preserved</li>
     * </ul>
     *
     * <p>If the list does not contain the specified element, it remains unchanged.
     *
     * <p><strong>Equality Semantics:</strong>
     * Element comparison is performed using {@code equals}, not reference equality.
     *
     * <p><strong>Null Handling:</strong>
     * Whether {@code null} values are permitted is implementation-specific.
     * Implementations must enforce and document their nullability policy.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide different performance characteristics depending on their internal
     * structure.
     *
     * @param val the element to be removed from this list
     * @return {@code true} if an element was removed as a result of this call;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code val} is {@code null} and the
     *         implementation does not permit {@code null} elements
     */
    boolean remove(T val);

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * <p><strong>Contract:</strong>
     * <ul>
     *   <li>Returns {@code true} if and only if at least one element in the list
     *       is {@code equals()} to the specified element</li>
     *   <li>The list is not modified by this operation</li>
     *   <li>Relative order and content of elements remain unchanged</li>
     * </ul>
     *
     * <p><strong>Equality Semantics:</strong>
     * Element comparison is performed using {@code equals}, not reference equality.
     *
     * <p><strong>Null Handling:</strong>
     * Whether {@code null} values are permitted is implementation-specific.
     * Implementations must enforce and document their nullability policy.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time performance depending on their internal
     * structure.
     *
     * @param val the element whose presence is to be tested
     * @return {@code true} if the list contains at least one element equal to {@code val};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code val} is {@code null} and the
     *         implementation does not permit {@code null} elements
     */
    boolean contains(T val);

    /**
     * Returns the number of elements currently in this list.
     *
     * <p><strong>Contract:</strong>
     * <ul>
     *   <li>The returned value is always non-negative</li>
     *   <li>The value reflects the number of elements that would be returned
     *       by traversing the list via an iterator or indexed access</li>
     *   <li>The list is not modified by this operation</li>
     * </ul>
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time performance depending on internal
     * structure.
     *
     * @return the number of elements in this list
     */
    int size();

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * <p><strong>Contract:</strong>
     * <ul>
     *   <li>Returns {@code true} if and only if {@link #size()} is {@code 0}</li>
     *   <li>The list is not modified by this operation</li>
     * </ul>
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time performance depending on internal
     * structure.
     *
     * @return {@code true} if the list contains no elements, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Removes all elements from this list.
     *
     * <p><strong>Contract:</strong>
     * <ul>
     *   <li>After this method returns, {@link #size()} will return {@code 0}</li>
     *   <li>{@link #isEmpty()} will return {@code true}</li>
     *   <li>All previously contained elements are considered removed and will
     *       no longer be accessible via iteration or indexed access</li>
     *   <li>The list is structurally modified to reflect an empty state</li>
     * </ul>
     *
     * <p><strong>Null Handling:</strong>
     * Implementations must properly handle removal of {@code null} elements if
     * they were previously permitted.
     *
     * <p><strong>Performance Characteristics:</strong>
     * No guarantees are made regarding time complexity. Implementations may
     * provide constant-time or linear-time performance depending on internal
     * structure.
     *
     * @throws UnsupportedOperationException if the list does not support
     *         element removal
     */
    void clear();

    /**
     * Replaces the element at the specified position in this list with the given value.
     *
     * <p>This operation updates the element stored at the provided index without
     * altering the size, order, or structural layout of the list.</p>
     *
     * <p>The method returns the element previously associated with the specified
     * position, allowing callers to observe the prior state.</p>
     *
     * @param index the zero-based position of the element to replace
     * @param val   the new element to be stored at the specified position
     * @return the element previously stored at the specified index
     *
     * @throws IndexOutOfBoundsException if {@code index < 0 || index >= size()}
     * @throws NullPointerException if {@code val} is {@code null} and null values
     *         are not permitted by this list implementation
     *
     * @implNote
     * This method is non-structural and does not change the list size.
     * Implementations may use different access strategies depending on their
     * underlying data structure.
     *
     * @complexity
     * Time Complexity: implementation-dependent
     * Space Complexity: O(1)
     */
    T set(int index, T val);
}
