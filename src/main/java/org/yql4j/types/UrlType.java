/**
 * 
 */
package org.yql4j.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * @author Philipp
 *
 */
public class UrlType {

	private long executionStartTime;
	private long executionStopTime;
	private long executionTime;
	private String content;

	/**
	 * @return the executionStartTime
	 */
	public long getExecutionStartTime() {
		return executionStartTime;
	}

	/**
	 * @param executionStartTime the executionStartTime to set
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
	 * @param executionStopTime the executionStopTime to set
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
	 * @param executionTime the executionTime to set
	 */
	@JsonProperty("execution-time")
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	@JacksonXmlText
	public void setContent(String content) {
		this.content = content;
	}
}
