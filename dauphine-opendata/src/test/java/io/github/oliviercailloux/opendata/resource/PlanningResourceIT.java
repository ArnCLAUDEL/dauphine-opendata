package io.github.oliviercailloux.opendata.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.dao.PlanningDao;
import io.github.oliviercailloux.opendata.entity.Planning;

@RunWith(Arquillian.class)
public class PlanningResourceIT extends AbstractResourceIT<Planning, PlanningDao> {

	@Inject
	private PlanningDao dao;

	public PlanningResourceIT() {
		super("planning");
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
	protected Planning makeEntity() {
		return new Planning();
	}

	@Override
	protected GenericType<List<Planning>> getEntitiesType() {
		return new GenericType<List<Planning>>() {
			// no implementation required
		};
	}

}
