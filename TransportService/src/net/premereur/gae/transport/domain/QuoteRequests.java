package net.premereur.gae.transport.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS)
public class QuoteRequests implements Serializable {

	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name="quoteRequest")
	private List<QuoteRequest> quoteRequests = Collections.emptyList();

	public QuoteRequests() {

	}

	public QuoteRequests(List<QuoteRequest> quoteRequests) {
		this.quoteRequests = quoteRequests;
	}

	public List<QuoteRequest> getQuoteRequests() {
		return quoteRequests;
	}
}
