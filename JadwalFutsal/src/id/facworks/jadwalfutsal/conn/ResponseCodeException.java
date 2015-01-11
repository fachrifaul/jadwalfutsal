package id.facworks.jadwalfutsal.conn;

/**
 * <p>Response code exception, is an exception that occur if we receive HTTP response code other than 200 OK from server</p>
 * 
 * @author Andry
 *
 */
public class ResponseCodeException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * HTTP Response code
	 */
	public int responseCode;
	/**
	 * HTTP Response error message
	 */
	public String responseMessage;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return responseMessage;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return responseCode + " : " + responseMessage;
	}
	
	
	/**
	 * Create Response Code Exception with given HTTP response code and error message
	 * @param responseCode		HTTP response code, other than 200 OK
	 * @param responseMessage	HTTP error message
	 */
	public ResponseCodeException(int responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	
}
