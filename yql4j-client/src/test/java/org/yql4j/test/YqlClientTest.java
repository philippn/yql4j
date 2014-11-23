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
package org.yql4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yql4j.ResultFormat;
import org.yql4j.YqlClient;
import org.yql4j.YqlClients;
import org.yql4j.YqlException;
import org.yql4j.YqlQuery;
import org.yql4j.YqlResult;
import org.yql4j.test.types.PlaceArrayType;
import org.yql4j.test.types.PlaceType;
import org.yql4j.test.types.StockArrayType;
import org.yql4j.types.QueryResultType;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Tests for the {@link YqlClient}.
 */
public class YqlClientTest {

	private YqlClient client;

	@Before
	public void setUp() {
		client = YqlClients.createDefault();
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void testSimpleQuery() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans");
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsString().contains("Arctic Ocean"));
	}

	@Test
	public void testQueryWithVariable() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name=@name");
		query.addVariable("name", "Arctic Ocean");
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsString().contains("Arctic Ocean"));
	}

	@Test
	public void testQueryAlias() throws Exception {
		YqlQuery query = new YqlQuery("philippn", "ocean");
		query.addVariable("name", "Arctic Ocean");
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsString().contains("Arctic Ocean"));
	}

	@Test
	public void testObjectMappingEmptyResultXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Nordsee'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsMappedObject(
				new TypeReference<QueryResultType<PlaceArrayType>>() {}).
				getResults() == null);
	}

	@Test
	public void testObjectMappingSingleResultXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		QueryResultType<PlaceArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceArrayType>>() {});
		assertEquals(1, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		PlaceType atlantic = mappedResult.getResults().getPlace()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
	}

	@Test
	public void testObjectMappingMultiResultXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean' or name='Indian Ocean'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		QueryResultType<PlaceArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceArrayType>>() {});
		assertEquals(2, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		PlaceType atlantic = mappedResult.getResults().getPlace()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
		PlaceType indian = mappedResult.getResults().getPlace()[1];
		assertNotNull(indian);
		assertEquals("Indian Ocean", indian.getName());
	}

	@Test
	public void testObjectMappingEmptyResultJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Nordsee'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsMappedObject(
				new TypeReference<QueryResultType<PlaceArrayType>>() {}).
				getResults() == null);
	}

	@Test
	public void testObjectMappingSingleResultJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		QueryResultType<PlaceArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceArrayType>>() {});
		assertEquals(1, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		PlaceType atlantic = mappedResult.getResults().getPlace()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
	}

	@Test
	public void testObjectMappingMultiResultJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean' or name='Indian Ocean'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		QueryResultType<PlaceArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceArrayType>>() {});
		assertEquals(2, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		PlaceType atlantic = mappedResult.getResults().getPlace()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
		PlaceType indian = mappedResult.getResults().getPlace()[1];
		assertNotNull(indian);
		assertEquals("Indian Ocean", indian.getName());
	}

	@Test
	public void testStockMappingXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from yahoo.finance.stocks where symbol='ALV.DE'");
		query.setDiagnostics(true);
		query.useCommunityOpenDataTables();
		YqlResult result = client.query(query);
		QueryResultType<StockArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<StockArrayType>>() {});
		assertEquals(1, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		assertEquals("Property & Casualty Insurance", mappedResult.getResults().getStock()[0].getIndustry());
	}

	@Test
	public void testStockMappingJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from yahoo.finance.stocks where symbol='ALV.DE'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		query.useCommunityOpenDataTables();
		YqlResult result = client.query(query);
		QueryResultType<StockArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<StockArrayType>>() {});
		assertEquals(1, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		assertEquals("Property & Casualty Insurance", mappedResult.getResults().getStock()[0].getIndustry());
	}

	@Test
	public void testErrorHandling() throws Exception {
		YqlQuery query = new YqlQuery("select * from yahoo.finance.stocks where symbol='ALV.DE'");
		try {
			client.query(query);
			fail();
		} catch (YqlException e) {
			assertTrue(e.getMessage().contains("No definition found for Table yahoo.finance.stocks"));
		}
		
		query.setFormat(ResultFormat.JSON);
		try {
			client.query(query);
			fail();
		} catch (YqlException e) {
			assertTrue(e.getMessage().contains("No definition found for Table yahoo.finance.stocks"));
		}
	}
}
