package net.premereur.gae.transport.service.restlet;

import net.premereur.gae.transport.service.demand.v1.TransportDemandsResource;
import net.premereur.gae.transport.service.servlet.GAERestletServlet;
import net.premereur.gae.transport.service.servlet.GuiceRouter;

import org.restlet.Context;

import com.google.inject.Injector;

public class TransportDemandsRestletServlet extends GAERestletServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected GuiceRouter getGuiceRouter(Injector injector, Context context) {
		return new GuiceRouter(injector, context) {
			@Override
			protected void attachRoutes() {
				attach("/v1/transportDemands", TransportDemandsResource.class);
			}
		};
	}

}
