package io.github.oliviercailloux.opendata;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class TestUtils {

	public static String makeWarName(final String warName) {
		return warName + ".war";
	}

	public static WebArchive makeWar(final String warName) {
		final File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();

		final JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "io.github.oliviercailloux.opendata.resource")
				.addPackages(true, "io.github.oliviercailloux.opendata.service")
				.addPackages(true, "io.github.oliviercailloux.opendata.provider").addClass(DauphineOpenDataApp.class)
				.addAsResource("arquillian.xml");

		final WebArchive war = ShrinkWrap.create(WebArchive.class, makeWarName(warName)).addAsLibraries(libs)
				.addAsLibrary(jar);

		return war;
	}

}
