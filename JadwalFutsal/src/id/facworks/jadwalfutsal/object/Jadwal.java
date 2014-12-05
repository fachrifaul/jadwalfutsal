package id.facworks.jadwalfutsal.object;

public class Jadwal {
	public final String[] data;

	public Jadwal(String jam, String senin, String selasa, String rabu,
			String kamis, String jumat, String sabtu, String minggu) {
		data = new String[] { jam, senin, selasa, rabu, kamis, jumat, sabtu,
				minggu };
	}
}
