package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.entity.Planning;

@Path("planning")
public class PlanningResource extends AbstractResource<Planning> {

	private static final String PLANNING_NAME = "Planning";

	public PlanningResource(final Dao<Planning> dao) {
		super(dao, PLANNING_NAME);
	}

	public PlanningResource() {
		super(PLANNING_NAME);
	}

}
