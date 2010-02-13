package net.premereur.gae;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Base class for unit tests that use Google Appengine.
 * 
 * @author gpremer
 * 
 */
public abstract class LocalAppEngineServiceTestCase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
	public void setUp() {
		helper.setUp();
	}

    @After
	public void tearDown() {
		helper.tearDown();
	}

	@BeforeClass
	public static void allowMultipleEntityManagerFactpories() {
		System.setProperty("appengine.orm.disable.duplicate.emf.exception", "1");
	}

}