package net.premereur.gae.transport.service.restlet;

import net.premereur.gae.transport.service.servlet.GAERestletServlet;
import net.premereur.gae.transport.service.servlet.GuiceRouter;
import net.premereur.gae.transport.service.task.MakeQuoteResource;

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
				attach("/quotesByRequestId/{requestId}", MakeQuoteResource.class);
			}
		};
	}

}
