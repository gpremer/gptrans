package net.premereur.gae.transport.service.v1.resource;

import net.premereur.gae.transport.domain.QuoteRequestRepository;

public abstract class QuoteRequestResourceBase extends ResourceBase {

	protected final QuoteRequestRepository repository;

	public QuoteRequestResourceBase(QuoteRequestRepository repository) {
		super();
		this.repository = repository;
	}

	protected QuoteRequestRepository getRepository() {
		return repository;
	}

}
