package id.facworks.jadwalfutsal.db;

public final class Constants {

	private Constants() {
	}

//	public static final String[] IMAGES = new String[] {
//			"assets://gedung/gedung.jpg", "assets://gedung/rektorat.jpg",
//			"assets://gedung/ways.jpg" };

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";

//		public static final String ID = "id.facworks.jadwalfutsal.ID";
//		public static final String TITLE = "id.facworks.jadwalfutsal.TITLE";
//		public static final String LAT = "id.facworks.jadwalfutsal.LAT";
//		public static final String LNG = "id.facworks.jadwalfutsal.LNG";
//		public static final String DESCRIPTION = "id.facworks.jadwalfutsal.DESCRIPTION";
//		public static final String ELEVATION = "id.facworks.jadwalfutsal.ELEVATION";
//		public static final String PAGE = "id.facworks.jadwalfutsal.PAGE";
//		public static final String WEBPAGE = "id.facworks.jadwalfutsal.WEBPAGE";
//		public static final String GEDUNGJSON = "id.facworks.jadwalfutsal.GEDUNGJSON";
		
		public static final String LOGIN_TOKEN_KEY				= "id.facworks.jadwalfutsal.LOGIN_TOKEN";
		public static final String LOGIN_USER_ID_KEY			= "id.facworks.jadwalfutsal.LOGIN_USER_ID";
	}
	//private static final String SERVER_BASE_URL = "http://192.168.43.74/";
	private static final String SERVER_BASE_URL = "http://www.mochamadsm.com/abud/";
	
	public static final String LOGIN_URL 			= SERVER_BASE_URL + "register.php";
	
	public static final String LAPANG_URL = SERVER_BASE_URL + "lapang.php";
	public static final String JADWAL_LAPANG_URL = SERVER_BASE_URL + "jadwallapang.php";
	public static final String BOOKING_LAPANG_URL = SERVER_BASE_URL + "booking_lapang.php";
	public static final String USER_LAPANG_URL = SERVER_BASE_URL + "getuser.php?tag=";
	
	
	
//	public static final String BARANG_COVER = SERVER_BASE_URL + "webs/images/";
//	public static final String BARANG_COVER_SQUARE = SERVER_BASE_URL + "media/images/square/";
	
	public static final String RESULT_KEY = "result";
	public static final String MESSAGE_KEY = "message";
	public static final String DATA_KEY = "data";

	public static final String SUCCESS_VALUE = "success";
	public static final String FAILED_VALUE = "failed";
	
	public static final String PAGE_PARAM 			= "page";
	public static final String LIMIT_PARAM 			= "limit";
	
	public static final String TAG			 		= "TAG";
	public static final String USER_NAME_PARAM 		= "user_name";
	public static final String NAME_PARAM	 		= "name";
	public static final String PASSWORD_PARAM 		= "password";
	public static final String USER_ID_PARAM 		= "user_id";
	public static final String UID_PARAM 			= "uid";
	public static final String EMAIL_PARAM 			= "email";
	public static final String NOHP_PARAM 			= "nohp";
	
	public static final String ID_LAPANG	 		= "lid";
	public static final String KATEGORI_LAPANG 		= "kategori_lapang";
	public static final String TANGGAL_LAPANG		= "tanggal";
	public static final String JAM_MULAI_LAPANG 	= "jam_awal";
	public static final String JAM_AKHIR_LAPANG 	= "jam_akhir";
	public static final String STATUS_lAPANG 		= "status";
	
}

