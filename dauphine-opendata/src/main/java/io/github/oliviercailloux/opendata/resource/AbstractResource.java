package io.github.oliviercailloux.opendata.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.dao.DaoException;
import io.github.oliviercailloux.opendata.dao.EntityAlreadyExistsDaoException;
import io.github.oliviercailloux.opendata.dao.EntityDoesNotExistDaoException;
import io.github.oliviercailloux.opendata.entity.Entity;
import io.github.oliviercailloux.opendata.util.ExceptionalSupplier;

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
public class AbstractResource<E extends Entity, D extends Dao<E>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

	@Inject
	protected D dao;

	/**
	 * The response to send for the current request.
	 */
	@Context
	protected HttpServletResponse response;

	/**
	 * The name of the resource, mostly used for logging.
	 */
	protected final String resourceName;

	/**
	 * The path of the resource, used to build a URL.
	 */
	protected final String resourcePath;

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 *
	 * @param resourceName The name of the resource, mostly used for logging
	 * @param resourcePath The path of the resource, used to build a URL
	 */
	public AbstractResource(final String resourceName, final String resourcePath) {
		this.resourceName = Preconditions.checkNotNull(resourceName, "resourceName");
		this.resourcePath = Preconditions.checkNotNull(resourcePath, "resourcePath");
		// dao will be set by injection
	}

	/**
	 * This setter should not be used and is only for field injection.
	 *
	 * @param dao The dao to use
	 */
	public void setDao(final D dao) {
		this.dao = Preconditions.checkNotNull(dao, "dao");
	}

	/**
	 * Checks whether the field injection worked.
	 *
	 * @throws NullPointerException If a field is null
	 */
	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(dao, "dao");
		Preconditions.checkNotNull(response, "response");
	}

	/**
	 * Makes a 201 - Created response with the location of the resource.<br />
	 * The location is the following : /<tt>resourcePath</tt>/<tt>id</tt>.
	 *
	 * @param id The id of the created resource
	 * @return The created response
	 */
	protected Response makeCreatedResponse(final Long id) {
		return Response.created(URI.create("/" + resourcePath + "/" + id)).build();
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
	 * Tries to execute the given task and catches any thrown {@link DaoException}.
	 *
	 * @param task The task to execute
	 * @return {@code Optional.empty()} if the exception was thrown
	 */
	protected <R> Optional<R> tryDaoTask(final ExceptionalSupplier<R, DaoException> task) {
		try {
			return Optional.of(task.get());
		} catch (final DaoException e) {
			LOGGER.error("an error occured while retrieving the data", e);
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
	public List<E> get() throws DaoException {
		LOGGER.info("[{}] - finding all entities ..", resourceName);
		return dao.findAll();
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
	public Response get(@PathParam("id") final String id) throws DaoException {
		LOGGER.info("[{}] - finding entity with id [{}] ..", resourceName, id);

		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {

			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();

		final E entity = dao.findOne(parsedId);
		if (entity != null) {
			return Response.ok(entity).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
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
	public Response post(final E entity) throws DaoException {
		LOGGER.info("[{}] - creating entity [{}] ..", resourceName, entity);
		try {
			final E persistedEntity = dao.persist(entity);
			return makeCreatedResponse(persistedEntity.getId());
		} catch (final EntityAlreadyExistsDaoException e) {
			LOGGER.info("[{}] - entity [{}] already exist ..", resourceName, entity, e);
			return Response.status(Status.CONFLICT).entity("entity already exists").build();
		}
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
	public Response put(@PathParam("id") final String id, final E entity) throws DaoException {
		LOGGER.info("[{}] - merging entity with id [{}] ..", resourceName, id);
		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();

		if (entity.getId() != null && entity.getId() != parsedId) {
			LOGGER.warn("[{}] - the provided id is [{}] is different than the url one [{}]", resourceName,
					entity.getId(), parsedId);
			return Response.status(Status.FORBIDDEN).build();
		}

		if (dao.findOne(parsedId) == null) {
			LOGGER.info("[{}] - entity does not exist, creating it ..", resourceName);
			final E persistedEntity = dao.persist(entity);
			return makeCreatedResponse(persistedEntity.getId());
		} else {
			dao.merge(entity);
			return Response.noContent().build();
		}
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
	public Response delete(@PathParam("id") final String id) throws DaoException {
		LOGGER.info("[{}] - removing entity with id [{}] ..", resourceName, id);
		final Optional<Long> parsedIdOpt = tryParseId(id);
		if (!parsedIdOpt.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final Long parsedId = parsedIdOpt.get();
		try {
			dao.remove(parsedId);
			return Response.noContent().build();
		} catch (final EntityDoesNotExistDaoException e) {
			LOGGER.info("[{}] - removal failed, entity [{}] does not exist", resourceName, id, e);
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
