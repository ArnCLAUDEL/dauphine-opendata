package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.entity.Planning;
import io.github.oliviercailloux.opendata.service.GenericRestServices.PlanningRestService;

/**
 * Represents the resource class for the entity {@link Planning}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("planning")
public class PlanningResource extends AbstractResource<Planning, PlanningRestService> {

	public static final String RESOURCE_NAME = "Planning";
	public static final String RESOURCE_PATH = "planning";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public PlanningResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
