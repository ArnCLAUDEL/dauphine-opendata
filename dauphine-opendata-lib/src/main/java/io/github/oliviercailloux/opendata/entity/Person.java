package io.github.oliviercailloux.opendata.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.google.common.base.Strings;

/**
 * Created by Ziad & sofian on 03/12/2017.
 */
@Entity
public class Person {

	/**
	 * id of a person ( length = 8 ) (not null) we need to add pattern @pattern when
	 * we have more information about the ID
	 */
	@Id
	// type === table for unique primary key (use in triple)
	@GeneratedValue(strategy = GenerationType.TABLE)
	String id;

	/**
	 * Returns this person's first name, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = Strings.nullToEmpty(firstName);
	}

	/**
	 * Returns this person's last name, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets this person's last name.
	 *
	 * @param lastName if <code>null</code>, will be converted to an empty string.
	 */
	public void setLastName(final String lastName) {
		this.lastName = Strings.nullToEmpty(lastName);
	}

	@NotNull
	@Column(nullable = false)
	private String firstName = "";

	@NotNull
	@Column(nullable = false)
	private String lastName = "";

	/**
	 * Function type in directory
	 */
	@Enumerated(EnumType.STRING)
	private FunctionType role;

	/**
	 * number
	 */
	@Column(nullable = true)
	String number = "";

	public FunctionType getRole() {
		return role;
	}

	public void setRole(final FunctionType role) {
		this.role = role;
	}

	/**
	 * fax number
	 */
	@Column(nullable = true)
	String fax = "";

	/**
	 * name of the office
	 */
	@Column(nullable = false)
	String office = "";

	/**
	 * email of a person a person can have multiple email addresses exemple :
	 *
	 * Courriel tristan.cazenave@lamsade.dauphine.fr Courriel
	 * tristan.cazenave@dauphine.fr
	 *
	 *
	 */
	@ElementCollection
	@Column(nullable = false)
	List<String> emails = new ArrayList<>();

	/**
	 * groups of which the user is a part
	 */
	@ElementCollection
	@Column(nullable = true)
	List<String> structures = new ArrayList<>();

	public Person() {
	}

	/**
	 * Returns this person's id.
	 *
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets this lecture's id.
	 *
	 * @param group if <code>null</code>, will be converted to an empty string.
	 */
	public void setId(final String id) {
		this.id = Strings.nullToEmpty(id);
	}

	/**
	 * Returns this person's number, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets this person's number.
	 *
	 * @param number if <code>null</code>, will be converted to an empty string.
	 */
	public void setNumber(final String number) {
		this.number = Strings.nullToEmpty(number);
	}

	/**
	 * Returns this person's fax, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets this person's fax.
	 *
	 * @param fax if <code>null</code>, will be converted to an empty string.
	 */
	public void setFax(final String fax) {
		this.fax = Strings.nullToEmpty(fax);
	}

	/**
	 * Returns this person's office, or an empty string if unknown.
	 *
	 * @return not <code>null</code>.
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * Sets this person's office.
	 *
	 * @param office if <code>null</code>, will be converted to an empty string.
	 */
	public void setOffice(final String office) {
		this.office = Strings.nullToEmpty(office);
	}

	/**
	 * Returns the list of emails of this person (which may be empty). Writing to
	 * the list modifies the emails of this person.
	 *
	 * @return not <code>null</code>.
	 */
	public List<String> getEmails() {
		return emails;
	}

	/**
	 * Sets the list of emails of this person
	 *
	 * @param emails not <code>null</code>.
	 */
	public void setEmails(final List<String> emails) {
		this.emails = Objects.requireNonNull(emails);
	}

	/**
	 * Add an email to the list of emails of this person
	 *
	 * @param email not <code>null</code>.
	 */
	public void addEmail(final String email) {
		this.emails.add(Objects.requireNonNull(email));
	}

	/**
	 * Returns the list of structures of this person (which may be empty). Writing
	 * to the list modifies the structures of this person.
	 *
	 * @return not <code>null</code>.
	 */
	public List<String> getStructures() {
		return structures;
	}

	/**
	 * Sets the list of structures of this person
	 *
	 * @param structures not <code>null</code>.
	 */
	public void setStructures(final List<String> structures) {
		this.structures = Objects.requireNonNull(structures);
	}

	/**
	 * Add a structure to the list of structures of this person
	 *
	 * @param structure not <code>null</code>.
	 */
	public void addStructure(final String structure) {
		this.structures.add(Objects.requireNonNull(structure));
	}
}
