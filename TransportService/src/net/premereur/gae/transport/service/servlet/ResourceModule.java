package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.domain.CallbackService;
import net.premereur.gae.transport.domain.JPAQuoteRequestRepository;
import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.domain.ServiceLocator;
import net.premereur.gae.transport.domain.TaskQueueCallbackService;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.jpa.JpaUnit;

/**
 * The Guice module used for the Restlet resources.
 * 
 * @author gpremer
 * 
 */
public class ResourceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersistenceInitialiser.class).asEagerSingleton();
		bind(QuoteRequestRepository.class).to(JPAQuoteRequestRepository.class);
		bindConstant().annotatedWith(JpaUnit.class).to("transactions-optional");
		bind(CallbackService.class).to(TaskQueueCallbackService.class);
		bind(ServiceLocator.class).asEagerSingleton();
	}

}
