package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Triple;

public class TripleDao extends AbstractDao<Triple> {

	/**
	 * This constructor expects both managed entity manager and user transaction.
	 *
	 * @param entityManager   A managed entity manager
	 * @param userTransaction A managed user transaction
	 */
	public TripleDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Triple.class, "Triple");
	}

}
