/**
 * 
 */
package org.yql4j.test.types;

/**
 * @author Philipp
 *
 */
public class PlaceType {

	private String lang;
	private String uri;
	private String woeid;
	private PlaceTypeNameType placeTypeName;
	private String name;

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
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the woeid
	 */
	public String getWoeid() {
		return woeid;
	}

	/**
	 * @param woeid the woeid to set
	 */
	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}

	/**
	 * @return the placeType
	 */
	public PlaceTypeNameType getPlaceTypeName() {
		return placeTypeName;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceTypename(PlaceTypeNameType placeType) {
		this.placeTypeName = placeType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
