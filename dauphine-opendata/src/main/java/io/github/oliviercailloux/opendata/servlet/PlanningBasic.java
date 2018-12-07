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

import biweekly.ICalendar;
import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.mapper.ICalendarMapper;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Created by Ziad && Sofian on 08/02/2018.
 */
@WebServlet("/planning-id")
public class PlanningBasic extends HttpServlet {

	/**
	 * Object for DB access
	 */
	@Inject
	private Dao dao;

	/**
	 * mapper beetwen Planning class and Icalendar
	 */
	@Inject
	private ICalendarMapper iCalendarMapper;

	private static final Logger LOGGER = Logger.getLogger(PlanningBasic.class.getName());

	/**
	 * return planning from specific iD (JSON format) parameter id : id of a
	 * planning
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("resource")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final ServletOutputStream out = new ServletHelper().configureAndGetOutputStream(response);
		// Add content type (mime type of icalendar)
		response.setContentType("text/calendar");
		response.setCharacterEncoding("UTF-8");
		// check parameter
		String idPlanning = request.getParameter("id");
		// check if exist else throw Illegal Argument exception
		if (idPlanning == null) {
			throw new IllegalArgumentException("We need id of a planning ! ");
		}

		LOGGER.info("we return planning with id  : " + idPlanning);

		// get planning
		ICalendar iCalendar = dao.getPlanning(idPlanning);
		if (iCalendar == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.println("Impossible to find planning with id : " + idPlanning);
			return;
		}

		LOGGER.info(" successful find person with id : " + idPlanning);
		out.println(String.valueOf(iCalendar));
	}

}