/**
 * 
 */
package org.yql4j;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	public static final String ENV_COMMUNITY_OPEN_DATA_TABLES = 
			"store://datatables.org/alltableswithkeys";

	private String consumerKey;
	private String consumerSecret;
	private boolean diagnostics;
	private ResultFormat format;
	private List<String> environmentFiles = new ArrayList<>();
	private String queryString;
	private String aliasPrefix;
	private String aliasName;
	private Map<String, String> variables = new HashMap<>();

	private URI compiledUri;

	/**
	 * Constructor for calling the specified query.
	 * @param queryString the query to execute
	 */
	public YqlQuery(String queryString) {
		checkNotNull(queryString);
		this.queryString = queryString;
	}

	/**
	 * Constructor for calling the specified YQL query alias.
	 * @param aliasPrefix the prefix of the query alias
	 * @param aliasName the name of query alias
	 */
	public YqlQuery(String aliasPrefix, String aliasName) {
		checkNotNull(aliasPrefix);
		checkNotNull(aliasName);
		this.aliasPrefix = aliasPrefix;
		this.aliasName = aliasName;
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
		clearUri();
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
		clearUri();
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
		clearUri();
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
		clearUri();
	}

	/**
	 * @return the environmentFiles
	 */
	public List<String> getEnvironmentFiles() {
		return unmodifiableList(environmentFiles);
	}

	/**
	 * @param environmentFile the environmentFile to add
	 */
	public void addEnvironmentFile(String environmentFile) {
		checkNotNull(environmentFile);
		environmentFiles.add(environmentFile);
		clearUri();
	}

	/**
	 * @param environmentFile the environmentFile to remove
	 */
	public void removeEnvironmentFile(String environmentFile) {
		checkNotNull(environmentFile);
		environmentFiles.remove(environmentFile);
		clearUri();
	}

	/**
	 * Adds the environment file for community Open Data tables.
	 */
	public void useCommunityOpenDataTables() {
		addEnvironmentFile(ENV_COMMUNITY_OPEN_DATA_TABLES);
		clearUri();
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @return the aliasPrefix
	 */
	public String getAliasPrefix() {
		return aliasPrefix;
	}

	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @return a set containing the names of all the variables defined
	 */
	public Set<String> getVariableNames() {
		return unmodifiableSet(variables.keySet());
	}

	/**
	 * @param name the name of the variable
	 * @return the value or <code>null</code> if undefined
	 */
	public String getVariableValue(String name) {
		checkNotNull(name);
		return variables.get(name);
	}

	/**
	 * @param name the name of the variable to add
	 * @param value the value
	 */
	public void addVariable(String name, String value) {
		checkNotNull(name);
		checkNotNull(value);
		variables.put(name, value);
		clearUri();
	}

	/**
	 * @param name the name of the variable to remove
	 */
	public void removeVariable(String name) {
		checkNotNull(name);
		variables.remove(name);
		clearUri();
	}

	/**
	 * Returns the URI for this query.
	 * @return the URI
	 */
	public URI toUri() {
		if (compiledUri == null) {
			compiledUri = compileUri();
		}
		return compiledUri;
	}

	/**
	 * Returns a newly built URI for this query.
	 * @return the URI
	 */
	private URI compileUri() {
		try {
			boolean oAuth = (consumerKey != null) && (consumerSecret != null);
			boolean aliasQuery = queryString == null;
			
			String baseUri = oAuth ? QUERY_URL_OAUTH : QUERY_URL_PUBLIC;
			if (aliasQuery) {
				baseUri += "/" + aliasPrefix + "/" + aliasName;
			}
			URIBuilder builder = new URIBuilder(baseUri);
			
			// Set parameters
			builder.addParameter("diagnostics", Boolean.toString(diagnostics));
			for (String env : environmentFiles) {
				builder.addParameter("env", env);
			}
			if (format != null) {
				builder.addParameter("format", format.name().toLowerCase());
			}
			if (!aliasQuery) {
				builder.addParameter("q", queryString);
			}
			for (Entry<String, String> varDef : variables.entrySet()) {
				builder.addParameter(varDef.getKey(), varDef.getValue());
			}
			
			return builder.build();
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Clears the buffered URI.
	 */
	private void clearUri() {
		compiledUri = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YqlQuery [" + toUri() + "]";
	}
}
