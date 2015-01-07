package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.adapter.TableJadwalAdapter;
import id.facworks.jadwalfutsal.conn.WebApi;
import id.facworks.jadwalfutsal.db.Constants.Extra;
import id.facworks.jadwalfutsal.object.LapangFromJSON;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

public class TableActivity extends Activity {
	SharedPreferences prefs;

	
	private LapangJSON task;
	BaseTableAdapter baseTableAdapter;
	
	public ArrayList<LapangFromJSON> newPois;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		prefs = PreferenceManager
				.getDefaultSharedPreferences(TableActivity.this);

		Log.d("TOKOROTI",
				"id na " + prefs.getString(Extra.LOGIN_USER_ID_KEY, " "));

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		baseTableAdapter = new TableJadwalAdapter(this,new ArrayList<LapangFromJSON>());
		tableFixHeaders.setAdapter(baseTableAdapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		task = new LapangJSON();
		task.execute();
		super.onResume();
	}

	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_table, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.item_profile) {
			// finish();
			// startActivity(new Intent(ListActivity.this, MixView.class));

		}
		if (item.getItemId() == R.id.item_exit) {

			Editor editor = prefs.edit();
			editor.remove(Extra.LOGIN_USER_ID_KEY);
			editor.remove(Extra.LOGIN_TOKEN_KEY);
			editor.commit();

			finish();
			startActivity(new Intent(TableActivity.this, LoginActivity.class));
		}
		return super.onOptionsItemSelected(item);

	}

	private class LapangJSON extends AsyncTask<Integer, Object, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// if (listView != null) {
			// if (loadingFooterView == null) {
			// loadingFooterView = getActivity().getLayoutInflater()
			// .inflate(R.layout.load_more_footer, null);
			// ((ListView) listView).addFooterView(loadingFooterView);
			// }
			// }

		}

		protected String doInBackground(Integer... params) {
			WebApi api = WebApi.getInstance();
			try {
				//newPois = api.getLapang();
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}

			return null;

		}

		protected void onPostExecute(String result) {
			// if (isCancelled)
			if (result == null) {
				// for (int i = 0; i <= newPois.size() - 1; i++) {
				// System.out.println("lapang: " + newPois.get(i).getnomor());
				// // if (string == "Lapang" + newPois.get(i)) {
				// //
				// // }
				// }
//				for (LapangFromJSON lapang : newPois) {
//					// if (isCancelled)
//					// return;
//					//baseTableAdapter.add(lapang);
//				}
			}

			super.onPostExecute(result);
		}
	}

}
