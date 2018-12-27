package io.github.oliviercailloux.opendata.dao;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import io.github.oliviercailloux.opendata.entity.Lecture;

public class LectureDao extends AbstractDao<Lecture> {

	public LectureDao(final EntityManager entityManager, final UserTransaction userTransaction) {
		super(entityManager, userTransaction, Lecture.class, "Lecture");
	}

}
