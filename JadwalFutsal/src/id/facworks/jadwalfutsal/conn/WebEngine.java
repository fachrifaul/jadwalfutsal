package id.facworks.jadwalfutsal.conn;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * <p>
 * Class that encapsulates HTTP Post and GET request
 * </p>
 * 
 * @author Andry
 */
public class WebEngine {

	private static int TIME_OUT = 30000;
	private static int TIME_OUT_SOCKET = 50000;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	/**
	 * Create HTTP Get Connection
	 * 
	 * @param hostUrl
	 *            Server URL
	 * @param getVar
	 *            Get variables
	 * @return WebData object from this request to handle response
	 * @throws IOException
	 *             Connection failure exception
	 * @throws ResponseCodeException
	 *             Exception thrown when receiving response code other than 200
	 *             OK
	 */
	public static WebData openHttpGetConnection(String hostUrl,
			Hashtable<String, String> getVar) throws IOException,
			ResponseCodeException {
		return openHttpGetConnection(hostUrl, getVar, 0);
	}

	/**
	 * Create HTTP Get Connection
	 * 
	 * @param hostUrl
	 *            Server URL
	 * @param getVar
	 *            Get variables
	 * @param startBytes
	 *            Start bytes to receive when sending this request. We use this
	 *            to download content
	 * @return WebData object from this request to handle response
	 * @throws IOException
	 *             Connection failure exception
	 * @throws ResponseCodeException
	 *             Exception thrown when receiving response code other than 200
	 *             OK
	 */
	public static WebData openHttpGetConnection(String hostUrl,
			Hashtable<String, String> getVar, long startBytes)
			throws IOException, ResponseCodeException {
		InputStream is = null;

		HttpGet httpGet = null;
		HttpParams params = null;
		try {
			if ((getVar != null) && (!getVar.isEmpty())) {
				Set<String> keySet = getVar.keySet();
				boolean first = true;
				StringBuilder sb = new StringBuilder(hostUrl);
				for (String key : keySet) {
					if (first) {
						first = false;
						sb.append('?');
					} else {
						sb.append('&');
					}
					sb.append(URLEncoder.encode(key, "UTF-8"))
							.append('=')
							.append(URLEncoder.encode(getVar.get(key), "UTF-8"));
				}
				// Log.d("HTTP", sb.toString());
				httpGet = new HttpGet(sb.toString());
				sb.setLength(0);
			} else {
				httpGet = new HttpGet(hostUrl);
				params = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
				HttpConnectionParams.setSoTimeout(params, TIME_OUT_SOCKET);
			}
		} catch (IllegalArgumentException murl) {
			murl.printStackTrace();
			return new WebData(null, null);
		}

		HttpClient client = new DefaultHttpClient(params);

		if (startBytes > 0)
			httpGet.addHeader("Range", "bytes=" + startBytes + "-");
		HttpResponse response = client.execute(httpGet);

		StatusLine responseStatus = response.getStatusLine();
		if (responseStatus != null) {
			int responseCode = responseStatus.getStatusCode();
			if ((responseCode == HttpStatus.SC_OK)
					|| (responseCode == HttpStatus.SC_PARTIAL_CONTENT)) {
				is = response.getEntity().getContent();
			} else {
				throw new ResponseCodeException(responseCode,
						responseStatus.getReasonPhrase());
			}
		} else {
			throw new IOException("No response status");
		}
		return (new WebData(is, response));
	}

	/**
	 * Create HTTP Post Connection
	 * 
	 * @param hostUrl
	 *            Server URL
	 * @param postVar
	 *            Post variables
	 * @return WebData object from this request to handle response
	 * @throws IOException
	 *             Connection failure exception
	 * @throws ResponseCodeException
	 *             Exception thrown when receiving response code other than 200
	 *             OK
	 */
	public static WebData openHttpPostConnection(String hostUrl,
			Hashtable<String, String> postVar) throws IOException,
			ResponseCodeException {
		InputStream is = null;
		HttpPost httpPost = new HttpPost(hostUrl);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT_SOCKET);
		HttpClient client = new DefaultHttpClient(params);

		Log.d("HTTP", hostUrl);

		if (postVar != null)
			httpPost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
		else
			httpPost.addHeader("Content-Type", "text/plain");

		if ((postVar != null) && (!postVar.isEmpty())) {
			StringBuilder sb = new StringBuilder();
			Set<String> keySet = postVar.keySet();
			boolean first = true;
			for (String key : keySet) {
				if (first) {
					first = false;
				} else {
					sb.append('&');
				}
				sb.append(URLEncoder.encode(key, "UTF-8")).append('=')
						.append(URLEncoder.encode(postVar.get(key), "UTF-8"));
			}
			httpPost.setEntity(new StringEntity(sb.toString()));
			sb.setLength(0);
		}

		HttpResponse response = client.execute(httpPost);
		System.out.println(response);

		StatusLine responseStatus = response.getStatusLine();
		if (responseStatus != null) {
			int responseCode = responseStatus.getStatusCode();
			if ((responseCode == HttpStatus.SC_OK)
					|| (responseCode == HttpStatus.SC_PARTIAL_CONTENT)) {
				is = response.getEntity().getContent();
			} else {
				throw new ResponseCodeException(responseCode,
						responseStatus.getReasonPhrase());
			}
		} else {
			throw new IOException("No response status");
		}

