package id.facworks.jadwalfutsal;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class BantuanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bantuan);

		TextView bantuan = (TextView) findViewById(R.id.bantuan);
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		TextView text3 = (TextView) findViewById(R.id.text3);
		TextView text4 = (TextView) findViewById(R.id.text4);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		bantuan.setTypeface(face);
		text1.setTypeface(face);
		text2.setTypeface(face);
		text3.setTypeface(face);
		text4.setTypeface(face);

	}

}
