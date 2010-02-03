package net.premereur.gae.transport.service.quote.v1.serialisation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.premereur.gae.transport.domain.Quote;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS, name = "quote")
public class XmlQuote implements Serializable {

	private static final long serialVersionUID = 1L;

	private final BigDecimal price;

	private final String shipperReference;

	private final Date pickupFromTime;

	private final Date pickupToTime;

	private final Date validity;

	public XmlQuote(final Quote quote) {
		this.validity = quote.getValidity();
		this.price = quote.getPrice();
		this.shipperReference = quote.getShipperReference();
		this.pickupFromTime = quote.getPickupFromTime();
		this.pickupToTime = quote.getPickupToTime();
	}

	@SuppressWarnings("unused")
	private XmlQuote() {
		// for JAXB
		this.validity = null;
		this.price = null;
		this.shipperReference = null;
		this.pickupFromTime = null;
		this.pickupToTime = null;
	}

	public Date getValidity() {
		return validity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String getShipperReference() {
		return this.shipperReference;
	}

	public Date getPickupFromTime() {
		return pickupFromTime;
	}

	public Date getPickupToTime() {
		return pickupToTime;
	}

}
