package io.github.oliviercailloux.opendata.dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import com.google.common.base.Preconditions;

/**
 * Factory of {@link Dao} implementations.<br />
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@RequestScoped
public class DaoFactory {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction userTransaction;

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public DaoFactory() {
		// empty to add a warning in the javadoc
	}

	@PostConstruct
	private void assertFieldInjected() {
		Preconditions.checkNotNull(entityManager, "entityManager");
		Preconditions.checkNotNull(userTransaction, "userTransaction");
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
