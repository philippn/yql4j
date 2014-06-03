/**
 * 
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
