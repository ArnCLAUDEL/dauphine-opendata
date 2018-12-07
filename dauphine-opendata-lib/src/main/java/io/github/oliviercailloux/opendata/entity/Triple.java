package io.github.oliviercailloux.opendata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Strings;

/**
 * Created by Ziad & sofian on 07/02/2017. triple is a class to create data
 * attached to a subject (id) and put a predicate and an object
 */
@Entity
public class Triple {

	/**
	 * id of a triple
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	String id;

	/**
	 * subject is a string containing an id of an object (person or course) not null
	 */
	@Column(nullable = false)
	String subject;

	/**
	 * the predicate attached to the subject
	 */
	@Column(nullable = false)
	String predicate = "";

	/**
	 * object is a string containing the data
	 */
	@Column(nullable = false)
	String object = "";

	public Triple() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	/**
	 * Subject can not be null or empty
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) throws IllegalArgumentException {
		if (Strings.isNullOrEmpty(subject)) {
			throw new IllegalArgumentException("subject can not be null or empty");
		}
		this.subject = Strings.nullToEmpty(subject);
	}

	/**
	 * if <code>null</code>, will be converted to an empty string.
	 * 
	 * @return
	 */
	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = Strings.nullToEmpty(predicate);
	}

	public String getObject() {
		return object;
	}

	/**
	 * if <code>null</code>, will be converted to an empty string.
	 * 
	 * @param object
	 */
	public void setObject(String object) {
		this.object = Strings.nullToEmpty(object);
	}
}
