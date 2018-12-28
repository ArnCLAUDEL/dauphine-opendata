package io.github.oliviercailloux.opendata.resource;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Ignore;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.opendata.TestUtils;
import io.github.oliviercailloux.opendata.entity.Entity;

@Ignore
public abstract class AbstractResourceIT {

	protected static final String WAR_NAME = "resource-it-war";
	protected static final String BASE_URL = "http://localhost:8888/" + WAR_NAME + "/resource/";

	protected final Client client;

	public AbstractResourceIT(final Client client) {
		this.client = Preconditions.checkNotNull(client);
	}

	public AbstractResourceIT() {
		this(ResteasyClientBuilder.newClient());
	}

	@After
	public void closeClient() {
		client.close();
	}

	public static WebArchive makeWar() {
		return TestUtils.makeWar(WAR_NAME);
	}

	protected WebTarget getWebTarget() {
		return client.target(BASE_URL);
	}

	protected WebTarget getWebTarget(final String path) {
		return getWebTarget().path(path);
	}

	protected Builder acceptCharset(final String charset, final Builder builder) {
		builder.header(HttpHeaders.ACCEPT_CHARSET, charset);
		return builder;
	}

	protected Builder acceptUTF8(final Builder builder) {
		return acceptCharset(StandardCharsets.UTF_8.name(), builder);
	}

	protected Builder acceptLanguage(final String language, final Builder builder) {
		builder.header(HttpHeaders.ACCEPT_LANGUAGE, language);
		return builder;
	}

	protected Builder acceptEnglish(final Builder builder) {
		return acceptLanguage(Locale.ENGLISH.getLanguage(), builder);
	}

	protected Builder accept(final String accept, final Builder builder) {
		builder.header(HttpHeaders.ACCEPT, accept);
		return builder;
	}

	protected Builder acceptJson(final Builder builder) {
		return accept(MediaType.APPLICATION_JSON, builder);
	}

	protected Builder acceptXml(final Builder builder) {
		return accept(MediaType.APPLICATION_XML, builder);
	}

	protected Builder acceptJsonUTF8English(final String path) {
		final Builder builder = getWebTarget(path).request();
		acceptEnglish(builder);
		acceptJson(builder);
		acceptUTF8(builder);
		return builder;
	}

	protected Builder acceptXmlUTF8English(final String path) {
		final Builder builder = getWebTarget(path).request();
		acceptEnglish(builder);
		acceptXml(builder);
		acceptUTF8(builder);
		return builder;
	}

	protected InputStream getInputStream(final Response response) {
		return (InputStream) response.getEntity();
	}

	protected Scanner getScanner(final Response response) throws IOException {
		return new Scanner(new BufferedInputStream(getInputStream(response)));
	}

	protected <E extends Entity> E getEntity(final Response response, final Class<E> entityType) {
		response.bufferEntity();
		return response.readEntity(entityType);
	}

	protected void assertStatusCodeIs(final int expectedStatusCode, final Response response) {
		assertEquals("status code KO", expectedStatusCode, response.getStatus());
	}

	protected void assertStatusIsOk(final Response response) {
		assertStatusCodeIs(HttpServletResponse.SC_OK, response);
	}

	protected void assertStatusIsNoContent(final Response response) {
		assertStatusCodeIs(HttpServletResponse.SC_NO_CONTENT, response);
	}

	protected void assertStatusIsNotFound(final Response response) {
		assertStatusCodeIs(HttpServletResponse.SC_NOT_FOUND, response);
	}

	protected void assertStatusIsBadRequest(final Response response) {
		assertStatusCodeIs(HttpServletResponse.SC_BAD_REQUEST, response);
	}

	protected void assertStatusIsCreated(final Response response) {
		assertStatusCodeIs(HttpServletResponse.SC_CREATED, response);
	}

	protected void assertContentTypeIs(final String contentType, final Response response) {
		assertEquals("Content-Type KO", contentType, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	protected void assertContentTypeIsJsonUTF8(final Response response) {
		assertContentTypeIs("application/json; charset=UTF-8", response);
	}

	protected void assertContentTypeIsXmlUTF8(final Response response) {
		assertContentTypeIs("application/xml; charset=UTF-8", response);
	}

	protected void assertBodyIs(final String expectedBody, final Response response) throws IOException {
		assertEquals("Body KO", expectedBody, getScanner(response).nextLine());
	}

	protected <E extends Entity> void assertEntityIs(final E expectedEntity, final Response response) {
		assertEquals("Entity KO", expectedEntity, getEntity(response, expectedEntity.getClass()));
	}

}
