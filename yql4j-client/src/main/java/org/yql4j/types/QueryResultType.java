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
package org.yql4j.types;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This class represents the root of the YQL result envelope.
 */
@JsonRootName(value = "query")
public class QueryResultType<CT> {

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
	 * @param count
	 *            the count to set
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
	 * @param created
	 *            the created to set
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
	 * @param lang
	 *            the lang to set
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
	 * @param diagnostics
	 *            the diagnostics to set
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
	 * @param results
	 *            the results to set
	 */
	public void setResults(CT results) {
		this.results = results;
	}
}
