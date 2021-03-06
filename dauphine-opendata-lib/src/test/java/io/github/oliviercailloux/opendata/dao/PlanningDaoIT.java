package io.github.oliviercailloux.opendata.dao;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.TestUtils;
import io.github.oliviercailloux.opendata.entity.Planning;

@RunWith(Arquillian.class)
public class PlanningDaoIT extends AbstractDaoIT<Planning, PlanningDao> {

	@Inject
	private PlanningDao dao;

	@Deployment
	public static WebArchive makeWar() {
		return TestUtils.makeWar();
	}

	@Override
	protected void setUpDao() {
		setDao(dao);
	}

	@Override
	protected Planning changeEntity(final Planning originalEntity) {
		return originalEntity;
	}

	@Override
	protected Planning doMakeEntity() {
		return new Planning();
	}
}
