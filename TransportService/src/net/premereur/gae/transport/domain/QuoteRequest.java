package net.premereur.gae.transport.domain;

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


@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS)
public class QuoteRequest {
	@XmlAttribute
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date shipmentDate;

	private Float weight;

	private Integer numPackages;

	private String shipperReference;

	public QuoteRequest(Date shipmentDate, float weight, int numPackages, String shipperReference) {
		super();
		this.shipmentDate = shipmentDate;
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
	
	public Date getShipmentDate() {
		return shipmentDate;
	}

	public float getWeight() {
		if ( weight == null) {
			weight = 0f;
		}
		return weight;
	}

	public int getNumPackages() {
		if ( numPackages == null ) {
			numPackages = 1;
		}
		return numPackages;
	}

	public String getShipperReference() {
		return shipperReference;
	}

	@Override
	public String toString() {		
		return ToStringBuilder.reflectionToString(this);
	}
}
