package io.github.oliviercailloux.opendata.entity;

import java.io.Serializable;

/**
 * Represents an entity.<br />
 * <br />
 * This only contains a single method {@link #getId()} which ease the
 * implementation of generic entity operations.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
public interface Entity extends Serializable {

	Long getId();

}
