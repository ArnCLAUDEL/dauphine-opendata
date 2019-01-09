package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import io.github.oliviercailloux.opendata.entity.Entity;

/**
 * Represents a basic <tt>Data Access Object</tt> for <tt>CRUD</tt> operation
 * with {@link Entity} implementations.<br />
 * Every methods throws {@link DaoException} when an error occurs during the
 * transaction.
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
	 * @throws DaoException If an error occurs during the transaction
	 */
	List<E> findAll();

	/**
	 * Finds the entity with the given id.
	 *
	 * @throws DaoException If an error occurs during the transaction
	 */
	E findOne(Long id);

	/**
	 * Persists the given entity.
	 *
	 * @return The created entity
	 * @throws EntityAlreadyExistsDaoException If the given entity already exists
	 * @throws DaoException                    If an error occurs during the
	 *                                         transaction
	 */
	E persist(E entity) throws EntityAlreadyExistsDaoException;

	/**
	 * Merges the given entity with the one in the DB.
	 *
	 * @return The merged entity
	 * @throws DaoException If an error occurs during the transaction
	 */
	E merge(E entity);

	/**
	 * Removes the entity with the given id.
	 *
	 * @throws EntityDoesNotExistDaoException If the entity does not exist
	 * @throws DaoException                   If an error occurs during the
	 *                                        transaction
	 */
	void remove(Long id) throws EntityDoesNotExistDaoException;

	/**
	 * Flushes the underlying context.
	 *
	 * @throws DaoException If an error occurs during the transaction
	 */
	void flush();
}