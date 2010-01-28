package net.premereur.gae.transport.domain;

import com.google.inject.Inject;

public class ServiceLocator {

	private final CallbackService callbackService;

	private static ServiceLocator singleton;

	@Inject
	ServiceLocator(final CallbackService callbackService) {
		this.callbackService = callbackService;
		singleton = this;
	}

	public static ServiceLocator get() {
		return singleton;
	}

	public CallbackService getCallbackService() {
		return callbackService;
	}
}
