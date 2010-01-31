package net.premereur.gae.transport.service.v1.resource.serialisation;

import static net.premereur.gae.transport.domain.DomainIdSetter.setId;
import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.JAXB;
import javax.xml.transform.dom.DOMResult;

import net.premereur.gae.transport.domain.Quote;
import net.premereur.gae.transport.domain.QuoteRequest;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuotesSerialiserTest {

	DateTime earliestDate = new DateTime(2000, 1, 2, 3, 4, 5, 6);
	DateTime latestDate = new DateTime(2000, 1, 2, 5, 4, 5, 6);
	QuoteRequest qr1 = new QuoteRequest(earliestDate.toDate(), latestDate.toDate(), 1.5f, 1, "#AAA", null);
	QuoteRequest qr2 = new QuoteRequest(new Date(), new Date(), 2f, 1, "#AAB", null);
	DateTime validity = new DateTime(2000, 1, 2, 4, 0, 0, 0);
	Quote q1 = new Quote(qr1, validity.toDate(),new BigDecimal("5.23"), earliestDate.toDate(), earliestDate.plusHours(4).toDate());
	Quote q2 = new Quote(qr2, validity.toDate(),new BigDecimal("6.23"), latestDate.toDate(), latestDate.plusHours(4).toDate());
	XmlQuotes quotes = new XmlQuotes(Arrays.asList(q1, q2));

	@Before
	public void setupFixture() {
		setId(qr1, 1l);
		setId(qr2, 2l);
	}

	@Test
	public void shouldCreateXmlWithProperElementName() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(quotes, result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("quotes", eltNode.getLocalName());
		assertEquals("http://gpdeliver.appspot.com/schema/quote/v1/", eltNode.getNamespaceURI());
	}
	
	@Test
	public void shouldCreateXmlWithAllContainedQuotes() throws Exception {
		StringWriter sw = new StringWriter();
		JAXB.marshal(quotes, sw);
		System.out.println(sw);
		DOMResult result = new DOMResult();
		JAXB.marshal(quotes, result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		NodeList childNodes = eltNode.getElementsByTagName("quote");
		assertEquals(2, childNodes.getLength());
	}
}
