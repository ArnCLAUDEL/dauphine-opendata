package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
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
 * Transaction execution is thread safe.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <E> The entity
 */
public abstract class AbstractDao<E extends Entity> implements Dao<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);

	protected final EntityManager entityManager;
	protected final UserTransaction userTransaction;

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
	 * @param entityManager   A managed entity manager
	 * @param userTransaction A managed user transaction
	 * @param entityClass     The class of the entity, used with the entity manager
	 * @param entityName      The name of entity, should match with the table name
	 *                        since it is used to build SQL query
	 */
	public AbstractDao(final EntityManager entityManager, final UserTransaction userTransaction,
			final Class<E> entityClass, final String entityName) {
		this.entityManager = Preconditions.checkNotNull(entityManager, "entityManager");
		this.userTransaction = Preconditions.checkNotNull(userTransaction, "userTransaction");
		this.entityClass = Preconditions.checkNotNull(entityClass, "entityClass");
		this.entityName = Preconditions.checkNotNull(entityName, "entityName");
	}

	/**
	 * Tries to rollback the given transaction.<br />
	 * This does not fail if there is no active transaction.
	 *
	 * @param transaction The active transaction to rollback
	 * @throws DaoException If an exception is thrown by
	 *                      {@link UserTransaction#rollback()}
	 */
	protected final void tryRollback(final UserTransaction transaction) throws DaoException {
		try {
			LOGGER.info("rollbacking transaction with status [{}]..", transaction.getStatus());
			if (transaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
				LOGGER.warn("no active transaction, rollback aborted");
				return;
			}
			transaction.rollback();
			LOGGER.info("transaction rollbacked");
		} catch (IllegalStateException | SecurityException | SystemException e) {
			LOGGER.error("an error occured during the rollback", e);
			throw new DaoException("an error occured during the rollback", e);
		}
	}

	/**
	 * Executes the given task into a transaction, commits it if no error occurs
	 * else rollbacks it.<br />
	 *
	 * @param task The task to execute
	 * @return The result of the successful task
	 * @throws DaoException If any exception is thrown during the transaction
	 */
	protected final synchronized <R> R executeWithTransaction(final ExceptionalSupplier<R, DaoException> task)
			throws DaoException {
		try {
			LOGGER.info("beginning transaction ..");
			userTransaction.begin();
			LOGGER.info("executing the request ..");
			final R result = task.get();
			LOGGER.info("request executed");
			userTransaction.commit();
			LOGGER.info("transaction completed");
			return result;
		} catch (final DaoException e) {
			LOGGER.error("an error occured during the request [{}], starting the rollback ..", e.getMessage());
			LOGGER.debug("{}", e);
			tryRollback(userTransaction);
			throw e;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			LOGGER.error("an error occured during the transaction, starting the rollback ..", e);
			tryRollback(userTransaction);
			throw new DaoException("an error occured during the transaction", e);
		}
	}

	/**
	 * Delegates to {@link #executeWithTransaction(ExceptionalSupplier)}.
	 *
	 * @param task The task to execute
	 * @throws DaoException If thrown by
	 *                      {@link #executeWithTransaction(ExceptionalSupplier)}
	 */
	protected final void executeWithTransaction(final ExceptionalRunnable<DaoException> task) throws DaoException {
		executeWithTransaction(() -> {
			task.run();
			return null;
		});
	}

	protected List<E> doFindAll() {
		LOGGER.info("finding all entities ..");
		return entityManager.createQuery("SELECT t FROM " + entityName + " t", entityClass).getResultList();
	}

	@Override
	public List<E> findAll() throws DaoException {
		return executeWithTransaction(this::doFindAll);
	}

	/**
	 * Returns the entity with the given id.<br />
	 * Note that it returns null if the given id is null.
	 *
	 * @param id The id of the entity
	 * @return null if the entity does not exist or the given id is null
	 */
	protected E doFindOne(final Long id) {
		LOGGER.info("finding entity with id [{}] ..", id);
		if (id == null) {
			LOGGER.warn("received id null, returning null value by default");
			// TODO fail with NPE instead ?
			return null;
		}
		return entityManager.find(entityClass, id);
	}

	@Override
	public E findOne(final Long id) throws DaoException {
		return executeWithTransaction(() -> doFindOne(id));
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
	protected E doPersist(final E entity) throws EntityAlreadyExistsDaoException {
		LOGGER.info("creating entity [{}] ..", entity);
		try {
			entityManager.persist(entity);
			return doFindOne(entity.getId());
		} catch (final PersistenceException e) {
			throw new EntityAlreadyExistsDaoException(e);
		}
	}

	@Override
	public E persist(final E entity) throws DaoException {
		return executeWithTransaction(() -> doPersist(entity));
	}

	protected E doMerge(final E entity) {
		LOGGER.info("merging entity with id [{}] ..", entity.getId());
		entityManager.merge(entity);
		return doFindOne(entity.getId());
	}

	@Override
	public E merge(final E entity) throws DaoException {
		return executeWithTransaction(() -> doMerge(entity));
	}

	/**
	 * Remove the entity with the given id.
	 *
	 * @param id The id of the entity to remove
	 * @throws EntityDoesNotExistDaoException If the id is null or the entity does
	 *                                        not exist
	 */
	protected void doRemove(final Long id) throws EntityDoesNotExistDaoException {
		LOGGER.info("removing entity with id [{}] ..", id);
		if (id == null) {
			LOGGER.error("trying to remove entity with a null id, removal aborted");
			// TODO fail with NPE instead ?
			throw new EntityDoesNotExistDaoException("trying to remove an entity with a null id");
		}
		final E entity = doFindOne(id);
		if (entity == null) {
			LOGGER.error("entity [{}] does not exist, removal aborted", id);
			throw new EntityDoesNotExistDaoException("entity with id [" + id + "] does not exist");
		}
		entityManager.remove(entity);
	}

	@Override
	public void remove(final Long id) throws DaoException {
		executeWithTransaction(() -> doRemove(id));
	}

	@Override
	public void flush() throws DaoException {
		LOGGER.info("flushing entity manager ..");
		executeWithTransaction(entityManager::flush);
	}

}
