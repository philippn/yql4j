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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the <code>javascript</code> type used in the YQL diagnostics.
 */
public class JavascriptType {

	private long executionStartTime;
	private long executionStopTime;
	private long executionTime;
	private long instructionsUsed;
	private String tableName;

	/**
	 * @return the executionStartTime
	 */
	public long getExecutionStartTime() {
		return executionStartTime;
	}

	/**
	 * @param executionStartTime
	 *            the executionStartTime to set
	 */
	@JsonProperty("execution-start-time")
	public void setExecutionStartTime(long executionStartTime) {
		this.executionStartTime = executionStartTime;
	}

	/**
	 * @return the executionStopTime
	 */
	public long getExecutionStopTime() {
		return executionStopTime;
	}

	/**
	 * @param executionStopTime
	 *            the executionStopTime to set
	 */
	@JsonProperty("execution-stop-time")
	public void setExecutionStopTime(long executionStopTime) {
		this.executionStopTime = executionStopTime;
	}

	/**
	 * @return the executionTime
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * @param executionTime
	 *            the executionTime to set
	 */
	@JsonProperty("execution-time")
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the instructionsUsed
	 */
	public long getInstructionsUsed() {
		return instructionsUsed;
	}

	/**
	 * @param instructionsUsed the instructionsUsed to set
	 */
	@JsonProperty("instructions-used")
	public void setInstructionsUsed(long instructionsUsed) {
		this.instructionsUsed = instructionsUsed;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	@JsonProperty("table-name")
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
