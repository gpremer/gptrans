package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.service.quote.v1.QuoteRequestResource;
import net.premereur.gae.transport.service.quote.v1.QuoteRequestsResource;
import net.premereur.gae.transport.service.quote.v1.QuotesResource;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Restlet servlet that operates on public resources.
 * 
 * @author gpremer
 * 
 */
@Singleton
public class QuoteRestletServlet extends GAERestletServlet {
	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected GuiceRouter getGuiceRouter(Injector injector, Context context) {
		return new GuiceRouter(injector, context) {
			@Override
			protected void attachRoutes() {
				attach("/v1/quoteRequests", QuoteRequestsResource.class);
				attach("/v1/quoteRequests/{requestId}", QuoteRequestResource.class);
				attach("/v1/quoteRequests/{requestId}/quotes", QuotesResource.class);
			}
		};
	}

}
