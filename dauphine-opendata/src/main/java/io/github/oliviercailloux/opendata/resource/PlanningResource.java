package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PlanningDao;
import io.github.oliviercailloux.opendata.entity.Planning;

@Path("planning")
public class PlanningResource extends AbstractResource<Planning, PlanningDao> {

	private static final String RESOURCE_NAME = "Planning";
	private static final String RESOURCE_PATH = "planning";

	public PlanningResource(final PlanningDao dao) {
		super(dao, RESOURCE_NAME, RESOURCE_PATH);
	}

	public PlanningResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
