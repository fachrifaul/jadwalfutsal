package id.facworks.jadwalfutsal.object;

public class LapangFromJSON {
	public long id;
	public String status;
	public String mulai;
	public String akhir;

	public LapangFromJSON(long id, String status, String mulai, String akhir) {
		this.id = id;
		this.status = status;
		this.mulai = mulai;
		this.akhir = akhir;
	}

	public long getid() {
		return id;
	}

	public void setid(long id) {
		this.id = id;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	public String getmulai() {
		return mulai;
	}

	public void setmulai(String mulai) {
		this.mulai = mulai;
	}

	public String getakhir() {
		return akhir;
	}

	public void setakhir(String akhir) {
		this.akhir = akhir;
	}
}
