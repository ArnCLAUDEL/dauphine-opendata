package io.github.oliviercailloux.opendata.dao;

import java.util.List;

import io.github.oliviercailloux.opendata.entity.Entity;

public interface Dao<E extends Entity> {

	List<E> findAll() throws DaoException;

	E findOne(long primaryKey) throws DaoException;

	E mergeOrPersist(E entity) throws DaoException;

	E persist(E entity) throws DaoException;

	E merge(E entity) throws DaoException;

	void remove(long primaryKey) throws DaoException;

	default void remove(final E entity) throws DaoException {
		remove(entity.getId());
	}

	void flush() throws DaoException;
}