package net.premereur.gae.transport.domain;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public static enum Reason {
		QUOTE_NOT_VALID, QUOTE_EXPIRED, QUOTE_ALREADY_TAKEN, NO_SUCH_DEMAND
	}

	private Reason reason;

	public BusinessException(Reason reason, String message) {
		super(message);
		this.reason = reason;
	}

	public Reason getReason() {
		return this.reason;
	}

}
