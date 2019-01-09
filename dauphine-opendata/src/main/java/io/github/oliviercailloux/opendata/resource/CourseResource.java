package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.entity.Course;
import io.github.oliviercailloux.opendata.service.GenericRestServices.CourseRestService;

/**
 * Represents the resource class for the entity {@link Course}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("course")
public class CourseResource extends AbstractResource<Course, CourseRestService> {

	public static final String RESOURCE_NAME = "Course";
	public static final String RESOURCE_PATH = "course";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public CourseResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
