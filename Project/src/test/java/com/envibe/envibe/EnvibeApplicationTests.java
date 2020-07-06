package com.envibe.envibe;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Base test class that sets up environment and provides helper functions and mock HTTP request proxies.
 *
 * @author ARMmaster17
 */
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnvibeApplicationTests {

	/**
	 * Injected port of active Tomcat server (randomized in test environment).
	 */
	@LocalServerPort
	int port;

	/**
	 * Injected mock interface for performing HTTP requests against URI endpoints managed by controllers.
	 */
	@Autowired
	TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

	/**
	 * Handles setup of environment before any tests in the suite are run. Only runs once at the beginning of suite.
	 */
	@Before
	public void setup() {
		// Add pre-test setup here.

	}

	/**
	 * Smoke test that confirms that the application starts up with no runtime exceptions thrown.
	 * Note that Spring uses lazy dependency loading, so this will almost never fail unless you mess with core configuration or if there is a missing service dependency in the environment.
	 */
	@Test
	void contextLoads() {

	}

	/**
	 * Helper function that returns the FQDN of the active server with a relative URI endpoint path with any attached GET parameters. Performs no URI validation.
	 * @param sub_path Relative URI of desired endpoint.
	 * @return FQDN with desired endpoint path attached.
	 * @see EnvibeApplicationTests#port
	 */
	protected String getURI(String sub_path) {
		return "http://localhost:" + port + sub_path;
	}
}
