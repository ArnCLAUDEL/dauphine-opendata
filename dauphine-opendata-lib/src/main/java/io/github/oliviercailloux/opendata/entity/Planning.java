package io.github.oliviercailloux.opendata.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by Ziad & Sofian on 03/12/2017. object planning by user
 */
@Entity
public class Planning {

	/**
	 * The person whose planning it is (with all the lectures they are enrolled in)
	 */
	@Id
	// type === table for unique primary key (use in triple)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String idPlanning;

	@OneToOne
	@JoinColumn(nullable = false)
	private Person person;

	public Planning() {
	}

	/**
	 * depending on the future treatment, we can replace by arrayList or Linkedlist
	 * or stream in java 8 ? Not <code>null</code>.
	 */
	@OneToMany
	@JoinColumn(nullable = false)
	private List<Lecture> lectures;

	public Planning(final Person person) {
		this.person = Objects.requireNonNull(person);
		this.lectures = new ArrayList<>();
	}

	public Planning(final Person person, final List<Lecture> lectures) {
		this.person = Objects.requireNonNull(person);
		this.lectures = Objects.requireNonNull(lectures);
	}

	/**
	 * Returns this planning's person.
	 *
	 * @return not <code>null</code>.
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets this planning's person.
	 *
	 * @param person can't be <code>null</code>
	 */
	public void setPerson(final Person person) {
		this.person = Objects.requireNonNull(person);
	}

	/**
	 * Returns this planning's lectures.
	 *
	 * @return not <code>null</code>.
	 */
	public List<Lecture> getLectures() {
		return lectures;
	}

	/**
	 * Sets this planning's lectures.
	 *
	 * @param lectures can't be <code>null</code>
	 */
	public void setLectures(final ArrayList<Lecture> lectures) {
		this.lectures = Objects.requireNonNull(lectures);
	}

	/**
	 * Add this a lecture to this planning's lectures.
	 *
	 * @param lecture can't be <code>null</code>
	 */
	public void addLecture(final Lecture lecture) {
		this.lectures.add(Objects.requireNonNull(lecture));
	}

	public String getIdPlanning() {
		return idPlanning;
	}

	public void setIdPlanning(final String idPlanning) {
		this.idPlanning = idPlanning;
	}

	public void setLectures(final List<Lecture> lectures) {
		this.lectures = lectures;
	}
}
