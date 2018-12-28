package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PlanningDao;
import io.github.oliviercailloux.opendata.entity.Planning;

@Path("planning")
public class PlanningResource extends AbstractResource<Planning, PlanningDao> {

	private static final String PLANNING_NAME = "Planning";

	public PlanningResource(final PlanningDao dao) {
		super(dao, PLANNING_NAME);
	}

	public PlanningResource() {
		super(PLANNING_NAME);
	}

}
