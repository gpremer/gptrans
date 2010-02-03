package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.service.restlet.AdminRestletServlet;
import net.premereur.gae.transport.service.restlet.QuoteRestletServlet;
import net.premereur.gae.transport.service.restlet.TaskRestletServlet;

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
	private static final String TRANSPORT_ADMIN_URL_PATTERN = "/transport/admin/*";

	@Override
	protected void configureServlets() {
		filter(TRANSPORT_URL_PATTERN).through(PersistenceFilter.class);
		serve(TRANSPORT_SERVICE_URL_PATTERN).with(QuoteRestletServlet.class);
		serve(TRANSPORT_TASK_URL_PATTERN).with(TaskRestletServlet.class);
		serve(TRANSPORT_ADMIN_URL_PATTERN).with(AdminRestletServlet.class);
	}

}
