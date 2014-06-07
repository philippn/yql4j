/*
 * Copyright (C) 2014 Philipp Nanz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package org.yql4j;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.yql4j.YqlQuery.ENV_COMMUNITY_OPEN_DATA_TABLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder for {@link YqlQuery} instances.
 */
public class YqlQueryBuilder {

	private String consumerKey;
	private String consumerSecret;
	private Boolean diagnostics;
	private ResultFormat format;
	private List<String> environmentFiles = new ArrayList<>();
	private String queryString;
	private String aliasPrefix;
	private String aliasName;
	private Map<String, String> variables = new HashMap<>();

	private YqlQueryBuilder(String queryString) {
		checkNotNull(queryString);
		this.queryString = queryString;
	}

	private YqlQueryBuilder(String aliasPrefix, String aliasName) {
		checkNotNull(aliasPrefix);
		checkNotNull(aliasName);
		this.aliasPrefix = aliasPrefix;
		this.aliasName = aliasName;
	}

	/**
	 * Constructs a new {@link YqlQueryBuilder} instance from a YQL query
	 * string.
	 * 
	 * @param queryString
	 *            the query string
	 * @return the builder
	 */
	public static YqlQueryBuilder fromQueryString(String queryString) {
		return new YqlQueryBuilder(queryString);
	}

	/**
	 * Constructs a new {@link YqlQueryBuilder} instance from a YQL query alias.
	 * 
	 * @param aliasPrefix
	 *            the alias prefix
	 * @param aliasName
	 *            the alias name
	 * @return the builder
	 */
	public static YqlQueryBuilder fromQueryAlias(String aliasPrefix,
			String aliasName) {
		return new YqlQueryBuilder(aliasPrefix, aliasName);
	}

	/**
	 * Assigns consumer key and consumer secret for OAuth-authentication.
	 * 
	 * @param consumerKey
	 *            the consumer key
	 * @param consumerSecret
	 *            the consumer secret
	 * @return the builder
	 */
	public YqlQueryBuilder withConsumerKeyAndSecret(String consumerKey,
			String consumerSecret) {
		checkNotNull(consumerKey);
		checkNotNull(consumerSecret);
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		return this;
	}

	/**
	 * Disables diagnostics.
	 * 
	 * @return the builder
	 */
	public YqlQueryBuilder withDiagnosticsDisabled() {
		this.diagnostics = Boolean.FALSE;
		return this;
	}

	/**
	 * Enables diagnostics.
	 * 
	 * @return the builder
	 */
	public YqlQueryBuilder withDiagnosticsEnabled() {
		this.diagnostics = Boolean.TRUE;
		return this;
	}

	/**
	 * Sets the result format to request.
	 * 
	 * @param format
	 *            the result format
	 * @return the builder
	 */
	public YqlQueryBuilder withFormat(ResultFormat format) {
		this.format = format;
		return this;
	}

	/**
	 * Appends an environment file to the query.
	 * 
	 * @param environmentFile
	 *            the environment file
	 * @return the builder
	 */
	public YqlQueryBuilder withEnvironmentFile(String environmentFile) {
		checkNotNull(environmentFile);
		environmentFiles.add(environmentFile);
		return this;
	}

	/**
	 * Appends the environment file for community Open Data tables to the query.
	 * 
	 * @return the builder
	 */
	public YqlQueryBuilder withCommunityOpenDataTables() {
		environmentFiles.add(ENV_COMMUNITY_OPEN_DATA_TABLES);
		return this;
	}

	/**
	 * Assigns a variable for substitution.
	 * 
	 * @param name
	 *            the variable name
	 * @param value
	 *            the variable value
	 * @return the builder
	 */
	public YqlQueryBuilder withVariable(String name, String value) {
		checkNotNull(name);
		checkNotNull(value);
		variables.put(name, value);
		return this;
	}

	/**
	 * Returns a newly constructed {@link YqlQuery) instance.
	 * @return the query
	 */
	public YqlQuery build() {
		YqlQuery query = queryString != null ? 
				new YqlQuery(queryString) : new YqlQuery(aliasPrefix, aliasName);

		if ((consumerKey != null) && (consumerSecret != null)) {
			query.setConsumerKey(consumerKey);
			query.setConsumerSecret(consumerSecret);
		}
		if (diagnostics != null) {
			query.setDiagnostics(diagnostics.booleanValue());
		}
		if (format != null) {
			query.setFormat(format);
		}
		for (String env : environmentFiles) {
			query.addEnvironmentFile(env);
		}
		for (Entry<String, String> var : variables.entrySet()) {
			query.addVariable(var.getKey(), var.getValue());
		}

		return query;
	}
}
