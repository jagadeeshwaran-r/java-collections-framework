package com.util.collections.list;

import java.util.function.Predicate;

/**
 * Represents an ordered collection of elements.
 *
 * <p>A list defines a positional structure where each element occupies
 * a specific index. The position of an element is stable relative to
 * the ordering rules of the implementation and forms part of the list’s
 * observable contract.
 *
 * <p>Unlike the root {@code Collection} abstraction, a list introduces
 * ordering semantics. Elements are arranged in a linear sequence and
 * may appear multiple times unless otherwise restricted by a concrete
 * implementation.
 *
 * <p>This interface does not mandate mutability. Some implementations
 * may allow structural modification, while others may represent
 * immutable or fixed-size views. Mutation capabilities, if supported,
 * are defined by the implementation.
 *
 * <p>Index-based access is a defining characteristic of a list.
 * Implementations may vary in how efficiently indexed operations are
 * performed depending on their underlying structure.
 *
 * <p>The purpose of this abstraction is to model sequential data with
 * positional awareness while remaining independent of storage strategy
 * or performance characteristics.
 *
 * <p>Thread-safety is not implied and must be explicitly provided by
 * implementations when required.
 *
 * @author Jagadeesh Waran
 * @param <T> the type of elements maintained in this list
 */
public interface List<T> extends Collection<T> {

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>After this method returns successfully, the element will be
     * added to the end of the list, the size will increase by one,
     * and the relative order of existing elements will be preserved.
     *
     * <p>This operation modifies the list structurally. Whether null
     * elements are permitted is implementation-specific.
     *
     * <p>No guarantees are made regarding performance. The time
     * complexity depends on the implementation.
     *
     * @param val the element to be appended to this list
     * @return true if this list was modified as a result of this call
     * @throws IllegalArgumentException if null values are not permitted
     *         and the specified element is null
     */
    boolean add(T val);

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * <p>The element is inserted at the given index, and any elements
     * currently at that position, or after it are shifted one position
     * to the right. The size of the list increases by one.
     *
     * <p>Indices are zero-based. An index of 0 inserts at the beginning
     * of the list, and an index equal to size() inserts at the end.
     *
     * <p>This operation modifies the list structurally. Whether null
     * elements are permitted is implementation-specific.
     *
     * <p>No guarantees are made regarding performance. The time complexity
     * depends on the implementation.
     *
     * @param val the element to be inserted
     * @param index the position at which the element is to be inserted
     * @return true if this list was modified as a result of this call
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 or index > size())
     * @throws IllegalArgumentException if null values are not permitted
     *         and the specified element is null
     */
    boolean add(T val, int index);

    /**
     * Returns the element at the specified position in this list.
     *
     * <p>Indices are zero-based. The valid range is from 0 (inclusive)
     * to size() (exclusive).
     *
     * <p>This method provides positional access to elements and does not
     * modify the list. If null elements are permitted by the implementation,
     * this method may return null.
     *
     * <p>No guarantees are made regarding performance. Access time depends
     * on the implementation.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 or index >= size())
     */
    T get(int index);

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.
     *
     * <p>If the element is found, exactly one matching element is removed,
     * the size of the list decreases by one, and the relative order of the
     * remaining elements is preserved. If the element is not present, the
     * list remains unchanged.
     *
     * <p>Equality is determined using the equals' method.
     *
     * <p>Whether null elements are permitted is implementation-specific.
     *
     * <p>No guarantees are made regarding performance. The time complexity
     * depends on the implementation.
     *
     * @param val the element to be removed from this list
     * @return true if this list was modified as a result of this call;
     *         otherwise false
     * @throws IllegalArgumentException if null values are not permitted
     *         and the specified element is null
     */
    boolean remove(Object val);

    /**
     * Returns true if this list contains the specified element.
     *
     * <p>More formally, this method returns true if and only if the list
     * contains at least one element equal to the specified value.
     * Equality is determined using the equals' method.
     *
     * <p>This method does not modify the list.
     *
     * <p>Whether null elements are permitted is implementation-specific.
     *
     * <p>No guarantees are made regarding performance. The time complexity
     * depends on the implementation.
     *
     * @param val the element whose presence is to be tested
     * @return true if this list contains the specified element;
     *         otherwise false
     * @throws IllegalArgumentException if null values are not permitted
     *         and the specified element is null
     */
    boolean contains(T val);

    /**
     * Returns the number of elements in this list.
     *
     * <p>The returned value is always non-negative and reflects the number
     * of elements that would be returned by iterating over the list.
     * This method does not modify the list.
     *
     * <p>No guarantees are made regarding performance. The time complexity
     * depends on the implementation.
     *
     * @return the number of elements in this list
     */
    int size();

    /**
     * Returns true if this list contains no elements.
     *
     * <p>This method returns true if and only if size() is 0.
     * The list is not modified by this operation.
     *
     * <p>No guarantees are made regarding performance. The time
     * complexity depends on the implementation.
     *
     * @return true if this list contains no elements; otherwise false
     */
    boolean isEmpty();

    /**
     * Removes all elements from this list.
     *
     * <p>After this method returns, the list will be empty.
     * Calling size() will return 0 and isEmpty() will return true.
     * Elements previously contained in the list will no longer be
     * accessible through iteration or indexed access.
     *
     * <p>This operation performs a structural modification.
     *
     * <p>No guarantees are made regarding performance. The time complexity
     * depends on the implementation.
     *
     * @throws UnsupportedOperationException if this list does not support
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

    /**
     * Returns {@code true} if this list contains all elements of the specified
     * {@link Iterable}.
     * Returns true if this list contains all elements of the specified iterable.
     *
     * <p>More formally, this method returns true if for every element in the
     * given iterable, this list contains at least one equal element.
     * Equality is determined using the equals' method. Two null elements
     * are considered equal.
     *
     * <p>The order of elements in the iterable does not affect the result,
     * and duplicate elements are ignored. This method does not modify
     * the list.
     *
     * <p>The correctness of this operation depends on a proper implementation
     * of the equals method for the element type.
     *
     * @param iterable the elements to check for containment
     * @return true if this list contains all elements of the iterable;
     *         otherwise false
     * @throws NullPointerException if the specified iterable is null
     *
     * @implSpec
     * The default implementation iterates over the iterable and calls
     * contains(Object) for each element.
     */
    boolean containsAll(Iterable<T> iterable);

    /**
     * Appends all elements from the given iterable to the end of this list,
     * preserving their iteration order.
     *
     * <p>If this list is modified as a result of this call, its size increases
     * by the number of elements successfully added.
     *
     * <p>Null handling and structural modification behavior are
     * implementation-specific.
     *
     * @param iterable the elements to append
     * @return {@code true} if this list was modified
     * @throws NullPointerException if {@code elements} is {@code null}
     */
    boolean addAll(Iterable<T> iterable);

    /**
     * Returns an array containing all elements of this list in proper iteration
     * order; the runtime type of the returned array is that of the specified array.
     *
     * <p>
     * If the list fits in the specified array, it is returned therein. Otherwise,
     * a new array is allocated with the runtime component type of the specified
     * array and the size of this list.
     * </p>
     *
     * <p>
     * If the list fits in the specified array with room to spare (i.e., the array
     * has more elements than the list), the element in the array immediately
     * following the end of the list is set to {@code null}. This is useful in
     * determining the length of the list <i>only</i> if the caller knows that the
     * list does not contain any {@code null} elements.
     * </p>
     *
     * <p>
     * The elements are returned in the order defined by this list's iterator.
     * </p>
     *
     * @param a the array into which the elements of this list are to be stored,
     *          if it is large enough; otherwise, a new array of the same runtime
     *          type is allocated for this purpose
     * @return an array containing the elements of this list
     *
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in this list
     * @throws NullPointerException if the specified array is {@code null}
     *
     * @see #toArray()
     * @see java.util.Collection#toArray(Object[])
     */
    T[] toArray(T[] a);

    /**
     * Returns an array containing all elements of this list in iteration order.
     *
     * <p>The returned array is newly allocated and independent of this list.
     * Changes to the returned array will not affect the list, and changes to
     * the list will not be reflected in the array.
     *
     * <p>The elements appear in the order defined by this list's iterator.
     *
     * @return an array containing all elements of this list
     * @see #toArray(Object[])
     * @see java.util.Collection#toArray()
     */
    Object[] toArray();

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or {@code -1} if this list does not contain the element.
     *
     * <p>
     * More formally, returns the lowest index {@code i} such that the element
     * at position {@code i} is equal to the specified element, or
     * {@code -1} if there is no such index.
     *
     * <p>
     * If the list permits {@code null} elements, this method may search
     * for {@code null}.
     *
     * @param v the element to search for
     * @return the index of the first occurrence of the specified element,
     *         or {@code -1} if this list does not contain the element
     */
    int indexOf(T v);

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or {@code -1} if this list does not contain it.
     *
     * <p>If the element appears multiple times, the highest index is returned.
     * Element comparison follows the list’s equality semantics.
     *
     * @param element the element to search for, may be {@code null}
     * @return the index of the last matching element, or {@code -1} if not found
     */
    int lastIndexOf(T element);

    /**
     * Returns a list consisting of the elements of this list that satisfy
     * the given predicate.
     *
     * <p>The elements are evaluated in the order of iteration of this list.
     * The returned list preserves the encounter order of the elements that
     * match the predicate.
     *
     * <p>The returned list is independent of this list unless otherwise
     * specified by the implementation. Changes to the returned list will not
     * affect this list, and vice versa.
     *
     * @param condition the predicate used to test elements
     * @return a list containing all elements of this list that match the given predicate
     * @throws NullPointerException if the specified predicate is {@code null}
     */
    List<T> where(Predicate<? super T> condition);
    /**
     * Returns the first element in this list that satisfies the given predicate.
     *
     * <p><strong>Contract:</strong>
     * This method traverses the list in iteration order and evaluates the provided
     * {@code condition} against each element. The first element for which the
     * predicate returns {@code true} is returned immediately.
     *
     * <p>If no element satisfies the predicate, this method returns {@code null}.
     * The list is not modified by this operation.</p>
     *
     * <p><strong>Evaluation Semantics:</strong>
     * <ul>
     *   <li>Elements are evaluated strictly in list order (from index {@code 0} onward)</li>
     *   <li>Evaluation stops as soon as a matching element is found</li>
     *   <li>The predicate is applied at most {@code size()} times</li>
     * </ul>
     *
     * <p><strong>Null Handling:</strong>
     * <ul>
     *   <li>The supplied {@code condition} must not be {@code null}</li>
     *   <li>If {@code null} elements are permitted by the implementation, the
     *       predicate may receive {@code null} values</li>
     *   <li>A {@code null} return value may indicate either:
     *     <ul>
     *       <li>No matching element was found, or</li>
     *       <li>The first matching element itself is {@code null}</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * <p><strong>Performance Characteristics:</strong>
     * Time complexity is implementation-dependent but is typically linear
     * ({@code O(n)}) for sequential-access lists.
     *
     * <p><strong>Design Rationale:</strong>
     * This method provides a declarative, intention-revealing alternative to
     * manual iteration for locating elements based on behavioral criteria.
     * It is conceptually equivalent to a short-circuiting filter operation.
     *
     * @param condition a predicate used to test elements
     * @return the first element that satisfies the predicate, or {@code null}
     *         if no such element exists
     *
     * @throws NullPointerException if {@code condition} is {@code null}
     *
     * @apiNote
     * Callers that need to distinguish between “no match” and “matched {@code null}”
     * should use {@link #indexOf(Object)} or perform explicit iteration.
     */
    T firstWhere(Predicate<T> condition);

}
