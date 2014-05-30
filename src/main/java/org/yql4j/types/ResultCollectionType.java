/**
 * 
 */
package org.yql4j.types;


/**
 * @author Philipp
 *
 */
public abstract class ResultCollectionType<T> {

	public abstract T[] getContent();
}
