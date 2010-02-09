package net.premereur.gae.transport.domain;

import java.util.Date;

public class SystemTimeClockService implements ClockService {

	@Override
	public Date currentTime() {		
		return new Date();
	}

}
