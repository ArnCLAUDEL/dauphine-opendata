package io.github.oliviercailloux.opendata.dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import com.google.common.base.Preconditions;

@ApplicationScoped
public class DaoFactory {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	@PostConstruct
	private void assertFieldInjected() {
		Preconditions.checkNotNull(entityManager);
		Preconditions.checkNotNull(userTransaction);
	}

	@Produces
	public CourseDao makeCourseDao() {
		return new CourseDao(entityManager, userTransaction);
	}

	@Produces
	public LectureDao makeLectureDao() {
		return new LectureDao(entityManager, userTransaction);
	}

	@Produces
	public PersonDao makePersonDao() {
		return new PersonDao(entityManager, userTransaction);
	}

	@Produces
	public PlanningDao makePlanningDao() {
		return new PlanningDao(entityManager, userTransaction);
	}

	@Produces
	public TripleDao makeTripleDao() {
		return new TripleDao(entityManager, userTransaction);
	}

}
