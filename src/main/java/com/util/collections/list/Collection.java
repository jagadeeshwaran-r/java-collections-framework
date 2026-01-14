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
}
