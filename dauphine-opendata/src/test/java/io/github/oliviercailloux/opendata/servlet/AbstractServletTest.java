package io.github.oliviercailloux.opendata.servlet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;

import org.junit.After;
import org.junit.Ignore;

import com.google.common.base.Preconditions;

@Ignore
public abstract class AbstractServletTest {

	protected static final String WAR_NAME = "servlet-it";
	protected static final String BASE_URL = "http://localhost:8888/" + WAR_NAME;

	protected final Client client;
	protected final String path;

	public AbstractServletTest(final Client client, final String path) {
		Preconditions.checkNotNull(client);
		Preconditions.checkNotNull(path);
		this.client = client;
		this.path = path;
	}

	public AbstractServletTest(final String path) {
		this(ClientBuilder.newClient(), path);
	}

	protected Builder getRequestBuilder(final String path) {
		return client.target(BASE_URL + path).request();
	}

	protected Builder getRequestBuilder() {
		return getRequestBuilder(path);
	}

	@After
	public void closeClient() {
		client.close();
	}

}
