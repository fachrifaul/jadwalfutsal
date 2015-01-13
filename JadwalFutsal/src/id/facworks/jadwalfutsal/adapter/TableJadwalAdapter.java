package id.facworks.jadwalfutsal.adapter;

import id.facworks.jadwalfutsal.R;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.object.Jadwal;
import id.facworks.jadwalfutsal.object.JadwalJam;
import id.facworks.jadwalfutsal.object.LapangFromJSON;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

public class TableJadwalAdapter extends BaseTableAdapter {

	// private LapangJSON task;
	Context context;
	Spinner bank, jenis;
	EditText tanggal;
	private final JadwalJam nolapang[];
	private final String waktu[] = { "Jam", "Senin", "Selasa", "Rabu", "Kamis",
			"Jumat", "Sabtu", "Minggu" };

	private final int[] widths = { 100, 80, 80, 80, 80, 80, 80, 80, };
	private final float density;

	Random random = new Random();
	private static final String _CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STR_LENGTH = 7;

	public LapangFromJSON newPois = add(lapangs);
	protected ArrayList<LapangFromJSON> nolapangss = new ArrayList<LapangFromJSON>();
	//nolapangss = new ArrayList<LapangFromJSON>();
	

	public TableJadwalAdapter(Context context,
			ArrayList<LapangFromJSON> arrayList) {
		this.context = context;
		
		nolapangss.add(lapangs);
		
		nolapang = new JadwalJam[] { new JadwalJam("Lapang 1"),
				new JadwalJam("Lapang 2") };

		density = context.getResources().getDisplayMetrics().density;

		nolapang[0].list.add(new Jadwal("09.00-10.00", "Isi", "Kosong", "Isi",
				"Isi", "Isi", "Kosong", "Isi"));
		nolapang[0].list.add(new Jadwal("10.00-11.00", "Isi", "Kosong", "Isi",
				"Isi", "Isi", "Kosong", "Isi"));
		nolapang[0].list.add(new Jadwal("11.00-12.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("12.00-13.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("13.00-14.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("14.00-15.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("15.00-16.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("16.00-17.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("17.00-18.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("18.00-19.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("19.00-20.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("21.00-22.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("22.00-23.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[0].list.add(new Jadwal("23.00-00.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));

		nolapang[1].list.add(new Jadwal("09.00-10.00", "Isi", "Kosong", "Isi",
				"Isi", "Isi", "Kosong", "Isi"));
		nolapang[1].list.add(new Jadwal("10.00-11.00", "Isi", "Kosong", "Isi",
				"Isi", "Isi", "Kosong", "Isi"));
		nolapang[1].list.add(new Jadwal("11.00-12.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("12.00-13.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("13.00-14.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("14.00-15.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("15.00-16.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("16.00-17.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("17.00-18.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("18.00-19.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("19.00-20.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("21.00-22.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("22.00-23.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));
		nolapang[1].list.add(new Jadwal("23.00-00.00", "Kosong", "Kosong",
				"Isi", "Isi", "Isi", "Isi", "Isi"));

	}

	@Override
	public int getRowCount() {
		return 30;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent) {
		final View view;

		switch (getItemViewType(row, column)) {
		case 0:
			view = getFirstHeader(row, column, convertView, parent);
			break;
		case 1:
			view = getHeader(row, column, convertView, parent);
			break;
		case 2:
			view = getFirstBody(row, column, convertView, parent);
			break;
		case 3:
			view = getBody(row, column, convertView, parent);
			break;
		case 4:
			view = getFamilyView(row, column, convertView, parent);
			break;
		default:
			throw new RuntimeException("wtf?");
		}
		return view;
	}

	private View getFirstHeader(int row, int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table_header_first,
					parent, false);
		}
		TextView header = ((TextView) convertView.findViewById(R.id.text1));
		header.setText(waktu[0]);
		return convertView;
	}

	private View getHeader(int row, int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table_header, parent,
					false);
		}
		TextView header = ((TextView) convertView.findViewById(R.id.text1));
		header.setText(waktu[column + 1]);
		return convertView;
	}

	private View getFirstBody(int row, int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table_first, parent,
					false);
		}
		convertView
				.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color3
						: R.drawable.bg_table_color3);
		((TextView) convertView.findViewById(R.id.text1))
				.setText(getStatusLapang(row).data[column + 1]);
		return convertView;
	}

	private View getBody(final int row, final int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table, parent, false);
		}
		// convertView
		// .setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1
		// : R.drawable.bg_table_color2);
		int no_lapang = 0;

		// task = new LapangJSON();
		// task.execute();

		// String tasi = task.get();
		// System.out.println("tasi: " + tasi);

		// int ukuran = newPois.size();
		// System.out.println("ukuran: " + newPois.size());
		// final String string;
		// if (column == -1) {
		// string = getLapang(row).name;
		// System.out.println("nama lapang: " + string);
		// for (int i = 0; i <= ukuran; i++) {
		// if (string == newPois.get(i).getnomor()) {
		// no_lapang = 0;
		//
		// System.out.println("lapangs: " + newPois.get(i).getnomor());
		// }
		// // } else if (string == newPois.get(i).getnomor()) {
		// // no_lapang = 1;
		// // // JadwalJam rowItem = getLapang(no_lapang);
		// // }
		// }
		// } else {
		// string = "";
		// }

		if (getStatusLapang(row).data[column + 1] == "Isi") {
			convertView.setBackgroundResource(R.drawable.bg_table_color1);
		} else {
			convertView.setBackgroundResource(R.drawable.bg_table_color2);
		}

		TextView header = ((TextView) convertView.findViewById(R.id.text1));
		header.setText(getStatusLapang(row).data[column + 1]);
		header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (getStatusLapang(row).data[column + 1] == "Isi") {
					Toast.makeText(context, "Sudah ada yang Booking!",
							Toast.LENGTH_SHORT).show();
				} else {
					// Toast.makeText(context,
					// "yes! "+getDevice(row).data[column + 1],
					// Toast.LENGTH_SHORT).show();
					/*
					 * Dialog dialog = new Dialog(context);
					 * dialog.setContentView(R.layout.dialog_booking);
					 * dialog.setTitle("Booking");
					 * 
					 * EditText code_booking = (EditText) dialog
					 * .findViewById(R.id.KodeBoooking);
					 * code_booking.setText(getRandomString());
					 * 
					 * dialog.show();
					 */
					bookingdialog();
				}

			}
		});
		// ((TextView)
		// convertView.findViewById(R.id.text1)).setText(getDevice(row).data[column
		// + 1]);

		return convertView;
	}

