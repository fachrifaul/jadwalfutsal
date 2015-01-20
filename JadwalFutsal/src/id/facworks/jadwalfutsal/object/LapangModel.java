package id.facworks.jadwalfutsal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class LapangModel implements Parcelable {

	private String id_user;
	private String code_booking;
	private String kategori_lapang;
	private String tanggal;
	private String jam_awal;
	private String jam_akhir;

	private String status;

	public LapangModel() {
		super();
	}

	public LapangModel(Parcel in) {
		// TODO Auto-generated constructor stub
		id_user = in.readString();
		code_booking = in.readString();
		kategori_lapang = in.readString();
		tanggal = in.readString();
		jam_awal = in.readString();
		jam_akhir = in.readString();
		status = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id_user);
		dest.writeString(code_booking);
		dest.writeString(kategori_lapang);
		dest.writeString(tanggal);
		dest.writeString(jam_awal);
		dest.writeString(jam_akhir);
		dest.writeString(status);
	}

	public static final Parcelable.Creator<LapangModel> CREATOR = new Parcelable.Creator<LapangModel>() {

		@Override
		public LapangModel createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new LapangModel(in);
		}

		@Override
		public LapangModel[] newArray(int size) {
			// TODO Auto-generated method stub
			return new LapangModel[size];
		}
	};

	public String getid_user() {
		return id_user;
	}

	public void setid_user(String id_user) {
		this.id_user = id_user;
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

}
