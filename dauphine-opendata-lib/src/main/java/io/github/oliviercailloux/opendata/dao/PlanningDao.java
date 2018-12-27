package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Planning;

public class PlanningDao extends AbstractDao<Planning> {

	public PlanningDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Planning.class, "Planning");
	}

}
