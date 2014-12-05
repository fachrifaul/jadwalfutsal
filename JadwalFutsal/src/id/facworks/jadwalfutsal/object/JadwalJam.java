package id.facworks.jadwalfutsal.object;

import java.util.ArrayList;
import java.util.List;

public class JadwalJam {
		public final String name;
		public final List<Jadwal> list;

		public JadwalJam(String name) {
			this.name = name;
			list = new ArrayList<Jadwal>();
		}

		public int size() {
			return list.size();
		}

		public Jadwal get(int i) {
			return list.get(i);
		}
	}