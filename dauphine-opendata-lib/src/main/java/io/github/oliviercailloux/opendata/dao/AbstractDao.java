package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.entity.Entity;

/**
 * Provides a base implementation of the interface {@link Dao}.<br />
 * <br />
 * This implementation uses an {@link EntityManager} to interact with the
 * persistence layer. Only the class and the name of the entity are required to
 * implement all methods.<br />
 * <br />
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <E> The entity
 */
public abstract class AbstractDao<E extends Entity> implements Dao<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);

	protected final EntityManager entityManager;

	/**
	 * The class of the entity, used with the entity manager.
	 */
	protected final Class<E> entityClass;

	/**
	 * The name of entity, should match with the table name since it is used to
	 * build SQL query.
	 */
	protected final String entityName;

	/**
	 * This constructor expects both managed entity manager and user transaction.
	 *
	 * @param entityManager A managed entity manager
	 * @param entityClass   The class of the entity, used with the entity manager
	 * @param entityName    The name of entity, should match with the table name
	 *                      since it is used to build SQL query
	 */
	public AbstractDao(final EntityManager entityManager, final Class<E> entityClass, final String entityName) {
		this.entityManager = Preconditions.checkNotNull(entityManager, "entityManager");
		this.entityClass = Preconditions.checkNotNull(entityClass, "entityClass");
		this.entityName = Preconditions.checkNotNull(entityName, "entityName");
	}

	@Override
	public List<E> findAll() {
		LOGGER.info("finding all entities ..");
		return entityManager.createQuery("SELECT t FROM " + entityName + " t", entityClass).getResultList();
	}

	/**
	 * Returns the entity with the given id.<br />
	 *
	 * @param id The id of the entity, must not be null
	 * @return null if the entity does not exist or the given id is null
	 */
	@Override
	public E findOne(final Long id) {
		LOGGER.info("finding entity with id [{}] ..", id);
		Preconditions.checkNotNull(id, "cannot find an entity with a null id");
		return entityManager.find(entityClass, id);
	}

	/**
	 * Persists the given entity.
	 *
	 * @param entity The entity to persist, must not be null
	 * @return The managed entity
	 * @throws EntityAlreadyExistsDaoException If a {@link PersistenceException} is
	 *                                         thrown by
	 *                                         {@link EntityManager#persist(Object)}
	 */
	@Override
	public E persist(final E entity) throws EntityAlreadyExistsDaoException {
		LOGGER.info("creating entity [{}] ..", entity);
		Preconditions.checkNotNull(entity, "cannot persist a null entity");

		if (entity.getId() != null) {
			final E existingEntity = findOne(entity.getId());
			if (existingEntity != null) {
				final String errorMessage = "entity with id [" + existingEntity.getId() + "] already exists";
				LOGGER.error(errorMessage);
				throw new EntityAlreadyExistsDaoException(errorMessage);
			}
		}

		entityManager.persist(entity);
		return entity;
	}

	/**
	 * @param entity the entity to merge, must not be null
	 */
	@Override
	public E merge(final E entity) {
		LOGGER.info("merging entity with id [{}] ..", entity.getId());
		Preconditions.checkNotNull(entity, "cannot merge a null entity");
		return entityManager.merge(entity);
	}

	/**
	 * Remove the entity with the given id.
	 *
	 * @param id The id of the entity to remove, must not be null
	 * @throws EntityDoesNotExistDaoException The entity does not exist
	 */
	@Override
	public void remove(final Long id) throws EntityDoesNotExistDaoException {
		LOGGER.info("removing entity with id [{}] ..", id);
		Preconditions.checkNotNull(id, "cannot remove an entity with a null id");

		final E entity = findOne(id);
		if (entity == null) {
			final String errorMessage = "entity with id [" + id + "] does not exist";
			LOGGER.error(errorMessage);
			throw new EntityDoesNotExistDaoException(errorMessage);
		}
		entityManager.remove(entity);
	}

	@Override
	public void flush() {
		LOGGER.info("flushing entity manager ..");
		entityManager.flush();
	}

}