	private View getFamilyView(int row, int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table_family, parent,
					false);
		}

		final String string;
		if (column == -1) {
			string = getLapang(row).name;

			System.out.println("nama lapang: " + string);
			System.out.println("ukuran: " + nolapangss.size());
			System.out.println("lapangs: " + nolapangss.get(0));
			// for (int i = 0; i <= newPois.size(); i++) {
			// if (string == newPois.get(i).getnomor()) {
			// System.out.println("lapangs: " + newPois.get(0).getnomor());
			// }
			// }
		} else {
			string = "";
		}
		((TextView) convertView.findViewById(R.id.text1)).setText(string);
		return convertView;
	}

	@Override
	public int getWidth(int column) {
		return Math.round(widths[column + 1] * density);
	}

	@Override
	public int getHeight(int row) {
		final int height;
		if (row == -1) {
			height = 35;
		} else if (isFamily(row)) {
			height = 25;
		} else {
			height = 45;
		}
		return Math.round(height * density);
	}

	@Override
	public int getItemViewType(int row, int column) {
		final int itemViewType;
		if (row == -1 && column == -1) {
			itemViewType = 0;
		} else if (row == -1) {
			itemViewType = 1;
		} else if (isFamily(row)) {
			itemViewType = 4;
		} else if (column == -1) {
			itemViewType = 2;
		} else {
			itemViewType = 3;
		}
		return itemViewType;
	}

	private boolean isFamily(int row) {
		int family = 0;
		while (row > 0) {
			row -= nolapang[family].size() + 1;
			family++;
		}
		return row == 0;
	}

	private JadwalJam getLapang(int row) {
		int nomor = 0;
		while (row >= 0) {
			row -= nolapang[nomor].size() + 1;
			nomor++;
		}
		return nolapang[nomor - 1];
	}

	private Jadwal getStatusLapang(int row) {
		int nomor = 0;
		while (row >= 0) {
			row -= nolapang[nomor].size() + 1;
			nomor++;
		}
		nomor--;
		return nolapang[nomor].get(row + nolapang[nomor].size());
	}

	@Override
	public int getViewTypeCount() {
		return 5;
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

	private void bookingdialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_booking);
		dialog.setTitle("Booking");

		bank = (Spinner) dialog.findViewById(R.id.rekening);
		jenis = (Spinner) dialog.findViewById(R.id.jenisbooking);

		tanggal = (EditText) dialog.findViewById(R.id.tanggal);

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

		EditText code_booking = (EditText) dialog
				.findViewById(R.id.KodeBoooking);
		code_booking.setText(getRandomString());

		// Loading spinner data from database
		loadSpinnerData();

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
		DatePickerDialog dpd = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// Display Selected date in textbox
						tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1)
								+ "-" + year);

					}
				}, mYear, mMonth, mDay);
		dpd.show();
	}

	private void loadSpinnerData() {
		// database handler
		LocationsDB db = new LocationsDB(context);

		// Spinner Drop down elements
		List<String> lables = db.getAllLabels();
		List<String> jenis_booking = db.getAllJenis();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, lables);
		ArrayAdapter<String> jenisAdapter = new ArrayAdapter<String>(context,
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

	// private class LapangJSON extends AsyncTask<Integer, Object, String> {
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// // if (listView != null) {
	// // if (loadingFooterView == null) {
	// // loadingFooterView = getActivity().getLayoutInflater()
	// // .inflate(R.layout.load_more_footer, null);
	// // ((ListView) listView).addFooterView(loadingFooterView);
	// // }
	// // }
	//
	// }
	//
	// protected String doInBackground(Integer... params) {
	// WebApi api = WebApi.getInstance();
	// try {
	// newPois = api.getLapang();
	// } catch (Exception e) {
	// e.printStackTrace();
	// return e.getMessage();
	// }
	//
	// return null;
	//
	// }
	//
	// protected void onPostExecute(String result) {
	// // if (isCancelled)
	// if (result == null) {
	// for (int i = 0; i <= newPois.size() - 1; i++) {
	// System.out.println("lapang: " + newPois.get(i).getnomor());
	// // if (string == "Lapang" + newPois.get(i)) {
	// //
	// // }
	// }
	// }
	// // if (listView != null) {
	// // if (loadingFooterView != null) {
	// // ((ListView) listView).removeFooterView(loadingFooterView);
	// // loadingFooterView = null;
	// // }
	// // if (result == null) {
	// // // if (newPois.size() == 0) {
	// // // // No more data can be fetched
	// // // page = -1;
	// // // } else {
	// // // page++;
	// // for (Barang city : newPois) {
	// // if (isCancelled)
	// // return;
	// // adapter.add(city);
	// // adapter.notifyDataSetChanged();
	// // }
	// // // }
	// // } else {
	// // Toast.makeText(getActivity(), result, Toast.LENGTH_LONG)
	// // .show();
	// // }
	// // if (isCancelled)
	// // return;
	// //
	// // if (adapter.getCount() == 0) {
	// // ((ListView) listView).addFooterView(getActivity()
	// // .getLayoutInflater().inflate(
	// // R.layout.empty_content, null));
	// // }
	// // }
	// super.onPostExecute(result);
	// }
	// }

}