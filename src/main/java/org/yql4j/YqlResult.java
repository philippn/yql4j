/**
 * 
 */
package org.yql4j;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.yql4j.types.QueryResultType;
import org.yql4j.types.ResultCollectionType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Philipp
 *
 */
public final class YqlResult {

	private Map<String, String> headers = new HashMap<>();
	private String content;
	private ObjectMapper objectMapper;

	/**
	 * Constructor.
	 * @param content the content as <code>String</code>
	 * @param headers the headers returned
	 * @param objectMapper the object mapper to use
	 */
	public YqlResult(String content, Map<String, String> headers, 
			ObjectMapper objectMapper) {
		checkNotNull(content);
		checkNotNull(headers);
		checkNotNull(objectMapper);
		this.content = content;
		this.headers = headers;
		this.objectMapper = objectMapper;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @return the objectMapper
	 */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * @return the content as <code>String</code>
	 */
	public String getContentAsString() {
		return content;
	}

	/**
	 * @param valueTypeRef
	 * @return the content as mapped object graph
	 * @throws IOException
	 */
	public <T extends ResultCollectionType<?>> QueryResultType<T> getContentAsMappedObject(
			TypeReference<QueryResultType<T>> valueTypeRef) throws IOException {
		return objectMapper.readValue(content, valueTypeRef);
	}
}
