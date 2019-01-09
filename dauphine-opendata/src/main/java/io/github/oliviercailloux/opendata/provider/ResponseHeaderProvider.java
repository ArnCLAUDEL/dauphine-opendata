package io.github.oliviercailloux.opendata.provider;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Preconditions;

/**
 * Provider that sets the default attribute of any outgoing
 * {@link HttpServletResponse}.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@Provider
@RequestScoped
public class ResponseHeaderProvider implements ContainerResponseFilter {

	private static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	@Context
	private HttpServletResponse response;

	/**
	 * This constructor should not be used since this class requires field
	 * injection.<br />
	 */
	public ResponseHeaderProvider() {
		// empty to add a warning in the javadoc
	}

	/**
	 * Checks whether the field injection worked.
	 *
	 * @throws NullPointerException If a field is null
	 */
	@PostConstruct
	public void checkFieldInitialized() {
		Preconditions.checkNotNull(response, "response");
	}

	/**
	 * Sets the following attributes :<br />
	 * - Locale = en <br />
	 * - Encoding = UTF-8
	 *
	 * @param requestContext  The current request context
	 * @param responseContext The current response context
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
		response.setLocale(DEFAULT_LOCALE);
		response.setCharacterEncoding(DEFAULT_CHARSET);
	}

}
