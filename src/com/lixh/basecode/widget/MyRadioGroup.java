package com.lixh.basecode.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lixh.basecode.R;

/**
 * 
 * @Title: MyRadioGroup.java
 * @Package com.cihon.widget
 * @Description: 自定的view RadioGroup viewpaper
 * @author lixh
 * @date 2015年5月15日 下午8:37:36
 * @version V1.0
 */
public class MyRadioGroup extends RelativeLayout {
	Context context;
	private ViewPager viewPager;
	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;
	private RadioGroup rg;
	private final OnCheckedChange listener = new OnCheckedChange();
	private int spaceLine_H = 35;
	private int spaceLine_W = 1;
	private DisplayMetrics dm;

	public MyRadioGroup(Context context) {
		super(context);
		initView(context, null);
	}

	public MyRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	private void initView(Context context2, AttributeSet attrs) {
		this.context = context2;
		dm = getResources().getDisplayMetrics();
		spaceLine_H = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, spaceLine_H, dm);
		spaceLine_W = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, spaceLine_W, dm);
	}

	/**
	 * 设置分割线的宽 高
	 * 
	 * @param widght
	 * @param height
	 */
	public void setline(int widght, int height) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		spaceLine_H = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, widght, dm);
		spaceLine_W = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, widght, dm);
	}

	/**
	 * 在arrays.xml中配置如 <string-array name="MyOrderTitle"> <item>全部</item>
	 * <item>待支付</item> <item>已完成</item> </string-array>
	 * 
	 * @param rg
	 * @param arraysID
	 */
	public void setRadioGroup(RadioGroup rg, int arraysID) {
		String title[] = context.getResources().getStringArray(arraysID);
		this.rg = rg;
		fillRidioButton(title);

	}

	/**
	 * 在代码中写入title
	 * 
	 * @param rg
	 * @param title
	 */
	public void setRadioGroup(RadioGroup rg, String title[]) {
		this.rg = rg;
		fillRidioButton(title);

	}

	public class MyPagerAdapter extends BasePagerAdapter {

		/**
		 * @param fm
		 * @param mFragment
		 */
		public MyPagerAdapter(FragmentManager fm, Fragment[] mFragment) {
			super(fm, mFragment);
		}

	}

	public class BasePagerAdapter extends FragmentPagerAdapter {
		Fragment[] mFragment;

		public BasePagerAdapter(FragmentManager fm, Fragment[] mFragment) {
			super(fm);
			this.mFragment = mFragment;

		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return mFragment.length;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragment[position];
		}

	}

	/**
	 * ViewPaper 适配器
	 * 
	 * @param pager
	 * @param supportFragmentManager
	 * @param f
	 */
	public void setViewPager(ViewPager pager,
			FragmentManager supportFragmentManager, Fragment[] f) {
		this.viewPager = pager;
		viewPager.setAdapter(new MyPagerAdapter(supportFragmentManager, f));
		if (viewPager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}

		viewPager.setOnPageChangeListener(pageListener);
		rg.check(0);
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	private class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageSelected(int position) {
			rg.check(position);
		}

	}

	public class OnCheckedChange implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			viewPager.setCurrentItem(checkedId, true);

		}
	};

	protected View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	private void fillRidioButton(String[] title) {
		rg.setOnCheckedChangeListener(listener);
		for (int i = 0; i < title.length; i++) {
			RadioButton rb = (RadioButton) inflate(R.layout.layout_myradiobutton);
			rb.setId(i);

			rb.setText(title[i]);
			RadioGroup.LayoutParams pl = new RadioGroup.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			pl.weight = 1;
			rb.setLayoutParams(pl);

			TextView t = new TextView(context);
			t.setBackgroundColor(context.getResources().getColor(
					R.color.color_assist_text_orange));
			t.setLayoutParams(new LayoutParams(1, spaceLine_H));
			if (i == 0) {
				rb.setBackgroundResource(R.drawable.radiobutton_leftcorner_selector);
			} else if (i == title.length - 1) {
				rb.setBackgroundResource(R.drawable.radiobutton_rightcorner_selector);
			}
			rg.addView(rb);
			if (i < title.length - 1) {
				rg.addView(t);

			}

		}
	}
}
