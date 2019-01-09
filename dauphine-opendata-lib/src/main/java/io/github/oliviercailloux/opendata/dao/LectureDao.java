package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;

import io.github.oliviercailloux.opendata.entity.Lecture;

public class LectureDao extends AbstractDao<Lecture> {

	/**
	 * This constructor expects both managed entity manager.
	 *
	 * @param entityManager A managed entity manager
	 */
	public LectureDao(final EntityManager entityManager) {
		super(entityManager, Lecture.class, "Lecture");
	}

}
