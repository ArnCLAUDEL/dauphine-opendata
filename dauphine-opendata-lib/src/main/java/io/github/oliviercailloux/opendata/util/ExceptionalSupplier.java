package io.github.oliviercailloux.opendata.util;

@FunctionalInterface
public interface ExceptionalSupplier<R, E extends Exception> {
	R get() throws E;
}
