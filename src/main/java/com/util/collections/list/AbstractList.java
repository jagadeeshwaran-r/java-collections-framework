package com.util.collections.list;

import java.util.Objects;

/**
 * Abstract base class for list implementations providing common validation,
 * boundary checking, and null-handling policies.
 *
 * <p><strong>Purpose:</strong>
 * This class centralizes cross-cutting concerns that are common to all list
 * implementations, such as:
 * <ul>
 *   <li>Index boundary validation</li>
 *   <li>Insert-position validation</li>
 *   <li>Nullability enforcement</li>
 *   <li>Size tracking</li>
 * </ul>
 * Concrete subclasses are responsible solely for structural storage and
 * traversal logic.
 *
 * <p><strong>State Ownership:</strong>
 * <ul>
 *   <li>{@code count} represents the number of elements currently stored</li>
 *   <li>{@code isNullable} defines whether {@code null} elements are permitted</li>
 * </ul>
 *
 * <p><strong>Index Semantics:</strong>
 * <ul>
 *   <li>Valid access indices are in the range {@code [0, count - 1]}</li>
 *   <li>Valid insertion indices are in the range {@code [0, count]}</li>
 * </ul>
 *
 * <p><strong>Design Philosophy:</strong>
 * This abstraction enforces correctness through explicit precondition checks
 * rather than defensive programming in each concrete implementation. By
 * consolidating validation logic here, subclasses remain simpler, safer, and
 * easier to reason about.
 *
 * <p><strong>Thread Safety:</strong>
 * This class is not thread-safe. External synchronization is required if
 * instances are accessed concurrently.
 *
 */
abstract class AbstractList<T> implements List<T> {

    /**
     * The number of elements currently contained in the list.
     *
     * <p>This value is maintained by concrete subclasses and must accurately
     * reflect the number of structurally linked elements.</p>
     */
    protected int count = 0;

    /**
     * Indicates whether {@code null} elements are permitted in this list.
     *
     * <p>This policy is immutable and enforced consistently across all
     * mutating operations.</p>
     */
    protected final boolean isNullable;

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
     * <p>A valid insertion index lies in the inclusive range {@code [0, count]}.</p>
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected void checkIsInsertable(int index) {
        if (!isInInsertableBoundary(index)) {
            throwIndexOutOfBoundException(index);
        }
    }

    /**
     * Validates whether the specified index refers to an existing element.
     *
     * <p>A valid access index lies in the range {@code [0, count - 1]}.</p>
     *
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected void checkIndexOrElseThrow(int index) {
        if (!isValidIndex(index)) {
            throwIndexOutOfBoundException(index);
        }
    }

    /**
     * Determines whether the specified index is within the legal insertion bounds.
     *
     * @param index the index to test
     * @return {@code true} if the index is in {@code [0, count]}, {@code false} otherwise
     */
    protected boolean isInInsertableBoundary(int index) {
        return index >= 0 && index <= count;
    }

    /**
     * Determines whether the specified index refers to an existing element.
     *
     * @param index the index to test
     * @return {@code true} if the index is in {@code [0, count - 1]}, {@code false} otherwise
     */
    protected boolean isValidIndex(int index) {
        return index >= 0 && index < count;
    }

    /**
     * Throws a standardized {@link IndexOutOfBoundsException} for the given index.
     *
     * <p>This method centralizes exception formatting to ensure consistent
     * error messages across all list implementations.</p>
     *
     * @param index the invalid index
     * @throws IndexOutOfBoundsException always
     */
    protected void throwIndexOutOfBoundException(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }

    /**
     * Enforces the list's nullability policy for a given element.
     *
     * <p>If {@code isNullable == false}, passing {@code null} will result in
     * an {@link IllegalArgumentException}.</p>
     *
     * @param data the element to validate
     * @throws IllegalArgumentException if {@code null} is not permitted
     */
    protected void checkNullAllowed(T data) {
        if (!isNullable && Objects.isNull(data)) {
            throw new IllegalArgumentException("List doesn't allow null value");
        }
    }
}
