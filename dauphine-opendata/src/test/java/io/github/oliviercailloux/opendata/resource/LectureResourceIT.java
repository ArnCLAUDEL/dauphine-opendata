package io.github.oliviercailloux.opendata.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.github.oliviercailloux.opendata.dao.LectureDao;
import io.github.oliviercailloux.opendata.entity.Lecture;

@RunWith(Arquillian.class)
public class LectureResourceIT extends AbstractResourceIT<Lecture, LectureDao> {

	@Inject
	private LectureDao dao;

	public LectureResourceIT() {
		super("lecture");
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
	protected Lecture makeEntity() {
		return new Lecture();
	}

	@Override
	protected GenericType<List<Lecture>> getEntitiesType() {
		return new GenericType<List<Lecture>>() {
			// no implementation required
		};
	}

}