		return (new WebData(is, response));
	}

	/**
	 * Create HTTP Post Connection
	 * 
	 * @param hostUrl
	 *            Server URL
	 * @param postData
	 *            Request body content
	 * @return WebData object from this request to handle response
	 * @throws IOException
	 *             Connection failure exception
	 * @throws ResponseCodeException
	 *             Exception thrown when receiving response code other than 200
	 *             OK
	 */
	public static WebData openHttpPostConnection(String hostUrl, String postData)
			throws IOException, ResponseCodeException {
		InputStream is = null;

		// Log.d("HTTP", hostUrl);

		HttpPost httpPost = new HttpPost(hostUrl);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT_SOCKET);
		HttpClient client = new DefaultHttpClient(params);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setEntity(new StringEntity(URLEncoder
				.encode(postData, "UTF-8")));

		HttpResponse response = client.execute(httpPost);

		StatusLine responseStatus = response.getStatusLine();
		if (responseStatus != null) {
			int responseCode = responseStatus.getStatusCode();
			if ((responseCode == HttpStatus.SC_OK)
					|| (responseCode == HttpStatus.SC_PARTIAL_CONTENT)) {
				is = response.getEntity().getContent();
			} else {
				throw new ResponseCodeException(responseCode,
						responseStatus.getReasonPhrase());
			}
		} else {
			throw new IOException("No response status");
		}

		return (new WebData(is, response));
	}

	public static WebData openHttpPostConnection(String hostUrl,
			String contentType, byte[] postData) throws IOException,
			ResponseCodeException {
		InputStream is = null;

		// Log.d("HTTP", hostUrl);

		HttpPost httpPost = new HttpPost(hostUrl);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT_SOCKET);
		HttpClient client = new DefaultHttpClient(params);
		httpPost.addHeader("Content-Type", contentType);
		httpPost.setEntity(new ByteArrayEntity(postData));

		HttpResponse response = client.execute(httpPost);

		StatusLine responseStatus = response.getStatusLine();
		if (responseStatus != null) {
			int responseCode = responseStatus.getStatusCode();
			if ((responseCode == HttpStatus.SC_OK)
					|| (responseCode == HttpStatus.SC_PARTIAL_CONTENT)) {
				is = response.getEntity().getContent();
			} else {
				throw new ResponseCodeException(responseCode,
						responseStatus.getReasonPhrase());
			}
		} else {
			throw new IOException("No response status");
		}

		return (new WebData(is, response));
	}

	/**
	 * Read HTTP response body
	 * 
	 * @param is
	 *            InputStream from HTTP response
	 * @return HTTP response body (text)
	 * @throws IOException
	 *             Thrown when InputStream has been closed
	 */
	public static String readResponse(InputStream is) throws IOException {
		int numRead = 0;
		StringBuilder sb = new StringBuilder();
		byte[] data = new byte[256];
		while ((numRead = is.read(data)) > 0) {
			sb.append(new String(data, 0, numRead));
		}
		is.close();
		String resp = sb.toString();
		sb.setLength(0);
		return resp;
	}

	/**
	 * Read HTTP response body
	 * 
	 * @param is
	 *            InputStream from HTTP response
	 * @return HTTP response body (text)
	 * @throws IOException
	 *             Thrown when InputStream has been closed
	 */
	public static byte[] readByteResponse(InputStream is) throws IOException {
		int numRead = 0;
		byte[] data = new byte[256];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((numRead = is.read(data)) > 0) {
			bos.write(data, 0, numRead);
		}
		bos.flush();
		is.close();
		return bos.toByteArray();
	}

	//
	// public static JSONObject loginUser(String tag, String name, String
	// password) {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", tag));
	// params.add(new BasicNameValuePair("name", name));
	// params.add(new BasicNameValuePair("password", password));
	// JSONObject json = getJSONFromUrl(Constants.LOGIN_URL, params);
	// // return json
	// // Log.e("JSON", json.toString());
	// return json;
	// }

	// public static JSONObject registerUser(String tag, String name,
	// String email, String password) {
	// // Building Parameters
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("tag", tag));
	// params.add(new BasicNameValuePair("name", name));
	// params.add(new BasicNameValuePair("email", email));
	// params.add(new BasicNameValuePair("password", password));
	//
	// // getting JSON Object
	// JSONObject json = getJSONFromUrl(Constants.LOGIN_URL, params);
	// Log.e("JSON", json.toString());
	// return json;
	// }

	public static JSONObject getJSONFromUrl(String url,
			List<NameValuePair> params) throws IOException,
			ResponseCodeException {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jObj = null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			jObj = null;
		} catch (IOException e) {
			e.printStackTrace();
			jObj = null;
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			jObj = null;
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			jObj = null;
		}

		// return JSON String
		return jObj;

	}

	public static JSONObject getJSONFromUrl2(String url) throws IOException,
			ResponseCodeException {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			jObj = null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			jObj = null;
		} catch (IOException e) {
			e.printStackTrace();
			jObj = null;
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			jObj = null;
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			jObj = null;
		}

		// return JSON String
		return jObj;

	}
}
