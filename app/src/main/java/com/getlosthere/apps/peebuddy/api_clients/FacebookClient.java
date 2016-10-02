package com.getlosthere.apps.peebuddy.api_clients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.getlosthere.apps.peebuddy.R;
import com.getlosthere.apps.peebuddy.applications.RestApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;
/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class FacebookClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = FacebookApi.class;
	public static final String REST_URL = "http://graph.facebook.com";
	public static final String REST_CONSUMER_KEY = RestApplication.getContext().getResources().getString(R.string.facebook_app_id);
	public static final String REST_CONSUMER_SECRET = RestApplication.getContext().getResources().getString(R.string.facebook_secret_key);
	public static final String REST_CALLBACK_URL = "oauth://cprest"; // Change this (here and in manifest)

	public FacebookClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);


	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}