/**
 * 
 */
package org.yql4j.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yql4j.ResultFormat;
import org.yql4j.YqlClient;
import org.yql4j.YqlException;
import org.yql4j.YqlQuery;
import org.yql4j.YqlResult;
import org.yql4j.impl.DefaultYqlClient;
import org.yql4j.test.types.PlaceCollectionType;
import org.yql4j.test.types.PlaceType;
import org.yql4j.types.QueryResultType;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Philipp
 *
 */
public class YqlClientTest {

	private YqlClient client;

	@Before
	public void setUp() {
		client = new DefaultYqlClient();
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
	public void testMappingQueryEmptyResultXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Nordsee'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsMappedObject(
				new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {}).
				getResults() == null);
	}

	@Test
	public void testMappingQuerySingleResultXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		QueryResultType<PlaceCollectionType, PlaceType> results = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {});
		assertEquals(1, results.getCount());
		assertNotNull(results.getResults());
		PlaceType atlantic = results.getResults().getContent()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
	}

	@Test
	public void testMappingQueryMulipleResultsXml() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean' or name='Indian Ocean'");
		query.setDiagnostics(true);
		YqlResult result = client.query(query);
		QueryResultType<PlaceCollectionType, PlaceType> results = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {});
		assertEquals(2, results.getCount());
		assertNotNull(results.getResults());
		PlaceType atlantic = results.getResults().getContent()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
		PlaceType indian = results.getResults().getContent()[1];
		assertNotNull(indian);
		assertEquals("Indian Ocean", indian.getName());
	}

	@Test
	public void testMappingQueryEmptyResultJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Nordsee'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsMappedObject(
				new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {}).
				getResults() == null);
	}

	@Test
	public void testMappingQuerySingleResultJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		QueryResultType<PlaceCollectionType, PlaceType> results = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {});
		assertEquals(1, results.getCount());
		assertNotNull(results.getResults());
		PlaceType atlantic = results.getResults().getContent()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
	}

	@Test
	public void testMappingQueryMulipleResultsJson() throws Exception {
		YqlQuery query = new YqlQuery("select * from geo.oceans where name='Atlantic Ocean' or name='Indian Ocean'");
		query.setDiagnostics(true);
		query.setFormat(ResultFormat.JSON);
		YqlResult result = client.query(query);
		QueryResultType<PlaceCollectionType, PlaceType> results = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<PlaceCollectionType, PlaceType>>() {});
		assertEquals(2, results.getCount());
		assertNotNull(results.getResults());
		PlaceType atlantic = results.getResults().getContent()[0];
		assertNotNull(atlantic);
		assertEquals("Atlantic Ocean", atlantic.getName());
		PlaceType indian = results.getResults().getContent()[1];
		assertNotNull(indian);
		assertEquals("Indian Ocean", indian.getName());
	}

	@Test
	public void testOpenDataTablesQuery() throws Exception {
		YqlQuery query = new YqlQuery("select * from yahoo.finance.stocks where symbol='ALV.DE'", true);
		YqlResult result = client.query(query);
		assertTrue(result.getContentAsString().contains("Insurance"));
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
