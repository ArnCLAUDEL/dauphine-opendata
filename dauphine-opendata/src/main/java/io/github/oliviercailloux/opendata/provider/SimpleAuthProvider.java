package io.github.oliviercailloux.opendata.provider;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class SimpleAuthProvider implements ContainerRequestFilter {

	private static final String ERROR_MESSAGE = "resources are read-only";

	private static final Response NOT_AUTHORIZED_RESPONSE = Response
			.status(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE).build();

	private boolean isAuthorized(final ContainerRequestContext requestContext) {
		return true || HttpMethod.GET.equals(requestContext.getMethod());
	}

	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		if (!isAuthorized(requestContext)) {
			requestContext.abortWith(NOT_AUTHORIZED_RESPONSE);
		}
	}

}
