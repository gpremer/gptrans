package net.premereur.gae.transport.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;

public class JPAQuoteRequestRepository extends JPARepository implements QuoteRequestRepository {

	@Inject
	public JPAQuoteRequestRepository(final EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuoteRequest> findAll() {
		return getEntityManager().createQuery("SELECT FROM QuoteRequest").getResultList();
	}

	@Override
	@Transactional
	public void store(QuoteRequest qr) {
		getEntityManager().merge(qr);
	}

	@Override
	public QuoteRequest findByKey(Long key) {
		QuoteRequest quoteRequest = getEntityManager().find(QuoteRequest.class, key);
		return quoteRequest;
	}

	@Override
	public void removeAll() {
		getEntityManager().createQuery("DELETE FROM QuoteRequest").executeUpdate();
	}

	@Override
	public Quote getQuoteForReference(String quoteReference) throws BusinessException {
		try {
			return (Quote) getEntityManager().createQuery("SELECT FROM Quote WHERE id = :id").setParameter("id", quoteReference).getSingleResult();
		} catch (PersistenceException e) {
			throw new BusinessException(BusinessException.Reason.QUOTE_NOT_VALID, "Could not find quote for reference " + quoteReference);
		}
	}

}
