package io.github.oliviercailloux.opendata.resource;

import java.util.List;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.dao.DaoException;
import io.github.oliviercailloux.opendata.entity.Entity;
import io.github.oliviercailloux.opendata.util.ExceptionalSupplier;

@RequestScoped
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AbstractResource<E extends Entity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

	@Inject
	protected Dao<E> dao;

	protected final String resourceName;

	public AbstractResource(final Dao<E> dao, final String resourceName) {
		this.dao = Preconditions.checkNotNull(dao);
		this.resourceName = Preconditions.checkNotNull(resourceName);
	}

	public AbstractResource(final String resourceName) {
		this.resourceName = resourceName;
		// DAO will be set by injection
	}

	public void setDao(final Dao<E> dao) {
		this.dao = Preconditions.checkNotNull(dao);
	}

	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(dao);
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
	public E get(@PathParam("id") final String id) throws DaoException {
		LOGGER.info("[{}] - finding entity with id [{}] ..", resourceName, id);
		return dao.findOne(Long.parseLong(id));
	}

	@POST
	public void post(final E entity) throws DaoException {
		LOGGER.info("[{}] - creating entity [{}] ..", resourceName, entity);
		dao.persist(entity);
	}

	@PUT
	public void put(final E entity) throws DaoException {
		LOGGER.info("[{}] - merging entity [{}] ..", resourceName, entity);
		dao.mergeOrPersist(entity);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") final String id) throws DaoException {
		LOGGER.info("[{}] - removing entity with id [{}] ..", resourceName, id);
		dao.remove(Long.parseLong(id));
	}

}
