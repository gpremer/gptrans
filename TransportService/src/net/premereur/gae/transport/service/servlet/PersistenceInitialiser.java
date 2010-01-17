package net.premereur.gae.transport.service.servlet;

import com.google.inject.Inject;
import com.wideplay.warp.persist.PersistenceService;

public class PersistenceInitialiser {

	@Inject
	PersistenceInitialiser(PersistenceService service) {
		service.start();
	}
}