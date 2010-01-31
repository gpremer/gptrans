package net.premereur.gae.transport.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private final BigDecimal price;

	private final String shipperReference;

	private final Date pickupFromTime;

	private final Date pickupToTime;

	private final Date validity;
	
	@Transient
	private transient final QuoteRequest originator;

	public Quote(final QuoteRequest originator, Date validity, final BigDecimal price, final Date pickupFromTime, final Date pickupToTime) {
		this.originator = originator;
		this.validity = validity;
		this.price = price;
		this.shipperReference = this.originator.getCustomerReference();
		this.pickupFromTime = pickupFromTime;
		this.pickupToTime = pickupToTime;
	}

	@SuppressWarnings("unused")
	private Quote() {
		this.originator = null;
		this.validity = null;
		this.price = null;
		this.shipperReference = null;
		this.pickupFromTime = null;
		this.pickupToTime = null;
	}
	
	public QuoteRequest getOriginator() {
		return originator;
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

	public Long getId() {
		return id;
	}

	public Date getPickupFromTime() {
		return pickupFromTime;
	}

	public Date getPickupToTime() {
		return pickupToTime;
	}

}
