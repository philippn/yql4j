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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.yql4j.YqlQuery.ENV_COMMUNITY_OPEN_DATA_TABLES;

import org.junit.Test;
import org.yql4j.ResultFormat;
import org.yql4j.YqlQuery;
import org.yql4j.YqlQueryBuilder;

/**
 * Tests for the {@link YqlQueryBuilder}.
 */
public class YqlQueryBuilderTest {

	@Test
	public void testQueryString() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from geo.oceans");
		YqlQuery query = builder.build();
		assertEquals("select * from geo.oceans", query.getQueryString());
	}

	@Test
	public void testQueryAlias() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryAlias("philippn", "ocean");
		YqlQuery query = builder.build();
		assertEquals("philippn", query.getAliasPrefix());
		assertEquals("ocean", query.getAliasName());
	}

	@Test
	public void testRepeatedBuild() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryAlias("philippn", "ocean");
		builder.withVariable("name", "Arctic Ocean");
		YqlQuery query1 = builder.build();
		builder.withVariable("name", "Indian Ocean");
		YqlQuery query2 = builder.build();
		assertEquals("Arctic Ocean", query1.getVariableValue("name"));
		assertEquals("Indian Ocean", query2.getVariableValue("name"));
	}

	@Test
	public void testQueryWithConsumerkeyAndSecret() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from geo.oceans where name=@name");
		builder.withConsumerKeyAndSecret("abc", "xyz");
		YqlQuery query = builder.build();
		assertEquals("abc", query.getConsumerKey());
		assertEquals("xyz", query.getConsumerSecret());
	}

	@Test
	public void testQueryWithDiagnosticsEnabled() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from geo.oceans where name=@name");
		assertFalse(builder.build().isDiagnostics());
		builder.withDiagnosticsEnabled();
		assertTrue(builder.build().isDiagnostics());
	}

	@Test
	public void testQueryWithFormat() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from geo.oceans where name=@name");
		assertNull(builder.build().getFormat());
		builder.withFormat(ResultFormat.JSON);
		assertEquals(ResultFormat.JSON, builder.build().getFormat());
	}

	@Test
	public void testQueryWithVariable() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from geo.oceans where name=@name");
		builder.withVariable("name", "Arctic Ocean");
		YqlQuery query = builder.build();
		assertEquals("Arctic Ocean", query.getVariableValue("name"));
	}

	@Test
	public void testQueryWithEnvironment() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from yahoo.finance.stocks where symbol='ALV.DE'");
		builder.withEnvironment(ENV_COMMUNITY_OPEN_DATA_TABLES);
		YqlQuery query = builder.build();
		assertEquals(1, query.getEnvironmentFiles().size());
		assertEquals(ENV_COMMUNITY_OPEN_DATA_TABLES, query.getEnvironmentFiles().get(0));
	}

	@Test
	public void testQueryWithTable() throws Exception {
		String tableFile = "https://raw.githubusercontent.com/philippn/"
				+ "yql-tables/master/yahoo.finance.components.xml";
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString("select * from mytable where symbol='^GDAXI'");
		builder.withTable(tableFile, "mytable");
		YqlQuery query = builder.build();
		assertEquals(1, query.getTableFiles().size());
		assertEquals("mytable", query.getTableFiles().get(tableFile));
	}
}
