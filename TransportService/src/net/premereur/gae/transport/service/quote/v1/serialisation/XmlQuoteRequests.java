package net.premereur.gae.transport.service.quote.v1.serialisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.premereur.gae.transport.domain.QuoteRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS, name = "quoteRequests")
public class XmlQuoteRequests implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="quoteRequest")
	private final List<XmlQuoteRequest> quoteRequests;

	public XmlQuoteRequests() {
		this.quoteRequests = Collections.emptyList();
	}

	public XmlQuoteRequests(List<QuoteRequest> quoteRequests) {
		this.quoteRequests = new ArrayList<XmlQuoteRequest>();
		for (QuoteRequest qr : quoteRequests) {
			this.quoteRequests.add(new XmlQuoteRequest(qr));
		}
	}

	public List<XmlQuoteRequest> getQuoteRequests() {
		return quoteRequests;
	}
}
