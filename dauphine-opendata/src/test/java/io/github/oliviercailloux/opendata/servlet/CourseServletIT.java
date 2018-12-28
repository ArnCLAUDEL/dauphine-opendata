package io.github.oliviercailloux.opendata.servlet;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Ignore;
import org.junit.Test;

//@RunWith(Arquillian.class)
@Ignore
public class CourseServletIT extends AbstractServletTest {

	public static void main(final String[] args) {
		System.out.println(ClientBuilder.newClient().getClass().getName());
	}

	public CourseServletIT() {
		super(ClientBuilder.newClient(), "/course");
	}

	@Deployment
	public static WebArchive getWar() {
		final File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();
		final WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_NAME + ".war")
				.addPackages(true, "io.github.oliviercailloux.opendata.servlet").addAsResource("arquillian.xml")
				.addAsLibraries(files);
		return war;
	}

	@Test
	public void testPost() throws Exception {
		final Form form = new Form();
		final Response response = getRequestBuilder().post(Entity.form(form));
		assertEquals("response KO", HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void testGet0Entity() throws Exception {
		final String result = getRequestBuilder().get(String.class);
		assertEquals("response KO", "[]", result);
	}

}
