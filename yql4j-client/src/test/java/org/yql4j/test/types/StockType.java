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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the <code>stock</code> type used in the
 * <code>yahoo.finance.stocks</code> table.
 */
public class StockType {

	private String symbol;
	private String companyName;
	private String market;
	private Date start;
	private Date end;
	private String sector;
	private String industry;
	private int fullTimeEmployees;

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	@JsonProperty("CompanyName")
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @param market the market to set
	 */
	@JsonProperty("Market")
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	@JsonProperty("Sector")
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	@JsonProperty("Industry")
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the fullTimeEmployees
	 */
	public int getFullTimeEmployees() {
		return fullTimeEmployees;
	}

	/**
	 * @param fullTimeEmployees the fullTimeEmployees to set
	 */
	@JsonProperty("FullTimeEmployees")
	public void setFullTimeEmployees(int fullTimeEmployees) {
		this.fullTimeEmployees = fullTimeEmployees;
	}
}
