package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Triple;

public class TripleDao extends AbstractDao<Triple> {

	public TripleDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Triple.class, "Triple");
	}

}
