package net.premereur.gae.transport.service.v1.resource;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;
import net.premereur.gae.transport.domain.QuoteRequestRepository;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

import com.google.inject.Inject;

public class QuoteRequestResource extends QuoteRequestResourceBase {

	@Inject
	private Logger logger;

	/** The underlying QuoteRequest object. */
	private QuoteRequest quoteRequest;

	private static final QuoteConverter converter = new QuoteConverter();

	@Inject
	public QuoteRequestResource(QuoteRequestRepository repository) {
		super(repository);
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
		try {
			DomRepresentation representation = new DomRepresentation(MediaType.TEXT_XML);
			// Generate a DOM document representing the quoteRequest.
			Document d = representation.getDocument();
			d.appendChild(converter.createDOMElement(quoteRequest, d));
			d.normalizeDocument();

			return representation;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't create XML representation", e);
			return null;
		}
	}
}
