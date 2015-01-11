package id.facworks.jadwalfutsal.view;

import java.util.List;

import android.R;
import android.content.Context;
import android.widget.ArrayAdapter;

public class HintAdapter extends ArrayAdapter<String> {

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
}