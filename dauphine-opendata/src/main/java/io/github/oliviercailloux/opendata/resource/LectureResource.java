package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.entity.Lecture;
import io.github.oliviercailloux.opendata.service.GenericRestServices.LectureRestService;

/**
 * Represents the resource class for the entity {@link Lecture}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("lecture")
public class LectureResource extends AbstractResource<Lecture, LectureRestService> {

	public static final String RESOURCE_NAME = "Lecture";
	public static final String RESOURCE_PATH = "lecture";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public LectureResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
