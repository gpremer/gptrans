package net.premereur.gae.transport.service.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;

public class ResourceBase extends ServerResource {

	@Inject
	Logger logger;

	/**
	 * Generate an XML representation of an error response.
	 * 
	 * @param errorMessagev
	 *            the error message.
	 * @param errorCode
	 *            the error code.
	 */
	protected Representation generateErrorRepresentation(String errorMessage, String errorCode) {
		DomRepresentation result = null;
		// This is an error
		// Generate the output representation
		try {
			result = new DomRepresentation(MediaType.TEXT_XML);
			// Generate a DOM document representing the list of
			// items.
			Document d = result.getDocument();
			Element eltError = d.createElement("error");
			Element eltCode = d.createElement("code");
			eltCode.appendChild(d.createTextNode(errorCode));
			eltError.appendChild(eltCode);

			Element eltMessage = d.createElement("message");
			eltMessage.appendChild(d.createTextNode(errorMessage));
			eltError.appendChild(eltMessage);
		} catch (IOException e) {
			// This should be possible
			logger.log(Level.SEVERE, "Couldn't create error message for (" + errorMessage + ", " + errorCode + ")", e);
		}

		return result;
	}

	protected long getRequestId() {
		try {
			return Long.parseLong((String) getRequest().getAttributes().get("requestId"));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	@Override
	protected void doRelease() throws ResourceException {
		super.doRelease();
	}

}