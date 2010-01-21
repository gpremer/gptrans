package net.premereur.gae.transport.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@XmlRootElement(namespace=Constants.QUOTE_SERVICE_NS)
public class QuoteRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="shipmentDate")
	private Date shipmentDate;

	private float weight;

	public QuoteRequest(Date shipmentDate, float weight) {
		super();
		this.shipmentDate = shipmentDate;
		this.weight = weight;
	}

	@SuppressWarnings("unused")
	private QuoteRequest() {
		// For JPA
	}
	
	public Long getId() {
		return id;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

}
