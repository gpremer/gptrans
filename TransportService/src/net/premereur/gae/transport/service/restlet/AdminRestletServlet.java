package net.premereur.gae.transport.service.restlet;

import net.premereur.gae.transport.service.admin.CleanQuoteRequestsResource;
import net.premereur.gae.transport.service.servlet.GAERestletServlet;
import net.premereur.gae.transport.service.servlet.GuiceRouter;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class AdminRestletServlet extends GAERestletServlet {
	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected GuiceRouter getGuiceRouter(Injector injector, Context context) {
		return new GuiceRouter(injector, context) {
			@Override
			protected void attachRoutes() {
				attach("/cleanQuoteRequests", CleanQuoteRequestsResource.class);
			}
		};
	}


}
