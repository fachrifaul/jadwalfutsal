package id.facworks.jadwalfutsal;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class KontakActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_kontak);

		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");

		text1.setTypeface(face);
		text2.setTypeface(face);

	}

}
