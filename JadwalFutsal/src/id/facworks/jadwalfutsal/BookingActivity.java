package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.view.HintAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends Activity {

	private Spinner bank, jenis, jammulai, jamakhir, lapang;
	private EditText tanggalnya, code_booking, nohp, jumlah_transfer;
	private SubmitTask task_submit;

	private Random random = new Random();
	private static final String _CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STR_LENGTH = 7;

	private SharedPreferences prefs;
	String idna;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_booking);

		bank = (Spinner) findViewById(R.id.rekening);
		jenis = (Spinner) findViewById(R.id.jenisbooking);
		jammulai = (Spinner) findViewById(R.id.jam_mulai);
		jamakhir = (Spinner) findViewById(R.id.jam_akhir);
		lapang = (Spinner) findViewById(R.id.lapang);

		tanggalnya = (EditText) findViewById(R.id.tanggal);

		Button cancel = (Button) findViewById(R.id.cancel);
		// if button is clicked, close the custom dialog
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						HomeActivity.class));
				overridePendingTransition(R.anim.right_slide_in,
						R.anim.right_slide_out);
			}
		});
		ImageView pick_calender = (ImageView) findViewById(R.id.pick_tanggal);
		// if button is clicked, close the custom dialog
		pick_calender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				picktanggal();

			}

		});

		code_booking = (EditText) findViewById(R.id.KodeBoooking);
		code_booking.setText(getRandomString());

		// Loading spinner data from database
		loadSpinnerData();

		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task_submit = new SubmitTask();
				task_submit.execute("");
			}
		});

		TextView textView1 = (TextView) findViewById(R.id.textView1);
		nohp = (EditText) findViewById(R.id.nohp);
		jumlah_transfer = (EditText) findViewById(R.id.jumlah_transfer);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		textView1.setTypeface(face);
		tanggalnya.setTypeface(face);
		code_booking.setTypeface(face);
		nohp.setTypeface(face);
		jumlah_transfer.setTypeface(face);

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			startActivity(new Intent(getApplicationContext(),
					HomeActivity.class));
			overridePendingTransition(R.anim.right_slide_in,
					R.anim.right_slide_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		List<String> jam_mulai = db.getAllJam();
		jam_mulai.remove(13);
		List<String> jam_akhir = db.getAllJam();
		jam_akhir.remove(0);
		// System.out.println("size: " + jam_akhir.size());
		// System.out.println("so: " + (jam_akhir.size() - 1));

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);
		ArrayAdapter<String> jenisAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, jenis_booking);
		HintAdapter jam_mulai_Adapter = new HintAdapter(
				getApplicationContext(), jam_mulai, R.layout.spinner_item);
		HintAdapter jam_akhir_Adapter = new HintAdapter(
				getApplicationContext(), jam_akhir, R.layout.spinner_item);
		ArrayAdapter<CharSequence> lapangadapter = ArrayAdapter
				.createFromResource(this, R.array.nama_lapang,
						android.R.layout.simple_spinner_item);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		jenisAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		jam_mulai_Adapter.setDropDownViewResource(R.layout.spinner_dropdown);
		jam_mulai_Adapter.add("Jam Mulai");

		jam_akhir_Adapter.setDropDownViewResource(R.layout.spinner_dropdown);
		jam_akhir_Adapter.add("Jam Akhir");

		lapangadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		bank.setAdapter(dataAdapter);
		jenis.setAdapter(jenisAdapter);

		jammulai.setAdapter(jam_mulai_Adapter);
		jammulai.setSelection(jam_mulai_Adapter.getCount());

		jamakhir.setAdapter(jam_akhir_Adapter);
		jamakhir.setSelection(jam_akhir_Adapter.getCount());

		lapang.setAdapter(lapangadapter);
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
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		idna = prefs.getString(Extra.LOGIN_USER_ID_KEY, "");
		System.out.println("idna: " + idna);

		String[] values = new String[6];

		values[0] = code_booking.getText().toString().trim();
		values[1] = tanggalnya.getText().toString();
		values[2] = jammulai.getSelectedItem().toString();
		values[3] = lapang.getSelectedItem().toString();
		values[4] = String.valueOf(jenis.getSelectedItemPosition());
		values[5] = idna;
		return values;
	}

	private class SubmitTask extends AsyncTask<String, Object, String> {

		// private LoadingFragment loading;

		private boolean isCancelled = false;

		@Override
		protected String doInBackground(String... params) {
			String[] values = readInputValues();
			String[] lapang = values[3].split("\\-");

			try {
				WebApi api = WebApi.getInstance();
				return api.submit_lapang(getApplicationContext(), values[5],
						(values[0] + "-" + values[4]), lapang[1], values[1],
						values[2] + ":00", "isi");
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			// return null;

		}

		@Override
		protected void onPostExecute(String result) {

			// showProgress(false);
			if (!isCancelled) {

				if (result == null) {

					startActivity(new Intent(getApplicationContext(),
							SplashActivity2.class));
					finish();

				} else {
					Toast.makeText(getApplicationContext(), result,
							Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result);
		}

		protected void onCancelled() {
			// task = null;
			// showProgress(false);
		}
	}

}
