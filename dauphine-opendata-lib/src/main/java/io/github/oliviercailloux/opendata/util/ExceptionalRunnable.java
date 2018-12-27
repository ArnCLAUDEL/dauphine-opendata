package io.github.oliviercailloux.opendata.util;

@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {
	void run() throws E;
}
