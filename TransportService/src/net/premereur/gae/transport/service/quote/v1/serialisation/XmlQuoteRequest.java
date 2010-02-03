package net.premereur.gae.transport.service.quote.v1.serialisation;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.premereur.gae.transport.domain.QuoteRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(namespace = Constants.QUOTE_SCHEMA_NS, name = "quoteRequest")
public class XmlQuoteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private Long id;

	private Date earliestShipmentTime;

	private Date latestShipmentTime;

	private Float weight;

	private Integer numPackages;

	private String customerReference;

	private String callbackURL;

	public XmlQuoteRequest(final QuoteRequest request) {
		this.id = request.getId();
		this.earliestShipmentTime = request.getEarliestShipmentTime();
		this.latestShipmentTime = request.getLatestShipmentTime();
		this.weight = request.getWeight();
		this.numPackages = request.getNumPackages();
		this.customerReference = request.getCustomerReference();
		this.callbackURL = request.getCallbackURL();
	}

	@SuppressWarnings("unused")
	private XmlQuoteRequest() {
		// for JAXB
	}

	public Long getId() {
		return id;
	}

	public Date getEarliestShipmentTime() {
		return earliestShipmentTime;
	}

	public Date getLatestShipmentTime() {
		return latestShipmentTime;
	}

	public float getWeight() {
		return weight;
	}

	public int getNumPackages() {
		return numPackages;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

}
