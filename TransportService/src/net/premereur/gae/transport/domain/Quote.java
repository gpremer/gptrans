package net.premereur.gae.transport.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.datanucleus.jpa.annotations.Extension;

@Entity
public class Quote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	private BigDecimal price;

	private String customerReference;

	private Date pickupFromTime;

	private Date pickupToTime;

	private Date validity;

	@ManyToOne(cascade = CascadeType.REFRESH)
	private QuoteRequest originator;

	public Quote(final QuoteRequest originator, Date validity, final BigDecimal price, final Date pickupFromTime, final Date pickupToTime) {
		this.originator = originator;
		this.validity = validity;
		this.price = price;
		this.customerReference = this.originator.getCustomerReference();
		this.pickupFromTime = pickupFromTime;
		this.pickupToTime = pickupToTime;
	}

	@SuppressWarnings("unused")
	private Quote() {
		this.originator = null;
		this.validity = null;
		this.price = null;
		this.customerReference = null;
		this.pickupFromTime = null;
		this.pickupToTime = null;
	}

	public String getId() {
		return id;
	}
	
	// For unit testing, because of datanucleus
	void setId(String id) {
		this.id = id;
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

	public String getCustomerReference() {
		return this.customerReference;
	}

	public Date getPickupFromTime() {
		return pickupFromTime;
	}

	public Date getPickupToTime() {
		return pickupToTime;
	}

}
