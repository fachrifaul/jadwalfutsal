package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.object.Lapang;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekView.EventClickListener;
import com.alamkanak.weekview.WeekView.MonthChangeListener;
import com.alamkanak.weekview.WeekViewEvent;

public class JadwalLapangActivity extends Activity {

	private WeekView mWeekView, mWeekView2;
	private WeekViewEvent event;

	// private DatabaseHelper dbHelper;
	private LocationsDB dbHelper_lapang;
	// ArrayList<Lapang> daftarJadwal = new ArrayList<Lapang>();
	ArrayList<Lapang> daftarJadwal1 = new ArrayList<Lapang>();
	ArrayList<Lapang> daftarJadwal2 = new ArrayList<Lapang>();
	String id, tanggal, jam, status, kategori_lapang, code;

	// private Spinner bank, jenis, jammulai, jamakhir;
	// private EditText tanggalnya, code_booking;
	// private SubmitTask task_submit;
	//
	// private Random random = new Random();
	// private static final String _CHAR =
	// "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	// private static final int RANDOM_STR_LENGTH = 7;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_jadwal_lapang);

		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		Log.d("Futsal",
				"id na " + prefs.getString(Extra.LOGIN_USER_ID_KEY, " "));

		dbHelper_lapang = LocationsDB.getInstance(this);
		daftarJadwal1 = dbHelper_lapang.getAllLapang1();
		daftarJadwal2 = dbHelper_lapang.getAllLapang2();

