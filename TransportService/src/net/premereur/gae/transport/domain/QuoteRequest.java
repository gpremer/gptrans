package net.premereur.gae.transport.domain;

import static java.lang.Math.log;
import static java.lang.Math.max;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS)
public class QuoteRequest {
	private static final int TARIF_DECREASE_UNIT = 6;

	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date earliestShipmentTime;

	private Date latestShipmentTime;

	private Float weight;

	private Integer numPackages;

	private String customerReference;

	public QuoteRequest(Date earliestShipmentTime, Date latestShipmentTime, Float weight, Integer numPackages, String customerReference) {
		super();
		this.earliestShipmentTime = earliestShipmentTime;
		this.latestShipmentTime = latestShipmentTime;
		this.weight = weight;
		this.numPackages = numPackages;
		this.customerReference = customerReference;
	}

	@SuppressWarnings("unused")
	private QuoteRequest() {
		// For JPA
	}

	public Long getId() {
		return id;
	}

	// For unit testing
	void setId(Long id) {
		this.id = id;
	}

	public Date getEarliestShipmentTime() {
		return earliestShipmentTime;
	}

	public Date getLatestShipmentTime() {
		return latestShipmentTime;
	}

	public float getWeight() {
		return valueWithDefault(weight, 0f);
	}

	public int getNumPackages() {
		return valueWithDefault(numPackages, 1);
	}

	public String getCustomerReference() {
		return customerReference;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	private static final <T> T valueWithDefault(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public Quotes getQuotes() {
		DateTime next = new DateTime(getEarliestShipmentTime());
		DateTime last = new DateTime(getLatestShipmentTime());
		ArrayList<Quote> quotes = new ArrayList<Quote>();
		BigDecimal price;
		double basePrice = 5*(1+log(getNumPackages()))*log(max(2, getWeight())) ;
		double discount = 1;
		while ( next.isBefore(last) ) {
			price  = new BigDecimal(basePrice * discount);			
			final Date start = next.toDate();
			next = next.plusHours(TARIF_DECREASE_UNIT);
			final Date end = next.toDate();
			Quote quote = new Quote(this, price, start, end);
			quotes.add(quote);
			discount *= 0.95; 
		}
		return new Quotes(quotes);
	}
}
