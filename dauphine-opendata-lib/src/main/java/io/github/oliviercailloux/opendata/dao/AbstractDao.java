package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
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
	protected final Class<E> entityClass;
	protected final String entityName;

	public AbstractDao(final EntityManager entityManager, final UserTransaction userTransaction,
			final Class<E> entityClass, final String entityName) {
		this.entityManager = Preconditions.checkNotNull(entityManager);
		this.userTransaction = Preconditions.checkNotNull(userTransaction);
		this.entityClass = Preconditions.checkNotNull(entityClass);
		this.entityName = Preconditions.checkNotNull(entityName);
	}

	protected final void tryRollback(final UserTransaction transaction) throws DaoException {
		try {
			transaction.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			LOGGER.error("an error occured while doing a rollback", e);
			throw new DaoException("an error occured doing a rollback", e);
		}
	}

	protected final synchronized <R> R executeWithTransaction(final ExceptionalSupplier<R, DaoException> task)
			throws DaoException {
		try {
			LOGGER.debug("beginning transaction ..");
			userTransaction.begin();
			final R result = task.get();
			userTransaction.commit();
			LOGGER.debug("transaction completed ..");
			return result;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | PersistenceException | NotSupportedException e) {
			LOGGER.error("an error occured during the transaction, doing rollback ..", e);
			tryRollback(userTransaction);
			throw new DaoException("an error occured during the transaction", e);
		}
	}

	protected final void executeWithTransaction(final ExceptionalRunnable<DaoException> task) throws DaoException {
		executeWithTransaction(() -> {
			task.run();
			return null;
		});
	}

	protected List<E> doFindAll() {
		LOGGER.debug("finding all entities ..");
		return entityManager.createQuery("SELECT t FROM " + entityName + " t", entityClass).getResultList();
	}

	@Override
	public List<E> findAll() throws DaoException {
		return executeWithTransaction(this::doFindAll);
	}

	protected E doFindOne(final long id) {
		LOGGER.debug("finding entity with id [{}] ..", id);
		return entityManager.find(entityClass, id);
	}

	@Override
	public E findOne(final long id) throws DaoException {
		return executeWithTransaction(() -> doFindOne(id));
	}

	protected E doPersist(final E entity) {
		LOGGER.debug("creating entity with id [{}] ..", entity.getId());
		entityManager.persist(entity);
		return doFindOne(entity.getId());
	}

	@Override
	public E persist(final E entity) throws DaoException {
		return executeWithTransaction(() -> doPersist(entity));
	}

	protected E doMerge(final E entity) {
		LOGGER.debug("merging entity with id [{}] ..", entity.getId());
		entityManager.merge(entity);
		return doFindOne(entity.getId());
	}

	@Override
	public E merge(final E entity) throws DaoException {
		return executeWithTransaction(() -> doMerge(entity));
	}

	protected E doMergeOrPersist(final E entity) {
		if (doFindOne(entity.getId()) == null) {
			LOGGER.debug("entity with id [{}] does not exist for the merge", entity.getId());
			doPersist(entity);
		} else {
			doMerge(entity);
		}
		return doFindOne(entity.getId());
	}

	@Override
	public E mergeOrPersist(final E entity) throws DaoException {
		return executeWithTransaction(() -> doMergeOrPersist(entity));
	}

	protected void doRemove(final long id) {
		LOGGER.debug("removing entity with id [{}] ..", id);
		final E entity = doFindOne(id);
		entityManager.remove(entity);
	}

	@Override
	public void remove(final long id) throws DaoException {
		executeWithTransaction(() -> doRemove(id));
	}

	@Override
	public void flush() throws DaoException {
		LOGGER.debug("flushing entity manager ..");
		executeWithTransaction(entityManager::flush);
	}

}
