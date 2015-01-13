//package id.facworks.jadwalfutsal;
//
//import id.facworks.jadwalfutsal.db.Constants.Extra;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//public class HomeActivity2 extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home);
//
//		TextView textsplash = (TextView) findViewById(R.id.profil_rfa);
//
//		Typeface face = Typeface.createFromAsset(getAssets(),
//				"fonts/Lato-Regular.ttf");
//		textsplash.setTypeface(face);
//
//		Button jadwal = (Button) findViewById(R.id.jadwal);
//		jadwal.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				finish();
//				Intent newIntent = new Intent(HomeActivity2.this,
//						MainActivity2.class);
//				startActivity(newIntent);
//				overridePendingTransition(R.anim.right_slide_in,
//						R.anim.right_slide_out);
//			}
//		});
//
//	}
//
//	@Override
//	protected void onResume() {
//		Log.d("JadwalFutsal", "resume login");
//
//		SharedPreferences prefs = PreferenceManager
//				.getDefaultSharedPreferences(getApplicationContext());
//		if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
//				&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {
//			// startActivity(new Intent(LoginActivity.this,
//			// TableActivity.class));
//			startActivity(new Intent(HomeActivity2.this, SplashActivity.class));
//			finish();
//		}
//		super.onResume();
//	}
//
//}
