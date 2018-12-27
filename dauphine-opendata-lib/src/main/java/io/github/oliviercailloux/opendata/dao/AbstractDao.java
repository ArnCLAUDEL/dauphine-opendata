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

	protected final <R> R executeWithTransaction(final ExceptionalSupplier<R, DaoException> task) throws DaoException {
		try {
			userTransaction.begin();
			final R result = task.get();
			userTransaction.commit();
			return result;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | PersistenceException | NotSupportedException e) {
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
		return entityManager.createQuery("SELECT t FROM " + entityName + " t", entityClass).getResultList();
	}

	@Override
	public List<E> findAll() throws DaoException {
		return executeWithTransaction(this::doFindAll);
	}

	protected E doFindOne(final long primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	@Override
	public E findOne(final long primaryKey) throws DaoException {
		return executeWithTransaction(() -> doFindOne(primaryKey));
	}

	protected E doPersist(final E entity) {
		entityManager.persist(entity);
		return doFindOne(entity.getId());
	}

	@Override
	public E persist(final E entity) throws DaoException {
		return executeWithTransaction(() -> doPersist(entity));
	}

	protected E doMerge(final E entity) {
		entityManager.merge(entity);
		return doFindOne(entity.getId());
	}

	@Override
	public E merge(final E entity) throws DaoException {
		return executeWithTransaction(() -> doMerge(entity));
	}

	protected E doMergeOrPersist(final E entity) {
		if (doFindOne(entity.getId()) == null) {
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

	protected void doRemove(final long primaryKey) {
		final E entity = doFindOne(primaryKey);
		entityManager.remove(entity);
	}

	@Override
	public void remove(final long primaryKey) throws DaoException {
		executeWithTransaction(() -> doRemove(primaryKey));
	}

	@Override
	public void flush() throws DaoException {
		executeWithTransaction(entityManager::flush);
	}

}
