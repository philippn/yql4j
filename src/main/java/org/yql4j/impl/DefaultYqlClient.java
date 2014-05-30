/**
 * 
 */
package org.yql4j.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
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
import org.yql4j.YqlClient;
import org.yql4j.YqlException;
import org.yql4j.YqlQuery;
import org.yql4j.YqlResult;

/**
 * @author Philipp
 *
 */
public class DefaultYqlClient implements YqlClient {

	protected final Logger logger = LoggerFactory.getLogger(DefaultYqlClient.class);

	protected static final String SERVICE_URL_PUBLIC = 
			"http://query.yahooapis.com/v1/public/yql";
	protected static final String SERVICE_URL_OAUTH = 
			"http://query.yahooapis.com/v1/yql";

	protected CloseableHttpClient httpclient = HttpClients.createDefault();

	/* (non-Javadoc)
	 * @see org.yql4j.YqlClient#query(org.yql4j.YqlQuery)
	 */
	@Override
	public YqlResult query(YqlQuery query) throws YqlException {
		checkNotNull(query);
		try {
			HttpUriRequest request = createHttpRequest(query);
			request = signHttpRequest(request, query);
			try (CloseableHttpResponse response = httpclient.execute(request)) {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					Map<String, String> headers = new HashMap<>();
					for (Header header : response.getAllHeaders()) {
						headers.put(header.getName(), header.getValue());
					}
					return new YqlResult(EntityUtils.toString(entity), headers);
				} else {
					throw new YqlException("Failed to execute YQL query (URL=" + 
							query.buildUri() + "): Received unexpected status code " + 
							response.getStatusLine().getStatusCode());
				}
			}
		} catch (ParseException | OAuthException | IOException e) {
			throw new YqlException("Failed to execute YQL query (URL=" + 
					query.buildUri() + "): " + e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		httpclient.close();
	}

	protected HttpUriRequest createHttpRequest(YqlQuery query) {
		URI uri = query.buildUri();
		logger.debug("YQL Request URL: " + uri.toString());
		return new HttpGet(uri);
	}

	protected HttpUriRequest signHttpRequest(HttpUriRequest request, YqlQuery query) throws OAuthException {
		boolean oAuth = (query.getConsumerKey() != null) && 
				(query.getConsumerSecret() != null);
		if (oAuth) {
			// We are doing two-legged OAuth
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
					query.getConsumerKey(), query.getConsumerSecret());
			consumer.sign(request);
		}
		return request;
	}
}
