package io.github.oliviercailloux.opendata.resource;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.dao.DaoException;
import io.github.oliviercailloux.opendata.entity.Entity;
import io.github.oliviercailloux.opendata.service.RestService;

/**
 * Provides a base implementation for a JAX-RS resource class with a RESTful
 * API.<br />
 * The following request are already implemented :
 * <ul>
 * <li>GET /resource</li>
 * <li>GET /resource/{id}</li>
 * <li>POST /resource</li>
 * <li>PUT /resource/{id}</li>
 * <li>DELETE /resource/{id}</li>
 * </ul>
 * By default, every methods produce and consume JSON and XML.<br />
 * Note that this resource class is request scoped.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 * @param <E> The entity
 * @param <D> The entity Dao
 */
@RequestScoped
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AbstractResource<E extends Entity, S extends RestService<E>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

	@Inject
	protected S service;

	/**
	 * The name of the resource, mostly used for logging.
	 */
	protected final String resourceName;

	/**
	 * The path of the resource, used to build a URL.
	 */
	protected final String resourcePath;

	/**
	 *
	 * @param dao          The dao to use
	 * @param resourceName The name of the resource, mostly used for logging
	 * @param resourcePath The path of the resource, used to build a URL
	 */
	public AbstractResource(final String resourceName, final String resourcePath) {
		this.resourceName = Preconditions.checkNotNull(resourceName, "resourceName");
		this.resourcePath = Preconditions.checkNotNull(resourcePath, "resourcePath");
		// service will be set via injection
	}

	/**
	 * This setter should not be used and is only for field injection.
	 *
	 * @param service The service to use
	 */
	public void setService(final S service) {
		this.service = Preconditions.checkNotNull(service, "service");
	}

	/**
	 * Checks whether the field injection worked.
	 *
	 * @throws NullPointerException If a field is null
	 */
	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(service, "service");
	}

	/**
	 * Tries to parse the id as a long and catches any thrown
	 * {@link NumberFormatException}.
	 *
	 * @param id The id to parse
	 * @return {@code Optional.empty()} if the exception was thrown
	 */
	protected Optional<Long> tryParseId(final String id) {
		try {
			return Optional.of(Long.parseLong(id));
		} catch (final NumberFormatException e) {
			LOGGER.info("[{}] - id [{}] cannot  be parsed..", resourceName, id, e);
			return Optional.empty();
		}
	}

	/**
	 * Returns all elements of the current resource.
	 *
	 * @return all elements of the current resource
	 * @throws DaoException If thrown by {@link Dao#findAll()}
	 */
	@GET
	public Response get() throws Exception {
		return service.get();
	}

	/**
	 * Returns the element of the current resource with the given id.<br />
	 * - BAD_REQUEST if the id cannot be parsed as a long.<br />
	 * - NOT_FOUND if the element does not exist.
	 *
	 * @param id The id of the element
	 * @return Either a BAD_REQUEST, NOT_FOUND or OK response
	 * @throws DaoException If thrown by {@link Dao#findOne(Long)}
	 */
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") final String id) throws Exception {
		LOGGER.info("[{}] - finding entity with id [{}] ..", resourceName, id);

		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();

		return service.get(parsedId);
	}

	/**
	 * Creates the given element of the current resource.<br />
	 * - CREATED if the creation was successful.<br />
	 * - CONFLICT if the element already exists.
	 *
	 * @param entity The entity to create
	 * @return Either a CREATED or CONFLICT response
	 * @throws DaoException If thrown by {@link Dao#persist(Entity)}
	 */
	@POST
	public Response post(final E entity) throws Exception {
		return service.post(entity);
	}

	/**
	 * Creates or updates the given element of the given id.<br />
	 * - BAD_REQUEST if the id cannot be parsed as a long.<br />
	 * - FORBIDDEN if the given element has a different id than the given one.<br />
	 * - CREATED if the creation was successful. Note that a different id may be
	 * used for the creation.<br />
	 * - NO_CONTENT if the merge was successful.<br />
	 *
	 * @param id     The id of the resource to update
	 * @param entity The element to create or merge
	 * @return Either a BAD_REQUEST, FORBIDDEN, CREATED or NO_CONTENT response
	 * @throws DaoException If thrown by wither {@link Dao#findOne(Long)} or
	 *                      {@link Dao#merge(Entity)}
	 */
	@PUT
	@Path("{id}")
	public Response put(@PathParam("id") final String id, final E entity) throws Exception {
		LOGGER.info("[{}] - merging entity with id [{}] ..", resourceName, id);
		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();

		if (entity.getId() == null || entity.getId() != null && entity.getId() != parsedId) {
			LOGGER.warn("[{}] - the provided id is [{}] is different than the url one [{}]", resourceName,
					entity.getId(), parsedId);
			return Response.status(Status.FORBIDDEN).build();
		}

		return service.put(parsedId, entity);
	}

	/**
	 * Deletes the element with the given id.<br />
	 * - BAD_REQUEST if the id cannot be parsed as a long.<br />
	 * - NOT_FOUND if the element does not exist.<br />
	 * - NO_CONTENT if the deletion was successful.<br />
	 *
	 * @param id The id of the element to remove
	 * @return Either a BAD_REQUEST, NOT_FOUND or NO_CONTENT response.
	 * @throws DaoException If thrown by {@link Dao#remove(Long)}
	 */
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") final String id) throws Exception {
		LOGGER.info("[{}] - removing entity with id [{}] ..", resourceName, id);
		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();
		return service.delete(parsedId);
	}

}
