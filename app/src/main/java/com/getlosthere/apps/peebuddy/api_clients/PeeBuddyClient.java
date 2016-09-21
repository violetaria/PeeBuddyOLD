package com.getlosthere.apps.peebuddy.api_clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PeeBuddyClient {
	private static final String BASE_URL = "http://localhost:3000/api/v1";

	private static AsyncHttpClient client = new AsyncHttpClient();

	// https://localhost:3000/api/v1/users
	public static void login(String username, String password, AsyncHttpResponseHandler responseHandler) {
		String url = "/users/";
		RequestParams loginParams = new RequestParams();
		loginParams.add("user",username);
		loginParams.add("password",password);
		RequestParams params = new RequestParams();
		params.add("user",loginParams.toString());

		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}