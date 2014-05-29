/**
 * 
 */
package org.yql4j;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Philipp
 *
 */
public final class YqlResult {

	private Map<String, String> headers = new HashMap<>();
	private String content;

	/**
	 * Constructor.
	 * @param content the content as <code>String</code>
	 * @param headers the headers returned
	 */
	public YqlResult(String content, Map<String, String> headers) {
		checkNotNull(content);
		checkNotNull(headers);
		this.content = content;
		this.headers = headers;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}
}
