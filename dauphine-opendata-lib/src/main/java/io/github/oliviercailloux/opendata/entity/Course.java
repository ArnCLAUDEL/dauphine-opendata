package io.github.oliviercailloux.opendata.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.common.base.Strings;

@Entity
/**
 * Course object to represent a Dauphine course.
 *
 * @author Zakaria BENZAIT
 * @author Ouafa BOUCENNA
 * @version %I%, %G%
 * @since 1.0
 */
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Not <code>null</code>, The id Course
	 */
	@Id
	// type === table for unique primary key (use in triple)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String idCourse;

	/**
	 * Not <code>null</code>, the description course
	 */
	private String description = "";

	/**
	 * Not <code>null</code>, List of teachers and witch kind of course they will
	 * teach
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private List<CoursePart> coursePart = new ArrayList<>();

	/**
	 * Returns the list of course Part ( (TD, 20h, Teacher),()...),
	 *
	 * @return not <code>null</code>.
	 */
	public List<CoursePart> getCoursePart() {
		return coursePart;
	}

	/**
	 * Sets the list of course Part.
	 *
	 * @param coursePart Not <code>null</code>, will be converted to an empty
	 *                   string.
	 */
	public void setCoursePart(final List<CoursePart> coursePart) {
		this.coursePart = coursePart;
	}

	/**
	 * Not <code>null</code>, The name of the course
	 */
	@Column(nullable = false)
	private String courseName = "";

	/**
	 * May be <code>null</code>, the language of the course in witch it written
	 */
	@Column(nullable = true)
	private Locale locale;

	/**
	 * Not <code>null</code>, The course's credit
	 */
	@Column(nullable = false)
	private int credits;

	/**
	 * Not <code>null</code>, The course specialty
	 */
	@Column(nullable = false)
	private TypeSpecialty specialty;

	/**
	 * Course constructor without fields
	 */
	public Course() {

	}

	/**
	 * Course constructor with fields
	 *
	 * @param courseName Not <code>null</code>, the name of the course ( Big Data,
	 *                   ...)
	 */
	public Course(final String courseName) {
		this.courseName = Strings.nullToEmpty(courseName);
	}

	/**
	 * Returns this course's id, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getIdCourse() {
		return idCourse;
	}

	/**
	 * Sets this course's id.
	 *
	 * @param idCourse Not <code>null</code>,
	 */
	public void setIdCourse(final String idCourse) {
		this.idCourse = Strings.nullToEmpty(idCourse);
	}

	/**
	 * Returns this course's description, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets this course's description.
	 *
	 * @param description Not <code>null</code>.
	 */
	public void setDescription(final String description) {
		this.description = Strings.nullToEmpty(description);
	}

	/**
	 * Returns this course's name, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets this course's name.
	 *
	 * @param courseName Not <code>null</code>.
	 */
	public void setCourseName(final String courseName) {
		this.courseName = Strings.nullToEmpty(courseName);
	}

	/**
	 * Returns this course's locale.
	 *
	 * @return not <code>null</code>.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets this course's locale.
	 *
	 * @param locale may be <code>null</code>,
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	/**
	 * Returns this course's credits.
	 *
	 * @return not <code>null</code>.
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets this course's credits.
	 *
	 * @param credits not <code>null</code>.
	 */
	public void setCredits(final int credits) {
		this.credits = credits;
	}

	/**
	 * Returns this course's specialty.
	 *
	 * @return not <code>null</code>.
	 */
	public TypeSpecialty getSpecialty() {
		return specialty;
	}

	/**
	 * Sets this course's specialty.
	 *
	 * @param specialty Not <code>null</code>,
	 */
	public void setSpecialty(final TypeSpecialty specialty) {
		this.specialty = specialty;
	}

}
