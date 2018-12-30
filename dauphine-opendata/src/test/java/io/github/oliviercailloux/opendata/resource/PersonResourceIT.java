package io.github.oliviercailloux.opendata.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.dao.PersonDao;
import io.github.oliviercailloux.opendata.entity.Person;

@RunWith(Arquillian.class)
public class PersonResourceIT extends AbstractResourceIT<Person, PersonDao> {

	@Inject
	private PersonDao dao;

	public PersonResourceIT() {
		super("person");
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
	protected Person makeEntity() {
		return new Person();
	}

	@Override
	protected GenericType<List<Person>> getEntitiesType() {
		return new GenericType<List<Person>>() {
			// no implementation required
		};
	}

}
