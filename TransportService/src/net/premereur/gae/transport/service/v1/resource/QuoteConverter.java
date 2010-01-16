package net.premereur.gae.transport.service.v1.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import net.premereur.gae.transport.domain.QuoteRequest;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class QuoteConverter {

	public Element createDOMElement(QuoteRequest quoteRequest, Document d) {
		Element eltQuoteRequest = d.createElement("quoteRequest");

		Element eltId = d.createElement("id");
		eltId.appendChild(d.createTextNode(quoteRequest.getId().toString()));
		eltQuoteRequest.appendChild(eltId);

		Element eltShipmentDate = d.createElement("shipmentDate");
		eltShipmentDate.appendChild(d.createTextNode(formatDate(quoteRequest.getShipmentDate())));
		eltQuoteRequest.appendChild(eltShipmentDate);

		Element eltWeight = d.createElement("weight");
		eltWeight.appendChild(d.createTextNode(new Float(quoteRequest.getWeight()).toString()));
		eltQuoteRequest.appendChild(eltWeight);
		return eltQuoteRequest;
	}

	public QuoteRequest fromRepresentation(Representation entity) {
		Form form = new Form(entity);
		Date qShipmentDate = parseDate(form.getFirstValue("shipmentDate"));
		float qWeight = Float.parseFloat(form.getFirstValue("weight"));

		return new QuoteRequest(qShipmentDate, qWeight);
	}

	protected Date parseDate(String dateString) {
		return DatatypeConverter.parseDateTime(dateString).getTime();
	}

	protected String formatDate(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTime(date);
		return DatatypeConverter.printDate(calendar);
	}
}
