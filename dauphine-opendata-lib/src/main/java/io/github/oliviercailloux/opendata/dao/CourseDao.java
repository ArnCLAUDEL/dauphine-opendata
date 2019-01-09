package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;

import io.github.oliviercailloux.opendata.entity.Course;

public class CourseDao extends AbstractDao<Course> {

	/**
	 * This constructor expects both managed entity manager.
	 *
	 * @param entityManager A managed entity manager
	 */
	public CourseDao(final EntityManager entityManager) {
		super(entityManager, Course.class, "Course");
	}

}
