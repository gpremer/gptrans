package net.premereur.gae.transport.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Demand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Quote quote;

	public Demand(String quoteReference) throws BusinessException {
		this.quote = ServiceLocator.get().getQuoteRequestRepository().getQuoteForReference(quoteReference);
		if (quote.getValidity().before(getClock().currentTime())) {
			throw new BusinessException(BusinessException.Reason.QUOTE_EXPIRED, "The quote was only valid until " + quote.getValidity() + " but is now "
					+ new Date());
		}
	}

	private ClockService getClock() {
		return ServiceLocator.get().getClockService();
	}

	public Long getId() {
		return id;
	}

	public Quote getQuote() {
		return this.quote;
	}
}
