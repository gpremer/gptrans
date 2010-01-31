package net.premereur.gae.transport.domain;

import java.util.List;

public interface QuoteRequestRepository {

	void store(QuoteRequest qr);

	List<QuoteRequest> findAll();

	QuoteRequest findByKey(Long quoteRequestId);

	void removeAll();
}
