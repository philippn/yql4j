/**
 * 
 */
package org.yql4j.types;


/**
 * @author Philipp
 *
 */
public abstract class ResultCollectionType<VT> {

	public abstract VT[] getContent();
}
