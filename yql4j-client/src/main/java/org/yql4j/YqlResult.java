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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.yql4j.types.QueryResultType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Instances of this class contain the result of a YQL query execution.
 */
public final class YqlResult {

	private Map<String, String> headers = new HashMap<>();
	private String content;
	private ObjectMapper objectMapper;

	/**
	 * Constructor.
	 * 
	 * @param content
	 *            the content as <code>String</code>
	 * @param headers
	 *            the headers returned
	 * @param objectMapper
	 *            the object mapper to use
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
	 * Returns the content as raw {@link String}.
	 * 
	 * @return the content as {@link String}
	 */
	public String getContentAsString() {
		return content;
	}

	/**
	 * Returns the content mapped into an object graph as requested per
	 * <code>valueTypeRef</code>
	 * 
	 * @param valueTypeRef
	 *            the type to request
	 * @param <CT>
	 *            the result content type
	 * @return the content as mapped object graph
	 * @throws IOException
	 *             if an error occurred
	 */
	public <CT> QueryResultType<CT> getContentAsMappedObject(
			TypeReference<QueryResultType<CT>> valueTypeRef) throws IOException {
		return objectMapper.readValue(content, valueTypeRef);
	}
}
