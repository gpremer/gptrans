package net.premereur.gae.transport.service.task;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;
import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.domain.Quotes;
import net.premereur.gae.transport.service.common.ResourceBase;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import com.google.inject.Inject;

public class MakeQuoteResource extends ResourceBase {

	@Inject
	private Logger logger;

	private final QuoteRequestRepository repository;

	@Inject
	public MakeQuoteResource(QuoteRequestRepository repository) {
		super();
		this.repository = repository;
	}

	public QuoteRequestRepository getRepository() {
		return repository;
	}

	@Post
	public Representation computeOffers(Representation entry) {
		logger.entering(MakeQuoteResource.class.getName(), "computeOffers", entry);
		Long quoteRequestId = getRequestId();
		QuoteRequest quoteRequest;
		try {
			quoteRequest = getRepository().findByKey(quoteRequestId);
		} catch (Exception e) {
			logger.log(Level.INFO, "Couldn't retrieve quoteRequest with key " + quoteRequestId, e);
			setExisting(false);
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}
		return new JaxbRepresentation<Quotes>(quoteRequest.getQuotes());
	}
}
