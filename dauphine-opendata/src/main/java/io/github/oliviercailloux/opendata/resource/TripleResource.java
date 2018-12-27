package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.entity.Triple;

@Path("triple")
public class TripleResource extends AbstractResource<Triple> {

	private static final String TRIPLE_NAME = "Triple";

	public TripleResource(final Dao<Triple> dao) {
		super(dao, TRIPLE_NAME);
	}

	public TripleResource() {
		super(TRIPLE_NAME);
	}

}
