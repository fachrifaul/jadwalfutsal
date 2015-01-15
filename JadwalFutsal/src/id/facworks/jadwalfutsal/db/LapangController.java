package id.facworks.jadwalfutsal.db;

import id.facworks.jadwalfutsal.object.LapangModel;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;

public class LapangController {

	Context context;
	DBHelper dbHelper;
	String table = "jadwal";
	Cursor c;

	/**
	 * Inisialisasi parameter yang ada di database disini contoh: String id =
	 * "id"; String idSync = "idSync";
	 */
	private String user_id = "id_user";
	private String code_booking = "code_booking";
	private String kategori_lapang = "kategori_lapang";
	private String tanggal = "tanggal";
	private String jam = "jam";
	private String status = "status";

	public LapangController(Context ctx) {
		super();
		this.context = ctx;
		dbHelper = DBHelper.getDBAdapterInstance(ctx);
	}

	/*
	 * Untuk insert satu object
	 */
	public void insert(LapangModel obj) {
		ContentValues values = new ContentValues();
		dbHelper.openDataBase();
		try {
			// Disini untuk meletakkan data sementara dari object
			// sebelum dimasukkan ke database
			ContentValues cv = new ContentValues();
			LapangModel rm = obj;
			cv.put(user_id, rm.getid_user());
			cv.put(code_booking, rm.getcode_booking());
			cv.put(kategori_lapang, rm.getkategori_lapang());
			cv.put(tanggal, rm.gettanggal());
			cv.put(jam, rm.getjam());
			cv.put(status, rm.getstatus());
			dbHelper.insertRecordsInDB(table, cv);
			values.clear();

		} catch (SQLException e) {
			// TODO: handle exception
			throw e;
		}
		dbHelper.close();
	}

	/*
	 * Untuk menyimpan data sekaligus banyak
	 */
	public void insertAll(List<LapangModel> objects) {

		try {
			dbHelper.openDataBase();
			for (int counter = 0; counter < objects.size(); counter++) {
				ContentValues cv = new ContentValues();
				LapangModel rm = objects.get(counter);
				cv.put(user_id, rm.getid_user());
				cv.put(code_booking, rm.getcode_booking());
				cv.put(kategori_lapang, rm.getkategori_lapang());
				cv.put(tanggal, rm.gettanggal());
				cv.put(jam, rm.getjam());
				cv.put(status, rm.getstatus());
				dbHelper.insertRecordsInDB(table, cv);
				cv.clear();
			}
			dbHelper.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public List<LapangModel> getAll() {
		List<LapangModel> objects = new ArrayList<LapangModel>();

		try {
			dbHelper.openDataBase();
			c = dbHelper.selectRecordsFromDB(table, null, null, null, null,
					null, null);
			while (c.moveToNext()) {
				// Disini masukkan object data kedalam list
				LapangModel rm = new LapangModel();
				rm.setid_user(c.getString(c.getColumnIndex(user_id)));
				rm.setcode_booking(c.getString(c.getColumnIndex(code_booking)));
				rm.setkategori_lapang(c.getString(c.getColumnIndex(kategori_lapang)));
				rm.settanggal(c.getString(c.getColumnIndex(tanggal)));
				rm.setjam(c.getString(c.getColumnIndex(jam)));
				rm.setstatus(c.getString(c.getColumnIndex(status)));
				objects.add(rm);
			}
			c.close();
			c = null;
			dbHelper.close();
		} catch (SecurityException e) {
			e.printStackTrace();// TODO: handle exception
		}
		return objects;
	}

	public void removeall() {
		dbHelper.openDataBase();
		dbHelper.deleteRecordInDB(table, null, null);
		dbHelper.close();
	}
}
