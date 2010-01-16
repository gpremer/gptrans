package net.premereur.gae.transport.service.servlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;

public class ResourceModule extends AbstractModule {

	@Override
	protected void configure() {

	}

	@Provides
	@RequestScoped
	EntityManager providesEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	@Provides
	@Singleton
	EntityManagerFactory providesEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("transactions-optional");
	}

}
