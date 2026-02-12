package com.util.collections.list;

/**
 * Represents the fundamental abstraction of a group of elements.
 *
 * <p>This interface serves as the root contract for all collection types
 * within this framework. It defines the minimal capability required for a
 * structure to be considered a collection: the ability to contain elements
 * and provide traversal over them.
 *
 * <p>No assumptions are made about ordering, uniqueness, mutability,
 * indexing, or storage strategy. Those characteristics are defined by
 * specialized subinterfaces such as ordered, indexed, or set-like
 * collections.
 *
 * <p>The purpose of this abstraction is to provide a common behavioral
 * foundation that enables polymorphic use of different collection types
 * without coupling code to specific structural semantics.
 *
 * <p>By keeping this interface intentionally minimal, the design promotes:
 * consistency across collection implementations, clear capability layering,
 * and flexibility for future extensions.
 *
 * <p>Thread-safety is not implied. Concurrency guarantees, if any,
 * must be provided and documented by concrete implementations.
 * @author jagadeesh waran
 * @param <T> the type of elements contained in this collection
 */
public interface Collection<T> extends Iterable<T> {

    /**
     * Returns the number of elements in this collection.
     *
     * @return the current element count
     */
    int size();
}
