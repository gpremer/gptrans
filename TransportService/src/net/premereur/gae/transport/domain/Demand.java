package net.premereur.gae.transport.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Entity
public class Demand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String quoteReference; // Copying information from Quote instead of
									// linking to it because it is owned by a
									// different repository

	public Demand(String quoteReference) throws BusinessException {
		Quote quote = getQuoteRequestRepository().getQuoteForReference(quoteReference);
		this.quoteReference = quoteReference;
		ensureQuoteIsStillValid(quote);
		ensureThereIsNoOtherQuoteForThisDemand(quoteReference);
	}

	private void ensureQuoteIsStillValid(Quote quote) throws BusinessException {
		if (quote.getValidity().before(getClock().currentTime())) {
			throw new BusinessException(BusinessException.Reason.QUOTE_EXPIRED, "The quote was only valid until " + quote.getValidity() + " but is now "
					+ new Date());
		}
	}

	private void ensureThereIsNoOtherQuoteForThisDemand(String quoteReference) throws BusinessException {
		// NOTE: there certainly is a race condition, but as long as the demand is billed it doesn't matter too much
		try {
			Demand demand = getDemandRepository().getDemandForQuoteReference(quoteReference);
			throw new BusinessException(BusinessException.Reason.QUOTE_ALREADY_TAKEN, "The quote was already fulfilled by demand " + demand);
		} catch (BusinessException e) {
			// This is what we want
			if (e.getReason() != BusinessException.Reason.NO_SUCH_DEMAND) {
				throw e;
			}
		}
	}

	private QuoteRequestRepository getQuoteRequestRepository() {
		return ServiceLocator.get().getQuoteRequestRepository();
	}

	private DemandRepository getDemandRepository() {
		return ServiceLocator.get().getDemandRepository();
	}

	private ClockService getClock() {
		return ServiceLocator.get().getClockService();
	}

	public Long getId() {
		return id;
	}

	public String getQuoteReference() {
		return this.quoteReference;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
