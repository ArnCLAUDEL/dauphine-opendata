package io.github.oliviercailloux.opendata;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.opendata.provider.ResponseHeaderProvider;
import io.github.oliviercailloux.opendata.provider.SimpleAuthProvider;
import io.github.oliviercailloux.opendata.resource.CourseResource;
import io.github.oliviercailloux.opendata.resource.LectureResource;
import io.github.oliviercailloux.opendata.resource.PersonResource;
import io.github.oliviercailloux.opendata.resource.PlanningResource;
import io.github.oliviercailloux.opendata.resource.TripleResource;

/**
 * Defines the base path, resource classes and providers for JAX-RS.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
@ApplicationPath("resource")
public class DauphineOpenDataApp extends Application {

	final ImmutableSet<Class<?>> classes = ImmutableSet.of(CourseResource.class, LectureResource.class,
			PersonResource.class, PlanningResource.class, TripleResource.class, ResponseHeaderProvider.class,
			SimpleAuthProvider.class);

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

}
