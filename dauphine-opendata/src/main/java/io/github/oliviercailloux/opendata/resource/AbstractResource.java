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

@RequestScoped
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AbstractResource<E extends Entity, D extends Dao<E>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

	@Inject
	protected D dao;

	@Context
	protected HttpServletResponse response;

	protected final String resourceName;

	protected final String resourcePath;

	public AbstractResource(final String resourceName, final String resourcePath) {
		this.resourceName = Preconditions.checkNotNull(resourceName);
		this.resourcePath = Preconditions.checkNotNull(resourcePath);
		// DAO will be set by injection
	}

	public AbstractResource(final D dao, final String resourceName, final String resourcePath) {
		this(resourceName, resourcePath);
		this.dao = Preconditions.checkNotNull(dao);
	}

	public void setDao(final D dao) {
		this.dao = Preconditions.checkNotNull(dao);
	}

	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(dao);
	}

	protected Optional<Long> tryParseId(final String id) {
		try {
			return Optional.of(Long.parseLong(id));
		} catch (final NumberFormatException e) {
			LOGGER.debug("[{}] - id [{}] cannot  be parsed..", resourceName, id, e);
			return Optional.empty();
		}
	}

	protected <R> Optional<R> tryDaoTask(final ExceptionalSupplier<R, DaoException> task) {
		try {
			return Optional.of(task.get());
		} catch (final DaoException e) {
			LOGGER.error("an error occured while retrieving the data", e);
			return Optional.empty();
		}
	}

	@GET
	public List<E> get() throws DaoException {
		LOGGER.info("[{}] - finding all entities ..", resourceName);
		return dao.findAll();
	}

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

	@POST
	public Response post(final E entity) throws DaoException {
		LOGGER.info("[{}] - creating entity [{}] ..", resourceName, entity);
		try {
			final E persistedEntity = dao.persist(entity);
			return Response.created(URI.create("/" + resourcePath + "/" + persistedEntity.getId())).build();
		} catch (final EntityAlreadyExistsDaoException e) {
			LOGGER.debug("[{}] - entity [{}] already exist ..", resourceName, entity, e);
			return Response.status(Status.CONFLICT).entity("entity already exists").build();
		}
	}

	@PUT
	public Response put(final E entity) throws DaoException {
		LOGGER.info("[{}] - merging entity [{}] ..", resourceName, entity);
		if (dao.findOne(entity.getId()) == null) {
			LOGGER.debug("[{}] - entity does not exist, creating it ..", resourceName);
			final E persistedEntity = dao.persist(entity);
			return Response.created(URI.create("/" + resourcePath + "/" + persistedEntity.getId())).build();
		}

		dao.merge(entity);
		return Response.noContent().build();
	}

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
			LOGGER.debug("[{}] - removal failed, entity [{}] does not exist", resourceName, id, e);
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
