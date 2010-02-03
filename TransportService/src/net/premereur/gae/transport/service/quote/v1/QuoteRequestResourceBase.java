package net.premereur.gae.transport.service.quote.v1;

import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.service.common.ResourceBase;

public abstract class QuoteRequestResourceBase extends ResourceBase {

	private final QuoteRequestRepository repository;

	public QuoteRequestResourceBase(QuoteRequestRepository repository) {
		super();
		this.repository = repository;
	}

	protected QuoteRequestRepository getRepository() {
		return repository;
	}

}
