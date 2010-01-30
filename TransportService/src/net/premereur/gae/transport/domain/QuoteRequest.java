package net.premereur.gae.transport.domain;

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
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date earliestShipmentTime;

	private Date latestShipmentTime;

	private Float weight;

	private Integer numPackages;

	private String shipperReference;

	public QuoteRequest(Date earliestShipmentTime, Date latestShipmentTime, Float weight, Integer numPackages, String shipperReference) {
		super();
		this.earliestShipmentTime = earliestShipmentTime;
		this.latestShipmentTime = latestShipmentTime;
		this.weight = weight;
		this.numPackages = numPackages;
		this.shipperReference = shipperReference;
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

	public String getShipperReference() {
		return shipperReference;
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
		while ( next.isBefore(last) ) {
			Quote quote = new Quote(this);
			quotes.add(quote);
			next = next.plusHours(6);
		}
		return new Quotes(quotes);
		
	}
}
