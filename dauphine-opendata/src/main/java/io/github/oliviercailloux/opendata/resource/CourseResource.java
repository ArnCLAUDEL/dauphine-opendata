package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.CourseDao;
import io.github.oliviercailloux.opendata.entity.Course;

@Path("course")
public class CourseResource extends AbstractResource<Course, CourseDao> {

	private static final String COURSE_NAME = "Course";

	public CourseResource(final CourseDao dao) {
		super(dao, COURSE_NAME);
	}

	public CourseResource() {
		super(COURSE_NAME);
	}

}
