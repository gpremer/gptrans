package net.premereur.gae.testutil;

import java.util.Date;

import net.premereur.gae.transport.domain.ClockService;

import org.joda.time.DateTime;

public class FixedTimeClockService implements ClockService {

	private Date date;

	public FixedTimeClockService(Date date) {
		super();
		this.date = date;
	}

	public FixedTimeClockService(DateTime time) {
		super();
		this.date = time.toDate();
	}

	@Override
	public Date currentTime() {
		return date;
	}

}
