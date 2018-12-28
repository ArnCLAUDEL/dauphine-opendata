package io.github.oliviercailloux.opendata.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.github.oliviercailloux.opendata.entity.Entity;

public abstract class AbstractDaoIT<E extends Entity, D extends Dao<E>> {

	protected D dao;

	private final List<E> entitiesToDelete = Lists.newLinkedList();

	protected void setDao(final D dao) {
		this.dao = Preconditions.checkNotNull(dao);
	}

	@Before
	public void before() {
		Preconditions.checkNotNull(dao);
	}

	@After
	public void after() throws DaoException {
		final Iterator<E> itr = entitiesToDelete.iterator();
		E e;
		while (itr.hasNext()) {
			e = itr.next();
			dao.remove(e);
			itr.remove();
		}
	}

	protected abstract E changeEntity(final E originalEntity);

	protected abstract E doMakeEntity();

	protected E makeEntity(final boolean persist, final boolean removeAfter) throws DaoException {
		final E e = doMakeEntity();
		if (persist) {
			dao.persist(e);
		}
		if (removeAfter) {
			entitiesToDelete.add(e);
		}
		return e;
	}

	protected E makeEntity(final boolean persist) throws DaoException {
		return makeEntity(persist, true);
	}

	protected E makeEntity() throws DaoException {
		return makeEntity(true, true);
	}

	protected List<E> makeEntities(final int nb, final boolean persist, final boolean removeAfter) throws DaoException {
		final List<E> entities = Lists.newLinkedList();
		for (int i = 0; i < nb; i++) {
			entities.add(makeEntity(persist, removeAfter));
		}
		return entities;
	}

	protected List<E> makeEntities(final int nb, final boolean persist) throws DaoException {
		return makeEntities(nb, persist, true);
	}

	protected List<E> makeEntities(final int nb) throws DaoException {
		return makeEntities(nb, true, true);
	}

	@Test
	public void testFindAll() throws DaoException {
		final List<E> persistedEntities = makeEntities(10);
		final List<E> foundEntities = dao.findAll();
		assertEquals("not the same size", persistedEntities.size(), foundEntities.size());
		assertTrue("an element was not found", foundEntities.containsAll(persistedEntities));
	}

	@Test
	public void testFindOne() throws DaoException {
		final E persistedEntity = makeEntity();
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course", persistedEntity, foundEntity);
	}

	@Test
	public void testPersist() throws DaoException {
		final E persistedEntity = makeEntity();
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course", persistedEntity, foundEntity);
	}

	@Test
	public void testMerge() throws DaoException {
		final E persistedEntity = makeEntity();
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course before changing name", persistedEntity, foundEntity);
		final E changedEntity = changeEntity(foundEntity);
		final E foundEntity2 = dao.merge(changedEntity);
		assertEquals("entity was not changed", changedEntity, foundEntity2);
	}

	@Test
	public void testMergeNoPersist() throws DaoException {
		final E persistedEntity = makeEntity();
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course before changing name", persistedEntity, foundEntity);
		final E changedEntity = changeEntity(foundEntity);
		final E foundEntity2 = dao.mergeOrPersist(changedEntity);
		assertEquals("entity was not changed", changedEntity, foundEntity2);
	}

	@Test
	public void testPersistNoMerge() throws DaoException {
		final E persistedEntity = dao.mergeOrPersist(makeEntity());
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course", persistedEntity, foundEntity);
	}

	@Test
	public void testRemoveEntity() throws DaoException {
		final E persistedEntity = makeEntity(true, false);
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course", persistedEntity, foundEntity);
		dao.remove(foundEntity);
		final E deletedEntity = dao.findOne(persistedEntity.getId());
		assertNull("course was not deleted", deletedEntity);
	}

	@Test
	public void testRemoveId() throws DaoException {
		final E persistedEntity = makeEntity(true, false);
		final E foundEntity = dao.findOne(persistedEntity.getId());
		assertEquals("not the same course", persistedEntity, foundEntity);
		dao.remove(foundEntity.getId());
		final E deletedEntity = dao.findOne(persistedEntity.getId());
		assertNull("course was not deleted", deletedEntity);
	}

	@Test
	public void testFlush() throws DaoException {
		dao.flush();
	}

}
