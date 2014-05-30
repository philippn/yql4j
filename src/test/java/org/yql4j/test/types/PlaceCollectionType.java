/**
 * 
 */
package org.yql4j.test.types;

import org.yql4j.types.ResultCollectionType;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

/**
 * @author Philipp
 *
 */
public class PlaceCollectionType extends ResultCollectionType<PlaceType> {

	@JacksonXmlElementWrapper(useWrapping=false)
	private PlaceType[] place;

	/**
	 * @return the place
	 */
	public PlaceType[] getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(PlaceType[] place) {
		this.place = place;
	}

	/* (non-Javadoc)
	 * @see org.yql4j.types.ResultCollectionType#getContent()
	 */
	@Override
	public PlaceType[] getContent() {
		return getPlace();
	}
}
