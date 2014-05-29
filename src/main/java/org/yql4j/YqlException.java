/**
 * 
 */
package org.yql4j;

/**
 * @author Philipp
 *
 */
public class YqlException extends Exception {

	private static final long serialVersionUID = 7900396107814939229L;

	/**
	 * Constructor.
	 * @param message
	 */
	public YqlException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public YqlException(String message, Throwable cause) {
		super(message, cause);
	}
}
