package net.premereur.gae.transport.service.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		// Further modules are omitted...
		return Guice.createInjector(new ServletModule(), new ResourceModule());
	}
}