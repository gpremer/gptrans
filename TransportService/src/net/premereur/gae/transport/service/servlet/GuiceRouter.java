package net.premereur.gae.transport.service.servlet;

import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import com.google.inject.Injector;

/**
 * A Restlet router that is receiving a Guice injector which it passes to a
 * custom finder.
 * 
 * @author gpremer
 * 
 */
public abstract class GuiceRouter extends Router {
	private final Injector injector;

	public GuiceRouter(Injector injector, Context context) {
		super(context);
		this.injector = injector;
		attachRoutes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Finder createFinder(Class<?> targetClass) {
		return new GuiceFinder(injector, getContext(), (Class<? extends ServerResource>) targetClass);
	}

	/**
	 * Concrete routers override this to add routes.
	 */
	protected abstract void attachRoutes();

}