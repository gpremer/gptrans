package net.premereur.gae.transport.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS)
public class Quote {
	@XmlID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	private transient final QuoteRequest originator;
	
	public Quote(QuoteRequest originator) {
		this.originator = originator;
	}

	public QuoteRequest getOriginator() {
		return originator;
	}
}
