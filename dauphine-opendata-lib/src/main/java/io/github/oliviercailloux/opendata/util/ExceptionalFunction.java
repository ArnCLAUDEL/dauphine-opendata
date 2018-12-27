package io.github.oliviercailloux.opendata.util;

@FunctionalInterface
public interface ExceptionalFunction<T, R, E extends Exception> {
	R apply(T t) throws E;
}