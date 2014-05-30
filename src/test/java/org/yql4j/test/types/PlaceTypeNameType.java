/**
 * 
 */
package org.yql4j.test.types;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * @author Philipp
 *
 */
public class PlaceTypeNameType {

	private String content;
	private int code;

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

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
}
