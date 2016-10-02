package com.getlosthere.apps.peebuddy.applications;

import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.getlosthere.apps.peebuddy.api_clients.FacebookClient;
import com.getlosthere.apps.peebuddy.api_clients.PeeBuddyClient;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     FacebookClient client = RestApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class RestApplication extends com.activeandroid.app.Application {
	private static Context context;
	private static PeeBuddyClient peeBuddyClient;

	@Override
	public void onCreate() {
		super.onCreate();
		RestApplication.context = this;
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		peeBuddyClient = new PeeBuddyClient();
	}

	public static Context getContext(){
		return context;
	}

	public static FacebookClient getFacebookClient() {
		return (FacebookClient) FacebookClient.getInstance(FacebookClient.class, RestApplication.context);
	}

	public static PeeBuddyClient getPeeBuddyClient() {
		return peeBuddyClient;
	}
}