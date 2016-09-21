package com.getlosthere.apps.peebuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.getlosthere.apps.peebuddy.R;
import com.getlosthere.apps.peebuddy.api_clients.FacebookClient;

import java.util.Arrays;

public class LoginActivity extends OAuthLoginActionBarActivity<FacebookClient> {
	private CallbackManager callbackManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		callbackManager = CallbackManager.Factory.create();

		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						Toast.makeText(getApplicationContext(),"login succeeded",Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.login, menu);
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
		// Intent i = new Intent(this, PhotosActivity.class);
		// startActivity(i);
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
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

	}

}
