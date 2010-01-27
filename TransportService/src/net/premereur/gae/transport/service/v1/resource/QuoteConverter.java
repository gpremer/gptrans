package net.premereur.gae.transport.service.v1.resource;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import net.premereur.gae.transport.domain.QuoteRequest;

import org.restlet.data.Form;
import org.restlet.representation.Representation;

public final class QuoteConverter {

	public QuoteRequest fromRepresentation(Representation entity) {
		final Form form = new Form(entity);
		return new QuoteRequest(getFormDate(form, "earliestShipmentTime"), getFormDate(form, "latestShipmentTime"), getFormFloat(form, "weight"), getFormInt(
				form, "numPackages"), getFormString(form, "shipperReference"), getFormString(form, "callbackURL"));
	}

	public String getFormString(Form form, String field) {
		return form.getFirstValue(field);
	}

	public int getFormInt(Form form, String field) {
		return Integer.parseInt(form.getFirstValue(field));
	}

	public float getFormFloat(Form form, String field) {
		return Float.parseFloat(form.getFirstValue(field));
	}

	public Date getFormDate(Form form, String field) {
		return parseDate(form.getFirstValue(field));
	}

	public Date parseDate(String dateString) {
		return DatatypeConverter.parseDateTime(dateString).getTime();
	}

}
