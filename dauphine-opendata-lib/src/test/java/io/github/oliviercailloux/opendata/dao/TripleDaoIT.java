package io.github.oliviercailloux.opendata.dao;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.TestUtils;
import io.github.oliviercailloux.opendata.entity.Triple;

@RunWith(Arquillian.class)
public class TripleDaoIT extends AbstractDaoIT<Triple, TripleDao> {

	@Inject
	private TripleDao dao;

	@Deployment
	public static WebArchive makeWar() {
		return TestUtils.makeWar();
	}

	@Override
	@Before
	public void before() {
		setDao(dao);
		super.before();
	}

	@Override
	protected Triple changeEntity(final Triple originalEntity) {
		return originalEntity;
	}

	@Override
	protected Triple doMakeEntity() {
		return new Triple();
	}

}
