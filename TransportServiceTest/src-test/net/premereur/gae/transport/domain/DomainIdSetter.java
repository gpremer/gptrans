package net.premereur.gae.transport.domain;

/**
 * This class provides access to otherwise hidden functionality. Reflective
 * access is not possible because of Datanucleus object enhancement.
 * 
 * @author gpremer
 */
public final class DomainIdSetter {

	public static void setId(QuoteRequest qr, Long value) {
		qr.setId(value);
	}
}
