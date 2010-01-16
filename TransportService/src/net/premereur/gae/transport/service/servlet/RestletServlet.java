package net.premereur.gae.transport.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.premereur.gae.transport.service.resource.QuoteRequestResource;
import net.premereur.gae.transport.service.resource.QuoteRequestsResource;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServletAdapter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class RestletServlet extends HttpServlet {
	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Injector injector;
	// private Context context;
	private ServletAdapter adapter;

	@Override
	public void init() throws ServletException {
		Context context = new Context();
		Application application = new Application();
		application.setContext(context);
		application.setInboundRoot(new GuiceRouter(injector, context) {
			@Override
			protected void attachRoutes() {
				attach("/quoteRequests", QuoteRequestsResource.class);
				attach("/quoteRequests/{requestId}", QuoteRequestResource.class);
			}
		});
		adapter = new ServletAdapter(getServletContext());
		adapter.setTarget(application);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adapter.service(request, response);
	}
}
