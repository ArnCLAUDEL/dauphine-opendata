package io.github.oliviercailloux.opendata.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.google.common.base.Strings;

import io.github.oliviercailloux.opendata.utils.DateUtils;

/**
 * Created by Ziad & Sofian on 03/12/2017. class that represents a lecture with
 * a date, a room, and a group of participants
 */
@Entity
public class Lecture implements Serializable {

	/**
	 * exemple of object use in planning of Paris Dauphine
	 * "com.adesoft.gwt.core.client.rpc.data.planning.SquareEvent/3694954416"
	 * "java.lang.Integer/3438268394" "java.lang.String/2004016611" "Conception
	 * agile d'appli Web en java" "CAILLOUX OLIVIER" "A5STI86 Gr01" "U_B042"
	 * "38-UNIX" "M2 IF"
	 *
	 **/
	private static final long serialVersionUID = 1L;

	@Inject
	DateUtils dateUtils;

	/**
	 * The id Lecture Not <code>null</code>.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idLecture;

	public String getIdLecture() {
		return idLecture;
	}

	public void setIdLecture(final String idLecture) {
		this.idLecture = idLecture;
	}

	/**
	 * Course (basic information about course (author, type ...)
	 */
	@JoinColumn(nullable = false)
	@OneToOne
	private Course course = new Course();

	/**
	 * Date of course , we have a getter with date format
	 */
	@Column(nullable = false)
	private Date date = new Date();

	/**
	 * ROOM must be defined in a separate class with multiple information U_B042"
	 * 38-UNIX
	 */
	@Column(nullable = false)
	private String room = "";

	/**
	 * Group of participants must be defined in a separate class with name and ID
	 * A5STI86 Gr01 M2 IF
	 */
	@Column(nullable = false)
	private String groupName = "";

	/**
	 *
	 *
	 * the teacher responsible for the lecture.
	 */
	@OneToOne
	@JoinColumn(nullable = false)
	private Person teacher = new Person();

	public Lecture() {
	}

	public Lecture(final Course course, final Date date) {
		this.course = Objects.requireNonNull(course);
		this.date = Objects.requireNonNull(date);
	}

	public Lecture(final Course course, final Date date, final String room, final String group, final Person teacher) {
		this.course = Objects.requireNonNull(course);
		this.date = Objects.requireNonNull(date);
		this.room = Strings.nullToEmpty(room);
		this.groupName = Strings.nullToEmpty(group);
		this.teacher = Objects.requireNonNull(teacher);
	}

	/**
	 * Méthode who return date attribute with format defined in param
	 *
	 * @param format is optional and if null return default format JJ/MM/YYYY HH:MM
	 * @return
	 */
	public String getDateWithFormat(final String format) {
		return DateUtils.transformDate(this.date, Optional.ofNullable(format));
	}

	/**
	 * Returns this lecture's course.
	 *
	 * @return not <code>null</code>.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Sets this lecture's course.
	 *
	 * @param course can't be <code>null</code>
	 */
	public void setCourse(final Course course) {
		this.course = Objects.requireNonNull(course);
	}

	/**
	 * Returns this lecture's date.
	 *
	 * @return not <code>null</code>.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets this lecture's date.
	 *
	 * @param date can't be <code>null</code>
	 */
	public void setDate(final Date date) {
		this.date = Objects.requireNonNull(date);
	}

	/**
	 * Returns this lecture's room, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * Sets this lecture's room.
	 *
	 * @param room if <code>null</code>, will be converted to an empty string.
	 */
	public void setRoom(final String room) {
		this.room = Strings.nullToEmpty(room);
	}

	/**
	 * Returns this lecture's group, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getGroup() {
		return groupName;
	}

	/**
	 * Sets this lecture's group.
	 *
	 * @param group if <code>null</code>, will be converted to an empty string.
	 */
	public void setGroup(final String group) {
		this.groupName = Strings.nullToEmpty(group);
	}

	/**
	 * Returns this lecture's teacher.
	 *
	 * @return not <code>null</code>.
	 */
	public Person getTeacher() {
		return teacher;
	}

	/**
	 * Sets this lecture's teacher.
	 *
	 * @param teacher can't be <code>null</code>
	 */
	public void setTeacher(final Person teacher) {
		this.teacher = Objects.requireNonNull(teacher);
	}
}
