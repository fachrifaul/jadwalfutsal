package id.facworks.jadwalfutsal.conn;

import id.facworks.jadwalfutsal.db.Constants;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.object.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

public class WebApi {

	private final String CONNECTION_FAILED_ERROR_MESSAGE = "Connection Failed";
	private final String INVALID_RESPONSE_ERROR_MESSAGE = "Invalid Response Data";

	private static String KEY_SUCCESS = "success";

	private static WebApi api;

	public static WebApi getInstance() {
		if (api == null) {
			api = new WebApi();
		}
		return api;
	}

	public static void destroy() {
		api = null;
	}

	public List<WeekViewEvent> getLapang() throws Exception {

		List<WeekViewEvent> arrayLapang = new ArrayList<WeekViewEvent>();
		WeekViewEvent event;

		try {
			WebData webData = WebEngine.openHttpGetConnection(
					Constants.LAPANG_URL, null);
			String responseBody = WebEngine.readResponse(webData.is);

			Log.i("Response is......................", "" + responseBody);

			JSONObject jso = new JSONObject(responseBody);

			if (jso.has(Constants.DATA_KEY)) {
				JSONArray jsa = jso.getJSONArray(Constants.DATA_KEY);
				int numElement = jsa.length();
				for (int i = 0; i < numElement; i++) {
					jso = jsa.getJSONObject(i);

					String Tanggal_Booking = jso
							.getString(Constants.TANGGAL_LAPANG);
					String Tanggal[] = Tanggal_Booking.split("\\-");
					String Jam_Mulai_Booking = jso
							.getString(Constants.JAM_MULAI_LAPANG);
					String Jam_Akhir_Booking = jso
							.getString(Constants.JAM_MULAI_LAPANG);
					String Jam_Mulai[] = Jam_Mulai_Booking.split("\\:");
					String Jam_Akhir[] = Jam_Akhir_Booking.split("\\:");

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam_Mulai[0] + " - " + Jam_Mulai[1]
							+ " - ");
					System.out.println(Jam_Akhir[0] + " - " + Jam_Akhir[1]
							+ " - ");

					event = new WeekViewEvent(Long.parseLong(jso
							.getString(Constants.ID_LAPANG)),
							jso.getString(Constants.STATUS_lAPANG),
							Integer.parseInt(Tanggal[0]),
							Integer.parseInt(Tanggal[1]),
							Integer.parseInt(Tanggal[2]),
							Integer.parseInt(Jam_Mulai[0]),
							Integer.parseInt(Jam_Mulai[1]),
							Integer.parseInt(Tanggal[0]),
							Integer.parseInt(Tanggal[1]),
							Integer.parseInt(Tanggal[2]),
							Integer.parseInt(Jam_Akhir[0]),
							Integer.parseInt(Jam_Akhir[1]));

					arrayLapang.add(event);
				}
			}
			// }
			// } else {
			// throw new Exception(INVALID_RESPONSE_ERROR_MESSAGE);
			// }
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
		} catch (ResponseCodeException e) {
			e.printStackTrace();
			throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception(INVALID_RESPONSE_ERROR_MESSAGE);
		}
		return arrayLapang;
	}

	public String register2(Context context, String tag, String username,
			String password, String email) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag));
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		// getting JSON Object
		String response = null;
		try {
			JSONObject json = WebEngine.getJSONFromUrl(Constants.LOGIN_URL,
					params);
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				System.out.println(res);
				if (Integer.parseInt(res) == 1) {
					// user successfully registred
					// JSONObject json_user = json.getJSONObject("user");

					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(context);
					Editor prefEditor = prefs.edit();
					prefEditor.putString(Extra.LOGIN_USER_ID_KEY,
							json.getString(Constants.UID_PARAM));
					prefEditor.commit();

				} else {
					// Error in registration
					response = INVALID_RESPONSE_ERROR_MESSAGE;
				}
			} else {
				response = INVALID_RESPONSE_ERROR_MESSAGE;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (ResponseCodeException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (JSONException e) {
			e.printStackTrace();
			response = INVALID_RESPONSE_ERROR_MESSAGE;
		}

		return response;
	}

	public String Login2(Context context, String tag, String username,
			String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag));
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("password", password));

		String response = null;
		try {
			JSONObject json = WebEngine.getJSONFromUrl(Constants.LOGIN_URL,
					params);
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				System.out.println(res);
				if (Integer.parseInt(res) == 1) {

					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(context);
					Editor prefEditor = prefs.edit();
					prefEditor.putString(Extra.LOGIN_USER_ID_KEY,
							json.getString(Constants.UID_PARAM));
					prefEditor.commit();

				} else {
					// Error in registration
					response = INVALID_RESPONSE_ERROR_MESSAGE;
				}
			} else {
				response = INVALID_RESPONSE_ERROR_MESSAGE;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (ResponseCodeException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (JSONException e) {
			e.printStackTrace();
			response = INVALID_RESPONSE_ERROR_MESSAGE;
		}

		return response;
	}

	public String submit_lapang(Context context, String id_user,
			String code_booking, String kategori_lapang, String tanggal,
			String jam_awal, String jam_akhir, String status) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id_user", id_user));
		params.add(new BasicNameValuePair("code_booking", code_booking));
		params.add(new BasicNameValuePair("kategori_lapang", kategori_lapang));
		params.add(new BasicNameValuePair("tanggal", tanggal));
		params.add(new BasicNameValuePair("jam_awal", jam_awal));
		params.add(new BasicNameValuePair("jam_akhir", jam_akhir));
		params.add(new BasicNameValuePair("status", status));

		// getting JSON Object
		String response = null;
		try {
			JSONObject json = WebEngine.getJSONFromUrl(
					Constants.BOOKING_LAPANG_URL, params);
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				System.out.println(res);
				if (Integer.parseInt(res) == 1) {

				} else {
					// Error in registration
					response = INVALID_RESPONSE_ERROR_MESSAGE;
				}
			} else {
				response = INVALID_RESPONSE_ERROR_MESSAGE;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (ResponseCodeException e) {
			e.printStackTrace();
			response = CONNECTION_FAILED_ERROR_MESSAGE;
		} catch (JSONException e) {
			e.printStackTrace();
			response = INVALID_RESPONSE_ERROR_MESSAGE;
		}

		return response;
	}

	public ArrayList<User> getUser(String id_user) throws Exception {

		ArrayList<User> user = new ArrayList<User>();
		User usernya;
		try {

			WebData webData = WebEngine.openHttpGetConnection(
					Constants.USER_LAPANG_URL + id_user, null);
			String responseBody = WebEngine.readResponse(webData.is);

			Log.i("Response is......................", "" + responseBody);

			JSONObject jso = new JSONObject(responseBody);

			if (jso.has(Constants.DATA_KEY)) {
				JSONArray jsa = jso.getJSONArray(Constants.DATA_KEY);

				jso = jsa.getJSONObject(0);

				usernya = new User(jso.getString(Constants.NAME_PARAM),
						jso.getString(Constants.NOHP_PARAM));

				user.add(usernya);

			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
		} catch (ResponseCodeException e) {
			e.printStackTrace();
			throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception(INVALID_RESPONSE_ERROR_MESSAGE);
		}

		return user;
	}
}
