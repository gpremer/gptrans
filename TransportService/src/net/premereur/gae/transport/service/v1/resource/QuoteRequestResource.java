package net.premereur.gae.transport.service.v1.resource;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;
import net.premereur.gae.transport.domain.QuoteRequestRepository;

import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import com.google.inject.Inject;

public class QuoteRequestResource extends QuoteRequestResourceBase {

	@Inject
	private Logger logger;

	/** The underlying QuoteRequest object. */
	private QuoteRequest quoteRequest;

	@Inject
	public QuoteRequestResource(QuoteRequestRepository repository) {
		super(repository);
	}

	protected QuoteRequest getQuoteRequest() {
		return quoteRequest;
	}
	
	@Override
	protected void doInit() throws ResourceException {
		Long quoteRequestId = getRequestId();
		try {
			quoteRequest = getRepository().findByKey(quoteRequestId);
		} catch (Exception e) {
			logger.log(Level.INFO, "Couldn't retrieve quoteRequest with key " + quoteRequestId, e);
			this.quoteRequest = null;
		}
		setExisting(this.quoteRequest != null);
	}

	/**
	 * Handle DELETE requests.
	 */
	@Delete
	public void removeQuoteRequest() {
		// quoteRequests can never be revoked
		setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
	}

	/**
	 * Handle PUT requests.
	 * 
	 * @throws IOException
	 */
	@Put
	public void storeQuoteRequest(Representation entity) throws IOException {
		// quoteRequests cannot be modified
		setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
	}

	@Get("xml")
	public Representation toXml() {
		return new JaxbRepresentation<QuoteRequest>(quoteRequest);
	}
}
