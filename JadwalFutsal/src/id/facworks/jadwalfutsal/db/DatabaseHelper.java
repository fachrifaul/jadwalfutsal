package id.facworks.jadwalfutsal.db;

import id.facworks.jadwalfutsal.object.Lapang;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
	private static final String DB_NAME = "jadwalfutsal";
	private static final int DB_VER = 1;

	private static final String DATABASE_TABLE_JADWAL = "jadwal";
	public static final String FIELD_ID_JADWAL = "id_user";
	public static final String FIELD_CODE_JADWAL = "code_booking";
	public static final String FIELD_KATEGORI_JADWAL = "kategori_lapang";
	public static final String FIELD_TANGGAL_JADWAL = "tanggal";
	public static final String FIELD_JAM_AWAL_JADWAL = "jam_awal";
	public static final String FIELD_JAM_AKHIR_JADWAL = "jam_akhir";
	public static final String FIELD_STATUS_JADWAL = "status";

	private static DatabaseHelper dbInstance;
	private static SQLiteDatabase db;
	Context context;

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
		this.context = context;
	}

	public static DatabaseHelper getInstance(Context context) {
		if (dbInstance == null) {
			dbInstance = new DatabaseHelper(context);
			db = dbInstance.getWritableDatabase();
		}

		return dbInstance;
	}

	@Override
	public synchronized void close() {
		super.close();
		if (dbInstance != null) {
			dbInstance.close();
		}
	}

	public ArrayList<Lapang> getAllLapang() {
		ArrayList<Lapang> daftarLapang = new ArrayList<Lapang>();

		Cursor cursor = db.query(DATABASE_TABLE_JADWAL, new String[] {
				FIELD_CODE_JADWAL, FIELD_KATEGORI_JADWAL, FIELD_TANGGAL_JADWAL,
				FIELD_JAM_AWAL_JADWAL, FIELD_JAM_AKHIR_JADWAL,
				FIELD_STATUS_JADWAL }, null, null, null, null,
				FIELD_STATUS_JADWAL);

		if (cursor.getCount() >= 1) {
			cursor.moveToFirst();

			do {

				Lapang kamus = new Lapang(cursor.getString(cursor
						.getColumnIndex(FIELD_ID_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_CODE_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_KATEGORI_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_TANGGAL_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_JAM_AWAL_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_JAM_AKHIR_JADWAL)),
						cursor.getString(cursor
								.getColumnIndex(FIELD_STATUS_JADWAL)));

				daftarLapang.add(kamus);

			} while (cursor.moveToNext());
		}
		System.out.println("------------------------" + daftarLapang.size());
		return daftarLapang;

	}

}
