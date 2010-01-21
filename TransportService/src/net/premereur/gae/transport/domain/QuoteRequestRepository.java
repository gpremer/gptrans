package net.premereur.gae.transport.domain;


public interface QuoteRequestRepository {

	void store(QuoteRequest qr);

	QuoteRequests findAll();

	QuoteRequest findByKey(Long quoteRequestId);
}
