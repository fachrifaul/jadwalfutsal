package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.object.Lapang;
import id.facworks.jadwalfutsal.object.User;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekView.EventClickListener;
import com.alamkanak.weekview.WeekView.MonthChangeListener;
import com.alamkanak.weekview.WeekViewEvent;

public class JadwalLapangActivity extends Activity {

	private WeekView mWeekView, mWeekView2;
	private WeekViewEvent event;

	private LocationsDB dbHelper_lapang;
	ArrayList<Lapang> daftarJadwal1 = new ArrayList<Lapang>();
	ArrayList<Lapang> daftarJadwal2 = new ArrayList<Lapang>();
	String id, tanggal, jam_awal, jam_akhir, status, kategori_lapang, code;

	private static final int TYPE_THREE_DAY_VIEW = 2;
	private static final int TYPE_WEEK_VIEW = 3;
	private int mWeekViewType = TYPE_THREE_DAY_VIEW;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_jadwal_lapang);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

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

					// bookingdialog();

					System.out.println("idlawan: " + event.getId());
					System.out.println("name: " + event.getName());
					// int siz = daftarJadwal1.size();
					// for (int i = 0; i < siz; i++) {
					// code = daftarJadwal1.get(i).getcode_booking();
					// String[] codenya = code.split("\\-");
					// if (Integer.parseInt(codenya[1]) == 3) {

					// }else{
					//
					// }
					// }
					carilawan(String.valueOf(event.getId()));

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
					id = daftarJadwal1.get(i).getuser_id();
					code = daftarJadwal1.get(i).getcode_booking();
					status = daftarJadwal1.get(i).getstatus();
					jam_awal = daftarJadwal1.get(i).getjam_awal();
					jam_akhir = daftarJadwal1.get(i).getjam_akhir();
					tanggal = daftarJadwal1.get(i).gettanggal();

					// if (kategori_lapang == "1") {
					String[] codenya = code.split("\\-");
					String Tanggal[] = tanggal.split("\\-");
					String Jam_Awal[] = jam_awal.split("\\:");
					String Jam_Akhir[] = jam_akhir.split("\\:");

					System.out.println(" id : " + id);
					System.out.println(" idlong : " + Long.parseLong(id));

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam_Awal[0] + " - " + Jam_Awal[1]
							+ " - ");
					System.out.println(Jam_Akhir[0] + " - " + Jam_Akhir[1]
							+ " - ");

					System.out.println("---------" + newMonth + "--"
							+ Integer.parseInt(Tanggal[1]) + "-"
							+ status.toString().toLowerCase());

					if (newMonth == Integer.parseInt(Tanggal[1])) {
						event = new WeekViewEvent(Long.parseLong(id), status,
								Integer.parseInt(Tanggal[0]), Integer
										.parseInt(Tanggal[1]), Integer
										.parseInt(Tanggal[2]), Integer
										.parseInt(Jam_Awal[0]), Integer
										.parseInt(Jam_Awal[1]), Integer
										.parseInt(Tanggal[0]), Integer
										.parseInt(Tanggal[1]), Integer
										.parseInt(Tanggal[2]), Integer
										.parseInt(Jam_Akhir[0]), Integer
										.parseInt(Jam_Akhir[1]));

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
					jam_awal = daftarJadwal2.get(i).getjam_awal();
					jam_akhir = daftarJadwal2.get(i).getjam_akhir();
					tanggal = daftarJadwal2.get(i).gettanggal();

					String Tanggal[] = tanggal.split("\\-");
					String Jam_Awal[] = jam_awal.split("\\:");
					String Jam_Akhir[] = jam_akhir.split("\\:");

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam_Awal[0] + " - " + Jam_Awal[1]
							+ " - ");
					System.out.println(Jam_Akhir[0] + " - " + Jam_Akhir[1]
							+ " - ");

					System.out.println("---------" + newMonth + "--"
							+ Integer.parseInt(Tanggal[1]) + "-"
							+ status.toString().toLowerCase());

					if (newMonth == Integer.parseInt(Tanggal[1])) {
						event = new WeekViewEvent(1, status, Integer
								.parseInt(Tanggal[0]), Integer
								.parseInt(Tanggal[1]), Integer
								.parseInt(Tanggal[2]), Integer
								.parseInt(Jam_Awal[0]), Integer
								.parseInt(Jam_Awal[1]), Integer
								.parseInt(Tanggal[0]), Integer
								.parseInt(Tanggal[1]), Integer
								.parseInt(Tanggal[2]), Integer
								.parseInt(Jam_Akhir[0]), Integer
								.parseInt(Jam_Akhir[1]));
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
						efekdong();
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

	public void efekdong() {
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}

	private void bookingdialog() {

		finish();
		startActivity(new Intent(getApplicationContext(), BookingActivity.class));
		efekdong();
	}

	public void carilawan(String id_lawan) {
		final Dialog dialog;
		dialog = new Dialog(JadwalLapangActivity.this);
		dialog.setContentView(R.layout.layout_dialog_carilawan);
		dialog.setTitle("Cari Lawan !");

		TextView nama = (TextView) dialog.findViewById(R.id.textnama);
		nama.setText("Nama : " + asik(id_lawan).get(0).getuser());
		TextView nohp = (TextView) dialog.findViewById(R.id.textnohp);
		nohp.setText("Nohp : " + asik(id_lawan).get(0).getnohp());

		dialog.findViewById(R.id.ok_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
					}
				});

		dialog.show();
	}

	public ArrayList<User> asik(String id_lawan) {

		System.out.println("idlawan: " + id_lawan);

		try {
			WebApi api = WebApi.getInstance();
			return api.getUser(id_lawan);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public void goToday() {
		// TODO Auto-generated method stub
		System.out.println("yes!");
		mWeekView.goToToday();
		mWeekView2.goToToday();
	}

	public void go3dayview() {
		if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
			mWeekViewType = TYPE_THREE_DAY_VIEW;
			mWeekView.setNumberOfVisibleDays(3);
			mWeekView2.setNumberOfVisibleDays(3);

			// Lets change some dimensions to best fit the view.
			mWeekView.setColumnGap((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
							.getDisplayMetrics()));
			mWeekView.setTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 12, getResources()
							.getDisplayMetrics()));
			mWeekView.setEventTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 12, getResources()
							.getDisplayMetrics()));

			mWeekView2.setColumnGap((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
							.getDisplayMetrics()));
			mWeekView2.setTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 12, getResources()
							.getDisplayMetrics()));
			mWeekView2.setEventTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 12, getResources()
							.getDisplayMetrics()));
		}
	}

	public void goweekview() {
		if (mWeekViewType != TYPE_WEEK_VIEW) {

			mWeekViewType = TYPE_WEEK_VIEW;
			mWeekView.setNumberOfVisibleDays(7);
			mWeekView2.setNumberOfVisibleDays(7);

			// Lets change some dimensions to best fit the view.
			mWeekView.setColumnGap((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
							.getDisplayMetrics()));
			mWeekView.setTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 10, getResources()
							.getDisplayMetrics()));
			mWeekView.setEventTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 10, getResources()
							.getDisplayMetrics()));

			mWeekView2.setColumnGap((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
							.getDisplayMetrics()));
			mWeekView2.setTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 10, getResources()
							.getDisplayMetrics()));
			mWeekView2.setEventTextSize((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 10, getResources()
							.getDisplayMetrics()));
		}
	}

}
