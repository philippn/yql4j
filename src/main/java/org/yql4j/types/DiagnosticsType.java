/**
 * 
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
