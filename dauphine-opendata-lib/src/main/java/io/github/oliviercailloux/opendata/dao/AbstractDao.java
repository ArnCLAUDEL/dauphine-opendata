package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.entity.Entity;
import io.github.oliviercailloux.opendata.util.ExceptionalRunnable;
import io.github.oliviercailloux.opendata.util.ExceptionalSupplier;

/**
 * Provides a base implementation of the interface {@link Dao}.<br />
 * <br />
 * This implementation uses an {@link EntityManager} and a
 * {@link UserTransaction} to interact with the persistence layer. Only the
 * class and the name of the entity are required to implement all methods.<br />
 * <br />
 * Each method has its associated method doXXX() which contains the interaction
 * with the entityManager. These methods are then given to either
 * {@link #executeWithTransaction(ExceptionalRunnable)} or
 * {@link #executeWithTransaction(ExceptionalSupplier)} to be executed in a
 * transaction. <br />
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
	public List<E> findAll() throws DaoException {
		LOGGER.info("finding all entities ..");
		return entityManager.createQuery("SELECT t FROM " + entityName + " t", entityClass).getResultList();
	}

	/**
	 * Returns the entity with the given id.<br />
	 * Note that it returns null if the given id is null.
	 *
	 * @param id The id of the entity
	 * @return null if the entity does not exist or the given id is null
	 */
	@Override
	public E findOne(final Long id) throws DaoException {
		LOGGER.info("finding entity with id [{}] ..", id);
		if (id == null) {
			LOGGER.warn("received id null, returning null value by default");
			// TODO fail with NPE instead ?
			return null;
		}
		return entityManager.find(entityClass, id);
	}

	/**
	 * Persists the given entity.
	 *
	 * @param entity The entity to persist
	 * @return The managed entity
	 * @throws EntityAlreadyExistsDaoException If a {@link PersistenceException} is
	 *                                         thrown by
	 *                                         {@link EntityManager#persist(Object)}
	 */
	@Override
	public E persist(final E entity) throws DaoException {
		LOGGER.info("creating entity [{}] ..", entity);
		try {
			entityManager.persist(entity);
			return findOne(entity.getId());
		} catch (final PersistenceException e) {
			throw new EntityAlreadyExistsDaoException(e);
		}
	}

	@Override
	public E merge(final E entity) throws DaoException {
		LOGGER.info("merging entity with id [{}] ..", entity.getId());
		return entityManager.merge(entity);
	}

	/**
	 * Remove the entity with the given id.
	 *
	 * @param id The id of the entity to remove
	 * @throws EntityDoesNotExistDaoException If the id is null or the entity does
	 *                                        not exist
	 */
	@Override
	public void remove(final Long id) throws DaoException {
		LOGGER.info("removing entity with id [{}] ..", id);
		if (id == null) {
			LOGGER.error("trying to remove entity with a null id, removal aborted");
			// TODO fail with NPE instead ?
			throw new EntityDoesNotExistDaoException("trying to remove an entity with a null id");
		}
		final E entity = findOne(id);
		if (entity == null) {
			LOGGER.error("entity [{}] does not exist, removal aborted", id);
			throw new EntityDoesNotExistDaoException("entity with id [" + id + "] does not exist");
		}
		entityManager.remove(entity);
	}

	@Override
	public void flush() throws DaoException {
		LOGGER.info("flushing entity manager ..");
		entityManager.flush();
	}

}
