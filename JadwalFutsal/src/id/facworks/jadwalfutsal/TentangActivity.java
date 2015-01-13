package id.facworks.jadwalfutsal;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class TentangActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tentang);

		TextView welcome = (TextView) findViewById(R.id.welcome);
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		TextView text3 = (TextView) findViewById(R.id.text3);
		TextView text4 = (TextView) findViewById(R.id.text4);
		TextView text5 = (TextView) findViewById(R.id.text5);
		TextView text6 = (TextView) findViewById(R.id.text6);
		TextView text7 = (TextView) findViewById(R.id.text7);
		TextView text8 = (TextView) findViewById(R.id.text8);
		TextView text9 = (TextView) findViewById(R.id.text9);

		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/Lato-Regular.ttf");
		welcome.setTypeface(face);
		text1.setTypeface(face);
		text2.setTypeface(face);
		text3.setTypeface(face);
		text4.setTypeface(face);
		text5.setTypeface(face);
		text6.setTypeface(face);
		text7.setTypeface(face);
		text8.setTypeface(face);
		text9.setTypeface(face);

	}
	
	

}
