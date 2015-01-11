package id.facworks.jadwalfutsal.conn;

import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

/**
 * <p>This class encapsulates a response object from a HTTP request</p>
 * @author Andry
 *
 */
public class WebData {
	/**
	 * Input Stream for this response
	 */
	public InputStream is;
	
	/**
	 * HttpResponse object for this response
	 */
	public HttpResponse response;

	/**
	 * @param is		InputStream data
	 * @param response	HttpResponse received from sending a HTTP request
	 */
	public WebData(InputStream is, HttpResponse response) {
		this.is = is;
		this.response = response;
	}
	
	/**
	 * Get HTTP response code
	 * @return	HTTP response code
	 */
	public int getResponseCode() {
		int responseCode = -1;
		if (response != null) {
			StatusLine sl = response.getStatusLine();
			if (sl != null)
				responseCode = sl.getStatusCode();
		}
		return responseCode;
	}
	
	/**
	 * Get Content-Length header value
	 * @return	Content-Length header value
	 */
	public long getContentSize() {
		long size = -1;
		if (response != null) {
			Header contentLengthHeader = response.getFirstHeader("Content-Length"); 
			if (contentLengthHeader != null) {
				size = Long.parseLong(contentLengthHeader.getValue());
			} 
		}
		return size;
	}
}
