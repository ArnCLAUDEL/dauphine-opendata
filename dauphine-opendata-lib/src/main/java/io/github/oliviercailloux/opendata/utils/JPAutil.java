package io.github.oliviercailloux.opendata.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAutil {

	private EntityManagerFactory factory;

	private EntityManager entityManager;

	public EntityManager getEntityManager(String persistUnit) {

		if (entityManager == null) {
			factory = Persistence.createEntityManagerFactory(persistUnit);

			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}
}
