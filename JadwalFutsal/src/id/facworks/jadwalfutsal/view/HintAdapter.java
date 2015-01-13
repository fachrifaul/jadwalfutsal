package id.facworks.jadwalfutsal.view;

import java.util.List;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HintAdapter extends ArrayAdapter<String> {
	Typeface face = Typeface.createFromAsset(getContext().getAssets(),
			"fonts/Lato-Regular.ttf");
	public HintAdapter(Context theContext, List<String> objects) {
		super(theContext, R.id.text1, R.id.text1, objects);
	}

	public HintAdapter(Context theContext, List<String> jam_mulai,
			int theLayoutResId) {
		super(theContext, theLayoutResId, R.id.text1, jam_mulai);
	}

	@Override
	public int getCount() {
		// don't display last item. It is used as hint.
		int count = super.getCount();
		return count > 0 ? count - 1 : count;
	}

	// Affects default (closed) state of the spinner
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		view.setTypeface(face);
		return view;
	}

	// Affects opened state of the spinner
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getDropDownView(position, convertView,
				parent);
		view.setTypeface(face);
		return view;
	}
}