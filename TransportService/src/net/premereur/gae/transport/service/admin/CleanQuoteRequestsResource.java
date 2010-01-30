package net.premereur.gae.transport.service.admin;

import net.premereur.gae.transport.domain.QuoteRequestRepository;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

public class CleanQuoteRequestsResource extends ServerResource {

	private final QuoteRequestRepository repository;

	@Inject
	public CleanQuoteRequestsResource(QuoteRequestRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Post
	public Representation cleanQuoteRequests() {
		repository.removeAll();
		Representation rep = new StringRepresentation("All quote requests are removed", MediaType.TEXT_PLAIN);
		return rep;
	}
}
