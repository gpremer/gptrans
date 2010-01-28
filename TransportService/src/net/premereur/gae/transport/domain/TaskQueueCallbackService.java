package net.premereur.gae.transport.domain;

import java.util.logging.Logger;

import com.google.inject.Inject;

public class TaskQueueCallbackService implements CallbackService {
	@Inject
	private Logger logger;
	
	@Override
	public void scheduleQuoteCallback(QuoteRequest quoteRequest, String callbackURL) {
		logger.entering(TaskQueueCallbackService.class.getSimpleName(), "scheduleQuoteCallback");	
	}

}
