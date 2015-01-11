package id.facworks.jadwalfutsal.conn;

import id.facworks.jadwalfutsal.db.Constants;
import id.facworks.jadwalfutsal.db.Constants.Extra;

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
import android.preference.PreferenceManager;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

public class WebApi {

	int PAGE_ITEMS_SIZE = 5;
	private final String CONNECTION_FAILED_ERROR_MESSAGE = "Connection Failed";
	private final String INVALID_RESPONSE_ERROR_MESSAGE = "Invalid Response Data";

	// String TAG_ID = "detail_id";
	// String TAG_TITLE = "title";
	// String TAG_DESC = "description";
	// String TAG_IMAGE = "cover";
	// String TAG_TIME = "timelog";
	//
	// String TAG_GALLERY_ID = "gallery_id";
	// String TAG_PHOTOPATH = "photo_path";
	// String TAG_IKLAN = "fl_iklan";
	// String TAG_URL = "url";

	String Barang_ID = "ImageID";
	String Barang_NAME = "ImageName";
	String Barang_COVER = "ImagePath_Thumbnail";
	String Barang_TWITTER = "ImagePath_FullPhoto";

	// private static String login_tag = "login";
	// private static String register_tag = "register";
	//
	private static String KEY_SUCCESS = "success";
	// private static String KEY_ERROR = "error";
	// private static String KEY_ERROR_MSG = "error_msg";
	// private static String KEY_UID = "uid";
	// private static String KEY_NAME = "name";
	// private static String KEY_EMAIL = "email";
	// private static String KEY_CREATED_AT = "created_at";

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
			// String tempString;
			// if (jso.has(Constants.RESULT_KEY)) {
			// tempString = jso.getString(Constants.RESULT_KEY);
			// if (tempString.equals(Constants.FAILED_VALUE)) {
			// if (jso.has(Constants.MESSAGE_KEY))
			// throw new Exception(
			// jso.getString(Constants.MESSAGE_KEY));
			// else
			// throw new Exception(INVALID_RESPONSE_ERROR_MESSAGE);
			// } else if (tempString.equals(Constants.SUCCESS_VALUE)) {

