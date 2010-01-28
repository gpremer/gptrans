package net.premereur.gae.transport.domain;

public interface CallbackService {

	void scheduleQuoteCallback(QuoteRequest quoteRequest, String callbackURL);

}
