package id.facworks.jadwalfutsal.webservice;

import id.facworks.jadwalfutsal.db.Constants;
import id.facworks.jadwalfutsal.db.DatabaseHelper;
import id.facworks.jadwalfutsal.db.LapangController;
import id.facworks.jadwalfutsal.object.Lapang;
import id.facworks.jadwalfutsal.object.LapangModel;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class GetAllLapang {

	private static final String SERVER_URL = Constants.JADWAL_LAPANG_URL;
	Context context;
	private DatabaseHelper dbHelper;
	ArrayList<Lapang> daftarJadwal = new ArrayList<Lapang>();

	public GetAllLapang(Context mContext) {
		this.context = mContext;
	}

	public boolean getAllJadwal() {

		boolean result = false;
		JSONObject j = new JSONObject();
		LapangController rc = new LapangController(context);
		try {
			HttpResponse re = HTTPPost.doPost(SERVER_URL, j);
			String temp = EntityUtils.toString(re.getEntity());

			JSONArray jArr = new JSONArray(temp);

			int counter = jArr.length();

			if (counter != 0) {
				result = true;
				rc.removeall();
				Log.i("coba", "hapus databases");
				for (int i = 0; i < counter; i++) {
					JSONObject jo = jArr.getJSONObject(i);

					LapangModel rm = new LapangModel();
					//rm.setId(i.to);
					rm.setid_user(jo.getString("uid"));
					rm.setcode_booking(jo.getString("code_booking"));
					rm.setkategori_lapang(jo.getString("kategori_lapang"));
					rm.settanggal(jo.getString("tanggal"));
					rm.setjam(jo.getString("jam").toString());
					rm.setstatus(jo.getString("status").toString());

					rc.insert(rm);

					Log.i("coba", "Yang di ambil: Nama : " + rm.getstatus());

				}

			} else {
				result = false;
			}

		} catch (ClientProtocolException e) {
			Log.e("request error", e.getMessage());
		} catch (IOException e) {
			Log.e("requset error", e.getMessage());
		} catch (JSONException e) {
			Log.e("json error", e.getMessage());
		}
		return result;

	}
}
