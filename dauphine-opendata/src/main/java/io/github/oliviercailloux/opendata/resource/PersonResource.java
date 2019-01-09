package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.entity.Person;
import io.github.oliviercailloux.opendata.service.GenericRestServices.PersonRestService;

/**
 * Represents the resource class for the entity {@link Person}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("person")
public class PersonResource extends AbstractResource<Person, PersonRestService> {

	public static final String RESOURCE_NAME = "Person";
	public static final String RESOURCE_PATH = "person";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public PersonResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
