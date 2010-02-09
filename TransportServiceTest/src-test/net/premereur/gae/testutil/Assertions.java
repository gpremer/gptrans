package net.premereur.gae.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public final class Assertions {

	public static void assertXmlTimeEquals(String time1, String time2) {
		assertEquals(DatatypeConverter.parseDateTime(time1), DatatypeConverter.parseDateTime(time2));
	}

	public static void assertTimeEqual(Date target, Date actual, long allowedDifference) {
		assertTrue(Math.abs(target.getTime() - actual.getTime()) <= allowedDifference);
	}
}
