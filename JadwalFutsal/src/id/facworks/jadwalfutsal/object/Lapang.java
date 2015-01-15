package id.facworks.jadwalfutsal.object;

public class Lapang {

	String code_booking, kategori_lapang, tanggal, jam, status, user_id;

	public Lapang(String user_id, String code_booking, String kategori_lapang,
			String tanggal, String jam, String status) {
		this.user_id = user_id;
		this.code_booking = code_booking;
		this.kategori_lapang = kategori_lapang;
		this.tanggal = tanggal;
		this.jam = jam;
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

	public String getjam() {
		return jam;
	}

	public void setjam(String jam) {
		this.jam = jam;
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