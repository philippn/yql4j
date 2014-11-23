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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;


/**
 * This class represents an array of <code>stock</code> types used in
 * the <code>yahoo.finance.stocks</code> table.
 */
public class StockArrayType {

	@JacksonXmlElementWrapper(useWrapping=false)
	private StockType[] stock;

	/**
	 * @return the stock
	 */
	public StockType[] getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(StockType[] stock) {
		this.stock = stock;
	}
}
