package io.github.oliviercailloux.opendata.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * CoursePart is an object to represent type of course {( TD,30H, Teacher1),
 * (CM,15H,Teacher2), ...}.
 *
 * @author Zakaria BENZAIT
 * @author Ouafa BOUCENNA
 */
@Entity
public class CoursePart implements Serializable {

	/**
	 * The id CoursePart Not <code>null</code>.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idCoursePart;

	/**
	 * The type of the course (TD, CM ) Not <code>null</code>.
	 */
	@Column(nullable = false)
	private final CourseType type;

	/**
	 * The number of hourly volume Not <code>null</code>.
	 */
	@Column(nullable = false)
	private final int volume;

	/**
	 * The teacher who will teach this course. Not <code>null</code>.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private final Person teacher;

	/**
	 * Retrun the IDIdCoursePart
	 *
	 * @return idCoursePart Not <code>null</code>.
	 */
	public String getIdCoursePart() {
		return idCoursePart;
	}

	/**
	 * Constructor with field
	 *
	 * @param type    The type of the course, not <code>null</code>.
	 * @param volume  The number of the hours reserved to this course, not
	 *                <code>null</code>.
	 * @param teacher the person who will teach the course, not <code>null</code>.
	 */
	public CoursePart(final CourseType type, final int volume, final Person teacher) {
		this.type = type;
		this.volume = volume;
		this.teacher = teacher;
	}

	/**
	 *
	 */
	public CoursePart() {
		this.type = null;
		this.volume = 0;
		this.teacher = null;
	}

}
