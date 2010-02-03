package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import net.premereur.gae.transport.domain.Quote;
import net.premereur.gae.transport.domain.QuoteRequest;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class QuoteGenerationTest {

	private QuoteRequest futureQuoteRequestSmallRange;
	private QuoteRequest futureQuoteRequestIntermediateRange;
	private QuoteRequest futureQuoteRequestLargeRange;

	@Before
	public void createFutureFixture() {
		DateTime earliestDate = new DateTime(2100, 1, 1, 0, 0, 0, 0);
		DateTime closeDate = new DateTime(2100, 1, 1, 1, 0, 0, 0);
		futureQuoteRequestSmallRange = new QuoteRequest(earliestDate.toDate(), closeDate.toDate(), 1.5f, 1, "#AAA", null);
		DateTime furtherDate = new DateTime(2100, 1, 1, 6, 0, 0, 1);
		futureQuoteRequestIntermediateRange = new QuoteRequest(earliestDate.toDate(), furtherDate.toDate(), 1.5f, 1, "#AAB", null);
		DateTime farDate = new DateTime(2100, 1, 4, 7, 0, 0, 1);
		futureQuoteRequestLargeRange = new QuoteRequest(earliestDate.toDate(), farDate.toDate(), 1.5f, 1, "#AAB", null);
	}

	@Test
	public void thereShouldBeOneQuoteInSmallTimeframe() throws Exception {
		List<Quote> quotes = futureQuoteRequestSmallRange.getQuotes();
		assertEquals(1, quotes.size());
	}

	@Test
	public void thereShouldBe2QuotesInIntermediateHourRange() throws Exception {
		List<Quote> quotes = futureQuoteRequestIntermediateRange.getQuotes();
		assertEquals(2, quotes.size());
	}

	@Test
	public void thereShouldBeMoreThan2QuotesInLargeHourRange() throws Exception {
		List<Quote> quotes = futureQuoteRequestLargeRange.getQuotes();
		assertTrue(quotes.size() > 2);
	}

	@Test
	public void quotesShouldBeAboutRequest() throws Exception {
		List<Quote> quotes = futureQuoteRequestSmallRange.getQuotes();
		assertSame(futureQuoteRequestSmallRange, quotes.iterator().next().getOriginator());
	}
	
	@Test
	public void quotePriceShouldBedifferent() throws Exception {
		List<Quote> quotes = futureQuoteRequestLargeRange.getQuotes();
		BigDecimal prevPrice = new BigDecimal(-1);
		for (Quote quote : quotes) {
			assertFalse(prevPrice.equals(quote.getPrice()));
			prevPrice = quote.getPrice();
		}
	}

}