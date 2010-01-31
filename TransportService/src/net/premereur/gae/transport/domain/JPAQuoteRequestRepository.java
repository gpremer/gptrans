package net.premereur.gae.transport.domain;

import java.util.List;

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
	public List<QuoteRequest> findAll() {
		return entityManager.createQuery("SELECT FROM QuoteRequest").getResultList();
	}

	@Override
	@Transactional
	public void store(QuoteRequest qr) {
		entityManager.persist(qr);
	}

	@Override
	public QuoteRequest findByKey(Long key) {
		QuoteRequest quoteRequest = entityManager.find(QuoteRequest.class, key);
		return quoteRequest;
	}
	
	@Override
	public void removeAll() {
		entityManager.createQuery("DELETE FROM QuoteRequest").executeUpdate();		
	}

}
