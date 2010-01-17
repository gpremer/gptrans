package net.premereur.gae.transport.service.servlet;

import com.wideplay.warp.persist.PersistenceFilter;

/**
 * Configures servlets in plain Java, courtesy of Guice.
 * 
 * @author gpremer
 * 
 */
public class ServletModule extends com.google.inject.servlet.ServletModule {

	@Override
	protected void configureServlets() {
		serve("/transport/quoteService/*").with(RestletServlet.class);
		filter("/transport/quoteService/*").through(PersistenceFilter.class);
	}

}
