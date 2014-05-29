/**
 * 
 */
package org.yql4j;

import java.io.Closeable;

/**
 * @author Philipp
 *
 */
public interface YqlClient extends Closeable {

	/**
	 * Executes the given query on YQL and returns the result.
	 * @param query the query to execute
	 * @return the result
	 * @throws YqlException if an error occurred
	 */
	public YqlResult query(YqlQuery query) throws YqlException;
}