		mWeekView = (WeekView) findViewById(R.id.weekViewLapang1);
		mWeekView.setOnEventClickListener(new EventClickListener() {

			public void onEventClick(WeekViewEvent event, RectF eventRect) {

				if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
						&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "")
								.equals(""))) {
					bookingdialog();
				} else {
					logindialog();
				}

			}
		});
		mWeekView.setMonthChangeListener(new MonthChangeListener() {

			@Override
			public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

				final List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

				int siz = daftarJadwal1.size();
				for (int i = 0; i < siz; i++) {
					// id = daftarJadwal.get(i).getID();
					// kategori_lapang =
					// daftarJadwal1.get(i).getkategori_lapang();
					code = daftarJadwal1.get(i).getcode_booking();
					status = daftarJadwal1.get(i).getstatus();
					jam = daftarJadwal1.get(i).getjam();
					tanggal = daftarJadwal1.get(i).gettanggal();

					// if (kategori_lapang == "1") {
					String[] codenya = code.split("\\-");
					String Tanggal[] = tanggal.split("\\-");
					String Jam[] = jam.split("\\:");

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam[0] + " - " + Jam[1] + " - ");

					System.out.println("---------" + newMonth + "--"
							+ Integer.parseInt(Tanggal[1]) + "-"
							+ status.toString().toLowerCase());

					if (newMonth == Integer.parseInt(Tanggal[1])) {
						event = new WeekViewEvent(1, status, Integer
								.parseInt(Tanggal[0]), Integer
								.parseInt(Tanggal[1]), Integer
								.parseInt(Tanggal[2]),
								Integer.parseInt(Jam[0]), Integer
										.parseInt(Jam[1]), Integer
										.parseInt(Tanggal[0]), Integer
										.parseInt(Tanggal[1]), Integer
										.parseInt(Tanggal[2]), Integer
										.parseInt(Jam[0]) + 1, Integer
										.parseInt(Jam[1]));
						// if (status.toString().toLowerCase() == "kosong") {
						// event.setColor(getResources().getColor(
						// R.color.warna_isi_biasa));
						// } else {
						// event.setColor(getResources().getColor(
						// R.color.warna_kosong));
						// }
						// System.out.println("---------"
						// + status.toString().toLowerCase());
						if (Integer.parseInt(codenya[1]) == 1) {
							event.setColor(getResources().getColor(
									R.color.biru_member));
						} else if (Integer.parseInt(codenya[1]) == 2) {
							event.setColor(getResources().getColor(
									R.color.kuning_biasa));
						} else if (Integer.parseInt(codenya[1]) == 3) {
							event.setColor(getResources().getColor(
									R.color.merah_lawan));
						} else {
							event.setColor(getResources().getColor(
									R.color.hijau_teuing));
						}

						events.add(event);
					}
				}

				if (events != null) {
					System.out.println("eventes : " + events.size() + "\n");
				}

				return events;
			}
		});

		mWeekView2 = (WeekView) findViewById(R.id.weekViewLapang2);
		mWeekView2.setOnEventClickListener(new EventClickListener() {

			public void onEventClick(WeekViewEvent event, RectF eventRect) {

				if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
						&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "")
								.equals(""))) {
					bookingdialog();
				} else {
					logindialog();
				}
			}
		});
		mWeekView2.setMonthChangeListener(new MonthChangeListener() {

			@Override
			public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
				final List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
				int siz = daftarJadwal2.size();
				for (int i = 0; i < siz; i++) {
					// id = daftarJadwal.get(i).getID();
					// kategori_lapang =
					// daftarJadwal2.get(i).getkategori_lapang();
					status = daftarJadwal2.get(i).getstatus();
					jam = daftarJadwal2.get(i).getjam();
					tanggal = daftarJadwal2.get(i).gettanggal();

					String Tanggal[] = tanggal.split("\\-");
					String Jam[] = jam.split("\\:");

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam[0] + " - " + Jam[1] + " - ");

					System.out.println("---------" + newMonth + "--"
							+ Integer.parseInt(Tanggal[1]) + "-"
							+ status.toString().toLowerCase());

					if (newMonth == Integer.parseInt(Tanggal[1])) {
						event = new WeekViewEvent(1, status, Integer
								.parseInt(Tanggal[0]), Integer
								.parseInt(Tanggal[1]), Integer
								.parseInt(Tanggal[2]),
								Integer.parseInt(Jam[0]), Integer
										.parseInt(Jam[1]), Integer
										.parseInt(Tanggal[0]), Integer
										.parseInt(Tanggal[1]), Integer
										.parseInt(Tanggal[2]), Integer
										.parseInt(Jam[0]) + 1, Integer
										.parseInt(Jam[1]));
						if (status.toString().toLowerCase() == "kosong") {
							event.setColor(getResources().getColor(
									R.color.warna_isi_biasa));
						} else {
							event.setColor(getResources().getColor(
									R.color.warna_kosong));
						}
						events.add(event);
					}
				}

				if (events != null) {
					System.out.println("eventes : " + events.size() + "\n");
				}

				return events;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getParent().getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {

		case R.id.action_today:
			mWeekView.goToToday();
			mWeekView2.goToToday();
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
			overridePendingTransition(R.anim.right_slide_in,
					R.anim.right_slide_out);
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		Log.d("JadwalFutsal", "resume login");

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if ((prefs.contains(Extra.LOGIN_USER_ID_KEY))
				&& (!prefs.getString(Extra.LOGIN_USER_ID_KEY, "").equals(""))) {

		}
		super.onResume();
	}

	private void logindialog() {
		final Dialog dialog;
		dialog = new Dialog(JadwalLapangActivity.this);
		dialog.setContentView(R.layout.dialog_login);
		dialog.setTitle("Perhatian !");

		dialog.findViewById(R.id.login_dong_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
						finish();
						Intent newIntent = new Intent(
								JadwalLapangActivity.this, LoginActivity.class);
						startActivity(newIntent);
						overridePendingTransition(R.anim.right_slide_in,
								R.anim.right_slide_out);
					}
				});
		dialog.findViewById(R.id.cancel_dong_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});

		dialog.show();
	}

	private void bookingdialog() {
		finish();
		startActivity(new Intent(getApplicationContext(), BookingActivity.class));
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}

}
