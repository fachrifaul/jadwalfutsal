package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.db.Constants.Extra;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

//import android.content.res.Resources;

public class HomeActivity extends TabActivity {
//	private static final String TAB_1 = "tab1";
//	private static final String TAB_2 = "tab2";

	private TabHost tabHost;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_home);

		// Resources resources= getResources();
		tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// tab Tentang
		intent = new Intent().setClass(this, TentangActivity.class);
		spec = tabHost
				.newTabSpec("List")
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_about,
								R.string.profil)).setContent(intent);
		tabHost.addTab(spec);

		// tab Jadwal
		intent = new Intent().setClass(this, JadwalLapangActivity.class);
		spec = tabHost
				.newTabSpec("Bookmark")
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_event,
								R.string.action_jadwal)).setContent(intent);
		tabHost.addTab(spec);

		// tab Kontak
		intent = new Intent().setClass(this, KontakActivity.class);
		spec = tabHost
				.newTabSpec("Setting")
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_ring_volume,
								R.string.kontak)).setContent(intent);
		tabHost.addTab(spec);

		// tab Bantuan
		intent = new Intent().setClass(this, BantuanActivity.class);
		spec = tabHost
				.newTabSpec("Setting")
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_help,
								R.string.bantuan)).setContent(intent);

		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);
		tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 80;
		tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 80;
		tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 80;
		tabHost.getTabWidget().getChildAt(3).getLayoutParams().height = 80;

		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundResource(R.drawable.tab_indicator_ab_example); // selected
		tabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {

				//invalidateOptionsMenu();

				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {

					tabHost.getTabWidget().getChildAt(i)
							.setBackgroundResource(android.R.color.transparent); // unselected
				}
				tabHost.getTabWidget()
						.getChildAt(tabHost.getCurrentTab())
						.setBackgroundResource(
								R.drawable.tab_indicator_ab_example); // selected

			}
		});
		//tabHost.setCurrentTabByTag(TAB_1);

	}

//	public boolean onCreateOptionsMenu(Menu menu) {
//		
//
//		final String currentTab = tabHost.getCurrentTabTag();
//
//		if (TAB_1.equals(currentTab)) {
//			getMenuInflater().inflate(R.menu.main, menu);
//		} else if (TAB_2.equals(currentTab)) {
//			getMenuInflater().inflate(R.menu.main2, menu);
//		}
//		return true;
//	}

	private class Tampilan extends LinearLayout {

		public Tampilan(Context context, int gambar, int label) {
			super(context);
			// TODO Auto-generated constructor stub

			ImageView iv = new ImageView(context);
			TextView tv = new TextView(context);

			iv.setImageResource(gambar);
			// tv.setText(label);
			// tv.setTextColor(R.color.white);
			tv.setGravity(0x01); // posisi ditengah

			setOrientation(LinearLayout.VERTICAL);
			addView(iv);
			addView(tv);

		}

	}

	@Override
	protected void onResume() {
		Log.d("JadwalFutsal", "resume login");

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
				&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {
			// startActivity(new Intent(LoginActivity.this,
			// TableActivity.class));
			// startActivity(new Intent(LoginActivity.this,
			// SplashActivity.class));
			// finish();
		}
		super.onResume();
	}

}
