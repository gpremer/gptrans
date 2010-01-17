package net.premereur.gae.transport.service.v1.resource;

import java.io.IOException;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;
import net.premereur.gae.transport.domain.QuoteRequestRepository;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;

public class QuoteRequestsResource extends QuoteRequestResourceBase {

	@Inject
	private Logger LOG;

	private static final QuoteConverter converter = new QuoteConverter();

	@Inject
	public QuoteRequestsResource(QuoteRequestRepository repository) {
		super(repository);
	}

	/**
	 * Handle POST requests: create a new item.
	 */
	@Post
	public Representation create(Representation entity) {
		try {
			QuoteRequest quoteRequest = converter.fromRepresentation(entity);
			getRepository().store(quoteRequest);
			LOG.info("Quote request created with id " + quoteRequest.getId());
			setStatus(Status.SUCCESS_CREATED);
			Representation rep = new StringRepresentation("Item created", MediaType.TEXT_PLAIN);
			// Indicates where the new resource is located.
			rep.setIdentifier(getRequest().getResourceRef().getIdentifier() + "/" + quoteRequest.getId());
			return rep;
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return generateErrorRepresentation("quote could not be saved ", "1");
		}
	}

	/**
	 * Returns a listing of all registered items.
	 */
	@SuppressWarnings("unchecked")
	@Get("xml")
	public Representation toXml() {
		LOG.info("listing all requests");
		// Generate the right representation according to its media type.
		try {
			DomRepresentation representation = new DomRepresentation(MediaType.TEXT_XML);

			// Generate a DOM document representing the list of quote requests.
			Document d = representation.getDocument();
			Element r = d.createElement("quoteRequests");
			d.appendChild(r);
			for (QuoteRequest quoteRequest : getRepository().findAll()) {
				r.appendChild(converter.createDOMElement(quoteRequest, d));
			}

			// Returns the XML representation of this document.
			return representation;
		} catch (IOException e) {
			LOG.severe("Problem listing all quote requests");
			System.err.println(e);
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return generateErrorRepresentation("Quote list could not be retrieved", "2");
		}

	}
}
