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
package org.yql4j.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yql4j.ResultFormat;
import org.yql4j.YqlClient;
import org.yql4j.YqlException;
import org.yql4j.YqlQuery;
import org.yql4j.YqlResult;
import org.yql4j.types.ErrorType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Stopwatch;

/**
 * This class implements the {@link YqlClient} contract using the <a
 * href="http://hc.apache.org/httpcomponents-client-ga/">Apache
 * HttpComponents HttpClient</a> for transport.
 */
public class HttpComponentsYqlClient implements YqlClient {

	protected final Logger logger = LoggerFactory
			.getLogger(HttpComponentsYqlClient.class);

	protected static final String SERVICE_URL_PUBLIC = 
			"http://query.yahooapis.com/v1/public/yql";
	protected static final String SERVICE_URL_OAUTH = 
			"http://query.yahooapis.com/v1/yql";

	protected CloseableHttpClient httpClient;
	protected ObjectMapper jsonMapper;
	protected ObjectMapper xmlMapper;

	/**
	 * Default constructor.
	 */
	public HttpComponentsYqlClient() {
		this(HttpClients.createDefault());
	}

	/**
	 * Constructor.
	 * 
	 * @param httpClient
	 *            the {@link CloseableHttpClient} instance to use
	 */
	public HttpComponentsYqlClient(CloseableHttpClient httpClient) {
		checkNotNull(httpClient);
		this.httpClient = httpClient;
		this.jsonMapper = createJsonMapper();
		this.xmlMapper = createXmlMapper();
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.yql4j.YqlClient#getJsonObjectMapper()
	 */
	@Override
	public ObjectMapper getJsonObjectMapper() {
		return jsonMapper;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.yql4j.YqlClient#getXmlObjectMapper()
	 */
	@Override
	public ObjectMapper getXmlObjectMapper() {
		return xmlMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yql4j.YqlClient#query(org.yql4j.YqlQuery)
	 */
	@Override
	public YqlResult query(YqlQuery query) throws YqlException {
		checkNotNull(query);
		try {
			HttpUriRequest request = createHttpRequest(query);
			request = signHttpRequest(request, query);

			Stopwatch timer = Stopwatch.createStarted();
			try (CloseableHttpResponse response = httpClient.execute(request)) {
				logger.debug("YQL query (URL=" + query.toUri() + ") took "
						+ timer.stop().elapsed(TimeUnit.MILLISECONDS) + "ms");

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					Map<String, String> headers = new HashMap<>();
					for (Header header : response.getAllHeaders()) {
						headers.put(header.getName(), header.getValue());
					}
					return new YqlResult(EntityUtils.toString(entity), headers,
							getAppropriateMapper(query));
				} else if (isClientError(response)) {
					HttpEntity entity = response.getEntity();
					ObjectMapper mapper = getAppropriateMapper(query);
					ErrorType error = mapper.readValue(
							EntityUtils.toString(entity), ErrorType.class);
					throw new YqlException("Failed to execute YQL query (URL="
							+ query.toUri() + "): " + error.getDescription());
				} else {
					throw new YqlException("Failed to execute YQL query (URL="
							+ query.toUri()
							+ "): Received unexpected status code "
							+ response.getStatusLine().getStatusCode());
				}
			}
		} catch (ParseException | OAuthException | IOException e) {
			throw new YqlException("Failed to execute YQL query (URL="
					+ query.toUri() + "): " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		httpClient.close();
	}

	/**
	 * Creates the Jackson {@link ObjectMapper} instance to use for XML
	 * processing.
	 * <p>
	 * Applies only if <code>format=json</code>.
	 * 
	 * @return a Jackson {@link ObjectMapper} instance
	 */
	protected ObjectMapper createJsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper;
	}

	/**
	 * Creates the Jackson {@link ObjectMapper} instance to use for XML
	 * processing.
	 * <p>
	 * Applies only if <code>format=xml</code>.
	 * 
	 * @return a Jackson {@link ObjectMapper} instance
	 */
	protected ObjectMapper createXmlMapper() {
		return new XmlMapper();
	}

	/**
	 * Create the HTTP request to execute.
	 * 
	 * @param query
	 *            the query specification
	 * @return the HTTP request
	 */
	protected HttpUriRequest createHttpRequest(YqlQuery query) {
		URI uri = query.toUri();
		logger.debug("YQL query URL: " + uri.toString());
		return new HttpGet(uri);
	}

	/**
	 * Signs the HTTP request using OAuth.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param query
	 *            the query specification
	 * @return the signed HTTP request
	 * @throws OAuthException
	 *             if an error occurred
	 */
	protected HttpUriRequest signHttpRequest(HttpUriRequest request,
			YqlQuery query) throws OAuthException {
		boolean oAuth = (query.getConsumerKey() != null)
				&& (query.getConsumerSecret() != null);
		if (oAuth) {
			// We are only signing the request at this point
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
					query.getConsumerKey(), query.getConsumerSecret());
			consumer.sign(request);
		}
		return request;
	}

	/**
	 * Returns whether the HTTP response is in an error state caused by the
	 * client.
	 * 
	 * @param response
	 *            the HTTP response
	 * @return <code>true</code> if response is in error caused by the client
	 */
	protected boolean isClientError(HttpResponse response) {
		int sc = response.getStatusLine().getStatusCode();
		return sc >= 400 && sc < 500;
	}

	/**
	 * Returns the {@link ObjectMapper} appropriate for the given query.
	 * 
	 * @param query
	 *            the query specification
	 * @return the appropriate Jackson {@link ObjectMapper}
	 */
	protected ObjectMapper getAppropriateMapper(YqlQuery query) {
		ResultFormat format = query.getFormat();
		if (ResultFormat.JSON.equals(format)) {
			return jsonMapper;
		}
		return xmlMapper;
	}
}
