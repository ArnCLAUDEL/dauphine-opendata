package io.github.oliviercailloux.opendata.service;

import javax.ws.rs.core.Response;

import io.github.oliviercailloux.opendata.entity.Entity;

public interface RestService<E extends Entity> {

	Response get() throws Exception;

	Response get(Long id) throws Exception;

	Response post(E entity) throws Exception;

	Response put(Long id, E entity) throws Exception;

	Response delete(Long id) throws Exception;

}
