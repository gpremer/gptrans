package net.premereur.gae.transport.domain;

import static net.premereur.gae.testutil.Assertions.assertTimeEqual;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class SystemTimeClockServiceTest {

	private ClockService clock;
	
	@Before
	public void windupClock() {
		clock= new SystemTimeClockService();
	}
	
	@Test
	public void shouldReturnCurrentTime() throws Exception {
		assertTimeEqual(new Date(), clock.currentTime(), 1);
	}
	
}
