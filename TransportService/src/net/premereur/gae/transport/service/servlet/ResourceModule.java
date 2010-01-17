package net.premereur.gae.transport.service.servlet;

import net.premereur.gae.transport.domain.JPAQuoteRequestRepository;
import net.premereur.gae.transport.domain.QuoteRequestRepository;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.jpa.JpaUnit;

public class ResourceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PersistenceInitialiser.class).asEagerSingleton();
		bind(QuoteRequestRepository.class).to(JPAQuoteRequestRepository.class);
		bindConstant().annotatedWith(JpaUnit.class).to("transactions-optional");		
	}

	
//	@Provides
//	@RequestScoped
//	EntityManager providesEntityManager(EntityManagerFactory emf) {
//		return emf.createEntityManager();
//	}
//
//	@Provides
//	@Singleton
//	EntityManagerFactory providesEntityManagerFactory() {
//		return Persistence.createEntityManagerFactory("transactions-optional");
//	}

}
