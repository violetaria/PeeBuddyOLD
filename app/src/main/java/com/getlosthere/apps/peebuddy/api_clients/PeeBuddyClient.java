package com.getlosthere.apps.peebuddy.api_clients;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class PeeBuddyClient {
	private static final String BASE_URL = "https://9f3f2026.ngrok.io";

	private static AsyncHttpClient client = new AsyncHttpClient();

//	// https://localhost:3000/api/v1/users
//	public static void login(String username, String password, AsyncHttpResponseHandler responseHandler) {
//		String url = "/users/";
//		RequestParams loginParams = new RequestParams();
//		loginParams.add("user",username);
//		loginParams.add("password",password);
//		RequestParams params = new RequestParams();
//		params.add("user",loginParams.toString());
//
//		client.post(getAbsoluteUrl(url), params, responseHandler);
//	}

	// https://localhost:3000/auth/facebook
	public static void facebookLogin(AsyncHttpResponseHandler responseHandler) {
		String url = "/auth/facebook/";
		Log.d("DEBUG",getAbsoluteUrl(url));
		client.get(getAbsoluteUrl(url), null, responseHandler);
	}

	public static void createSession(String token, String user_id, String expires_at, AsyncHttpResponseHandler responseHandler) {
		String url = "/sessions/";
		RequestParams params = new RequestParams();
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("user_id",user_id);
		map.put("expires_at",expires_at);
		params.put("session", map);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}