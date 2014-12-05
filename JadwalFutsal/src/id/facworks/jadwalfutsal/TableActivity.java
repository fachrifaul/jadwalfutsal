package id.facworks.jadwalfutsal;

import id.facworks.jadwalfutsal.adapter.TableJadwalAdapter;
import android.app.Activity;
import android.os.Bundle;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

public class TableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		BaseTableAdapter baseTableAdapter = new TableJadwalAdapter(this);
		tableFixHeaders.setAdapter(baseTableAdapter);

	}

}
