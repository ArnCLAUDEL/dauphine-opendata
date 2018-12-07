package io.github.oliviercailloux.opendata.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
import io.github.oliviercailloux.opendata.entity.Planning;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Servlet implementation class testPerson
 */
@WebServlet("/testPlanning")
public class testPlanning extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public testPlanning() {
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

		Person p = new Person();
		p.setFirstName("zakaria");
		p.setRole(FunctionType.ENS_VAC);
		Planning pa = new Planning(p);

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(p);
		entityManager.persist(pa);
		tx.commit();

		// get a the planning and the Person from the BDD
		Planning pa2 = entityManager.find(pa.getClass(), pa.getIdPlanning());
		out.println(Objects.requireNonNull(pa2.toString()));
		Person p2 = entityManager.find(p.getClass(), pa2.getPerson().getId());
		out.println(p2.getFirstName());

		// Update Person: change the name of the Person Zakaria to Zohir
		p.setFirstName("Zohir");
		tx.begin();
		pa.setPerson(p);
		entityManager.merge(pa);
		tx.commit();
		Planning pa3 = entityManager.find(pa.getClass(), pa.getIdPlanning());
		out.println(pa3.getPerson().getFirstName());
		// delete a the planing and the person: ( the fist object)
		tx.begin();
		Planning PersonDeleted = entityManager.merge(pa);
		entityManager.remove(PersonDeleted);
		entityManager.remove(PersonDeleted.getPerson());
		tx.commit();

		// get All the Planning created on the BDD
		List<Planning> Objects = entityManager.createQuery("select c from Planning c").getResultList();
		entityManager.close();
		out.println(Objects.get(0).getPerson().getFirstName());
		out.println("End All Operation Planning.");
	}

}
