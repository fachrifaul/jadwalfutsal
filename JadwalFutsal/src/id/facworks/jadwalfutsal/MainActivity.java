package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.db.DatabaseHelper;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.json.LapangJSON;
import id.facworks.jadwalfutsal.object.Lapang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by Raquib-ul-Alam Kanak on 7/21/2014. Website:
 * http://april-shower.com
 */
public class MainActivity extends Activity implements
		WeekView.MonthChangeListener, WeekView.EventClickListener,
		WeekView.EventLongPressListener {

	// private static final int TYPE_DAY_VIEW = 1;
	// private static final int TYPE_THREE_DAY_VIEW = 2;
	// private static final int TYPE_WEEK_VIEW = 3;
	// private int mWeekViewType = TYPE_THREE_DAY_VIEW;
	private WeekView mWeekView;

	// private List<WeekViewEvent> jadwalfutsal;
	private WeekViewEvent event;

	private SharedPreferences prefs;
	private LapangJSON task;
	// private AsyncGetDataFromWeb getallweb;
	private List<WeekViewEvent> newJadwal;

	private DatabaseHelper dbHelper;
	ArrayList<Lapang> daftarJadwal = new ArrayList<Lapang>();
	String id, tanggal, jam, status;

	private Spinner bank, jenis;
	private EditText tanggalnya, code_booking;
	private SubmitTask task_submit;

	private Random random = new Random();
	private static final String _CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STR_LENGTH = 7;

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;

		prefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);

		Log.d("Futsal",
				"id na " + prefs.getString(Extra.LOGIN_USER_ID_KEY, " "));

		dbHelper = DatabaseHelper.getInstance(this);
		daftarJadwal = dbHelper.getAllLapang();

		// OnAsyncResult asynResult = new OnAsyncResult() {
		//
		// @Override
		// public void onResultSuccess(final List<WeekViewEvent> jadwal) {
		// runOnUiThread(new Runnable() {
		// public void run() {
		// System.out.println("banyak : " + jadwal.size());
		// jadwalfutsal = jadwal;
		// }
		// });
		//
		// }
		// };
		//
		// task = new LapangJSON();
		// task.setOnResultListener(asynResult);
		// task.execute();

		// Get a reference for the week view in the layout.
		mWeekView = (WeekView) findViewById(R.id.weekView);
		// mWeekView.setOnClickListener(this);

		// Show a toast message about the touched event.
		mWeekView.setOnEventClickListener(this);

		// The week view has infinite scrolling horizontally. We have to provide
		// the events of a
		// month every time the month changes on the week view.
		mWeekView.setMonthChangeListener(this);

		// Set long press listener for events.
		mWeekView.setEventLongPressListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_today:
			mWeekView.goToToday();
			return true;

		case R.id.item_profile:
			return true;

		case R.id.item_exit:

			Editor editor = prefs.edit();
			editor.remove(Extra.LOGIN_USER_ID_KEY);
			editor.remove(Extra.LOGIN_TOKEN_KEY);
			editor.commit();

			finish();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public List<WeekViewEvent> onMonthChange(int newYear, final int newMonth) {

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
				event = new WeekViewEvent(1, status,
						Integer.parseInt(Tanggal[0]),
						Integer.parseInt(Tanggal[1]),
						Integer.parseInt(Tanggal[2]), Integer.parseInt(Jam[0]),
						Integer.parseInt(Jam[1]), Integer.parseInt(Tanggal[0]),
						Integer.parseInt(Tanggal[1]),
						Integer.parseInt(Tanggal[2]),
						Integer.parseInt(Jam[0]) + 1, Integer.parseInt(Jam[1]));
				if (status.toString().toLowerCase() == "kosong") {
					event.setColor(getResources().getColor(
							R.color.warna_isi_biasa));
				} else {
					event.setColor(getResources()
							.getColor(R.color.warna_kosong));
				}

				events.add(event);

			}
		}

		// // Populate the week view with some events.
		// Calendar startTime = Calendar.getInstance();
		// startTime.set(Calendar.HOUR_OF_DAY, 3);
		// startTime.set(Calendar.MINUTE, 0);
		// startTime.set(Calendar.MONTH, newMonth - 1);
		// startTime.set(Calendar.YEAR, newYear);
		// Calendar endTime = (Calendar) startTime.clone();
		// endTime.add(Calendar.HOUR, 1);
		// endTime.set(Calendar.MONTH, newMonth - 1);
		//
		// event = new WeekViewEvent(1, getEventTitle(startTime), startTime,
		// endTime);
		// event.setColor(getResources().getColor(R.color.event_color_01));
		// events.add(event);

		if (events != null) {
			System.out.println("eventes : " + events.size() + "\n");
		}

		return events;
	}

	private String getEventTitle(Calendar time) {
		return String.format("Event of %02d:%02d %s/%d",
				time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE),
				time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onEventClick(WeekViewEvent event, RectF eventRect) {
		// Toast.makeText(MainActivity.this, "Clicked " + event.getName(),
		// Toast.LENGTH_SHORT).show();
		System.out.println("eventisi : " + event.getName());
		if (event.getName().toLowerCase() == "isi") {
			Toast.makeText(this, "Sudah ada yang Booking!", Toast.LENGTH_SHORT)
					.show();
		}
		else if (event.getName().toLowerCase() == "kosong") {
			Toast.makeText(this, "Kosong !", Toast.LENGTH_SHORT)
					.show();
		}
//		} else {
//			bookingdialog();
//		}
	}

	@Override
	public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
		Toast.makeText(MainActivity.this,
				"Long pressed event: " + event.getName(), Toast.LENGTH_SHORT)
				.show();
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	//
	// // task = new LapangJSON();
	// // task.setOnResultListener(asynResult);
	// // task.execute();
	// final boolean _active = true;
	// final int _splashTime = 5500;
	// Thread splashThread = new Thread() {
	// // Timer Splash
	// public void run() {
	// try {
	//
	// getallweb = new AsyncGetDataFromWeb();
	// getallweb.execute();
	//
	// int waited = 0;
	// while (_active && (waited < _splashTime)) {
	// sleep(100);
	// if (_active) {
	// waited += 100;
	// }
	// }
	// } catch (InterruptedException e) {
	// // do nothing
	// } finally {
	// //
	// }
	// }
	// };
	//
	// super.onResume();
	// }

	private void bookingdialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_booking);
		dialog.setTitle("Booking");

		bank = (Spinner) dialog.findViewById(R.id.rekening);
		jenis = (Spinner) dialog.findViewById(R.id.jenisbooking);

		tanggalnya = (EditText) dialog.findViewById(R.id.tanggal);

		ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel);
		// if button is clicked, close the custom dialog
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		ImageView pick_calender = (ImageView) dialog
				.findViewById(R.id.pick_tanggal);
		// if button is clicked, close the custom dialog
		pick_calender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				picktanggal();

			}

		});

		code_booking = (EditText) dialog.findViewById(R.id.KodeBoooking);
		code_booking.setText(getRandomString());

		// Loading spinner data from database
		loadSpinnerData();

		Button submit = (Button) dialog.findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task_submit = new SubmitTask(dialog);
				task_submit.execute("");
			}
		});

		dialog.show();
	}

	private void picktanggal() {
		// TODO Auto-generated method stub
		// Process to get Current Date
		int mYear, mMonth, mDay;
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// Display Selected date in textbox
						tanggalnya.setText(year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth);

					}
				}, mYear, mMonth, mDay);
		dpd.show();
	}

	private void loadSpinnerData() {
		// database handler
		LocationsDB db = new LocationsDB(this);

		// Spinner Drop down elements
		List<String> lables = db.getAllLabels();
		List<String> jenis_booking = db.getAllJenis();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);
		ArrayAdapter<String> jenisAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, jenis_booking);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		jenisAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		bank.setAdapter(dataAdapter);
		jenis.setAdapter(jenisAdapter);
	}

	private int getRandomNumber() {
		int randomInt = 0;
		randomInt = random.nextInt(_CHAR.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public String getRandomString() {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STR_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = _CHAR.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	private String[] readInputValues() {
		String[] values = new String[4];

		values[0] = code_booking.getText().toString().trim();
		values[1] = tanggalnya.getText().toString();

		return values;
	}

	private class SubmitTask extends AsyncTask<String, Object, String> {

		// private LoadingFragment loading;

		private boolean isCancelled = false;
		Dialog dialog;

		private SubmitTask(Dialog dialog) {
			this.dialog = dialog;
		}

		@Override
		protected String doInBackground(String... params) {
			String[] values = readInputValues();
			try {
				WebApi api = WebApi.getInstance();
				return api.submit_lapang(getApplicationContext(), "1",
						values[0], "1", values[1], "03:00:00", "isi");
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			// return null;

		}

		@Override
		protected void onPostExecute(String result) {
			task = null;
			// showProgress(false);
			if (!isCancelled) {

				if (result == null) {
					//
					// startActivity(new Intent(RegisterActivity.this,
					// SplashActivity.class));
					dialog.dismiss();
					startActivity(new Intent(MainActivity.this,
							SplashActivity.class));
					finish();

				} else {
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result);
		}

		protected void onCancelled() {
			task = null;
			// showProgress(false);
		}
	}

}
