package net.premereur.gae.transport.service.quote.v1.serialisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.premereur.gae.transport.domain.Quote;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS, name = "quotes")
public class XmlQuotes implements Serializable {

	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "quote")
	private final List<XmlQuote> quotes;

	public XmlQuotes(Collection<Quote> quotes) {
		this.quotes = new ArrayList<XmlQuote>();
		for (Quote q : quotes) {
			this.quotes.add(new XmlQuote(q));
		}
	}

	@SuppressWarnings("unused")
	private XmlQuotes() {
		this.quotes = Collections.emptyList();
	}

	public List<XmlQuote> getQuotes() {
		return quotes;
	}

}
