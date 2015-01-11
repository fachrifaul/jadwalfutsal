//package id.facworks.jadwalfutsal;
//
//import java.lang.reflect.Method;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//public class contoh extends Activity {
//	boolean expand = true;
//	static boolean animWorkingFlag = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.layout_contoh);
//
//		View hello = findViewById(R.id.lin_hello);
//		final RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel_nah_hide);
//		hello.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				expand = !expand;
//				Animation a = expand(rel, expand);
//				rel.setAnimation(a);
//				a.start();
//			}
//		});
//
//	}
//
//	public static Animation expand(final View v, final boolean expand) {
//		try {
//			Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,
//					int.class);
//			m.setAccessible(true);
//			m.invoke(v,
//					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//					MeasureSpec.makeMeasureSpec(
//							((View) v.getParent()).getMeasuredWidth(),
//							MeasureSpec.AT_MOST));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		final int initialHeight = v.getMeasuredHeight();
//
//		if (expand) {
//			v.getLayoutParams().height = 0;
//		} else {
//			v.getLayoutParams().height = initialHeight;
//		}
//		v.setVisibility(View.VISIBLE);
//
//		Animation a = new Animation() {
//			@Override
//			protected void applyTransformation(float interpolatedTime,
//					Transformation t) {
//				int newHeight = 0;
//				if (expand) {
//					newHeight = (int) (initialHeight * interpolatedTime);
//				} else {
//					newHeight = (int) (initialHeight * (1 - interpolatedTime));
//				}
//				v.getLayoutParams().height = newHeight;
//				v.requestLayout();
//
//				if (interpolatedTime == 1 && !expand)
//					v.setVisibility(View.GONE);
//			}
//
//			@Override
//			public boolean willChangeBounds() {
//				return true;
//			}
//		};
//
//		a.setDuration(100);
//		a.setAnimationListener(new AnimationListener() {
//			public void onAnimationEnd(Animation arg0) {
//				animWorkingFlag = false;
//			}
//
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//			}
//
//			public void onAnimationStart(Animation animation) {
//				animWorkingFlag = true;
//			}
//		});
//
//		return a;
//	}
//}
