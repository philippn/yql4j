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
package org.yql4j.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Philipp
 *
 */
public class DiagnosticsType {

	private boolean publiclyCallable;
	private UrlType url;
	private long userTime;
	private long serviceTime;
	private String buildVersion;

	/**
	 * @return the publiclyCallable
	 */
	public boolean isPubliclyCallable() {
		return publiclyCallable;
	}

	/**
	 * @param publiclyCallable the publiclyCallable to set
	 */
	public void setPubliclyCallable(boolean publiclyCallable) {
		this.publiclyCallable = publiclyCallable;
	}

	/**
	 * @return the url
	 */
	public UrlType getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(UrlType url) {
		this.url = url;
	}

	/**
	 * @return the userTime
	 */
	public long getUserTime() {
		return userTime;
	}

	/**
	 * @param userTime the userTime to set
	 */
	@JsonProperty("user-time")
	public void setUserTime(long userTime) {
		this.userTime = userTime;
	}

	/**
	 * @return the serviceTime
	 */
	public long getServiceTime() {
		return serviceTime;
	}

	/**
	 * @param serviceTime the serviceTime to set
	 */
	@JsonProperty("service-time")
	public void setServiceTime(long serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * @return the buildVersion
	 */
	public String getBuildVersion() {
		return buildVersion;
	}

	/**
	 * @param buildVersion the buildVersion to set
	 */
	@JsonProperty("build-version")
	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}
}
