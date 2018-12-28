package io.github.oliviercailloux.opendata.util;

/**
 * Acts exactly like a {@link Function} but declares one thrown exception. This
 * is very handy when using lambda and method references with methods that throw
 * only one exception, for example a {@code IOException}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <T> The argument
 * @param <R> The result
 * @param <E> The thrown exception
 */
@FunctionalInterface
public interface ExceptionalFunction<T, R, E extends Exception> {
	R apply(T t) throws E;
}