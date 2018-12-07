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

import io.github.oliviercailloux.opendata.entity.Course;
import io.github.oliviercailloux.opendata.utils.ServletHelper;

/**
 * Servlet implementation class testCourse
 */
@WebServlet("/testCourse")
public class testCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public testCourse() {
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

		// add all the courses int the BDD
		EntityTransaction tx = entityManager.getTransaction();
		Course c = new Course("javaEE");
		Course c1 = new Course("Android");
		Course c2 = new Course("Agilite");
		tx.begin();
		entityManager.persist(c);
		entityManager.persist(c1);
		entityManager.persist(c2);
		tx.commit();
		entityManager.close();
		/*
		 * //get a course from the BDD Course c4 = entityManager.find(c.getClass(),"1")
		 * ; out.println(c4.getCourseName());
		 * 
		 * 
		 * // delete a course: delete the JavaEE Course ( the fist object) tx.begin();
		 * Course CourseDeleted =entityManager.merge(c);
		 * entityManager.remove(CourseDeleted); tx.commit();
		 * 
		 * //Update course: change the name of the course Android to Android2
		 * c1.setCourseName("Android2"); tx.begin(); entityManager.merge(c1);
		 * tx.commit();
		 * 
		 * // get All the Course List<Course> Objects
		 * =entityManager.createQuery("select c from Course c").getResultList();
		 * out.println(Objects.get(0).getCourseName());
		 */
		out.println("End All Operation.");
	}
}
