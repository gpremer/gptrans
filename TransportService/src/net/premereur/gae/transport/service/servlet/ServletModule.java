package net.premereur.gae.transport.service.servlet;

public class ServletModule extends com.google.inject.servlet.ServletModule {

	@Override
	protected void configureServlets() {
		serve("/transport/service/*").with(RestletServlet.class);
	}

}
