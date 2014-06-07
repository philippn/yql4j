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

/**
 * @author Philipp
 *
 */
public class YqlQueryBuilder {

	private YqlQuery query;

	private YqlQueryBuilder(YqlQuery query) {
		this.query = query;
	}

	public static YqlQueryBuilder fromQueryString(String queryString) {
		return new YqlQueryBuilder(new YqlQuery(queryString));
	}

	public static YqlQueryBuilder fromQueryAlias(String aliasPrefix,
			String aliasName) {
		return new YqlQueryBuilder(new YqlQuery(aliasPrefix, aliasName));
	}

	public YqlQueryBuilder withConsumerKeyAndSecret(String consumerKey,
			String consumerSecret) {
		query.setConsumerKey(consumerKey);
		query.setConsumerSecret(consumerSecret);
		return this;
	}

	public YqlQueryBuilder withDiagnosticsDisabled() {
		query.setDiagnostics(false);
		return this;
	}

	public YqlQueryBuilder withDiagnosticsEnabled() {
		query.setDiagnostics(true);
		return this;
	}

	public YqlQueryBuilder withFormat(ResultFormat format) {
		query.setFormat(format);
		return this;
	}

	public YqlQueryBuilder withEnvironmentFile(String environmentFile) {
		query.addEnvironmentFile(environmentFile);
		return this;
	}

	public YqlQueryBuilder withCommunityOpenDataTables() {
		query.useCommunityOpenDataTables();
		return this;
	}

	public YqlQueryBuilder withVariable(String name, String value) {
		query.addVariable(name, value);
		return this;
	}

	public YqlQuery build() {
		return query;
	}
}
