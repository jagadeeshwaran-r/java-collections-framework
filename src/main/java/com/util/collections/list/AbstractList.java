package com.util.collections.list;

import java.util.Objects;

/**
 * Abstract base class for {@link List} implementations that centralizes
 * validation logic, boundary enforcement, and null-handling policies.
 *
 * <h2>Purpose</h2>
 * <p>
 * This class consolidates cross-cutting concerns common to all list
 * implementations, including:
 * </p>
 * <ul>
 *   <li>Index boundary validation</li>
 *   <li>Insertion position validation</li>
 *   <li>Nullability enforcement</li>
 *   <li>Element count tracking</li>
 * </ul>
 *
 * <p>
 * Concrete subclasses are responsible exclusively for structural storage,
 * traversal mechanics, and node management.
 * </p>
 *
 * <h2>State Ownership</h2>
 * <ul>
 *   <li>{@code count} represents the number of elements currently stored</li>
 *   <li>{@code isNullable} defines whether {@code null} elements are permitted</li>
 * </ul>
 *
 * <h2>Index Semantics</h2>
 * <ul>
 *   <li>Valid access indices lie in the range {@code [0, count - 1]}</li>
 *   <li>Valid insertion indices lie in the range {@code [0, count]}</li>
 * </ul>
 *
 * <h2>Design Philosophy</h2>
 * <p>
 * This abstraction enforces correctness through explicit precondition checks
 * rather than duplicating defensive logic in each concrete implementation.
 * Centralizing validation improves consistency, safety, and maintainability.
 * </p>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * This class is <strong>not thread-safe</strong>. External synchronization is
 * required if instances are accessed concurrently.
 * </p>
 *
 * @param <T> the type of elements maintained by this list
 */
abstract class AbstractList<T>  extends AbstractCollection<T> implements List<T> {

    /**
     * Constructs an {@code AbstractList} with the specified nullability policy.
     *
     * @param isNullable {@code true} if {@code null} elements are allowed;
     *                   {@code false} otherwise
     */
    protected AbstractList(boolean isNullable) {
        this.isNullable = isNullable;
    }

    /**
     * Validates whether the specified index is a legal insertion position.
     *
     * <p>
     * A valid insertion index lies in the inclusive range {@code [0, count]}.
     * </p>
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if the index is outside the valid range
     */
    protected void checkIsInsertable(int index) {
        if (!isInInsertableBoundary(index))
            throwIndexOutOfBoundException(index);
    }

    /**
     * Validates whether the specified index refers to an existing element.
     *
     * <p>
     * A valid access index lies in the range {@code [0, count - 1]}.
     * </p>
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected void checkIndexOrElseThrow(int index) {
        if (!isValidIndex(index))
            throwIndexOutOfBoundException(index);
    }

    /**
     * Determines whether the specified index is within the legal insertion bounds.
     *
     * @param index the index to test
     * @return {@code true} if the index is in {@code [0, count]};
     *         {@code false} otherwise
     */
    protected boolean isInInsertableBoundary(int index) {
        return index >= 0 && index <= count;
    }

    /**
     * Determines whether the specified index refers to an existing element.
     *
     * @param index the index to test
     * @return {@code true} if the index is in {@code [0, count - 1]};
     *         {@code false} otherwise
     */
    protected boolean isValidIndex(int index) {
        return index >= 0 && index < count;
    }

    /**
     * Throws a standardized {@link IndexOutOfBoundsException} for the given index.
     *
     * <p>
     * Centralizing exception formatting ensures consistent diagnostics across
     * all list implementations.
     * </p>
     *
     * @param index the invalid index
     * @throws IndexOutOfBoundsException always
     */
    protected void throwIndexOutOfBoundException(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }

    /**
     * Enforces the list's nullability policy for the specified element.
     *
     * @param data the element to validate
     * @throws IllegalArgumentException if {@code null} is not permitted
     */
    protected void checkNullAllowed(Object data) {
        if (!isNullable && Objects.isNull(data))
            throw new IllegalArgumentException("List does not allow null values");
    }

