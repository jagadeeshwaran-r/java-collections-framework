package com.util.collections.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractCollection<T> implements Collection<T> {

    protected AbstractCollection(){}

    /**
     * The number of elements currently contained in this collection.
     *
     * <p>
     * This field represents the logical size of the collection and must accurately
     * reflect the number of elements exposed through iteration.
     * </p>
     *
     * <p>
     * Concrete subclasses are responsible for maintaining this value whenever
     * elements are added or removed.
     * </p>
     *
     * <p>
     * This field is intentionally {@code protected} to allow direct access by
     * subclasses while preventing exposure through the public API.
     * </p>
     */
    protected int count;

    /**
     * Indicates whether this collection permits {@code null} elements.
     *
     * <p>
     * If {@code true}, {@code null} values are considered valid elements and are
     * allowed to be stored, traversed, and returned by this collection.
     * </p>
     *
     * <p>
     * If {@code false}, attempts to insert {@code null} elements must be rejected
     * by concrete implementations, typically by throwing an
     * {@link IllegalArgumentException}.
     * </p>
     *
     * <p>
     * This policy is enforced consistently across all operations that accept
     * external input, ensuring predictable null-handling semantics.
     * </p>
     */
    protected boolean isNullable;

    /**
     * Determines whether this collection contains an element equal to the
     * specified value.
     *
     * <p>
     * Element comparison is performed using {@link Objects#equals(Object, Object)}
     * to ensure safe and consistent equality semantics, including support for
     * {@code null} values when permitted by the collection’s nullability policy.
     * </p>
     *
     * <p>
     * If this collection does not allow {@code null} elements and the specified
     * value is {@code null}, this method returns {@code false} immediately without
     * performing iteration.
     * </p>
     *
     * <h3>Behavioral Guarantees</h3>
     * <ul>
     *   <li>Does not modify the collection</li>
     *   <li>Preserves iteration order</li>
     *   <li>Respects the collection’s nullability contract</li>
     * </ul>
     *
     * <h3>Performance Characteristics</h3>
     * <ul>
     *   <li>Best case: {@code O(1)} (first element matches)</li>
     *   <li>Worst case: {@code O(n)} where {@code n} is the number of elements</li>
     * </ul>
     *
     * <h3>Design Notes</h3>
     * <ul>
     *   <li>This method serves as a protected utility for implementing higher-level
     *       containment operations</li>
     *   <li>Concrete subclasses inherit consistent containment semantics without
     *       duplicating logic</li>
     * </ul>
     *
     * @param val the value whose presence is to be tested
     * @return {@code true} if an equal element exists in this collection;
     *         {@code false} otherwise
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
     * Determines whether this collection contains <em>all</em> elements provided
     * by the specified {@link Iterable}.
     *
     * <p>
     * This method iterates over the supplied {@code iterable} and verifies that
     * each element is present in this collection using {@link #hasElement(Object)}.
     * </p>
     *
     * <p>
     * The evaluation is <em>fail-fast</em>: the method returns {@code false}
     * immediately upon encountering an element that is not contained in this
     * collection.
     * </p>
     *
     * <h3>Behavioral Guarantees</h3>
     * <ul>
     *   <li>Does not modify the collection</li>
     *   <li>Preserves consistent containment and null-handling semantics</li>
     *   <li>Fails fast on the first missing element</li>
     * </ul>
     *
     * <h3>Performance Characteristics</h3>
     * <ul>
     *   <li>Time Complexity: {@code O(n × m)}, where {@code n} is the number of
     *       elements in this collection and {@code m} is the number of elements
     *       in the provided iterable</li>
     *   <li>Space Complexity: {@code O(1)}</li>
     * </ul>
     *
     * <h3>Design Notes</h3>
     * <ul>
     *   <li>Centralizes containment logic for reuse by public APIs</li>
     *   <li>Relies on {@link Object#equals(Object)} for correctness</li>
     *   <li>Intended for internal use by abstract and concrete collection
     *       implementations</li>
     * </ul>
     *
     * @param iterable the elements whose presence is to be verified
     * @return {@code true} if all elements are present; {@code false} otherwise
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
     * Copies the elements of this collection into the provided array, following
     * the semantics defined by {@link java.util.Collection#toArray(Object[])}.
     *
     * <p>
     * If the supplied array is large enough to hold all elements, the elements
     * are written into it in iteration order and the array is returned. If the
     * array is larger than required, the element immediately following the last
     * collection element is set to {@code null} as mandated by the Java
     * Collection specification.
     * </p>
     *
     * <p>
     * If the supplied array is too small, a new array of the <em>same runtime
     * component type</em> is allocated and populated with the elements of this
     * collection. This guarantees that the returned array preserves the caller’s
     * expected type.
     * </p>
     *
     * <h3>Behavioral Guarantees</h3>
     * <ul>
     *   <li>Preserves element iteration order</li>
     *   <li>Preserves runtime array type</li>
     *   <li>Does not expose internal storage representation</li>
     *   <li>Complies fully with the {@code Collection.toArray(T[])} contract</li>
     * </ul>
     *
     * <h3>Performance Characteristics</h3>
     * <ul>
     *   <li>Time Complexity: {@code O(n)} where {@code n} is the number of elements</li>
     *   <li>Space Complexity: {@code O(n)} only if array reallocation is required</li>
     * </ul>
     *
     * <h3>Design Notes</h3>
     * <ul>
     *   <li>This method relies exclusively on the collection’s iterator and makes
     *       no assumptions about the underlying storage structure</li>
     *   <li>Centralizing this logic ensures consistent {@code toArray} behavior
     *       across all concrete collection implementations</li>
     * </ul>
     *
     * @param a the destination array into which the elements of this collection
     *          are to be stored, if it is large enough
     * @return an array containing all elements of this collection
     * @throws NullPointerException if the specified array is {@code null}
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
     * Computes the hash code for this list based on its elements and their order.
     *
     * <p>This implementation uses a <em>polynomial rolling hash</em>, which is the
     * standard approach employed by core Java collection types such as
     * {@link java.util.List} and {@link java.util.ArrayList}.
     *
     * <p>The hash is defined by the polynomial:
     * <pre>
     *   H = e₀·pⁿ⁻¹ + e₁·pⁿ⁻² + ... + eₙ₋₁
     * </pre>
     * where {@code eᵢ} is the hash code of the i-th element, {@code p} is a fixed
     * prime base (typically {@code 31}), and {@code n} is the number of elements.
     *
     * <p>For efficiency, the polynomial is evaluated iteratively as:
     * <pre>
     *   H = 0
     *   H = H * p + e
     * </pre>
     * which avoids explicit power computations while producing the same result.
     *
     * <p>In concrete terms, the computation follows:
     * <pre>
     *   hash = 31 * hash + elementHash
     * </pre>
     *
     * <p>This technique ensures:
     * <ul>
     *   <li><strong>Order sensitivity</strong> — lists with the same elements in
     *       different orders will (with high probability) produce different hash codes.</li>
     *   <li><strong>Good hash distribution</strong> — the use of a prime multiplier
     *       reduces collisions in hash-based data structures.</li>
     *   <li><strong>Consistency with {@code equals(Object)}</strong> — equal lists
     *       produce identical hash codes.</li>
     * </ul>
     *
     * <p>{@code null} elements are supported and contribute a hash value of {@code 0}.
     *
     * <p>Integer overflow during computation is intentional and permitted.
     * Hash codes are computed modulo {@code 2^32}, as defined by the Java
     * Language Specification.
     *
     * @return the hash code value for this collection.
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T v : this)
            hashCode = 31 * hashCode + (v == null ? 0 : v.hashCode());
        return hashCode;
    }

    /**
     * Returns a string representation of this list.
     *
     * <p>The returned string is intended for diagnostic and debugging purposes
     * and follows a consistent, human-readable format:</p>
     *
     * <pre>
     * {@code
     * ClassName{}
     * ClassName{e1}
     * ClassName{e1, e2, ..., en}
     * }
     * </pre>
     *
     * <p><strong>Formatting Rules:</strong>
     * <ul>
     *   <li>The simple runtime class name is used as the prefix</li>
     *   <li>Elements are enclosed in curly braces {@code {}}</li>
     *   <li>Elements are separated by {@code ", "} (comma and space)</li>
     *   <li>No trailing delimiter is included</li>
     * </ul>
     *
     * <p><strong>Behavioral Guarantees:</strong>
     * <ul>
     *   <li>Returns {@code ClassName{}} for an empty list</li>
     *   <li>Preserves element iteration order</li>
     *   <li>Safely represents {@code null} elements if permitted by the list</li>
     *   <li>Does not expose internal storage structure</li>
     * </ul>
     *
     * <p><strong>Design Notes:</strong>
     * <ul>
     *   <li>This implementation relies solely on the public {@link Iterator}
     *       abstraction rather than internal node or array structures</li>
     *   <li>The method is side effect free and does not modify list state</li>
     *   <li>Subclasses automatically inherit correct string formatting without
     *       additional overrides</li>
     * </ul>
     *
     * @return a string representation of this list
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
