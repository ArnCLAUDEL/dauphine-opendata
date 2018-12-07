package io.github.oliviercailloux.opendata.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.entity.Course;
import io.github.oliviercailloux.opendata.mapper.JsonMapper;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Created by Ziad && Sofian on 08/02/2018.
 */
@WebServlet("/course-id")
public class CourseBasic extends HttpServlet {

	/**
	 * acces to database
	 */
	@Inject
	private Dao dao;

	/**
	 * mapper json to object and object to json
	 */
	@Inject
	private JsonMapper jsonMapper;

	private static final Logger LOGGER = Logger.getLogger(CourseBasic.class.getName());

	/**
	 * return course from specific iD (JSON format)
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final ServletOutputStream out = new ServletHelper().configureAndGetOutputStream(response);
		// Add content type (JSON format)
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// check parameter
		String idCourse = request.getParameter("id");

		// check if exist else throw Illegal Argument exception
		if (idCourse == null) {
			throw new IllegalArgumentException("We need id of a Course ! ");
		}

		LOGGER.info("we return course with id  : " + idCourse);

		// get All the Course
		Course course = dao.getCourse(idCourse);

		if (course != null) {
			LOGGER.info(" successful find course with id : " + idCourse);
			// convert to JSON
			String json = jsonMapper.convertObjectToJson(course);
			out.println(json);

		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.println("Impossible to find course with id : " + idCourse);
		}

	}

}