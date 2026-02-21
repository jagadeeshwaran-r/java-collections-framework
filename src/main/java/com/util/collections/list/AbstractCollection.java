package com.util.collections.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Skeletal implementation of the {@link Collection} interface.
 *
 * <p>This abstract class exists to reduce the effort required to
 * implement the {@code Collection} interface. It provides common
 * implementations of core methods such as {@link #equals(Object)},
 * {@link #hashCode()}, {@link #toString()}, and shared utility
 * operations used by concrete collections.
 *
 * <p>Concrete subclasses are expected to define the underlying
 * storage mechanism and provide an {@link java.util.Iterator}
 * implementation. Subclasses must also ensure that the collection
 * size is correctly maintained when elements are added or removed.
 *
 * <p>This class centralizes reusable logic to promote consistency
 * across different collection implementations and to avoid code
 * duplication.
 *
 * @param <T> the type of elements maintained by this collection
 * @author Jagadeesh Waran
 */
public abstract class AbstractCollection<T> implements Collection<T> {

    protected AbstractCollection(){}

    /**
     * The number of elements in this collection.
     *
     * <p>Subclasses must update this field when elements
     * are added or removed.
     */
    protected int count;

    /**
     * Indicates whether {@code null} elements are permitted.
     *
     * <p>If {@code false}, attempts to insert {@code null}
     * should result in an exception in concrete implementations.
     */
    protected boolean isNullable;

    /**
     * Returns {@code true} if this collection contains an element
     * equal to the specified value.
     *
     * <p>Comparison is performed using {@link Objects#equals(Object, Object)}.
     * If {@code null} elements are not permitted and the given value
     * is {@code null}, this method returns {@code false}.
     *
     * @param val the value to test
     * @return {@code true} if an equal element exists
     */
    protected boolean hasElement(T val) {
        if (!isNullable && val == null)
            return false;

        for (T v : this)
            if (Objects.equals(v, val))
                return true;
        return false;
    }

    /**
     * Returns {@code true} if this collection contains all elements
     * from the given {@link Iterable}.
     *
     * <p>This method stops at the first missing element.
     *
     * @param iterable the elements to check
     * @return {@code true} if all elements are present
     * @throws NullPointerException if {@code iterable} is {@code null}
     */
    protected boolean hasAllElements(Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "iterable must not be null");
        for (T t : iterable)
            if (!hasElement(t))
                return false;
        return true;
    }

    /**
     * Copies the elements of this collection into the provided array.
     *
     * <p>If the array is too small, a new array of the same runtime
     * type is allocated. If the array is larger than needed, the
     * element immediately after the last entry is set to {@code null}.
     *
     * <p>Elements are stored in iteration order.
     *
     * @param a the destination array
     * @return an array containing all elements of this collection
     * @throws NullPointerException if {@code a} is {@code null}
     */
    protected T[] finishToArray(T[] a) {
        if (a.length < count)
            // Create a new array of the same runtime type
            a = Arrays.copyOf(a, count);

        int index = 0;
        for (T e : this)
            a[index++] = e;

        // Per Java spec: set first extra element to null
        if (a.length > count)
            a[count] = null;
        return a;
    }

    /**
     * Returns the hash code for this collection.
     *
     * <p>The hash code is computed as:
     *
     * <pre>
     * hash = 31 * hash + elementHash
     * </pre>
     *
     * <p>The computation is order-sensitive and consistent with
     * {@link #equals(Object)}. {@code null} elements contribute {@code 0}.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T v : this)
            hashCode = 31 * hashCode + (v == null ? 0 : v.hashCode());
        return hashCode;
    }

    /**
     * Compares the specified object with this collection for equality.
     *
     * <p>Returns {@code true} if the given object is also a
     * {@link Collection}, has the same size, and all corresponding
     * elements are equal in iteration order.
     *
     * @param o the object to compare
     * @return {@code true} if this collection is equal to {@code o}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collection<?> other)) return false;
        if (count != other.size()) return false;

        Iterator<?> i = other.iterator();
        for (T v : this) {
            if (!i.hasNext() || !Objects.equals(v, i.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of this collection.
     * The returned string consists of the simple runtime class name
     * followed by the elements enclosed in curly braces ({@code {}}).
     * Elements are separated by {@code ", "} and appear in iteration order.
     * For an empty collection, the result is {@code ClassName{}}.
     *
     * @return a string representation of this collection
     */
    @Override
    public String toString() {
        Iterator<T> iterator = iterator();
        if (!iterator.hasNext()) {
            return getClass().getSimpleName() + "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append("{");
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