			if (jso.has(Constants.DATA_KEY)) {
				JSONArray jsa = jso.getJSONArray(Constants.DATA_KEY);
				int numElement = jsa.length();
				for (int i = 0; i < numElement; i++) {
					jso = jsa.getJSONObject(i);

					String Tanggal_Booking = jso
							.getString(Constants.TANGGAL_LAPANG);
					String Tanggal[] = Tanggal_Booking.split("\\-");
					String Jam_Booking = jso.getString(Constants.JAM_LAPANG);
					String Jam[] = Jam_Booking.split("\\:");

					System.out.println(Tanggal[0] + " - " + Tanggal[1] + " - "
							+ Tanggal[2] + " - ");
					System.out.println(Jam[0] + " - " + Jam[1] + " - ");

					// Calendar startTime = Calendar.getInstance();
					// startTime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(Jam[0]));
					// startTime.set(Calendar.MINUTE, 0);
					// startTime.set(Calendar.DAY_OF_MONTH,
					// Integer.parseInt(Tanggal[2]));
					// startTime.set(Calendar.MONTH,Integer.parseInt(Tanggal[1])
					// - 1);
					// startTime.set(Calendar.YEAR,
					// Integer.parseInt(Tanggal[0]));
					// Calendar endTime = (Calendar) startTime.clone();
					// endTime.add(Calendar.HOUR, 1);
					// endTime.set(Calendar.MONTH,Integer.parseInt(Tanggal[1]) -
					// 1);

					event = new WeekViewEvent(Long.parseLong(jso
							.getString(Constants.ID_LAPANG)),
							jso.getString(Constants.STATUS_lAPANG),
							Integer.parseInt(Tanggal[0]),
							Integer.parseInt(Tanggal[1]),
							Integer.parseInt(Tanggal[2]),
							Integer.parseInt(Jam[0]), Integer.parseInt(Jam[1]),
							Integer.parseInt(Tanggal[0]),
							Integer.parseInt(Tanggal[1]),
							Integer.parseInt(Tanggal[2]),
							Integer.parseInt(Jam[0]) + 1,
							Integer.parseInt(Jam[1]));

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

	//
	// public ArrayList<Jadwal> getLapangJam() throws Exception {
	//
	// ArrayList<Jadwal> arrayLapangJam = new ArrayList<Jadwal>();
	//
	// try {
	// WebData webData = WebEngine.openHttpGetConnection(
	// Constants.LAPANG_URL, null);
	// String responseBody = WebEngine.readResponse(webData.is);
	//
	// // Log.i("Response is......................", "" + responseBody);
	//
	// JSONObject jso = new JSONObject(responseBody);
	// if (jso.has(Constants.DATA_KEY)) {
	// JSONArray jsa = jso.getJSONArray(Constants.DATA_KEY);
	// int numElement = jsa.length();
	// Jadwal LapangJampage;
	// for (int i = 0; i < numElement; i++) {
	// jso = jsa.getJSONObject(i);
	//
	// LapangJampage = new Jadwal(jso.getString(Constants.JAM),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS),
	// jso.getString(Constants.STATUS));
	//
	// arrayLapangJam.add(LapangJampage);
	// }
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
	// } catch (ResponseCodeException e) {
	// e.printStackTrace();
	// throw new Exception(CONNECTION_FAILED_ERROR_MESSAGE);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// throw new Exception(INVALID_RESPONSE_ERROR_MESSAGE);
	// }
	// return arrayLapangJam;
	// }

	// public String startLogin(Context context, String username, String
	// password) {
	// Hashtable<String, String> postVar = new Hashtable<String, String>();
	// postVar.put(Constants.USER_NAME_PARAM, username);
	// postVar.put(Constants.PASSWORD_PARAM, password);
	//
	// String response = null;
	// try {
	// WebData webData = WebEngine.openHttpPostConnection(
	// Constants.LOGIN_URL, postVar);
	// String responseBody = WebEngine.readResponse(webData.is);
	// JSONObject jso = new JSONObject(responseBody);
	// if (jso.has(Constants.RESULT_KEY)) {
	// if (!jso.getString(Constants.RESULT_KEY).equals(
	// Constants.SUCCESS_VALUE)) {
	// if (jso.has(Constants.MESSAGE_KEY))
	// response = jso.getString(Constants.MESSAGE_KEY);
	// else
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// } else {
	// SharedPreferences prefs = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// Editor prefEditor = prefs.edit();
	// prefEditor.putString(Extra.LOGIN_USER_ID_KEY,
	// jso.getString(Constants.USER_ID_PARAM));
	// prefEditor.commit();
	// }
	// } else {
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// response = CONNECTION_FAILED_ERROR_MESSAGE;
	// } catch (ResponseCodeException e) {
	// e.printStackTrace();
	// response = CONNECTION_FAILED_ERROR_MESSAGE;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// }
	// return response;
	// }
	//
	// public String register(Context context, String tag, String username,
	// String password, String email) {
	//
	// Hashtable<String, String> postVar = new Hashtable<String, String>();
	// postVar.put(Constants.TAG, tag);
	// postVar.put(Constants.NAME_PARAM, username);
	// postVar.put(Constants.EMAIL_PARAM, email);
	// postVar.put(Constants.PASSWORD_PARAM, password);
	// // postVar.put(Constants.LAST_NAME_PARAM, lastName);
	// // postVar.put(Constants.GENDER_PARAM, gender);
	// // postVar.put(Constants.BIRTH_DATE_PARAM, birthday.substring(0, 2));
	// // postVar.put(Constants.BIRTH_MONTH_PARAM, birthday.substring(3,
	// // 5));
	// // postVar.put(Constants.BIRTH_YEAR_PARAM, birthday.substring(6));
	// // postVar.put(Constants.PHONE_PARAM, phone);
	// // postVar.put(Constants.CITY_PARAM, city);
	// // postVar.put(Constants.COUNTRY_PARAM, country);
	//
	// String response = null;
	// try {
	// WebData webData = WebEngine.openHttpPostConnection(
	// Constants.LOGIN_URL, postVar);
	// // System.out.println(webData);
	// String responseBody = WebEngine.readResponse(webData.is);
	//
	// JSONObject jso = new JSONObject(responseBody);
	// if (jso.has(Constants.RESULT_KEY)) {
	//
	// if (jso.getString(Constants.RESULT_KEY).equals(
	// Constants.SUCCESS_VALUE)) {
	// if (jso.has(Constants.MESSAGE_KEY))
	// response = jso.getString(Constants.MESSAGE_KEY);
	// else
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// } else {
	//
	// SharedPreferences prefs = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// Editor prefEditor = prefs.edit();
	// prefEditor.putString(Extra.LOGIN_USER_ID_KEY,
	// jso.getString(Constants.UID_PARAM));
	// prefEditor.commit();
	//
	// }
	// } else {
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// response = CONNECTION_FAILED_ERROR_MESSAGE;
	// } catch (ResponseCodeException e) {
	// e.printStackTrace();
	// response = CONNECTION_FAILED_ERROR_MESSAGE;
	// } catch (JSONException e) {
	// e.printStackTrace();
	// response = INVALID_RESPONSE_ERROR_MESSAGE;
	// }
	//
	// return response;
	// }

	public String register2(Context context, String tag, String username,
			String password, String email) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", tag));
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		// getting JSON Object

		String response = null;

		// check for login response
		// JSONObject json = WebEngine
		// .registerUser(tag, username, email, password);
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

		// check for login response
		// JSONObject json = WebEngine.loginUser(tag, username, password);
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

	public String submit_lapang(Context context, String id_user,
			String code_booking, String kategori_lapang, String tanggal,
			String jam, String status) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id_user", id_user));
		params.add(new BasicNameValuePair("code_booking", code_booking));
		params.add(new BasicNameValuePair("kategori_lapang", kategori_lapang));
		params.add(new BasicNameValuePair("tanggal", tanggal));
		params.add(new BasicNameValuePair("jam", jam));
		params.add(new BasicNameValuePair("status", status));

		// getting JSON Object

		String response = null;

		// check for login response
		// JSONObject json = WebEngine
		// .registerUser(tag, username, email, password);
		try {
			JSONObject json = WebEngine.getJSONFromUrl(Constants.BOOKING_LAPANG_URL,
					params);
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				System.out.println(res);
				if (Integer.parseInt(res) == 1) {
					// user successfully registred
					// JSONObject json_user = json.getJSONObject("user");

//					SharedPreferences prefs = PreferenceManager
//							.getDefaultSharedPreferences(context);
//					Editor prefEditor = prefs.edit();
//					prefEditor.putString(Extra.LOGIN_USER_ID_KEY,
//							json.getString(Constants.UID_PARAM));
//					prefEditor.commit();

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
}
