package net.premereur.gae.transport.domain;

import static net.premereur.gae.transport.domain.DomainIdSetter.setId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.premereur.gae.LocalAppEngineServiceTestCase;
import net.premereur.gae.testutil.FixedTimeClockService;
import net.premereur.gae.transport.service.quote.v1.serialisation.XmlQuoteRequests;
import net.premereur.gae.transport.service.servlet.PersistenceInitialiser;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;
import com.wideplay.warp.persist.jpa.JpaUnit;

public class JPAQuoteRequestRepositoryTest extends LocalAppEngineServiceTestCase {

	private QuoteRequestRepository repository;

	private static Injector injector;

	@BeforeClass
	static public void initGuice() {
		injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(PersistenceInitialiser.class).asEagerSingleton();
				bind(ServiceLocator.class).asEagerSingleton();
				bind(ScheduleService.class).toInstance(mock(ScheduleService.class));
				bind(QuoteRequestRepository.class).to(JPAQuoteRequestRepository.class);
				bind(DemandRepository.class).toInstance(mock(DemandRepository.class));
				bind(ClockService.class).toInstance(new FixedTimeClockService(new DateTime(2100, 1, 1, 0, 0, 0, 0)));
				bindConstant().annotatedWith(JpaUnit.class).to("transactions-optional");
			}

		}, PersistenceService.usingJpa().across(UnitOfWork.REQUEST).buildModule());
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
	XmlQuoteRequests qrs = new XmlQuoteRequests(Arrays.asList(qr1, qr2));

	@Before
	public void setupFixture() {
		setId(qr1, 1l);
		setId(qr2, 2l);
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
		List<QuoteRequest> all = repository.findAll();
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
		List<QuoteRequest> all = repository.findAll();
		assertEquals(0, all.size());
	}

	@Test
	public void shouldStoreInRepositoryAfterCompute() throws Exception {
		qr1.computeQuotesIfNotAvailableYet();
		Quote firstQuote = qr1.getQuotes().iterator().next();
		assertNotNull(firstQuote.getValidity());
		QuoteRequest storedQr = repository.findByKey(qr1.getId());
		assertFalse(storedQr.getQuotes().isEmpty());
		assertNotNull(storedQr.getQuotes().iterator().next().getValidity());
	}

	@Test
	public void shouldRetrieveQuoteByReference() throws Exception {
		qr1.computeQuotesIfNotAvailableYet();
		Quote firstQuote = qr1.getQuotes().iterator().next();
		Quote loadedQuote = repository.getQuoteForReference(firstQuote.getId());
		assertNotNull("Should find a quote by it's reference", loadedQuote);
	}

	@Test
	public void shouldThrowBusinessExceptionWhenLookingForNotExistingQuoteReference() throws Exception {
		try {
			repository.getQuoteForReference("xxx");
			fail("should not find quote that does not exist");
		} catch (BusinessException e) {
			assertEquals(BusinessException.Reason.QUOTE_NOT_VALID, e.getReason());
		}
	}

}
