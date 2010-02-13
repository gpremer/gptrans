package net.premereur.gae;

import net.premereur.gae.testutil.FixedTimeClockService;
import net.premereur.gae.transport.domain.ClockService;
import net.premereur.gae.transport.domain.ServiceLocator;
import net.premereur.gae.transport.service.servlet.PersistenceInitialiser;

import org.joda.time.DateTime;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.jpa.JpaUnit;

public class BaseTestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersistenceInitialiser.class).asEagerSingleton();
		bindConstant().annotatedWith(JpaUnit.class).to("transactions-optional");
		bind(ServiceLocator.class).asEagerSingleton();
		bind(ClockService.class).toInstance(new FixedTimeClockService(new DateTime(2100, 1, 1, 0, 0, 0, 0)));
	}

}
