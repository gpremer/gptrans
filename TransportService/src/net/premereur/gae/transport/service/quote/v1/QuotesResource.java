package net.premereur.gae.transport.service.quote.v1;

import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.service.quote.v1.serialisation.XmlQuotes;

import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.google.inject.Inject;

public class QuotesResource extends QuoteRequestResource {

	@Inject
	public QuotesResource(QuoteRequestRepository repository) {
		super(repository);
	}

	@Get("xml")
	public Representation getQuotes(Representation item) {
		return new JaxbRepresentation<XmlQuotes>(new XmlQuotes(getQuoteRequest().getQuotes()));
	}
}
