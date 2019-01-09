package io.github.oliviercailloux.opendata.dao;

import java.util.List;
import java.util.Optional;

import io.github.oliviercailloux.opendata.entity.Entity;

/**
 * Represents a basic <tt>Data Access Object</tt> for <tt>CRUD</tt> operation
 * with {@link Entity} implementations.<br />
 * <br />
 * Every method rejects null argument.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <E> The entity
 */
public interface Dao<E extends Entity> {

	/**
	 * Finds all entities.
	 *
	 * @return All entities
	 */
	List<E> findAll();

	/**
	 * Finds the entity with the given id.
	 *
	 * @param id The id of the entity
	 * @return The entity or empty if it does not exist
	 */
	Optional<E> findOne(Long id);

	/**
	 * Persists the given entity.
	 *
	 * @param The entity to persist
	 * @return The persisted entity
	 * @throws EntityAlreadyExistsDaoException If the given entity already exists
	 */
	E persist(E entity) throws EntityAlreadyExistsDaoException;

	/**
	 * Merges the given entity with the one in the DB.
	 *
	 * @param entity The entity to merge
	 * @return The merged entity
	 */
	E merge(E entity);

	/**
	 * Removes the entity with the given id.
	 *
	 * @param id The id of the entity to remove
	 * @throws EntityDoesNotExistDaoException If the entity does not exist
	 */
	void remove(Long id) throws EntityDoesNotExistDaoException;

	/**
	 * Flushes the underlying context.
	 */
	void flush();
}