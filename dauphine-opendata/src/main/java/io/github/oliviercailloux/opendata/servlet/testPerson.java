package io.github.oliviercailloux.opendata.servlet;

import java.io.IOException;

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

import io.github.oliviercailloux.opendata.entity.FunctionType;
import io.github.oliviercailloux.opendata.entity.Person;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Servlet implementation class testPerson
 */
@WebServlet("/testPerson")
public class testPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public testPerson() {
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
		Person p = new Person();
		p.setFirstName("zakaria");
		p.setRole(FunctionType.ENS_VAC);
		Person p1 = new Person();
		p1.setFirstName("Mohamed");
		p1.setRole(FunctionType.ENS_VAC);
		Person p2 = new Person();
		p2.setFirstName("Ali");
		p2.setRole(FunctionType.ENS_VAC);
		tx.begin();
		entityManager.persist(p);
		entityManager.persist(p1);
		entityManager.persist(p2);
		tx.commit();
		entityManager.close();
		/*
		 * //get a Person from the BDD Person c4 = entityManager.find(p.getClass(),"1")
		 * ; out.println(c4.getFirstName());
		 * 
		 * 
		 * // delete a Person: delete the "zakaria" Person ( the fist object)
		 * tx.begin(); Person PersonDeleted =entityManager.merge(p);
		 * entityManager.remove(PersonDeleted); tx.commit();
		 * 
		 * //Update Person: change the name of the Person "Mohamed" to "Zohir"
		 * p1.setFirstName("Zohir"); tx.begin(); entityManager.merge(p1); tx.commit();
		 * 
		 * // get All the Person List<Person> Objects
		 * =entityManager.createQuery("select c from Person c").getResultList();
		 * out.println(Objects.get(0).getFirstName());
		 * 
		 */
		out.println("End All Operation Person.");
	}

}
