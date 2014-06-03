/**
 * 
 */
package org.yql4j;

import org.apache.http.impl.client.CloseableHttpClient;
import org.yql4j.impl.HttpComponentsYqlClient;

/**
 * Factory methods for {@link YqlClient} instances.
 * 
 * @author Philipp
 */
public class YqlClients {

	private YqlClients() {
		// Hide constructor
	}

	/**
	 * Returns a newly constructed {@link YqlClient} instance with default
	 * configuration.
	 * 
	 * @return the client
	 */
	public static YqlClient createDefault() {
		return new HttpComponentsYqlClient();
	}

	/**
	 * Returns a newly constructed {@link YqlClient} instance backed by the
	 * given preconfigured {@link CloseableHttpClient}.
	 * 
	 * @return the client
	 */
	public static YqlClient createWithHttpClient(CloseableHttpClient httpClient) {
		return new HttpComponentsYqlClient(httpClient);
	}
}
