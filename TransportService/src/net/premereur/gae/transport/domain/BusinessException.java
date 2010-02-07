package net.premereur.gae.transport.domain;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public static enum REASON {
		QUOTE_ALREADY_USED
	}
}
