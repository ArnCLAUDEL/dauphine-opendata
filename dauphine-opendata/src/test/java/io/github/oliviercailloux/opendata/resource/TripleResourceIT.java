package io.github.oliviercailloux.opendata.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.dao.TripleDao;
import io.github.oliviercailloux.opendata.entity.Triple;

@RunWith(Arquillian.class)
public class TripleResourceIT extends AbstractResourceIT<Triple, TripleDao> {

	@Inject
	private TripleDao dao;

	public TripleResourceIT() {
		super("triple");
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
	protected Triple makeEntity() {
		return new Triple();
	}

	@Override
	protected GenericType<List<Triple>> getEntitiesType() {
		return new GenericType<List<Triple>>() {
			// no implementation required
		};
	}

}
