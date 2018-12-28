package io.github.oliviercailloux.opendata.util;

/**
 * Acts exactly like a {@link Runnable} but declares one thrown exception. This
 * is very handy when using lambda and method references with methods that throw
 * only one exception, for example a {@code IOException}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <E> The thrown exception
 */
@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {
	void run() throws E;
}
