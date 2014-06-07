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
