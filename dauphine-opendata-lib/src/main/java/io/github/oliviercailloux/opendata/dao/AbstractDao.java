package io.github.oliviercailloux.opendata.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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

	@Override
	public Optional<E> findOne(final Long id) {
		LOGGER.info("finding entity with id [{}] ..", id);
		Preconditions.checkNotNull(id, "cannot find an entity with a null id");
		return Optional.ofNullable(entityManager.find(entityClass, id));
	}

	@Override
	public E persist(final E entity) throws EntityAlreadyExistsDaoException {
		LOGGER.info("creating entity [{}] ..", entity);
		Preconditions.checkNotNull(entity, "cannot persist a null entity");

		if (entity.getId() != null) {
			final Optional<E> existingEntityOpt = findOne(entity.getId());
			if (existingEntityOpt.isPresent()) {
				final String errorMessage = "entity with id [" + existingEntityOpt.get().getId() + "] already exists";
				LOGGER.error(errorMessage);
				throw new EntityAlreadyExistsDaoException(errorMessage);
			}
		}

		entityManager.persist(entity);
		return entity;
	}

	@Override
	public E merge(final E entity) {
		LOGGER.info("merging entity with id [{}] ..", entity.getId());
		Preconditions.checkNotNull(entity, "cannot merge a null entity");
		return entityManager.merge(entity);
	}

	@Override
	public void remove(final Long id) throws EntityDoesNotExistDaoException {
		LOGGER.info("removing entity with id [{}] ..", id);
		Preconditions.checkNotNull(id, "cannot remove an entity with a null id");

		final Optional<E> entityOpt = findOne(id);
		if (!entityOpt.isPresent()) {
			final String errorMessage = "entity with id [" + id + "] does not exist";
			LOGGER.error(errorMessage);
			throw new EntityDoesNotExistDaoException(errorMessage);
		}
		entityManager.remove(entityOpt.get());
	}

	@Override
	public void flush() {
		LOGGER.info("flushing entity manager ..");
		entityManager.flush();
	}

}
