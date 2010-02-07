package net.premereur.gae.transport.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Demand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Quote quote;
	
	public Demand(String quoteReference) throws BusinessException {
		this.quote = ServiceLocator.get().getQuoteRequestRepository().getQuoteForReference(quoteReference);
	}

	public Long getId() {
		return id;
	}
	
	public Quote getQuote() {
		return this.quote;
	}
}
