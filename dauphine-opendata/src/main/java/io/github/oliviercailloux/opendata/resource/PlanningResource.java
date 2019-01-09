package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.dao.PlanningDao;
import io.github.oliviercailloux.opendata.entity.Planning;

/**
 * Represents the resource class for the entity {@link Planning}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("planning")
public class PlanningResource extends AbstractResource<Planning, PlanningDao> {

	private static final String RESOURCE_NAME = "Planning";
	private static final String RESOURCE_PATH = "planning";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public PlanningResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
