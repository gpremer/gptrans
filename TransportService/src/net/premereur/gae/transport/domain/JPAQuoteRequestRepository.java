package net.premereur.gae.transport.domain;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;

public class JPAQuoteRequestRepository implements QuoteRequestRepository {

	private final EntityManager entityManager;

	@Inject
	public JPAQuoteRequestRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QuoteRequests findAll() {
		return new QuoteRequests(entityManager.createQuery("SELECT FROM QuoteRequest").getResultList());
	}

	@Override
	@Transactional
	public void store(QuoteRequest qr) {
		entityManager.persist(qr);
	}

	@Override
	public QuoteRequest findByKey(Long key) {
		return entityManager.find(QuoteRequest.class, key);
	}

}
