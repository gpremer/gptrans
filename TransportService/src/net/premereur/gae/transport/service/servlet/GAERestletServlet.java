package net.premereur.gae.transport.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Application;
import org.restlet.Context;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Common functionality for Servlets running in Google Appengine serving
 * resources the Restlet way.
 * 
 * @author gpremer
 * 
 */
public abstract class GAERestletServlet extends HttpServlet {

	@Inject
	private Injector injector;
	// private Context context;
	private GAEServletAdapter adapter;

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = new Context();
		Application application = new Application();
		application.setContext(context);
		application.setInboundRoot(getGuiceRouter(injector, context));
		adapter = new GAEServletAdapter(getServletContext());
		adapter.setTarget(application);
	}
	
	protected abstract GuiceRouter getGuiceRouter(Injector injector, Context context); 

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adapter.service(request, response);
	}


}
