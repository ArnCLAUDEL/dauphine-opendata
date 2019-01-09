package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;

import io.github.oliviercailloux.opendata.entity.Person;

public class PersonDao extends AbstractDao<Person> {

	/**
	 * This constructor expects both managed entity manager.
	 *
	 * @param entityManager A managed entity manager
	 */
	public PersonDao(final EntityManager entityManager) {
		super(entityManager, Person.class, "Person");
	}

}
