package net.premereur.gae.transport.domain;

import com.google.inject.Inject;

public class ServiceLocator {

	private final ScheduleService callbackService;

	private static ServiceLocator singleton;

	@Inject
	ServiceLocator(final ScheduleService callbackService) {
		this.callbackService = callbackService;
		singleton = this;
	}

	public static ServiceLocator get() {
		return singleton;
	}

	public ScheduleService getCallbackService() {
		return callbackService;
	}
}
