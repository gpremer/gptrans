package net.premereur.gae.transport.service.servlet;

/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.engine.http.HttpRequest;
import org.restlet.engine.http.HttpResponse;
import org.restlet.engine.http.HttpServerAdapter;
import org.restlet.ext.servlet.internal.ServletCall;

/**
 * HTTP adapter from Servlet calls to Restlet calls. This class can be used in
 * any Servlet, just create a new instance and override the service() method in
 * your Servlet to delegate all those calls to this class's service() method.
 * Remember to set the target Restlet, for example using a Restlet Router
 * instance. You can get the Restlet context directly on instances of this
 * class, it will be based on the parent Servlet's context for logging purpose.<br>
 * <br>
 * This class is especially useful when directly integrating Restlets with
 * Spring managed Web applications.<br>
 * I have adapted this class for use with Google AppEngine. The original
 * implementation was using a custom subclass of jul.logger which is not allowed
 * on AppEngine.
 * 
 * @author Jerome Louvel
 * @author gpremer
 */
public class GAEServletAdapter extends HttpServerAdapter {
	/** The target Restlet. */
	private volatile Restlet target;

	/**
	 * Constructor. Remember to manually set the "target" property before
	 * invoking the service() method.
	 * 
	 * @param context
	 *            The Servlet context.
	 */
	public GAEServletAdapter(ServletContext context) {
		this(context, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            The Servlet context.
	 * @param target
	 *            The target Restlet.
	 */
	public GAEServletAdapter(ServletContext context, Restlet target) {
		super(new Context("org.restlet"));
		this.target = target;
	}

	/**
	 * Returns the base reference of new Restlet requests.
	 * 
	 * @param request
	 *            The Servlet request.
	 * @return The base reference of new Restlet requests.
	 */
	public Reference getBaseRef(HttpServletRequest request) {
		Reference result = null;
		final String basePath = request.getContextPath() + request.getServletPath();
		final String baseUri = request.getRequestURL().toString();
		// Path starts at first slash after scheme://
		final int pathStart = baseUri.indexOf("/", request.getScheme().length() + 3);
		if (basePath.length() == 0) {
			// basePath is empty in case the webapp is mounted on root context
			if (pathStart != -1) {
				result = new Reference(baseUri.substring(0, pathStart));
			} else {
				result = new Reference(baseUri);
			}
		} else {
			if (pathStart != -1) {
				final int baseIndex = baseUri.indexOf(basePath, pathStart);
				if (baseIndex != -1) {
					result = new Reference(baseUri.substring(0, baseIndex + basePath.length()));
				}
			}
		}

		return result;
	}

	/**
	 * Returns the root reference of new Restlet requests. By default it returns
	 * the result of getBaseRef().
	 * 
	 * @param request
	 *            The Servlet request.
	 * @return The root reference of new Restlet requests.
	 */
	public Reference getRootRef(HttpServletRequest request) {
		return getBaseRef(request);
	}

	/**
	 * Returns the target Restlet.
	 * 
	 * @return The target Restlet.
	 */
	public Restlet getTarget() {
		return this.target;
	}

	/**
	 * Services a HTTP Servlet request as a Restlet request handled by the
	 * "target" Restlet.
	 * 
	 * @param request
	 *            The HTTP Servlet request.
	 * @param response
	 *            The HTTP Servlet response.
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (getTarget() != null) {
			// Set the current context
			Context.setCurrent(getContext());

			// Convert the Servlet call to a Restlet call
			ServletCall servletCall = new ServletCall(request.getLocalAddr(), request.getLocalPort(), request, response);
			HttpRequest httpRequest = toRequest(servletCall);
			HttpResponse httpResponse = new HttpResponse(servletCall, httpRequest);

			// Adjust the relative reference
			httpRequest.getResourceRef().setBaseRef(getBaseRef(request));

			// Adjust the root reference
			httpRequest.setRootRef(getRootRef(request));

			// Handle the request and commit the response
			getTarget().handle(httpRequest, httpResponse);
			commit(httpResponse);
		} else {
			getLogger().warning("Unable to find the Restlet target");
		}
	}

	/**
	 * Sets the target Restlet.
	 * 
	 * @param target
	 *            The target Restlet.
	 */
	public void setTarget(Restlet target) {
		this.target = target;
	}

	/**
	 * Converts a low-level Servlet call into a high-level Restlet request. In
	 * addition to the parent {@link HttpServerAdapter}, it also copies the
	 * Servlet's request attributes into the Restlet's request attributes map.
	 * 
	 * @param servletCall
	 *            The low-level Servlet call.
	 * @return A new high-level uniform request.
	 */
	@SuppressWarnings("unchecked")
	public HttpRequest toRequest(ServletCall servletCall) {
		final HttpRequest result = super.toRequest(servletCall);

		// Copy all Servlet's request attributes
		String attributeName;
		for (final Enumeration<String> namesEnum = servletCall.getRequest().getAttributeNames(); namesEnum.hasMoreElements();) {
			attributeName = namesEnum.nextElement();
			result.getAttributes().put(attributeName, servletCall.getRequest().getAttribute(attributeName));
		}

		return result;
	}

}
