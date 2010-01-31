package net.premereur.gae.transport.service.v1.resource.serialisation;

import static net.premereur.gae.testutil.Assertions.assertXmlTimeEquals;
import static net.premereur.gae.transport.domain.DomainIdSetter.setId;
import static org.junit.Assert.assertEquals;

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

public class QuoteSerialiserTest {

	DateTime earliestDate = new DateTime(2000, 1, 2, 3, 4, 5, 6);
	DateTime latestDate = new DateTime(2000, 1, 2, 5, 4, 5, 6);
	DateTime validity = new DateTime(2000, 1, 2, 4, 0, 0, 0);
	QuoteRequest qr1 = new QuoteRequest(earliestDate.toDate(), latestDate.toDate(), 1.5f, 1, "#AAA", null);
	QuoteRequest qr2 = new QuoteRequest(new Date(), new Date(), 2f, 1, "#AAB", null);
	Quote q1 = new Quote(qr1, validity.toDate(), new BigDecimal("5.23"), earliestDate.toDate(), earliestDate.plusHours(4).toDate());
	Quote q2 = new Quote(qr2, validity.toDate(), new BigDecimal("6.23"), latestDate.toDate(), latestDate.plusHours(4).toDate());
	XmlQuotes quotes = new XmlQuotes(Arrays.asList(q1, q2));

	@Before
	public void setupFixture() {
		setId(qr1, 1l);
		setId(qr2, 2l);
	}

	@Test
	public void shouldCreateXmlWithProperElementName() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(new XmlQuote(q1), result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("quote", eltNode.getLocalName());
		assertEquals("http://gpdeliver.appspot.com/schema/quote/v1/", eltNode.getNamespaceURI());
	}

	@Test
	public void shouldCreateXmlWithAllFields() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(new XmlQuote(q1), result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("The id should not be exposed", "", eltNode.getAttribute("id"));
		assertEquals("5.23", eltNode.getElementsByTagName("price").item(0).getTextContent());
		assertEquals("#AAA", eltNode.getElementsByTagName("shipperReference").item(0).getTextContent());
		assertXmlTimeEquals("2000-01-02T03:04:05.006Z", eltNode.getElementsByTagName("pickupFromTime").item(0).getTextContent());
		assertXmlTimeEquals("2000-01-02T07:04:05.006+00:00", eltNode.getElementsByTagName("pickupToTime").item(0).getTextContent());
		assertXmlTimeEquals("2000-01-02T04:00:00Z", eltNode.getElementsByTagName("validity").item(0).getTextContent());
	}

}
