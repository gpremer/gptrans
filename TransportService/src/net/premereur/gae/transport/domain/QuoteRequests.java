package net.premereur.gae.transport.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = Constants.QUOTE_SERVICE_NS)
public class QuoteRequests implements Serializable {

	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	private List<QuoteRequest> quoteRequests = Collections.emptyList();

	public QuoteRequests() {

	}

	public QuoteRequests(List<QuoteRequest> quoteRequests) {
		this.quoteRequests = quoteRequests;
	}

	@XmlElement(name = "quoteRequest")
	public List<QuoteRequest> getQuoteRequests() {
		return quoteRequests;
	}
}
