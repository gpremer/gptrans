package net.premereur.gae.transport.service.v1.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.premereur.gae.transport.domain.QuoteRequest;
import net.premereur.gae.transport.domain.QuoteRequestRepository;
import net.premereur.gae.transport.domain.ScheduleService;
import net.premereur.gae.transport.service.v1.resource.serialisation.XmlQuoteRequests;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.jaxb.JaxbRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.google.inject.Inject;

public class QuoteRequestsResource extends QuoteRequestResourceBase {

	// TODO inject this as well
	private static final QuoteConverter converter = new QuoteConverter();

	@Inject
	private Logger LOG;

	private final ScheduleService scheduleService;

	@Inject
	public QuoteRequestsResource(QuoteRequestRepository repository, final ScheduleService scheduleService) {
		super(repository);
		this.scheduleService = scheduleService;
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
			getScheduleService().scheduleQuoteComputation(quoteRequest);
			return rep;
		} catch (Exception e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			LOG.log(Level.WARNING, "Invalid input", e);
			return generateErrorRepresentation("quote could not be saved ", "1");
		}
	}

	/**
	 * Returns a listing of all registered items.
	 */
	@Get("xml")
	public Representation toXml() {
		LOG.info("listing all requests");
		// Generate the right representation according to its media type.
		return new JaxbRepresentation<XmlQuoteRequests>(new XmlQuoteRequests(getRepository().findAll()));
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

}
