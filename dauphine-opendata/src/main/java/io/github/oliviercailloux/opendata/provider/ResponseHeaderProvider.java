package io.github.oliviercailloux.opendata.provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
public class ResponseHeaderProvider implements ContainerResponseFilter {

	private static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	@Context
	private HttpServletResponse response;

	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
			throws IOException {
		response.setLocale(DEFAULT_LOCALE);
		response.setCharacterEncoding(DEFAULT_CHARSET);
	}

}
