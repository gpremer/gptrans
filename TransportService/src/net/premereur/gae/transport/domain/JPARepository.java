package net.premereur.gae.transport.domain;

import javax.persistence.EntityManager;

public abstract class JPARepository {

	protected final EntityManager entityManager;

	public JPARepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
