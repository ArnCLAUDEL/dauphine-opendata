package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PersonDao;
import io.github.oliviercailloux.opendata.entity.Person;

@Path("person")
public class PersonResource extends AbstractResource<Person, PersonDao> {

	private static final String RESOURCE_NAME = "Person";
	private static final String RESOURCE_PATH = "person";

	public PersonResource(final PersonDao dao) {
		super(dao, RESOURCE_NAME, RESOURCE_PATH);
	}

	public PersonResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
