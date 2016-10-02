package com.getlosthere.apps.peebuddy.activities;

import static com.getlosthere.apps.peebuddy.R.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.getlosthere.apps.peebuddy.R;
import com.getlosthere.apps.peebuddy.api_clients.FacebookClient;
import com.getlosthere.apps.peebuddy.api_clients.PeeBuddyClient;
import com.getlosthere.apps.peebuddy.applications.RestApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends OAuthLoginActionBarActivity<FacebookClient> {
	private CallbackManager callbackManager;
	private PeeBuddyClient peeBuddyClient = RestApplication.getPeeBuddyClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		callbackManager = CallbackManager.Factory.create();

		if(isFacebookLoggedIn()) {
			onLoginSuccess();
		}

		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						Toast.makeText(getApplicationContext(),"login succeeded",Toast.LENGTH_LONG).show();
						String fb_token = loginResult.getAccessToken().getToken().toString();
                        String fb_user_id = loginResult.getAccessToken().getUserId().toString();
						String expires_at = loginResult.getAccessToken().getExpires().toString();
						peeBuddyClient.createSession(fb_token, fb_user_id, expires_at, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
                               Toast.makeText(getApplicationContext(),"WOOHOO",Toast.LENGTH_LONG).show();
                                Log.d("DEBUG",response.toString());
								try {
									peeBuddyClient.addCookie("token",response.getString("token"));
								} catch (JSONException e) {
									e.printStackTrace();
								}
                                onLoginSuccess();
							}

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    Log.e("ERROR","Status code = " + statusCode);
                                    if(errorResponse != null) {
                                        Log.e("ERROR",errorResponse.toString());
                                    }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
                                Log.e("ERROR","Status code = " + statusCode);
                                Log.e("ERROR",responseString);
                            }
                        });
					}

					@Override
					public void onCancel() {
						Log.d("DEBUG","user cancelled");
					}

					@Override
					public void onError(FacebookException exception) {
						Log.e("ERROR",exception.toString());
					}
				});
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(login, menu);
		return true;
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToFacebook(View view) {
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
	}

	public boolean isFacebookLoggedIn(){
		return AccessToken.getCurrentAccessToken() != null;
	}
}
