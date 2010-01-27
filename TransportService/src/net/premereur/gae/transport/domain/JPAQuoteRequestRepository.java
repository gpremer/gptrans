package net.premereur.gae.transport.domain;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.wideplay.warp.persist.Transactional;

public class JPAQuoteRequestRepository implements QuoteRequestRepository {

	private final EntityManager entityManager;

	private final Injector injector;
	
	@Inject
	public JPAQuoteRequestRepository(EntityManager entityManager, Injector injector) {
		this.entityManager = entityManager;
		this.injector = injector;
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
		QuoteRequest quoteRequest = entityManager.find(QuoteRequest.class, key);
		//injector.injectMembers(quoteRequest);
		return quoteRequest;
	}
	
	@Override
	public void removeAll() {
		entityManager.createQuery("DELETE FROM QuoteRequest").executeUpdate();		
	}

}
