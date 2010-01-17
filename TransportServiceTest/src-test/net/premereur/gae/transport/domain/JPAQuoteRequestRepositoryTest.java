package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Date;

import net.premereur.gae.LocalAppEngineServiceTestCase;
import net.premereur.gae.transport.service.servlet.ResourceModule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

public class JPAQuoteRequestRepositoryTest extends LocalAppEngineServiceTestCase {

	private QuoteRequestRepository repository;

	private static Injector injector;

	@BeforeClass
	static public void initGuice() {
		injector = Guice.createInjector(new ResourceModule(), PersistenceService.usingJpa().across(UnitOfWork.REQUEST).buildModule());
	}

	@Before
	public void initRepository() {
		// System.setProperty("appengine.orm.disable.duplicate.emf.exception",Boolean.TRUE.toString());
		repository = injector.getInstance(QuoteRequestRepository.class);
	}

	@Test
	public void shouldStoreQuoteRequestTransactionally() throws Exception {
		QuoteRequest qr = new QuoteRequest(new Date(), 4.1f);

		repository.store(qr);
		assertNotNull(qr.getId());
	}

	@Test
	public void shouldLoadAllQuoteRequests() throws Exception {
		QuoteRequest qr = new QuoteRequest(new Date(), 4.1f);
		repository.store(qr);
		Collection<QuoteRequest> all = repository.findAll();
		assertNotNull(all);
		assertEquals(1, all.size());
	}

	@Test
	public void shouldFindQuoteRequestByKey() throws Exception {
		QuoteRequest qrOrig = new QuoteRequest(new Date(), 4.1f);
		repository.store(qrOrig);
		QuoteRequest qrFound = repository.findByKey(qrOrig.getId());
		assertNotNull(qrFound);
		assertEquals(qrOrig.getId(), qrFound.getId());
		
	}
}
