package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.premereur.gae.LocalAppEngineServiceTestCase;
import net.premereur.gae.transport.service.servlet.ResourceModule;

import org.joda.time.DateTime;
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

	DateTime earliestDate = new DateTime(2000, 1, 2, 3, 4, 5, 6);
	DateTime latestDate = new DateTime(2000, 1, 2, 5, 4, 5, 6);
	QuoteRequest qr1 = new QuoteRequest(earliestDate.toDate(), latestDate.toDate(), 1.5f, 1, "#AAA", null);
	QuoteRequest qr2 = new QuoteRequest(new Date(), new Date(), 2f, 1, "#AAB", null);
	QuoteRequests qrs = new QuoteRequests(Arrays.asList(qr1, qr2));

	@Before
	public void setupFixture() {
		qr1.setId(1l);
		qr2.setId(2l);
	}

	
	@Test
	public void shouldStoreQuoteRequestTransactionally() throws Exception {
		repository.store(qr1);
		assertNotNull(qr1.getId());
	}

	@Test
	public void shouldLoadAllQuoteRequests() throws Exception {
		repository.store(qr1);
		repository.store(qr2);
		List<QuoteRequest> all = repository.findAll().getQuoteRequests();
		assertNotNull(all);
		assertEquals(2, all.size());
	}

	@Test
	public void shouldFindQuoteRequestByKey() throws Exception {
		repository.store(qr1);
		QuoteRequest qrFound = repository.findByKey(qr1.getId());
		assertNotNull(qrFound);
		assertEquals(qr1.getId(), qrFound.getId());
		
	}
	
	@Test
	public void shouldCleanRepository() throws Exception {
		repository.store(qr1);
		repository.store(qr2);
		repository.removeAll();
		List<QuoteRequest> all = repository.findAll().getQuoteRequests();
		assertEquals(0, all.size());		
	}
}
