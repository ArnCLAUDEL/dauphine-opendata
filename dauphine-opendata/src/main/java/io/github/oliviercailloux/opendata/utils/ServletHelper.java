package io.github.oliviercailloux.opendata.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import io.github.oliviercailloux.opendata.servlet.AppServlet;

@RequestScoped
public class ServletHelper {

	public ServletOutputStream configureAndGetOutputStream(HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
		resp.setContentType(MediaType.TEXT_PLAIN);
		resp.setLocale(Locale.ENGLISH);
		return resp.getOutputStream();
	}

	public String getRedirectURL(ServletContext context) {
		Collection<String> mappings = context.getServletRegistration(AppServlet.class.getCanonicalName()).getMappings();
		assert (mappings.size() == 1);
		final String urlMapping = mappings.iterator().next();
		assert (urlMapping.charAt(0) == '/');
		return urlMapping.substring(1);
	}

}
