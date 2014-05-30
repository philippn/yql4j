/**
 * 
 */
package org.yql4j.types;

import com.fasterxml.jackson.annotation.JsonRootName;


/**
 * @author Philipp
 *
 */
@JsonRootName(value="error")
public class ErrorType {

	private String lang;
	private String description;

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
