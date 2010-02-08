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

	@Before
	public void initGuice() {
		repository = mock(QuoteRequestRepository.class);
		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(ServiceLocator.class).asEagerSingleton();
				bind(ScheduleService.class).toInstance(mock(ScheduleService.class));
				bind(QuoteRequestRepository.class).toInstance(repository);
			}
		});
		injector.getInstance(ServiceLocator.class);
	}

	@Test
	public void shouldCreateDemandForExistingQuoteReference() throws Exception {
		String quoteRef = "Qr#1";
		QuoteRequest quoteRequest = new QuoteRequest(new Date(), new Date(), 1f, 1, "cref", null);
		Quote quote = new Quote(quoteRequest, new Date(), new BigDecimal("3"), new Date(), new Date());
		when(repository.getQuoteForReference(quoteRef)).thenReturn(quote);

		Demand demand = new Demand(quoteRef); // should not throw exception
												// since quote exists
		assertEquals(quote, demand.getQuote());
	}

	@Test
	public void shouldNotCreateDemandIfQuoteReferenceDoesNotExist() throws Exception {
		String quoteRef = "Qr#1";
		when(repository.getQuoteForReference(quoteRef)).thenThrow(new BusinessException(BusinessException.Reason.QUOTE_NOT_VALID,""));

		try {
			new Demand(quoteRef); // should throw exception
			fail("shouldn't create demand when quote does not exist");
		} catch (BusinessException e) {
			assertEquals(BusinessException.Reason.QUOTE_NOT_VALID, e.getReason());
		}
	}
}
