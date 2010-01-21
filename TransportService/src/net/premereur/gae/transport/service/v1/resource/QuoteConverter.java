package net.premereur.gae.transport.service.v1.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import net.premereur.gae.transport.domain.QuoteRequest;

import org.restlet.data.Form;
import org.restlet.representation.Representation;

public final class QuoteConverter {

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
