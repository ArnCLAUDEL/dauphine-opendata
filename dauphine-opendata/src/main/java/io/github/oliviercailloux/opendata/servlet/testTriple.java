package io.github.oliviercailloux.opendata.servlet;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.oliviercailloux.opendata.entity.Triple;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Servlet implementation class testTriple
 */
@WebServlet("/testTriple")
public class testTriple extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public testTriple() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("resource")
		final ServletOutputStream out = new ServletHelper().configureAndGetOutputStream(response);
		out.println("Start DAO opretation");
		out.flush();

		EntityManagerFactory factory;
		EntityManager entityManager;
		factory = Persistence.createEntityManagerFactory("Dauphine-Open-Data");
		entityManager = factory.createEntityManager();

		// add all the Persons int the BDD
		EntityTransaction tx = entityManager.getTransaction();
		Triple t = new Triple();
		t.setObject("This is the first triple");

		Triple t1 = new Triple();
		t1.setObject("This is the first triple");

		tx.begin();
		entityManager.persist(t);
		entityManager.persist(t1);
		tx.commit();

		// get a Person from the BDD
		Triple t4 = entityManager.find(t.getClass(), "1");
		out.println(t4.getObject());

		// delete a Triple:
		tx.begin();
		Triple TripleDeleted = entityManager.merge(t);
		entityManager.remove(TripleDeleted);
		tx.commit();

		// Update Triple: change the Ojbect of the Triple
		t1.setObject("This the modified triple");
		tx.begin();
		entityManager.merge(t1);
		tx.commit();

		// get All the Triples
		List<Triple> Objects = entityManager.createQuery("select c from Triple c").getResultList();
		entityManager.close();
		out.println(Objects.get(0).getObject());

		out.println("End All Operation Triple.");
	}

}
