/**
 * 
 */
package org.yql4j.types;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Philipp
 *
 */
@JsonRootName(value="query")
public class QueryResultType<CT extends ResultCollectionType<?>> {

	private int count;
	private Date created;
	private String lang;
	private DiagnosticsType diagnostics;
	private CT results;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the diagnostics
	 */
	public DiagnosticsType getDiagnostics() {
		return diagnostics;
	}

	/**
	 * @param diagnostics the diagnostics to set
	 */
	public void setDiagnostics(DiagnosticsType diagnostics) {
		this.diagnostics = diagnostics;
	}

	/**
	 * @return the results
	 */
	public CT getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(CT results) {
		this.results = results;
	}
}
