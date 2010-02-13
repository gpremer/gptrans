package net.premereur.gae;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;

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

	static public Injector initGuice(Module... modules) {
		List<Module> moduleList = new ArrayList<Module>(Arrays.asList(modules));
		moduleList.add(new BaseTestModule());
		moduleList.add(PersistenceService.usingJpa().across(UnitOfWork.REQUEST).buildModule());
		Injector injector = Guice.createInjector(moduleList);
		return injector;
	}

}