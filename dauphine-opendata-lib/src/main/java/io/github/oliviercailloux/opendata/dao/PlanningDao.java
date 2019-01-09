package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;

import io.github.oliviercailloux.opendata.entity.Planning;

public class PlanningDao extends AbstractDao<Planning> {

	/**
	 * This constructor expects both managed entity manager.
	 *
	 * @param entityManager A managed entity manager
	 */
	public PlanningDao(final EntityManager entityManager) {
		super(entityManager, Planning.class, "Planning");
	}

}
