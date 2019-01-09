package io.github.oliviercailloux.opendata.resource;

import javax.ws.rs.Path;

import io.github.oliviercailloux.opendata.entity.Triple;
import io.github.oliviercailloux.opendata.service.GenericRestServices.TripleRestService;

/**
 * Represents the resource class for the entity {@link Triple}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Path("triple")
public class TripleResource extends AbstractResource<Triple, TripleRestService> {

	public static final String RESOURCE_NAME = "Triple";
	public static final String RESOURCE_PATH = "triple";

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public TripleResource() {
		super(RESOURCE_NAME, RESOURCE_PATH);
	}

}
