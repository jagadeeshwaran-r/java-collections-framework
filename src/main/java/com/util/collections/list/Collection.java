package com.util.collections.list;

/**
 * Root abstraction representing a generic collection of elements.
 *
 * <p>
 * This interface defines the minimal contract for all collection types in this
 * framework. It extends {@link Iterable}, enabling element traversal via the
 * enhanced {@code for} loop and iterator-based APIs.
 * </p>
 *
 * <h2>Design Intent</h2>
 * <ul>
 *   <li>Acts as a common supertype for all collection abstractions</li>
 *   <li>Provides iteration capability without prescribing storage semantics</li>
 *   <li>Deliberately minimal to allow flexible extension by subinterfaces</li>
 * </ul>
 *
 * <h2>Relation to Java Collections Framework</h2>
 * <p>
 * This interface is conceptually analogous to {@link java.util.Collection},
 * but is intentionally scoped to serve as a lightweight foundation for
 * custom collection implementations.
 * </p>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * Implementations are <strong>not required</strong> to be thread-safe.
 * External synchronization is required when collections are accessed
 * concurrently.
 * @author Jagadeesh Waran
 * </p>
 *
 * @param <T> the type of elements maintained by this collection
 */
public interface Collection<T> extends Iterable<T> {

    /**
     * Returns the number of elements in this collection.
     *
     * <p>This method provides the current size of the collection. It is a core
     * contract of the {@link Collection} interface, and all inheriting collections
     * must provide an efficient and accurate implementation.
     *
     * <p><b>Default Behavior:</b> Since this is an interface, no default size
     * computation is provided. Implementors are responsible for maintaining
     * the size consistently with additions and removals.
     *
     * <p><b>Performance Note:</b> The method should ideally run in O(1) time.
     * If the underlying collection cannot maintain a cached size, the method
     * may iterate over elements to count them, which can impact performance
     * for large collections.
     *
     * <p><b>API Note:</b> This method is used by default {@link # equals(Object)}
     * and {@link # hashCode()} implementations to validate collection size.
     * Accuracy of {@code size()} is essential to ensure correctness of equality
     * checks and hash code calculations.
     *
     * @return the number of elements in this collection
     */
    int size();
}
