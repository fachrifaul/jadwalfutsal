package id.facworks.jadwalfutsal.json;

import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.webservice.GetAllLapang;

import java.util.List;

import android.os.AsyncTask;

import com.alamkanak.weekview.WeekViewEvent;

public class LapangJSON extends AsyncTask<Integer, Object, String> {
	OnAsyncResult onPostEvent;
	List<WeekViewEvent> newJadwal;

	// private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

	public void setOnResultListener(OnAsyncResult onPostEvent) {
		if (onPostEvent != null) {
			this.onPostEvent = onPostEvent;
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	protected String doInBackground(Integer... params) {
		WebApi api = WebApi.getInstance();
		if (onPostEvent != null) {
			try {
				newJadwal = api.getLapang();
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
			onPostEvent.onResultSuccess(newJadwal);

			// for (WeekViewEvent jadwallapang : newJadwal) {
			// // if (isCancelled)
			// // return;
			// //events.add(jadwallapang);
			// }
		}

		return null;

	}

	public interface OnAsyncResult {
		public abstract void onResultSuccess(List<WeekViewEvent> newJadwal);
	}
}