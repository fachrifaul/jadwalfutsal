package id.facworks.jadwalfutsal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class ForgotActivity extends ActionBarActivity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	// private static final String[] DUMMY_CREDENTIALS = new String[] {
	// "foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	// private UserLoginTask mAuthTask = null;
	private ForgotPasswordTask task;

	// Values for email and password at the time of the login attempt.
	private String mUsernameEmail;

	// UI references.
	private EditText mUsernameEmailView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_forgot);

		// Set up the login form.

		mUsernameEmailView = (EditText) findViewById(R.id.usernameEmail);

		mUsernameEmailView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.usernameEmail
								|| id == EditorInfo.IME_NULL) {
							InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							in.hideSoftInputFromWindow(mUsernameEmailView
									.getApplicationWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.reset_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			Intent newIntent = new Intent(this, LoginActivity.class);
			NavUtils.navigateUpTo(this, newIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (task != null) {
			return;
		}

		// Reset errors.
		mUsernameEmailView.setError(null);

		// Store values at the time of the login attempt.
		mUsernameEmail = mUsernameEmailView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid username or email address.
		if (TextUtils.isEmpty(mUsernameEmail)) {
			mUsernameEmailView
					.setError(getString(R.string.error_field_required));
			focusView = mUsernameEmailView;
			cancel = true;
		} else if (mUsernameEmail.length() < 4) {
			mUsernameEmailView
					.setError(getString(R.string.error_invalid_username_email));
			focusView = mUsernameEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.forgot_progress);
			showProgress(true);
			// mAuthTask = new UserLoginTask();
			// mAuthTask.execute((Void) null);
			task = new ForgotPasswordTask();
			task.execute("");
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */

	private class ForgotPasswordTask extends AsyncTask<Object, Object, String>
			implements OnCancelListener {

		private boolean isCancelled = false;

		private Dialog loadingDialog;

		public void cancel() {
			isCancelled = true;
		}

		@Override
		protected String doInBackground(Object... params) {
			// try {
			// WebApi.getInstance().forgotpassword(
			// mUsernameEmailView.getText().toString());
			// } catch (Exception e) {
			// e.printStackTrace();
			// return e.getMessage();
			// }
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			task = null;
			showProgress(false);
			if (loadingDialog != null)
				loadingDialog.dismiss();
			if (isCancelled)
				return;
			if (result == null) {
				Toast.makeText(getApplicationContext(),
						R.string.forgot_password_success_message,
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				mUsernameEmailView
						.setError(getString(R.string.error_invalid_username_email));
				mUsernameEmailView.requestFocus();
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

		@Override
		public void onCancel(DialogInterface arg0) {
			task = null;
			showProgress(false);
			cancel();
		}

		protected void onCancelled() {
			task = null;
			showProgress(false);
		}
	}

}
