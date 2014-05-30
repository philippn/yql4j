/**
 * 
 */
package org.yql4j.test;

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
		assertTrue(result.getContent().contains("Arctic Ocean"));
	}

	@Test
	public void testOpenDataTablesQuery() throws Exception {
		YqlQuery query = new YqlQuery("select * from yahoo.finance.stocks where symbol='ALV.DE'", true);
		YqlResult result = client.query(query);
		assertTrue(result.getContent().contains("Insurance"));
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
