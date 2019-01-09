package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;

import io.github.oliviercailloux.opendata.entity.Triple;

public class TripleDao extends AbstractDao<Triple> {

	/**
	 * This constructor expects both managed entity manager.
	 *
	 * @param entityManager A managed entity manager
	 */
	public TripleDao(final EntityManager entityManager) {
		super(entityManager, Triple.class, "Triple");
	}

}
