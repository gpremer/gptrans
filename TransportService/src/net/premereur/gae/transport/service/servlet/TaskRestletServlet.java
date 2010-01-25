package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.service.admin.CleanQuoteRequestsResource;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Restlet servlet for task queue services. These resources are support to be
 * protected by access control.
 * 
 * @author gpremer
 * 
 */
@Singleton
public class TaskRestletServlet extends GAERestletServlet {
	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected GuiceRouter getGuiceRouter(Injector injector, Context context) {
		return new GuiceRouter(injector, context) {
			@Override
			protected void attachRoutes() {
				attach("/admin/cleanQuoteRequests", CleanQuoteRequestsResource.class);
				attach("/quoteByShipperRef", CleanQuoteRequestsResource.class);
			}
		};
	}

}
