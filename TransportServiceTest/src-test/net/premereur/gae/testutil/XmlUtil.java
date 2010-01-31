package net.premereur.gae.testutil;

import java.io.StringWriter;

import javax.xml.bind.JAXB;

public final class XmlUtil {

	public static void dumpSerialisation(Object o) {
		StringWriter sw = new StringWriter();
		JAXB.marshal(o, sw);
		System.out.println(sw);
	}


}
