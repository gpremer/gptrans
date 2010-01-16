package net.premereur.gae.transport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.restlet.engine.util.DateUtils;


public class ConvertTest {

	@Test
	public void shouldConvertStringToDate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD", Locale.GERMAN);
		List<String> f = DateUtils.FORMAT_RFC_1123;
		System.out.println(DateUtils.format(new Date(), f.get(0)));
		//System.out.println(format.parse("2010-02-03"));
	}
	
}
