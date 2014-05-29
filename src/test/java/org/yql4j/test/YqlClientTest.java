/**
 * 
 */
package org.yql4j.test;

import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yql4j.YqlClient;
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
}
