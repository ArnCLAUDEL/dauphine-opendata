package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.TripleDao;
import io.github.oliviercailloux.opendata.entity.Triple;

@Path("triple")
public class TripleResource extends AbstractResource<Triple, TripleDao> {

	private static final String RESOURCE_NAME = "Triple";
	private static final String RESOURCE_PATH = "triple";

	public TripleResource(final TripleDao dao) {
		super(dao, RESOURCE_NAME, RESOURCE_PATH);
	}

	public TripleResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
