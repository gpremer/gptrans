package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;

import net.premereur.gae.LocalAppEngineServiceTestCase;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public class JPADemandRepositoryTest extends LocalAppEngineServiceTestCase {

	private DemandRepository demandRepository;
	private QuoteRequestRepository quoteRequestRepository;

	private static Injector injector;

	private String quoteRef = "qref1";

	@BeforeClass
	static public void initGuice() {
		injector = initGuice(new AbstractModule() {

			@Override
			protected void configure() {
				bind(ScheduleService.class).toInstance(mock(ScheduleService.class));
				bind(QuoteRequestRepository.class).toInstance(mock(JPAQuoteRequestRepository.class));
				bind(DemandRepository.class).to(JPADemandRepository.class);
			}

		});
	}

	@Before
	public void initRepository() {
		demandRepository = injector.getInstance(DemandRepository.class);
		quoteRequestRepository = injector.getInstance(QuoteRequestRepository.class);
	}

	QuoteRequest quoteRequest = new QuoteRequest(new DateTime(2100, 1, 1, 0, 0, 0, 0).toDate(), new DateTime(2100, 1, 1, 12, 0, 0, 0).toDate(), 1f, 1, "#AAAA",
			null);
	Quote quote = new Quote(quoteRequest, new DateTime(2100, 1, 1, 1, 0, 0, 0).toDate(), new BigDecimal("4.2"), new DateTime(2100, 1, 1, 0, 0, 0, 0).toDate(),
			new DateTime(2100, 1, 1, 6, 0, 0, 0).toDate());

	public Demand createInitialDemand() throws BusinessException {
		quote.setId(quoteRef);
		when(quoteRequestRepository.getQuoteForReference(quoteRef)).thenReturn(quote);
		Demand demand = new Demand(quoteRef);
		return demand;
	}

	@Test(expected = BusinessException.class)
	public void shouldThrowExceptionIfDemandNotFound() throws Exception {
		demandRepository.getDemandForQuoteReference("blabla");
	}

	@Test
	public void shouldStoreDemand() throws Exception {
		Demand demand = createInitialDemand();
		demandRepository.store(demand);
		Collection<Demand> allDemands = demandRepository.findAll();
		assertEquals(1, allDemands.size());
		assertEquals(quoteRef, allDemands.iterator().next().getQuoteReference());
	}

	@Test
	public void shouldLoadDemandForQuote() throws Exception {
		Demand demand = createInitialDemand();
		demandRepository.store(demand);
		Demand storedDemand = demandRepository.getDemandForQuoteReference(quoteRef);
		assertEquals(quoteRef, storedDemand.getQuoteReference());
	}

}
