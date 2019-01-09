package io.github.oliviercailloux.opendata.service;

import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.dao.CourseDao;
import io.github.oliviercailloux.opendata.dao.LectureDao;
import io.github.oliviercailloux.opendata.dao.PersonDao;
import io.github.oliviercailloux.opendata.dao.PlanningDao;
import io.github.oliviercailloux.opendata.dao.TripleDao;
import io.github.oliviercailloux.opendata.entity.Course;
import io.github.oliviercailloux.opendata.entity.Lecture;
import io.github.oliviercailloux.opendata.entity.Person;
import io.github.oliviercailloux.opendata.entity.Planning;
import io.github.oliviercailloux.opendata.entity.Triple;
import io.github.oliviercailloux.opendata.resource.CourseResource;
import io.github.oliviercailloux.opendata.resource.LectureResource;
import io.github.oliviercailloux.opendata.resource.PersonResource;
import io.github.oliviercailloux.opendata.resource.PlanningResource;
import io.github.oliviercailloux.opendata.resource.TripleResource;

public final class GenericRestServices {

	private GenericRestServices() {
		// static class
	}

	public static class CourseRestService extends GenericRestService<Course, CourseDao> {
		public CourseRestService(final CourseDao dao, final UserTransaction userTransaction) {
			super(dao, userTransaction, CourseResource.RESOURCE_NAME, CourseResource.RESOURCE_PATH);
		}
	}

	public static class PersonRestService extends GenericRestService<Person, PersonDao> {
		public PersonRestService(final PersonDao dao, final UserTransaction userTransaction) {
			super(dao, userTransaction, PersonResource.RESOURCE_NAME, PersonResource.RESOURCE_PATH);
		}
	}

	public static class TripleRestService extends GenericRestService<Triple, TripleDao> {
		public TripleRestService(final TripleDao dao, final UserTransaction userTransaction) {
			super(dao, userTransaction, TripleResource.RESOURCE_NAME, TripleResource.RESOURCE_PATH);
		}
	}

	public static class PlanningRestService extends GenericRestService<Planning, PlanningDao> {
		public PlanningRestService(final PlanningDao dao, final UserTransaction userTransaction) {
			super(dao, userTransaction, PlanningResource.RESOURCE_NAME, PlanningResource.RESOURCE_PATH);
		}
	}

	public static class LectureRestService extends GenericRestService<Lecture, LectureDao> {
		public LectureRestService(final LectureDao dao, final UserTransaction userTransaction) {
			super(dao, userTransaction, LectureResource.RESOURCE_NAME, LectureResource.RESOURCE_PATH);
		}
	}

}
