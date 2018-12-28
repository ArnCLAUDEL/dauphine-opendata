package io.github.oliviercailloux.opendata.dao;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.TestUtils;
import io.github.oliviercailloux.opendata.entity.Course;

@RunWith(Arquillian.class)
public class CourseDaoIT extends AbstractDaoIT<Course, CourseDao> {

	@Inject
	private CourseDao dao;

	@Deployment
	public static WebArchive makeWar() {
		return TestUtils.makeWar();
	}

	@Override
	@Before
	public void before() {
		setDao(dao);
		super.before();
	}

	@Override
	protected Course changeEntity(final Course originalEntity) {
		return originalEntity;
	}

	@Override
	protected Course doMakeEntity() {
		return new Course();
	}

}
