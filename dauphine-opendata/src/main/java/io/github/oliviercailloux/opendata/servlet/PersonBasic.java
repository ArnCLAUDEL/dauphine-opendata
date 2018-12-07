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

import ezvcard.VCard;
import io.github.oliviercailloux.opendata.dao.Dao;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Created by Ziad && Sofian on 08/02/2018.
 */
@WebServlet("/person-id")
public class PersonBasic extends HttpServlet {

	/**
	 * Object for query Database
	 */
	@Inject
	private Dao dao;

	private static final Logger LOGGER = Logger.getLogger(PersonBasic.class.getName());

	/**
	 * return person from specific iD (JSON format) parameter id : id of a person*
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("resource")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final ServletOutputStream out = new ServletHelper().configureAndGetOutputStream(response);
		// Add content type (vcard format)
		response.setContentType("text/vcf");
		response.setCharacterEncoding("UTF-8");
		// check parameter
		String idPerson = request.getParameter("id");
		// check if exist else throw Illegal Argument exception
		if (idPerson == null) {
			throw new IllegalArgumentException("We need id of a Person ! ");
		}

		LOGGER.info("we return person with id  : " + idPerson);

		VCard vcard = dao.getPerson(idPerson);

		if (vcard == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			out.println("Impossible to find person with id : " + idPerson);
			return;
		}
		LOGGER.info(" successful find person with id : " + idPerson);
		out.println(String.valueOf(vcard));

	}

}