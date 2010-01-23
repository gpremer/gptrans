package net.premereur.gae.transport.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.JAXB;
import javax.xml.transform.dom.DOMResult;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuoteRequestSerialiserTest {

	DateTime earliestDate = new DateTime(2000, 1, 2, 3, 4, 5, 6);
	DateTime latestDate = new DateTime(2000, 1, 2, 5, 4, 5, 6);
	QuoteRequest qr1 = new QuoteRequest(earliestDate.toDate(), latestDate.toDate(), 1.5f, 1, "#AAA");
	QuoteRequest qr2 = new QuoteRequest(new Date(), new Date(), 2f, 1, "#AAB");
	QuoteRequests qrs = new QuoteRequests(Arrays.asList(qr1, qr2));

	@Before
	public void setupFixture() {
		qr1.setId(1l);
		qr2.setId(2l);
	}

	@Test
	public void shouldCreateXmlWithProperElementName() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(qr2, result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("quoteRequest", eltNode.getLocalName());
		assertEquals("http://gpdeliver.appspot.com/schema/quote/v1/", eltNode.getNamespaceURI());
	}

	@Test
	public void shouldCreateXmlWithAllFields() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(qr1, result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("1", eltNode.getAttribute("id"));
		assertEquals("1.5", eltNode.getElementsByTagName("weight").item(0).getTextContent());
		assertEquals("1", eltNode.getElementsByTagName("numPackages").item(0).getTextContent());
		assertEquals("#AAA", eltNode.getElementsByTagName("shipperReference").item(0).getTextContent());
		assertEquals("2000-01-02T03:04:05.006Z", eltNode.getElementsByTagName("earliestShipmentTime").item(0).getTextContent());
		assertEquals("2000-01-02T05:04:05.006Z", eltNode.getElementsByTagName("latestShipmentTime").item(0).getTextContent());
	}

	@Test
	public void shouldCreateXmlWithContainedElements() throws Exception {
		DOMResult result = new DOMResult();
		JAXB.marshal(qrs, result);
		Node root = result.getNode();
		Element eltNode = (Element) root.getFirstChild();
		assertEquals("quoteRequests", eltNode.getLocalName());
		assertEquals("http://gpdeliver.appspot.com/schema/quote/v1/", eltNode.getNamespaceURI());
		NodeList childNodes = eltNode.getElementsByTagName("quoteRequest");
		assertEquals(2, childNodes.getLength());
		assertEquals("quoteRequest", childNodes.item(0).getLocalName());
	}
}
