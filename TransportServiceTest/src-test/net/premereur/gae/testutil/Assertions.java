package net.premereur.gae.testutil;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;
public final class Assertions {

	public static void assertXmlTimeEquals(String time1, String time2) {
		assertEquals(DatatypeConverter.parseDateTime(time1), DatatypeConverter.parseDateTime(time2));
	}
}
