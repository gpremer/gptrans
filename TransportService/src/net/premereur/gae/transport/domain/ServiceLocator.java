package net.premereur.gae.transport.domain;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ServiceLocator {

	private final ScheduleService callbackService;

	private final Provider<QuoteRequestRepository> quoteRequestRepository;

	private final Provider<DemandRepository> demandRepository;

	private final ClockService clockService;

	private static ServiceLocator singleton;

	@Inject
	ServiceLocator(final ScheduleService callbackService, final Provider<QuoteRequestRepository> quoteRequestRepository,
			final Provider<DemandRepository> demandRepository, final ClockService clockService) {
		this.callbackService = callbackService;
		this.quoteRequestRepository = quoteRequestRepository;
		this.clockService = clockService;
		this.demandRepository = demandRepository;
		singleton = this;
	}

	public static ServiceLocator get() {
		return singleton;
	}

	public ScheduleService getCallbackService() {
		return callbackService;
	}

	public QuoteRequestRepository getQuoteRequestRepository() {
		return quoteRequestRepository.get();

	}

	public ClockService getClockService() {
		return clockService;
	}

	public DemandRepository getDemandRepository() {
		return demandRepository.get();
	}
}
