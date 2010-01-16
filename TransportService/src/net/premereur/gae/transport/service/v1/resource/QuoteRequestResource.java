package net.premereur.gae.transport.service.v1.resource;

import java.io.IOException;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;


public class QuoteRequestResource extends BaseResource {

	private static final Logger LOG = Logger.getLogger(QuoteRequestResource.class.getName());

	/** The underlying QuoteRequest object. */
	private QuoteRequest quoteRequest;

	private static final QuoteConverter converter = new QuoteConverter();

	@Override
	protected void doInit() throws ResourceException {
		// Get the "requestID" attribute value taken from the URI template
		// ../quoteRequests/{requestId}.
		Long quoteRequestId = getRequestId();
		try {
			quoteRequest = getEntityManager().find(QuoteRequest.class, quoteRequestId);
		} catch (Exception e) {
			LOG.fine("Looking up quote request for id " + quoteRequestId + "failed: " + e.getMessage());
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
			LOG.warning(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
