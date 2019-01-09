package io.github.oliviercailloux.opendata.service;

import java.net.URI;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.dao.EntityAlreadyExistsDaoException;
import io.github.oliviercailloux.opendata.dao.EntityDoesNotExistDaoException;
import io.github.oliviercailloux.opendata.entity.Entity;

public class GenericRestService<E extends Entity, D extends Dao<E>> implements RestService<E> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericRestService.class);

	protected final D dao;

	protected final UserTransaction userTransaction;

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
	public GenericRestService(final D dao, final UserTransaction userTransaction, final String resourceName,
			final String resourcePath) {
		this.dao = Preconditions.checkNotNull(dao, "dao");
		this.userTransaction = Preconditions.checkNotNull(userTransaction, "userTransaction");
		this.resourceName = Preconditions.checkNotNull(resourceName, "resourceName");
		this.resourcePath = Preconditions.checkNotNull(resourcePath, "resourcePath");
	}

	protected final void begin() throws NotSupportedException, SystemException {
		userTransaction.begin();
	}

	protected final void commit() throws SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException, SystemException {
		userTransaction.commit();
	}

	protected final void rollback() throws IllegalStateException, SecurityException, SystemException {
		userTransaction.rollback();
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

	@Override
	public Response get() throws Exception {
		LOGGER.info("[{}] - finding all entities ..", resourceName);
		return Response.ok(dao.findAll()).build();
	}

	@Override
	public Response get(final Long id) throws Exception {
		begin();
		final E entity = dao.findOne(id);
		commit();
		if (entity != null) {
			return Response.ok(entity).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@Override
	public Response post(final E entity) throws Exception {
		LOGGER.info("[{}] - creating entity [{}] ..", resourceName, entity);
		try {
			begin();
			final E persistedEntity = dao.persist(entity);
			commit();
			return makeCreatedResponse(persistedEntity.getId());
		} catch (final EntityAlreadyExistsDaoException e) {
			LOGGER.info("[{}] - entity [{}] already exist ..", resourceName, entity, e);
			rollback();
			return Response.status(Status.CONFLICT).entity("entity already exists").build();
		}
	}

	@Override
	public Response put(final Long id, final E entity) throws Exception {
		if (dao.findOne(id) == null) {
			LOGGER.info("[{}] - entity does not exist", resourceName);
			return Response.status(Status.NOT_FOUND).build();
		} else {
			begin();
			dao.merge(entity);
			commit();
			return Response.noContent().build();
		}
	}

	@Override
	public Response delete(final Long id) throws Exception {
		try {
			begin();
			dao.remove(id);
			commit();
			return Response.noContent().build();
		} catch (final EntityDoesNotExistDaoException e) {
			LOGGER.info("[{}] - removal failed, entity [{}] does not exist", resourceName, id, e);
			rollback();
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
