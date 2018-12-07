package io.github.oliviercailloux.opendata.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import biweekly.ICalendar;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import io.github.oliviercailloux.opendata.entity.Course;
import io.github.oliviercailloux.opendata.entity.CoursePart;
import io.github.oliviercailloux.opendata.entity.CourseType;
import io.github.oliviercailloux.opendata.entity.Lecture;
import io.github.oliviercailloux.opendata.entity.Person;
import io.github.oliviercailloux.opendata.entity.Planning;
import io.github.oliviercailloux.opendata.mapper.ICalendarMapper;

/**
 * Created by Ziad on 07/11/2017.
 */

@WebServlet("/appServlet")
public class AppServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
		resp.setContentType(MediaType.TEXT_PLAIN);
		final ServletOutputStream out = resp.getOutputStream();

		// TRY TEACHING FORMAT ...
		Course course = new Course();
		course.setDescription("test");
		Lecture lecture = new Lecture(course, new Date());

		out.println("TEST : " + lecture.getDateWithFormat("dd MM YYYY"));
		out.println("TEST null : " + lecture.getDateWithFormat(null));

		String str = "BEGIN:VCARD\r\n" + "VERSION:4.0\r\n" + "N:Doe;Jonathan;;Mr;\r\n" + "FN:John Doe\r\n"
				+ "END:VCARD\r\n";

		VCard vcard = Ezvcard.parse(str).first();
		String fullName = vcard.getFormattedName().getValue();
		out.println(fullName);

		// Test ICalendarMapper
		Planning planning = new Planning(new Person());

		planning.addLecture(lecture);
		ICalendarMapper iCalendarMapper = new ICalendarMapper();

		ICalendar ical = iCalendarMapper.encodePlanningToICalendar(planning);

		out.println(ical.toString());

		Gson gson = new Gson();

		String courseAsJson = gson.toJson(new Course());
		String courseTypeAsJson = gson.toJson(new CoursePart(CourseType.CM, 1, null));
		String lectureAsJson = gson.toJson(new Lecture());
		String personAsJson = gson.toJson(new Person());

		HashSet<CoursePart> courseTypeSet = new HashSet<>();
		courseTypeSet.add(new CoursePart(CourseType.CM, 1, null));

		// String personAndCourseTypeAsJson = gson.toJson(new PersonAndCourseType(new
		// Person(), courseTypeSet));
		String planningAsJson = gson.toJson(new Planning(new Person()));

		out.println(courseAsJson);
		out.println(courseTypeAsJson);
		out.println(lectureAsJson);
		out.println(personAsJson);
		// out.println(personAndCourseTypeAsJson);
		out.println(planningAsJson);

		Course courseFromJson = gson.fromJson(courseAsJson, Course.class);
		CoursePart courseTypeFromJson = gson.fromJson(courseAsJson, CoursePart.class);
		Lecture lectureFromJson = gson.fromJson(courseAsJson, Lecture.class);
		Person personFromJson = gson.fromJson(personAsJson, Person.class);
		// PersonAndCourseType personAndCourseTypeFromJson =
		// gson.fromJson(personAndCourseTypeAsJson, PersonAndCourseType.class);
		Planning planningFromJson = gson.fromJson(planningAsJson, Planning.class);

		out.println(courseFromJson.toString());
		out.println(courseTypeFromJson.toString());
		out.println(lectureFromJson.toString());
		out.println(personFromJson.toString());
		// out.println(personAndCourseTypeFromJson.toString());
		out.println(planningFromJson.toString());

	}
}
