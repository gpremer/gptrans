package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.domain.ClockService;
import net.premereur.gae.transport.domain.DemandRepository;
import net.premereur.gae.transport.domain.JPADemandRepository;
import net.premereur.gae.transport.domain.JPAQuoteRequestRepository;
import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.domain.ScheduleService;
import net.premereur.gae.transport.domain.ServiceLocator;
import net.premereur.gae.transport.domain.SystemTimeClockService;
import net.premereur.gae.transport.domain.TaskQueueScheduleService;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
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
		bind(ServiceLocator.class).asEagerSingleton();
		bind(QuoteRequestRepository.class).to(JPAQuoteRequestRepository.class).in(RequestScoped.class);
		bind(DemandRepository.class).to(JPADemandRepository.class).in(RequestScoped.class);
		bindConstant().annotatedWith(JpaUnit.class).to("transactions-optional");
		bind(ScheduleService.class).to(TaskQueueScheduleService.class);
		bind(ClockService.class).to(SystemTimeClockService.class).asEagerSingleton();

		bind(Queue.class).annotatedWith(Names.named(TaskQueueScheduleService.QUOTE_COMPUTE_QUEUE)).toInstance(QueueFactory.getDefaultQueue());
		bind(String.class).annotatedWith(Names.named(TaskQueueScheduleService.QUOTE_COMPUTE_TASK_URL)).toInstance("/transport/tasks/quotesByRequestId");
	}

}
