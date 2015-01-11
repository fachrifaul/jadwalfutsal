package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
public class LoginActivity extends Activity {

	private LoginTask mAuthTask = null;
	private ForgotPasswordTask task;

	// Values for Username and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	TextView forgot;

	private String mUsernameEmail;
	private EditText mUsernameEmailView;

	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUsernameView = (EditText) findViewById(R.id.username);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		forgot = (TextView) findViewById(R.id.forgot);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		mUsernameView.setTypeface(font);
		mPasswordView.setTypeface(font);
		forgot.setTypeface(font);

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		findViewById(R.id.register).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent newIntent = new Intent(LoginActivity.this,
								RegisterActivity.class);
						startActivity(newIntent);
						overridePendingTransition(R.anim.right_slide_in,
								R.anim.right_slide_out);
					}
				});
		findViewById(R.id.forgot).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						dialog = new Dialog(LoginActivity.this);
						dialog.setContentView(R.layout.dialog_forgot);
						dialog.setTitle("Forgot Password");

						mUsernameEmailView = (EditText) dialog
								.findViewById(R.id.usernameEmail);

						mUsernameEmailView
								.setOnEditorActionListener(new TextView.OnEditorActionListener() {
									@Override
									public boolean onEditorAction(
											TextView textView, int id,
											KeyEvent keyEvent) {
										if (id == R.id.usernameEmail
												|| id == EditorInfo.IME_NULL) {
											InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
											in.hideSoftInputFromWindow(
													mUsernameEmailView
															.getApplicationWindowToken(),
													InputMethodManager.HIDE_NOT_ALWAYS);
											attemptLogin();
											return true;
										}
										return false;
									}
								});

						mLoginFormView = dialog.findViewById(R.id.forgot_form);
						mLoginStatusView = dialog
								.findViewById(R.id.forgot_status);
						mLoginStatusMessageView = (TextView) dialog
								.findViewById(R.id.forgot_status_message);

						dialog.findViewById(R.id.reset_button)
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										attemptForgot();
									}
								});
						dialog.findViewById(R.id.cancel_button)
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										dialog.dismiss();
									}
								});

						dialog.show();
					}
				});
	}

	@Override
	protected void onResume() {
		Log.d("JadwalFutsal", "resume login");

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
				&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {
			//startActivity(new Intent(LoginActivity.this, TableActivity.class));
			startActivity(new Intent(LoginActivity.this, SplashActivity.class));
			finish();
		}
		super.onResume();
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid Username, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

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

		// Check for a valid Username address.
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (mUsername.length() < 4) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress);
			showProgress(true);
			mAuthTask = new LoginTask();
			mAuthTask.execute(mUsername, mPassword);
		}
	}

	public void attemptForgot() {
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
	private class LoginTask extends AsyncTask<String, Object, String> {

		private boolean isCancelled = false;

		@Override
		protected String doInBackground(String... params) {
			WebApi api = WebApi.getInstance();
			String result = api.Login2(getApplicationContext(), "login",
					params[0], params[1]);
			// if (result == null) {
			// int page = 0;
			// // ArrayList<PoIItem> favorites;
			// int favoriteSize = 0;
			// SharedPreferences pref = PreferenceManager
			// .getDefaultSharedPreferences(Login.this);
			// ScheduleDB db = ScheduleDB.getInstance(Login.this);
			// db.deleteAllFavorite();
			// try {
			// favorites = api.getListFavorite(
			// pref.getString(Extra.LOGIN_USER_ID_KEY, ""), -1,
			// page);
			// favoriteSize = favorites.size();
			// if (favoriteSize != 0) {
			// for (PoIItem poi : favorites) {
			// db.addFavorite(poi);
			// }
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			mAuthTask = null;
			showProgress(false);
			if (!isCancelled) {
				if (result == null) {
					finish();
//					startActivity(new Intent(LoginActivity.this,
//							TableActivity.class));
					startActivity(new Intent(LoginActivity.this,
							SplashActivity.class));

				} else {
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
				}

			}
			super.onPostExecute(result);
		}

		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}

	}

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

			// do nothing
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
				dialog.dismiss();
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
