package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.db.Constants.Extra;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

//import android.content.res.Resources;

public class HomeActivity extends TabActivity {
	private String TAB_1 = "profil";
	private String TAB_2 = "jadwal";
	private String TAB_3 = "kontak";
	private String TAB_4 = "bantuan";
	private static final int GO_TODAY = 1;
	private static final int PROFIL = 2;
	private static final int EXIT = 3;
	private TabHost tabHost;
	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_home);

		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		// Resources resources= getResources();
		tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// tab Tentang
		intent = new Intent().setClass(this, TentangActivity.class);
		spec = tabHost
				.newTabSpec(TAB_1)
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_about,
								R.string.profil)).setContent(intent);
		tabHost.addTab(spec);

		// tab Jadwal
		intent = new Intent().setClass(this, JadwalLapangActivity.class);
		spec = tabHost
				.newTabSpec(TAB_2)
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_event,
								R.string.action_jadwal)).setContent(intent);
		tabHost.addTab(spec);

		// tab Kontak
		intent = new Intent().setClass(this, KontakActivity.class);
		spec = tabHost
				.newTabSpec(TAB_3)
				.setIndicator(
						new Tampilan(this, R.drawable.ic_action_ring_volume,
								R.string.kontak)).setContent(intent);
		tabHost.addTab(spec);

		// tab Bantuan
		intent = new Intent().setClass(this, BantuanActivity.class);
		spec = tabHost
				.newTabSpec(TAB_4)
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

				invalidateOptionsMenu();

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
		tabHost.setCurrentTabByTag(TAB_1);

	}

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

		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
				&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {

		}
		super.onResume();
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		String currentTab = tabHost.getCurrentTabTag();
		MenuInflater inflater = getMenuInflater();
		if (TAB_2.equals(currentTab)) {
			inflater.inflate(R.menu.main2, menu);

			prefs = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());

			String idna = prefs.getString(Extra.LOGIN_USER_ID_KEY, "");
			System.out.println("idna: " + idna);

			boolean visible = true;

			if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
					&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "")
							.equals(""))) {
				MenuItem item_login = menu.findItem(R.id.item_login);
				item_login.setVisible(!visible);
				return true;
			} else {
				MenuItem item_booking = menu.findItem(R.id.item_booking);
				item_booking.setVisible(!visible);
				MenuItem item_profile = menu.findItem(R.id.item_profile);
				item_profile.setVisible(!visible);
				MenuItem item_exit = menu.findItem(R.id.item_exit);
				item_exit.setVisible(!visible);
				return true;
			}

		}

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		JadwalLapangActivity activity = (JadwalLapangActivity) getLocalActivityManager()
				.getActivity(TAB_2);
		switch (item.getItemId()) {
		case R.id.action_today:
			activity.goToday();
			return true;

		case R.id.action_three_day_view:
			item.setChecked(!item.isChecked());
			activity.go3dayview();
			return true;

		case R.id.action_week_view:
			item.setChecked(!item.isChecked());
			activity.goweekview();
			return true;

		case R.id.item_booking:
			finish();
			startActivity(new Intent(getApplicationContext(),
					BookingActivity.class));
			efekdong();
			return true;

		case R.id.item_login:

			finish();
			startActivity(new Intent(getApplicationContext(),
					LoginActivity.class));
			efekdong();
			return true;

		case R.id.item_profile:
			return true;

		case R.id.item_exit:

			Editor editor = prefs.edit();
			editor.remove(Extra.LOGIN_USER_ID_KEY);
			editor.remove(Extra.LOGIN_TOKEN_KEY);
			editor.commit();

			finish();
			startActivity(new Intent(getApplicationContext(),
					HomeActivity.class));
			efekdong();
			return true;

		}
		return true;
	}

	public void efekdong() {
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}
}
