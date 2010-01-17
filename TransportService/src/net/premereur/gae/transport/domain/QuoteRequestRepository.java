package net.premereur.gae.transport.domain;

import java.util.Collection;

public interface QuoteRequestRepository {

	void store(QuoteRequest qr);

	Collection<QuoteRequest> findAll();

	QuoteRequest findByKey(Long quoteRequestId);
}
