package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PersonDao;
import io.github.oliviercailloux.opendata.entity.Person;

@Path("person")
public class PersonResource extends AbstractResource<Person, PersonDao> {

	private static final String PERSON_NAME = "Person";

	public PersonResource(final PersonDao dao) {
		super(dao, PERSON_NAME);
	}

	public PersonResource() {
		super(PERSON_NAME);
	}

}
