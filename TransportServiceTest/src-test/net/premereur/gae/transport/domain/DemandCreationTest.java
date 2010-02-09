package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DemandCreationTest {

	private QuoteRequestRepository repository;

	private ClockService clock;

	@Before
	public void initGuice() {
		repository = mock(QuoteRequestRepository.class);
		clock = mock(ClockService.class);
		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(ServiceLocator.class).asEagerSingleton();
				bind(ScheduleService.class).toInstance(mock(ScheduleService.class));
				bind(ClockService.class).toInstance(clock);
				bind(QuoteRequestRepository.class).toInstance(repository);
			}
		});
		injector.getInstance(ServiceLocator.class);
	}

	@Test
	public void shouldCreateDemandForExistingQuoteReference() throws Exception {
		String quoteRef = "Qr#1";
		QuoteRequest quoteRequest = new QuoteRequest(new Date(), new Date(), 1f, 1, "cref", null);
		Quote quote = new Quote(quoteRequest, new Date(new Date().getTime() + 100), new BigDecimal("3"), new Date(), new Date());
		when(repository.getQuoteForReference(quoteRef)).thenReturn(quote);
		when(clock.currentTime()).thenReturn(new Date());

		Demand demand = new Demand(quoteRef); // should not throw exception
		// since quote exists
		assertEquals(quote, demand.getQuote());
	}

	@Test
	public void shouldNotCreateDemandIfQuoteReferenceDoesNotExist() throws Exception {
		String quoteRef = "Qr#1";
		when(repository.getQuoteForReference(quoteRef)).thenThrow(new BusinessException(BusinessException.Reason.QUOTE_NOT_VALID, ""));
		when(clock.currentTime()).thenReturn(new Date());
		
		try {
			new Demand(quoteRef); // should throw exception
			fail("shouldn't create demand when quote does not exist");
		} catch (BusinessException e) {
			assertEquals(BusinessException.Reason.QUOTE_NOT_VALID, e.getReason());
		}
	}

	@Test
	public void shouldNotCreateDemandIfQuoteIsExpired() throws Exception {
		String quoteRef = "Qr#1";
		QuoteRequest quoteRequest = new QuoteRequest(new Date(), new Date(), 1f, 1, quoteRef, null);
		long millisNow = new Date().getTime();
		Quote quote = new Quote(quoteRequest, new Date(millisNow + 100), new BigDecimal("3"), new Date(), new Date());
		when(repository.getQuoteForReference(quoteRef)).thenReturn(quote);
		when(clock.currentTime()).thenReturn(new Date(millisNow + 200)); // current time is after quote validity time

		try {
			new Demand(quoteRef); // should throw exception
			fail("shouldn't create demand when quote is expired");
		} catch (BusinessException e) {
			assertEquals(BusinessException.Reason.QUOTE_EXPIRED, e.getReason());
		}
	}

}