    /**
     * Determines whether this list contains an element equal to the specified value.
     *
     * <p>
     * Equality comparison is performed using {@link Objects#equals(Object, Object)}
     * to safely support nullable elements when permitted by the list's policy.
     * </p>
     *
     * <h3>Performance Characteristics</h3>
     * <ul>
     *   <li>Best case: {@code O(1)}</li>
     *   <li>Worst case: {@code O(n)}</li>
     * </ul>
     *
     * <p>
     * This method does not modify the list and preserves all structural invariants.
     * </p>
     *
     * @param val the value whose presence is to be tested
     * @return {@code true} if an equal element exists; {@code false} otherwise
     */
    @Override
    public boolean contains(T val) {
        return hasElement(val);
    }

    /**
     * Returns the index of the first occurrence of the specified value in this list,
     * or {@code -1} if this list does not contain the value.
     *
     * <p><strong>Search Semantics:</strong>
     * This method performs a forward traversal of the list starting from the
     * logical head (index {@code 0}) and compares each element to the supplied
     * value using {@link Objects#equals(Object, Object)}. The first index at which
     * equality is observed is returned immediately.
     * </p>
     *
     * <p><strong>Equality Contract:</strong>
     * Equality comparison is delegated to {@link Objects#equals(Object, Object)}
     * to ensure:
     * <ul>
     *   <li>Null-safe comparison when {@code null} values are permitted</li>
     *   <li>Behavior consistent with {@link #contains(Object)}</li>
     *   <li>Compliance with standard {@code List} equality semantics</li>
     * </ul>
     * </p>
     *
     * <p><strong>Behavioral Guarantees:</strong>
     * <ul>
     *   <li>Returns the index of the <em>first</em> matching element</li>
     *   <li>Does not modify the list structure</li>
     *   <li>Terminates early upon successful match detection</li>
     * </ul>
     * </p>
     *
     * <p><strong>Performance Characteristics:</strong>
     * <ul>
     *   <li>Best case: {@code O(1)} — the element is at the head of the list</li>
     *   <li>Worst case: {@code O(n)} — the element is absent or located at the tail</li>
     *   <li>Space complexity: {@code O(1)}</li>
     * </ul>
     * </p>
     *
     * <p><strong>Design Rationale:</strong>
     * This implementation is intentionally independent of the underlying storage
     * strategy and relies solely on the list’s {@link Iterable} contract. This
     * ensures that all concrete list implementations inherit consistent search
     * semantics while retaining the freedom to override this method for
     * storage-specific optimizations.
     * </p>
     *
     * @param v the value to search for, which may be {@code null} if permitted by
     *          the list's nullability policy
     * @return the zero-based index of the first matching element, or {@code -1}
     *         if no such element exists
     */
    @Override
    public int indexOf(T v) {
        int i = 0;
        for (T val : this) {
            if (Objects.equals(val, v))
                return i;
            i++;
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This implementation iterates over the provided {@link Iterable} and verifies
     * that each element is present in this list. Containment checks are delegated
     * to {@link #contains(Object)} to preserve consistent equality semantics and
     * null-handling behavior.
     * </p>
     *
     * <p>
     * The method employs a fail-fast strategy and returns {@code false} immediately
     * upon detecting a missing element.
     * </p>
     *
     * @implNote
     * Correctness depends on a proper implementation of
     * {@link Object#equals(Object)} for the element type {@code T}.
     */
    @Override
    public boolean containsAll(Iterable<T> iterable) {
        return hasAllElements(iterable);
    }

    /**
     * Appends all elements from the specified {@link Iterable} to the end of this list,
     * in the order they are provided by the iterable's iterator.
     *
     * <h2>Behavior</h2>
     * <p>
     * This method sequentially iterates over the supplied {@code iterable} and delegates
     * each insertion to {@link #add(Object)}. As a result:
     * </p>
     * <ul>
     *   <li>Element order is preserved</li>
     *   <li>All validation rules defined by {@code add(T)} are enforced</li>
     *   <li>Element count is updated incrementally</li>
     * </ul>
     *
     * <h2>Null Handling</h2>
     * <p>
     * If this list does not permit {@code null} elements, encountering a {@code null}
     * value during iteration will result in an {@link IllegalArgumentException}.
     * </p>
     *
     * <h2>Failure Semantics</h2>
     * <p>
     * This operation is <strong>not atomic</strong>. If an exception is thrown while
     * processing the iterable, elements added prior to the failure will remain
     * in the list.
     * </p>
     *
     * <h2>Performance Characteristics</h2>
     * <ul>
     *   <li>Time Complexity: {@code O(n)} where {@code n} is the number of elements
     *       in the provided iterable</li>
     *   <li>Space Complexity: {@code O(1)} excluding storage required by subclasses</li>
     * </ul>
     *
     * <h2>Design Notes</h2>
     * <ul>
     *   <li>Delegating to {@link #add(Object)} ensures consistent validation,
     *       boundary checks, and nullability enforcement</li>
     *   <li>Concrete subclasses are responsible only for structural insertion</li>
     *   <li>This method does not attempt to optimize bulk insertion</li>
     * </ul>
     *
     * @param iterable the elements to be appended to this list
     * @return {@code true} upon successful completion
     * @throws NullPointerException if {@code iterable} is {@code null}
     * @throws IllegalArgumentException if a {@code null} element is encountered
     *                                  and the list does not permit {@code null} values
     */
    @Override
    public boolean addAll(Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "iterable must not be null");
        for (T e : iterable) {
            add(e);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns an array containing all elements of this list in proper iteration
     * order. If the runtime length of the supplied array is insufficient to
     * hold the elements, a new array of the same runtime component type is
     * allocated and returned.
     * </p>
     *
     * <p>
     * If the supplied array has a length greater than the number of elements
     * in this list, the element immediately following the last list element
     * is set to {@code null}, in accordance with the
     * {@link java.util.Collection#toArray(Object[])} contract.
     * </p>
     *
     * <p>
     * This implementation delegates the full array population logic to
     * {@link #finishToArray(Object[])}, ensuring that all concrete list
     * implementations inherit identical, specification-compliant behavior.
     * </p>
     *
     * @param a the array into which the elements of this list are to be stored,
     *          if it is large enough; otherwise, a new array of the same runtime
     *          type is allocated for this purpose
     * @return an array containing the elements of this list
     *
     * @implNote
     * Array conversion logic is centralized in the abstract superclass to
     * guarantee consistent semantics, ordering, and runtime type preservation
     * across all concrete list implementations.
     */
    @Override
    public T[] toArray(T[] a) {
        return finishToArray(a);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns a newly allocated {@code Object[]} containing all elements of this
     * list in proper iteration order. The returned array is independent of the
     * underlying list structure and may be freely modified by the caller.
     * </p>
     *
     * <p>
     * This implementation performs a single pass over the list using its
     * iterator and allocates an array sized exactly to the current element
     * count, ensuring predictable memory usage and linear-time complexity.
     * </p>
     *
     * @return an array containing all elements of this list
     *
     * @implNote
     * This method intentionally avoids delegating to
     * {@link #toArray(Object[])} to eliminate unnecessary runtime type checks
     * and conditional logic when an {@code Object[]} result is sufficient.
     */
    @Override
    public Object[] toArray() {
        Object[] response = new Object[count];
        int index = 0;
        for (T v : this)
            response[index++] = v;
        return response;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or {@code -1} if the element is not present.
     *
     * <p>
     * Element comparison is performed using {@link Objects#equals(Object, Object)},
     * which safely handles {@code null} values. This method
     * <strong>relies entirely on the correctness of the {@code equals} implementation</strong>
     * of the elements stored in the list. Subclasses that violate the general
     * {@code equals} contract may produce unexpected results.
     * </p>
     *
     * <p>
     * If the element occurs multiple times, this method returns the index
     * of the <em>rightmost</em> (last) occurrence in the logical iteration order.
     * </p>
     *
     * <p>
     * This default implementation performs a forward traversal of the list,
     * updating the last matching index each time the element is found. Subclasses
     * with efficient random access (e.g., array-backed lists) are encouraged to
     * override this method to improve performance.
     * </p>
     *
     * <p>
     * The returned index is zero-based.
     * </p>
     *
     * @param value the element to search for, may be {@code null}
     * @return the index of the last occurrence of the specified element,
     *         or {@code -1} if this list does not contain the element
     *
     * @implNote
     * Time complexity: O(n) in the default traversal
     * Space complexity: O(1)
     *
     * @apiNote
     * This method performs logical equality comparison, not reference comparison.
     * For identity-based searches, clients must use alternative mechanisms.
     */
    @Override
    public int lastIndexOf(final T value) {
        int lastIndex = -1;
        int index = 0;

        for (final T element : this) {
            if (Objects.equals(value, element)) {
                lastIndex = index;
            }
            index++;
        }

        return lastIndex;
    }
}
