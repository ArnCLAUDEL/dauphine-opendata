package io.github.oliviercailloux.opendata.util;

/**
 * Acts exactly like a {@link Supplier} but declares one thrown exception. This
 * is very handy when using lambda and method references with methods that throw
 * only one exception, for example a {@code IOException}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <R> The result
 * @param <E> The thrown exception
 */
@FunctionalInterface
public interface ExceptionalSupplier<R, E extends Exception> {
	R get() throws E;
}
