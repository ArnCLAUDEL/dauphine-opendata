package io.github.oliviercailloux.opendata.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.CourseDao;
import io.github.oliviercailloux.opendata.dao.DaoException;
import io.github.oliviercailloux.opendata.entity.Course;

@RunWith(Arquillian.class)
public class CourseResourceIT extends AbstractResourceIT {

	@Inject
	private CourseDao dao;

	@Deployment
	public static WebArchive makeWar() {
		return AbstractResourceIT.makeWar();
	}

	@Before
	public void before() {
		Preconditions.checkNotNull(dao);
	}

	@Test
	public void testGet() throws IOException, DaoException {
		final Response response = acceptJsonUTF8English("course").get();
		assertStatusIsOk(response);
		assertContentTypeIsJsonUTF8(response);
		final List<Course> courses = dao.findAll();
		response.bufferEntity();

		final GenericType<List<Course>> coursesType = new GenericType<List<Course>>() {
		};
		assertEquals(courses, response.readEntity(coursesType));
	}

	@Test
	public void testGetId() throws IOException, DaoException {
		final Course c = new Course();
		final Course persistedCourse = dao.persist(c);
		final Response response = acceptJsonUTF8English("course/" + persistedCourse.getId()).get();
		assertStatusIsOk(response);
		assertContentTypeIsJsonUTF8(response);
		assertEntityIs(persistedCourse, response);
	}

	@Test
	public void testGetIdBadRequest() throws IOException, DaoException {
		final Response response = acceptJsonUTF8English("course/abc").get();
		assertStatusIsBadRequest(response);
	}

	@Test
	public void testGetIdNotFound() throws IOException, DaoException {
		final Response response = acceptJsonUTF8English("course/123456789").get();
		assertStatusIsNotFound(response);
	}

	@Test
	public void testPostAlreadyExists() throws DaoException {
		final Course c = new Course();
		final Course persistedCourse = dao.persist(c);
		final Response response = acceptJsonUTF8English("course").post(Entity.json(persistedCourse));
		assertStatusCodeIs(Status.CONFLICT.getStatusCode(), response);
	}

	@Test
	public void testPutPersist() {
		final Course c = new Course();
		final Response response = acceptJsonUTF8English("course").put(Entity.json(c));
		assertStatusIsCreated(response);
	}

	@Test
	public void testPutMerge() throws DaoException {
		final Course c = new Course();
		final Course persistedEntity = dao.persist(c);
		final Response response = acceptJsonUTF8English("course").put(Entity.json(persistedEntity));
		assertStatusIsNoContent(response);
	}

	@Test
	public void testDelete() throws DaoException {
		final Course c = new Course();
		final Course persistedCourse = dao.persist(c);
		final Response response = acceptJsonUTF8English("course/" + persistedCourse.getId()).delete();
		assertStatusIsNoContent(response);
		assertNull("entity was not removed", dao.findOne(persistedCourse.getId()));
	}

}
