package net.premereur.gae.transport.service.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class ServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		// Further modules are omitted...
		return Guice.createInjector(new ServletModule(), new ResourceModule(), PersistenceService.usingJpa().across(UnitOfWork.REQUEST).buildModule());
	}
}