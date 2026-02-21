package com.util.collections.list;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Skeletal implementation of the {@link List} interface.
 *
 * <p>This class provides common validation and bookkeeping logic
 * shared by all list implementations. It centralizes index boundary
 * checks, insertion position validation, null-handling policy,
 * and element count management.
 *
 * <p>Concrete subclasses are responsible for the underlying storage
 * structure and for implementing element access, modification,
 * and traversal mechanics.
 *
 * <p>Valid element indices are in the range {@code [0, count - 1]}.
 * Valid insertion indices are in the range {@code [0, count]}.
 *
 * <p>This class is not thread-safe. If multiple threads access a list
 * concurrently and at least one thread modifies it, external
 * synchronization is required.
 *
 * @param <T> the type of elements maintained by this list
 * @author Jagadeesh Waran
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
     * Returns {@code true} if this list contains an element
     * equal to the specified value.
     *
     * <p>More formally, returns {@code true} if and only if this list
     * contains at least one element {@code e} such that
     * {@code Objects.equals(e, val)}.
     *
     * @param val the value whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    @Override
    public boolean contains(T val) {
        return hasElement(val);
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or {@code -1} if this list does not contain the element.
     *
     * <p>More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(get(i), v)}, or {@code -1} if there is
     * no such index.
     *
     * @param v the element to search for
     * @return the index of the first occurrence of the specified element,
     *         or {@code -1} if this list does not contain the element
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
     * Appends all elements from the specified {@link Iterable}
     * to the end of this list, in the order returned by its iterator.
     *
     * <p>This method iterates over the given {@code iterable} and
     * adds each element using {@link #add(Object)}. If an exception
     * occurs while adding an element, elements successfully added
     * before the exception remain in the list.
     *
     * @param iterable the elements to be added to this list
     * @return {@code true} if this list was modified
     * @throws NullPointerException if {@code iterable} is {@code null}
     * @throws IllegalArgumentException if a {@code null} element is encountered
     *         and this list does not permit {@code null} values
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
     * <p>This implementation delegates to {@link #finishToArray(Object[])}
     * to perform the array population logic.
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

    /**
     * Creates and returns a new, empty list instance of the same
     * concrete type as this list.
     *
     * <p>This method is intended for use by internal operations that
     * need to create a new list (for example, filtering or transformation
     * operations) while preserving implementation-specific characteristics.
     *
     * <p>The returned list must be empty and ready to accept elements.
     *
     * @return a new empty list
     *
     * @implSpec
     * Subclasses must return a new, independent list instance.
     * The returned list should have the same ordering semantics and
     * null-handling behavior as this list.
     */
    protected abstract List<T> createEmptyList();

    /**
     * Returns a list consisting of the elements of this list that satisfy
     * the given predicate.
     *
     * <p>The elements are evaluated in the order of iteration of this list.
     * The returned list preserves the encounter order of the elements that
     * match the predicate.
     *
     * <p>The returned list is independent of this list unless otherwise
     * specified by the implementation.
     *
     * @param condition the predicate used to test elements
     * @return a list containing the elements of this list that match
     *         the given predicate
     * @throws NullPointerException if the specified predicate is {@code null}
     *
     * @implSpec
     * The default implementation iterates over this list and adds each
     * element that satisfies the predicate to a newly created list.
     */
    @Override
    public List<T> where(Predicate<? super T> condition) {
        Objects.requireNonNull(condition, "Condition must not be null");
        List<T> bucket = createEmptyList();
        for (T v : this)
            if (condition.test(v))
                bucket.add(v);
        return bucket;
    }

    /**
     * Returns the first element of this list that satisfies the given predicate,
     * or {@code null} if no such element exists.
     *
     * <p>This method iterates over the elements in iteration order and applies
     * the specified predicate to each element until a match is found.
     *
     * @param condition the predicate used to test elements
     * @return the first matching element, or {@code null} if none match
     * @throws NullPointerException if {@code condition} is {@code null}
     */
    @Override
    public T firstWhere(Predicate<T> condition) {
        if (condition == null) {
            throw new NullPointerException("Predicate condition must not be null");
        }

        for (T v : this) {
            if (condition.test(v)) {
                return v;
            }
        }
        return null;
    }
}
