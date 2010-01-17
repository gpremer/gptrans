package net.premereur.gae;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

/**
 * Base class for unit tests that use Google Appengine.
 * 
 * @author gpremer
 * 
 */
public abstract class LocalAppEngineServiceTestCase {

	@Before
	public void setUpAppEngine() throws Exception {
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")) {
		});
		dontStoreInDatastore();
	}

	public void dontStoreInDatastore() throws Exception {
		ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
		proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
	}


	@After
	public void tearDownAppEngine() throws Exception {
		clearProfiles();
		// not strictly necessary to null these out but there's no harm either
		ApiProxy.setDelegate(null);
		ApiProxy.setEnvironmentForCurrentThread(null);
	}

	public void clearProfiles() throws Exception {
		ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
		LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService(LocalDatastoreService.PACKAGE);
		datastoreService.clearProfiles();
	}
}