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
public class Quotes implements Serializable {

	/**
	 * Class version.
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "quote")
	private List<Quote> quotes = Collections.emptyList();

	public Quotes(List<Quote> quotes) {
		this.quotes = quotes;
	}
		
	@SuppressWarnings("unused")
	private Quotes() {
		
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

}
