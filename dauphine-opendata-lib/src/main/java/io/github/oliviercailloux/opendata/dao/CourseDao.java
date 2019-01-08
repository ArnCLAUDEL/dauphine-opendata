package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Course;

public class CourseDao extends AbstractDao<Course> {

	/**
	 * This constructor expects both managed entity manager and user transaction.
	 *
	 * @param entityManager   A managed entity manager
	 * @param userTransaction A managed user transaction
	 */
	public CourseDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Course.class, "Course");
	}

}
