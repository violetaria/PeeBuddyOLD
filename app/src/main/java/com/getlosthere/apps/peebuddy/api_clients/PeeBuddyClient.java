package com.getlosthere.apps.peebuddy.api_clients;

import com.getlosthere.apps.peebuddy.applications.RestApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

public class PeeBuddyClient {
	private static final String BASE_URL = "https://a8bc1a11.ngrok.io/api/v1";
	private static AsyncHttpClient client = new AsyncHttpClient();
	private PersistentCookieStore cookieStore = new PersistentCookieStore(RestApplication.getContext());;


	public PeeBuddyClient(){
		client.setCookieStore(cookieStore);
	}

	public void addCookie(String token, String value){
		BasicClientCookie newCookie = new BasicClientCookie(token, value);
		newCookie.setVersion(1);
		newCookie.setDomain("c469dba1.ngrok.io");
		newCookie.setPath("/");
		cookieStore.addCookie(newCookie);
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

	public static void getLocations(Double lat, Double lng, AsyncHttpResponseHandler responseHandler){
		String url = "/locations/";
		RequestParams params = new RequestParams();
		params.put("lat",lat);
		params.put("lng",lng);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}