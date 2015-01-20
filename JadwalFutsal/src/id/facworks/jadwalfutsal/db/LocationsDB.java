package id.facworks.jadwalfutsal.db;

import id.facworks.jadwalfutsal.object.Lapang;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper {

	/** Database name */
	private static String DBNAME = "jadwalfutsal";
	private static String DB_PATH = "/data/data/id.facworks.jadwalfutsal/databases/";

	/** Version number of the database */
	private static int VERSION = 1;

	/** A constant, stores the the table name */
	private static final String DATABASE_TABLE_BANK = "bank";
	public static final String FIELD_ROW_ID_BANK = "id";
	public static final String FIELD_NAME_BANK = "name";

	private static final String DATABASE_TABLE_JENIS = "jenis";
	public static final String FIELD_ROW_ID_JENIS = "id";
	public static final String FIELD_NAME_JENIS = "name";

	private static final String DATABASE_TABLE_JAM = "jam";
	public static final String FIELD_ROW_ID_JAM = "id";
	public static final String FIELD_NAME_JAM = "jam";

	private static final String DATABASE_TABLE_JADWAL = "jadwal";
	public static final String FIELD_ID_JADWAL = "id_user";
	public static final String FIELD_CODE_JADWAL = "code_booking";
	public static final String FIELD_KATEGORI_JADWAL = "kategori_lapang";
	public static final String FIELD_TANGGAL_JADWAL = "tanggal";
	public static final String FIELD_JAM_AWAL_JADWAL = "jam_awal";
	public static final String FIELD_JAM_AKHIR_JADWAL = "jam_akhir";
	public static final String FIELD_STATUS_JADWAL = "status";

	/** An instance variable for SQLiteDatabase */
	private SQLiteDatabase mDB;

	private Context myContext;
	public static LocationsDB dh = null;

	public static LocationsDB getInstance(Context context) {
		if (dh == null) {
			dh = new LocationsDB(context.getApplicationContext());
		} else {
			if (!dh.mDB.isOpen()) {
				dh = new LocationsDB(context);
			}
		}
		return dh;
	}

	public LocationsDB(Context context) {
		super(context, DBNAME, null, VERSION);
		this.myContext = context;
		try {
			createDataBase();
			openDataBase();
		} catch (IOException e) {
		}

	}

	/** Inserts a new location to the table locations */
	public long insert(ContentValues contentValues) {
		long rowID = mDB.insert(DATABASE_TABLE_BANK, null, contentValues);
		return rowID;

	}

	/** Deletes all locations from the table */
	public int del() {
		int cnt = mDB.delete(DATABASE_TABLE_BANK, null, null);
		return cnt;
	}

	/** Count Data */
	public long alldata() {
		SQLiteDatabase db = getReadableDatabase();
		long cnt = DatabaseUtils.queryNumEntries(db, DATABASE_TABLE_BANK);
		return cnt;
	}

	/** Returns all the locations from the table */
	public Cursor getAllLocations() {
		return mDB.query(DATABASE_TABLE_BANK, new String[] { FIELD_ROW_ID_BANK,
				FIELD_NAME_BANK }, null, null, null, null, null);
	}

	public SQLiteDatabase getDB() {
		return mDB;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	private void createDataBase() throws IOException {

		try {
			this.getReadableDatabase();
		} catch (Exception e) {
		}

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		// SQLiteDatabase db = getReadableDatabase();
		// long cnt = DatabaseUtils.queryNumEntries(db, DATABASE_TABLE);
		// System.out.println("jumlah " + cnt);

		try {
			String myPath = DB_PATH + DBNAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
			// database does't exist yet.
		}

		boolean res = false;
		if (checkDB != null) {
			try {
				Cursor c = checkDB.rawQuery("SELECT * FROM bank", null);
				c.close();
				res = true;
			} catch (Exception e) {

			}
			checkDB.close();
			return res;

		} else {
			return false;
		}

		// return checkDB != null ? true : false;

	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DBNAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DBNAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DBNAME;
		mDB = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		try {
			mDB = getWritableDatabase();
		} catch (Exception e) {
		}

	}

	@Override
	public synchronized void close() {

		if (mDB != null)
			mDB.close();

		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// int i = 0;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// DELETE DATABASE
		// String fileName = DB_PATH + DBNAME;
		// File oldFile = new File(fileName);
		// boolean resDelete = oldFile.delete();

		try {
			createDataBase();
			openDataBase();
		} catch (IOException e) {
		}
	}

	public List<String> getAllLabels() {
		List<String> labels = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_BANK;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				labels.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return labels;
	}

	public List<String> getAllJenis() {
		List<String> jenis = new ArrayList<String>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_JENIS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				jenis.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return jenis;
	}

	public ArrayList<String> getAllJam() {
		ArrayList<String> jam = new ArrayList<String>();

		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_JAM;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				jam.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		return jam;
	}

	public ArrayList<Lapang> getAllLapang1() {
		ArrayList<Lapang> jadwal = new ArrayList<Lapang>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_JADWAL
				+ " where " + FIELD_KATEGORI_JADWAL + " like 1";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() >= 1) {
			cursor.moveToFirst();

			do {
				Lapang info = new Lapang(cursor.getString(cursor
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
				jadwal.add(info);

			} while (cursor.moveToNext());
		}
		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return jadwal;
	}

	public ArrayList<Lapang> getAllLapang2() {
		ArrayList<Lapang> jadwal = new ArrayList<Lapang>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_JADWAL
				+ " where " + FIELD_KATEGORI_JADWAL + " like 2";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() >= 1) {
			cursor.moveToFirst();

			do {
				Lapang info = new Lapang(cursor.getString(cursor
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
				jadwal.add(info);

			} while (cursor.moveToNext());
		}
		// closing connection
		cursor.close();
		db.close();

		// returning lables
		return jadwal;
	}
}
