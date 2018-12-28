package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.CourseDao;
import io.github.oliviercailloux.opendata.entity.Course;

@Path("course")
public class CourseResource extends AbstractResource<Course, CourseDao> {

	private static final String RESOURCE_NAME = "Course";
	private static final String RESOURCE_PATH = "course";

	public CourseResource(final CourseDao dao) {
		super(dao, RESOURCE_NAME, RESOURCE_PATH);
	}

	public CourseResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
