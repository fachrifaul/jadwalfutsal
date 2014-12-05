package id.facworks.jadwalfutsal.adapter;

import id.facworks.jadwalfutsal.R;
import id.facworks.jadwalfutsal.db.LocationsDB;
import id.facworks.jadwalfutsal.object.Jadwal;
import id.facworks.jadwalfutsal.object.JadwalJam;

import java.util.Calendar;
import java.util.List;

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
	Context context;
	Spinner bank, jenis;
	EditText tanggal;
	private final JadwalJam familys[];
	private final String headers[] = { "Jam", "Senin", "Selasa", "Rabu",
			"Kamis", "Jumat", "Sabtu", "Minggu" };

	private final int[] widths = { 120, 100, 140, 60, 70, 60, 60, 60, };
	private final float density;

	public TableJadwalAdapter(Context context) {
		this.context = context;
		familys = new JadwalJam[] { new JadwalJam("Mobiles"),
				new JadwalJam("Tablets"), new JadwalJam("Others"), };

		density = context.getResources().getDisplayMetrics().density;

		familys[0].list.add(new Jadwal("07.00-08.00", "HTC", "Gingerbread",
				"10", "512 MB", "3.7\"", "512 MB", "1"));
		familys[0].list.add(new Jadwal("08.00-09.00", "Samsung", "Gingerbread",
				"10", "16 GB", "4\"", "512 MB", "1"));
		familys[0].list.add(new Jadwal("09.00-10.00", "Samsung",
				"Ice cream Sandwich", "15", "16 GB", "4.65\"", "1 GB", "1"));
		familys[0].list.add(new Jadwal("10.00-11.00", "Samsung",
				"Ice cream Sandwich", "15", "32 GB", "4.65\"", "1 GB", "1"));
		familys[0].list.add(new Jadwal("11.00-12.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("12.00-13.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("13.00-14.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("14.00-15.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("15.00-16.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("16.00-17.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("17.00-18.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("18.00-19.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("19.00-20.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("21.00-22.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("22.00-23.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("23.00-00.00", "LG", "Jelly Bean", "17",
				"8 GB", "4.7\"", "2 GB", "1"));
		familys[0].list.add(new Jadwal("Nexus 4 (16 GB)", "LG", "Jelly Bean",
				"17", "16 GB", "4.7\"", "2 GB", "1"));
		familys[1].list.add(new Jadwal("Nexus 7 (16 GB)", "Asus", "Jelly Bean",
				"16", "16 GB", "7\"", "1 GB", "1"));
		familys[1].list.add(new Jadwal("Nexus 7 (32 GB)", "Asus", "Jelly Bean",
				"16", "32 GB", "7\"", "1 GB", "1"));
		familys[1].list.add(new Jadwal("Nexus 10 (16 GB)", "Samsung",
				"Jelly Bean", "17", "16 GB", "10\"", "2 GB", "1"));
		familys[1].list.add(new Jadwal("Nexus 10 (32 GB)", "Samsung",
				"Jelly Bean", "17", "32 GB", "10\"", "2 GB", "1"));
		familys[2].list.add(new Jadwal("Nexus Q", "--", "Honeycomb", "13",
				"--", "--", "--", "1"));
	}

	@Override
	public int getRowCount() {
		return 24;
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
		header.setText(headers[0]);
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
		header.setText(headers[column + 1]);
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
				.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1
						: R.drawable.bg_table_color2);
		((TextView) convertView.findViewById(R.id.text1))
				.setText(getDevice(row).data[column + 1]);
		return convertView;
	}

	private View getBody(final int row, final int column, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_table, parent, false);
		}
		convertView
				.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1
						: R.drawable.bg_table_color2);

		TextView header = ((TextView) convertView.findViewById(R.id.text1));
		header.setText(getDevice(row).data[column + 1]);
		header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (getDevice(row).data[column + 1] == "LG") {
					Toast.makeText(context, "Sudah Dibooking!",
							Toast.LENGTH_SHORT).show();
				} else {
					// Toast.makeText(context,
					// "yes! "+getDevice(row).data[column + 1],
					// Toast.LENGTH_SHORT).show();
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
			string = getFamily(row).name;
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
			row -= familys[family].size() + 1;
			family++;
		}
		return row == 0;
	}

	private JadwalJam getFamily(int row) {
		int family = 0;
		while (row >= 0) {
			row -= familys[family].size() + 1;
			family++;
		}
		return familys[family - 1];
	}

	private Jadwal getDevice(int row) {
		int family = 0;
		while (row >= 0) {
			row -= familys[family].size() + 1;
			family++;
		}
		family--;
		return familys[family].get(row + familys[family].size());
	}

	@Override
	public int getViewTypeCount() {
		return 5;
	}

	private void bookingdialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog);
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

}