package io.github.oliviercailloux.opendata.dao;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.TestUtils;
import io.github.oliviercailloux.opendata.entity.Person;

@RunWith(Arquillian.class)
public class PersonDaoIT extends AbstractDaoIT<Person, PersonDao> {

	@Inject
	private PersonDao dao;

	@Deployment
	public static WebArchive makeWar() {
		return TestUtils.makeWar();
	}

	@Override
	protected void setUpDao() {
		setDao(dao);
	}

	@Override
	protected Person changeEntity(final Person originalEntity) {
		return originalEntity;
	}

	@Override
	protected Person doMakeEntity() {
		return new Person();
	}

}
