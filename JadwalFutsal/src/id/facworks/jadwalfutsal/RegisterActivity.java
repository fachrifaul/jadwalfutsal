package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a register screen to the user, offering registration
 * as well.
 */
public class RegisterActivity extends Activity implements OnClickListener {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the register task to ensure we can cancel it if requested.
	 */
	// private UserLoginTask mAuthTask = null;
	private RegisterTask task;

	// Values for email and password at the time of the register attempt.
	private String mEmail;
	// private String mUsername;
	private String mPassword;
	private String mName;

	// UI references.
	private EditText mEmailView;
	// private EditText mUsernameView;
	private EditText mPasswordView;
	private EditText mNameView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		// Set up the register form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		// mUsernameView = (EditText) findViewById(R.id.username);
		mNameView = (EditText) findViewById(R.id.name);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.password || id == EditorInfo.IME_NULL) {
							InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							in.hideSoftInputFromWindow(
									mPasswordView.getApplicationWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		mNameView.setTypeface(font);
		mEmailView.setTypeface(font);
		mPasswordView.setTypeface(font);

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// // Respond to the action bar's Up/Home button
	// case android.R.id.home:
	// Intent newIntent = new Intent(this, LoginActivity.class);
	// NavUtils.navigateUpTo(this, newIntent);
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	//
	// }

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}

	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.register_button:
			attemptLogin();
			// task = new RegisterTask();
			// task.execute("");
			break;
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the register
	 * form. If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual register attempt is made.
	 */
	public void attemptLogin() {
		if (task != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		// mUsernameView.setError(null);
		mPasswordView.setError(null);
		mNameView.setError(null);

		// Store values at the time of the register attempt.
		mEmail = mEmailView.getText().toString();
		// mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mName = mNameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		// Check for a valid username address.
		// if (TextUtils.isEmpty(mUsername)) {
		// mUsernameView.setError(getString(R.string.error_field_required));
		// focusView = mUsernameView;
		// cancel = true;
		// } else if (mUsername.length() < 4) {
		// mUsernameView.setError(getString(R.string.error_invalid_username));
		// focusView = mUsernameView;
		// cancel = true;
		// }

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid name
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		} else if (mName.length() < 4) {
			mNameView.setError(getString(R.string.error_invalid_username));
			focusView = mNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt register and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user register attempt.
			mLoginStatusMessageView.setText(R.string.register_progress);
			showProgress(true);
			task = new RegisterTask();
			task.execute("");
		}
	}

	/**
	 * Shows the progress UI and hides the register form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	private String[] readInputValues() {
		String[] values = new String[4];

		EditText editText = (EditText) findViewById(R.id.name);
		values[0] = editText.getText().toString().trim().toLowerCase();

		editText = (EditText) findViewById(R.id.password);
		values[1] = editText.getText().toString();

		editText = (EditText) findViewById(R.id.email);
		values[2] = editText.getText().toString().trim().toLowerCase();

		return values;
	}

	/**
	 * Represents an asynchronous register task used to authenticate the user.
	 */

	private class RegisterTask extends AsyncTask<String, Object, String> {

		// private LoadingFragment loading;

		private boolean isCancelled = false;

		@Override
		protected String doInBackground(String... params) {
			String[] values = readInputValues();
			// String validationError = validateData(values);
			// if (validationError == null) {
			// WebApi api = WebApi.getInstance();
			// return api.register(getApplicationContext(), values[0],
			// values[1], values[3]);
			// } else {
			// return validationError;
			// }
			try {
				WebApi api = WebApi.getInstance();
				return api.register2(getApplicationContext(), "register",
						values[0], values[1], values[2]);
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			// return null;

		}

		@Override
		protected void onPostExecute(String result) {
			task = null;
			showProgress(false);
			if (!isCancelled) {

				if (result == null) {
					// ConfirmationDialogFragment confirmDialog =
					// ConfirmationDialogFragment
					// .newInstance(
					// getString(R.string.register_success_message),
					// getString(android.R.string.ok),
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {
//					startActivity(new Intent(RegisterActivity.this,
//							TableActivity.class));
					startActivity(new Intent(RegisterActivity.this,
							SplashActivity.class));
					finish();
					// }
					// });
					// confirmDialog.show(getSupportFragmentManager(), null);
				} else {
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result);
		}

		protected void onCancelled() {
			task = null;
			showProgress(false);
		}
	}

}
