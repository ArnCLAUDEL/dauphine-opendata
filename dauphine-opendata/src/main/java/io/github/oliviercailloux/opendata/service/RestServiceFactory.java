package io.github.oliviercailloux.opendata.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.DaoFactory;
import io.github.oliviercailloux.opendata.service.GenericRestServices.CourseRestService;
import io.github.oliviercailloux.opendata.service.GenericRestServices.LectureRestService;
import io.github.oliviercailloux.opendata.service.GenericRestServices.PersonRestService;
import io.github.oliviercailloux.opendata.service.GenericRestServices.PlanningRestService;
import io.github.oliviercailloux.opendata.service.GenericRestServices.TripleRestService;

@RequestScoped
public class RestServiceFactory {

	@Inject
	private DaoFactory daoFactory;

	@Inject
	private UserTransaction userTransaction;

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public RestServiceFactory() {
		// empty to add a warning in the javadoc
	}

	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(daoFactory, "daoFactory");
		Preconditions.checkNotNull(userTransaction, "userTransaction");
	}

	@Produces
	public CourseRestService makeCourseRestService() {
		return new CourseRestService(daoFactory.makeCourseDao(), userTransaction);
	}

	@Produces
	public PersonRestService makePersonRestService() {
		return new PersonRestService(daoFactory.makePersonDao(), userTransaction);
	}

	@Produces
	public PlanningRestService makePlanningRestService() {
		return new PlanningRestService(daoFactory.makePlanningDao(), userTransaction);
	}

	@Produces
	public TripleRestService makeTripleRestService() {
		return new TripleRestService(daoFactory.makeTripleDao(), userTransaction);
	}

	@Produces
	public LectureRestService makeLectureRestService() {
		return new LectureRestService(daoFactory.makeLectureDao(), userTransaction);
	}

}
