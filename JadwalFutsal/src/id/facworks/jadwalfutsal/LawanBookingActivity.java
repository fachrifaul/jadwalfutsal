package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.db.LocationsDB;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LawanBookingActivity extends Activity {

	private Spinner bank, lapang;
	private EditText code_booking, nohp, jumlah_transfer;
	private SubmitTask task_submit;

	private Random random = new Random();
	private static final String _CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STR_LENGTH = 7;

	private SharedPreferences prefs;
	String idna;
	int jam_awal, menit_awal, jam_akhir, menit_akhir, hari_awal, bulan_awal,
			tahun_awal;
	String tanggal, awal_jam, akhir_jam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_lawan_booking);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			jam_awal = extras.getInt("jam_awal");
			menit_awal = extras.getInt("menit_awal");
			jam_akhir = extras.getInt("jam_akhir");
			menit_akhir = extras.getInt("menit_akhir");
			hari_awal = extras.getInt("hari_awal");
			bulan_awal = extras.getInt("bulan_awal");
			tahun_awal = extras.getInt("tahun_awal");
		}

		tanggal = String.valueOf(tahun_awal) + "-" + String.valueOf(bulan_awal)
				+ "-" + String.valueOf(hari_awal);
		awal_jam = String.valueOf(jam_awal) + ":" + String.valueOf(menit_awal);
		akhir_jam = String.valueOf(jam_akhir) + ":"
				+ String.valueOf(menit_akhir);

		bank = (Spinner) findViewById(R.id.rekening);

		lapang = (Spinner) findViewById(R.id.lapang);

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

	private void loadSpinnerData() {
		// database handler
		LocationsDB db = new LocationsDB(this);

		// Spinner Drop down elements
		List<String> lables = db.getAllLabels();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);
		ArrayAdapter<CharSequence> lapangadapter = ArrayAdapter
				.createFromResource(this, R.array.nama_lapang,
						android.R.layout.simple_spinner_item);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lapangadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		bank.setAdapter(dataAdapter);

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
		values[1] = tanggal;
		values[2] = awal_jam;
		values[3] = lapang.getSelectedItem().toString();
		values[4] = idna;
		values[5] = akhir_jam;
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
				return api.submit_lapang(getApplicationContext(), values[4],
						(values[0] + "-" + 4), lapang[1], values[1], values[2]
								+ ":00", values[5] + ":00", "kosong");
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
					finish();
					startActivity(new Intent(getApplicationContext(),
							SplashActivity2.class));

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
