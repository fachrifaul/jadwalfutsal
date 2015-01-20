package id.facworks.jadwalfutsal.object;

public class Lapang {

	String code_booking, kategori_lapang, tanggal, jam_awal, jam_akhir, status,
			user_id;

	public Lapang(String user_id, String code_booking, String kategori_lapang,
			String tanggal, String jam_awal,String jam_akhir,String status) {
		this.user_id = user_id;
		this.code_booking = code_booking;
		this.kategori_lapang = kategori_lapang;
		this.tanggal = tanggal;
		this.jam_awal = jam_awal;
		this.jam_akhir = jam_akhir;
		this.status = status;
	}

	public String getuser_id() {
		return user_id;
	}

	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getcode_booking() {
		return code_booking;
	}

	public void setcode_booking(String code_booking) {
		this.code_booking = code_booking;
	}

	public String getkategori_lapang() {
		return kategori_lapang;
	}

	public void setkategori_lapang(String kategori_lapang) {
		this.kategori_lapang = kategori_lapang;
	}

	public String gettanggal() {
		return tanggal;
	}

	public void settanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getjam_awal() {
		return jam_awal;
	}

	public void setjam_awal(String jam_awal) {
		this.jam_awal = jam_awal;
	}
	
	public String getjam_akhir() {
		return jam_akhir;
	}

	public void setjam_akhir(String jam_akhir) {
		this.jam_akhir = jam_akhir;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.status;
	}

}