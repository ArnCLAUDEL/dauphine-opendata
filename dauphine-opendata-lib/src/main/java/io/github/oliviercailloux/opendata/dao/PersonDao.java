package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Person;

public class PersonDao extends AbstractDao<Person> {

	/**
	 * This constructor expects both managed entity manager and user transaction.
	 *
	 * @param entityManager   A managed entity manager
	 * @param userTransaction A managed user transaction
	 */
	public PersonDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Person.class, "Person");
	}

}
