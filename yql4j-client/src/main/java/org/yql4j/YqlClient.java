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

import java.io.Closeable;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This interface represents the basic contract for a YQL client.
 */
public interface YqlClient extends Closeable {

	/**
	 * Executes the given query on YQL and returns the result.
	 * 
	 * @param query
	 *            the query to execute
	 * @return the result
	 * @throws YqlException
	 *             if an error occurred
	 */
	public YqlResult query(YqlQuery query) throws YqlException;

	/**
	 * Returns the Jackson <code>ObjectMapper</code> used internally to map 
	 * JSON results into an object graph.
	 * 
	 * @return the Jackson <code>ObjectMapper</code>
	 */
	public ObjectMapper getJsonObjectMapper();

	/**
	 * Returns the Jackson <code>ObjectMapper</code> used internally to map 
	 * XML results into an object graph.
	 * 
	 * @return the Jackson <code>ObjectMapper</code>
	 */
	public ObjectMapper getXmlObjectMapper();
}
