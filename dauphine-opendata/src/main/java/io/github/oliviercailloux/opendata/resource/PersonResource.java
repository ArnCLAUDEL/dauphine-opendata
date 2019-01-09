package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PersonDao;
import io.github.oliviercailloux.opendata.entity.Person;

/**
 * Represents the resource class for the entity {@link Person}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("person")
public class PersonResource extends AbstractResource<Person, PersonDao> {

	private static final String RESOURCE_NAME = "Person";
	private static final String RESOURCE_PATH = "person";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public PersonResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
