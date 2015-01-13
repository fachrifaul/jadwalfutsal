package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.webservice.GetAllLapang;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity2 extends Activity {
	protected boolean _active = true;
	protected int _splashTime = 5500;

	private AsyncGetDataFromWeb getallweb;

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		mContext = this;

		TextView textsplash = (TextView) findViewById(R.id.textSplash);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		textsplash.setTypeface(face);

		Thread splashThread = new Thread() {
			// Timer Splash
			public void run() {
				try {

					getallweb = new AsyncGetDataFromWeb();
					getallweb.execute();

					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(getApplicationContext());
					if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
							&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "")
									.equals(""))) {
					}
					finish();
					startActivity(new Intent(SplashActivity2.this,
							HomeActivity.class));
				}
			}
		};
		splashThread.start();
	}

	class AsyncGetDataFromWeb extends AsyncTask<Void, Integer, Void> {

		protected void onPreExecute() {
			Log.i("coba", "async start");
		}

		@Override
		protected void onPostExecute(Void result) {
			// Toast.makeText(mContext,
			// "Provider enabled oleh the user. GPS online",
			// Toast.LENGTH_LONG).show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetAllLapang gar = new GetAllLapang(mContext);
			gar.getAllJadwal();

			return null;
		}

	}
	//
	// @Override
	// protected void onResume() {
	// Log.d("JadwalFutsal", "resume login");
	//
	// SharedPreferences prefs = PreferenceManager
	// .getDefaultSharedPreferences(getApplicationContext());
	// if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
	// && (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {
	// // startActivity(new Intent(LoginActivity.this,
	// // TableActivity.class));
	// startActivity(new Intent(SplashActivity2.this, HomeActivity.class));
	// finish();
	// }
	// super.onResume();
	// }
}
