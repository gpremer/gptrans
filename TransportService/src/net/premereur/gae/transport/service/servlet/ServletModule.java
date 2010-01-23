package net.premereur.gae.transport.service.servlet;

import com.wideplay.warp.persist.PersistenceFilter;

/**
 * Configures servlets in plain Java, courtesy of Guice.
 * 
 * @author gpremer
 * 
 */
public class ServletModule extends com.google.inject.servlet.ServletModule {

	private static final String TRANSPORT_URL_PATTERN = "/transport/*";
	private static final String TRANSPORT_SERVICE_URL_PATTERN = "/transport/quoteService/*";
	private static final String TRANSPORT_TASK_URL_PATTERN = "/transport/tasks/*";

	@Override
	protected void configureServlets() {
		filter(TRANSPORT_URL_PATTERN).through(PersistenceFilter.class);
		serve(TRANSPORT_SERVICE_URL_PATTERN).with(ResourceRestletServlet.class);
		serve(TRANSPORT_TASK_URL_PATTERN).with(TaskRestletServlet.class);
	}

}
