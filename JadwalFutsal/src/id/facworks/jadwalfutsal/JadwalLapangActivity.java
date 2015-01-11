package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.db.DatabaseHelper;
import id.facworks.jadwalfutsal.object.Lapang;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekView.EventClickListener;
import com.alamkanak.weekview.WeekView.EventLongPressListener;
import com.alamkanak.weekview.WeekView.MonthChangeListener;
import com.alamkanak.weekview.WeekViewEvent;

public class JadwalLapangActivity extends Activity {

	private WeekView mWeekView, mWeekView2;
	private WeekViewEvent event;

	private DatabaseHelper dbHelper;
	ArrayList<Lapang> daftarJadwal = new ArrayList<Lapang>();
	String id, tanggal, jam, status;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_jadwal_lapang);

		dbHelper = DatabaseHelper.getInstance(this);
		daftarJadwal = dbHelper.getAllLapang();

		mWeekView = (WeekView) findViewById(R.id.weekViewLapang1);
		mWeekView.setOnEventClickListener(new EventClickListener() {

			public void onEventClick(WeekViewEvent event, RectF eventRect) {

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
										JadwalLapangActivity.this,
										LoginActivity.class);
								startActivity(newIntent);
								overridePendingTransition(
										R.anim.right_slide_in,
										R.anim.right_slide_out);
							}
						});
				dialog.findViewById(R.id.cancel_dong_button)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								dialog.dismiss();
							}
						});

				dialog.show();

			}
		});
		mWeekView.setMonthChangeListener(new MonthChangeListener() {

			@Override
			public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

				final List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

				// try {

				// } catch (NullPointerException e) {

				// }

				int siz = daftarJadwal.size();
				for (int i = 0; i < siz; i++) {
					// id = daftarJadwal.get(i).getID();
					status = daftarJadwal.get(i).getstatus();
					jam = daftarJadwal.get(i).getjam();
					tanggal = daftarJadwal.get(i).gettanggal();

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

		mWeekView2 = (WeekView) findViewById(R.id.weekViewLapang2);
		mWeekView2.setOnEventClickListener(new EventClickListener() {

			public void onEventClick(WeekViewEvent event, RectF eventRect) {
				// TODO Auto-generated method stub

			}
		});
		mWeekView2.setMonthChangeListener(new MonthChangeListener() {

			@Override
			public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
				final List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
				return events;
			}
		});
		mWeekView2.setEventLongPressListener(new EventLongPressListener() {

			@Override
			public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
				// TODO Auto-generated method stub

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

		}

		return super.onOptionsItemSelected(item);
	}

}
