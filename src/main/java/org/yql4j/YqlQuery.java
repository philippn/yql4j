/**
 * 
 */
package org.yql4j;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Philipp
 *
 */
public final class YqlQuery {

	private final Logger logger = LoggerFactory.getLogger(YqlQuery.class);

	public static final String QUERY_URL_PUBLIC = 
			"http://query.yahooapis.com/v1/public/yql";
	public static final String QUERY_URL_OAUTH = 
			"http://query.yahooapis.com/v1/yql";

	public static final String ENV_OPEN_DATA_TABLES = 
			"store://datatables.org/alltableswithkeys";

	private String apiKey;
	private String consumerKey;
	private String consumerSecret;
	private boolean diagnostics;
	private String environmentFile;
	private ResultFormat format;
	private String queryString;

	/**
	 * Constructor.
	 * @param queryString the query to execute
	 */
	public YqlQuery(String queryString) {
		this(queryString, false);
	}

	/**
	 * Constructor.
	 * @param queryString the query to execute
	 * @param includeOpenDataTables whether to include Open Data Tables
	 */
	public YqlQuery(String queryString, boolean includeOpenDataTables) {
		checkNotNull(queryString);
		this.queryString = queryString;
		if (includeOpenDataTables) {
			this.environmentFile = ENV_OPEN_DATA_TABLES;
		}
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return the diagnostics
	 */
	public boolean isDiagnostics() {
		return diagnostics;
	}

	/**
	 * @param diagnostics the diagnostics to set
	 */
	public void setDiagnostics(boolean diagnostics) {
		this.diagnostics = diagnostics;
	}

	/**
	 * @return the environmentFile
	 */
	public String getEnvironmentFile() {
		return environmentFile;
	}

	/**
	 * @param environmentFile the environmentFile to set
	 */
	public void setEnvironmentFile(String environmentFile) {
		this.environmentFile = environmentFile;
	}

	/**
	 * @return the format
	 */
	public ResultFormat getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(ResultFormat format) {
		this.format = format;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * Returns the URI for this query.
	 * @return the uri
	 */
	public URI buildUri() {
		try {
			boolean oAuth = (getConsumerKey() != null) && 
					(getConsumerSecret() != null);
			String baseUri = oAuth ? QUERY_URL_OAUTH : QUERY_URL_PUBLIC;
			URIBuilder builder = new URIBuilder(baseUri);
			
			// Set parameters
			builder.addParameter("diagnostics", Boolean.toString(isDiagnostics()));
			if (getEnvironmentFile() != null) {
				builder.addParameter("env", getEnvironmentFile());
			}
			if (getFormat() != null) {
				builder.addParameter("format", getFormat().name().toLowerCase());
			}
			builder.addParameter("q", getQueryString());
			
			return builder.build();
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YqlQuery [" + buildUri() + "]";
	}
}
