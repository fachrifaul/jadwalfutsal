package id.facworks.jadwalfutsal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class LapangModel implements Parcelable {

//	private String lid;
	private String code_booking;
	private String kategori_lapang;
	private String tanggal;
	private String jam;

	private String status;

	public LapangModel() {
		super();
	}

	public LapangModel(Parcel in) {
		// TODO Auto-generated constructor stub
//		lid = in.readString();
		code_booking = in.readString();
		kategori_lapang = in.readString();
		tanggal = in.readString();
		jam = in.readString();
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
//		dest.writeString(lid);
		dest.writeString(code_booking);
		dest.writeString(kategori_lapang);
		dest.writeString(tanggal);
		dest.writeString(jam);
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

//	public String getId() {
//		return lid;
//	}
//
//	public void setId(String lid) {
//		this.lid = lid;
//	}

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

}
