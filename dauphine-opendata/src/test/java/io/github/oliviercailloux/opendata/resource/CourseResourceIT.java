package io.github.oliviercailloux.opendata.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.dao.CourseDao;
import io.github.oliviercailloux.opendata.entity.Course;

@RunWith(Arquillian.class)
public class CourseResourceIT extends AbstractResourceIT<Course, CourseDao> {

	@Inject
	private CourseDao dao;

	public CourseResourceIT() {
		super("course");
	}
	
	@Deployment
	public static WebArchive makeWar() {
		return AbstractResourceIT.makeWar();
	}

	@Before
	public void before() {
		setDao(dao);
	}

	@Override
	protected Course makeEntity() {
		return new Course();
	}

	@Override
	protected GenericType<List<Course>> getEntitiesType() {
		return new GenericType<List<Course>>() {
			// no implementation required
		};
	}

}
