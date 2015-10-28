package com.lixh.basecode.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixh.basecode.R;
import com.lixh.basecode.util.DensityUtil;

/**
 * 
 * @Title: MyRadioGroup.java
 * @Package com.cihon.widget
 * @Description: 自定的view 实现 suport-v4中的 pagerTitleStrip viewpaper
 * @author lixh
 * @date 2015年5月15日 下午8:37:36
 * @version V1.0
 */
public class MyPagerTitleStrip extends LinearLayout {
	private ViewPager viewPager;
	private final PageListener pageListener = new PageListener();
	public OnPageChangeListener delegatePageListener;
	private TextView mPrevText;
	private TextView mCurrText;
	private TextView mNextText;
	private int mPrevIcon;
	private int mNextIcon;
	String title[];
	private static final float SIDE_ALPHA = 0.6f;
	private int mNonPrimaryAlpha;
	int mTextColor;

	/**
	 * @param context
	 * @param attrs
	 */
	public MyPagerTitleStrip(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	private void initView(Context context, AttributeSet attrs) {
		setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundResource(R.drawable.shape_buttom_line);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		addView(mPrevText = new TextView(context));
		addView(mCurrText = new TextView(context));
		addView(mNextText = new TextView(context));
		LinearLayout.LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.weight = 1.0f;
		mPrevText.setLayoutParams(lp);
		mCurrText.setLayoutParams(lp);
		mNextText.setLayoutParams(lp);
		setTextSize();
		mPrevText.setEllipsize(TruncateAt.END);
		mCurrText.setEllipsize(TruncateAt.END);
		mNextText.setEllipsize(TruncateAt.END);
		mCurrText.setGravity(Gravity.CENTER);
		setNonPrimaryAlpha(SIDE_ALPHA);
		mPrevText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (viewPager.getCurrentItem() - 1 >= 0 && viewPager != null) {
					viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
				}

			}
		});
		mNextText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (viewPager.getCurrentItem() + 1 < viewPager.getChildCount()
						&& viewPager != null) {
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
				}

			}
		});

	}

	private void setTextSize() {

		mPrevText.setTextSize(DensityUtil.sp2px(getContext(), 10));// 左边
		mCurrText.setTextSize(DensityUtil.sp2px(getContext(), 14));// 中间
		mNextText.setTextSize(DensityUtil.sp2px(getContext(), 10));// 右边

	}

	public class MyPagerAdapter extends BasePagerAdapter {

		/**
		 * 
		 * @param fm
		 * @param arrayId
		 *            array.xml
		 * @param mFragment
		 */

		public MyPagerAdapter(FragmentManager fm, int arraysID,
				Fragment[] mFragment) {
			super(fm, getResources().getStringArray(arraysID), mFragment);
		}

		/**
		 * 
		 * @param fm
		 * @param titles
		 * @param mFragment
		 */
		public MyPagerAdapter(FragmentManager fm, String[] titles,
				Fragment[] mFragment) {
			super(fm, titles, mFragment);
		}
	}

	public class BasePagerAdapter extends FragmentPagerAdapter {
		Fragment[] mFragment;
		String[] titles;

		public BasePagerAdapter(FragmentManager fm, String[] title,
				Fragment[] mFragment) {
			super(fm);
			this.mFragment = mFragment;
			this.titles = title;

		}

		@Override
		public CharSequence getPageTitle(int position) {

			return titles[position];
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

	public void setViewPager(ViewPager viewPager2,
			FragmentManager supportFragmentManager, List<String> titles,
			List<Fragment> mFragment) {
		this.setViewPager(viewPager2, supportFragmentManager,
				titles.toArray(new String[titles.size()]),
				mFragment.toArray(new Fragment[mFragment.size()]));

	}

	/**
	 * ViewPaper 适配器
	 * 
	 * @param pager
	 * @param supportFragmentManager
	 * @param f
	 */
	public void setViewPager(ViewPager pager,
			FragmentManager supportFragmentManager, int arrayId, Fragment[] f) {
		this.viewPager = pager;
		viewPager.setAdapter(new MyPagerAdapter(supportFragmentManager,
				arrayId, f));
		if (viewPager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		updateTextView(viewPager.getAdapter(), 0);
		viewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPaper 适配器
	 * 
	 * @param pager
	 * @param supportFragmentManager
	 * @param f
	 */
	public void setViewPager(ViewPager pager,
			FragmentManager supportFragmentManager, String[] titles,
			Fragment[] f) {
		this.viewPager = pager;
		viewPager.setAdapter(new MyPagerAdapter(supportFragmentManager, titles,
				f));
		if (viewPager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		updateTextView(viewPager.getAdapter(), 0);
		viewPager.setOnPageChangeListener(pageListener);
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
			updateTextView(viewPager.getAdapter(), position);

		}

	}

	public void updateTextView(PagerAdapter adapter, int currentPosition) {
		final int itemCount = adapter != null ? adapter.getCount() : 0;

		CharSequence text = null;
		if (currentPosition >= 1 && adapter != null) {
			text = adapter.getPageTitle(currentPosition - 1);
		}
		mPrevText.setText(text);

		mCurrText
				.setText(adapter != null && currentPosition < itemCount ? adapter
						.getPageTitle(currentPosition) : null);

		text = null;
		if (currentPosition + 1 < itemCount && adapter != null) {
			text = adapter.getPageTitle(currentPosition + 1);
		}

		mNextText.setText(text);
		if (mPrevText.getText().length() > 0
				&& mNextText.getText().length() > 0) {
			setDrawable(mPrevText, getmPrevIcon());
			setDrawable(mNextText, getmNextIcon());
			mNextText.setVisibility(View.VISIBLE);
			mPrevText.setVisibility(View.VISIBLE);
		} else {
			if (mNextText.getText().length() > 0) {
				mPrevText.setVisibility(View.INVISIBLE);
				setDrawable(mNextText, getmNextIcon());
			} else {
				mPrevText.setVisibility(View.VISIBLE);
			}
			if (mPrevText.getText().length() > 0) {
				mNextText.setVisibility(View.INVISIBLE);
				setDrawable(mPrevText, getmPrevIcon());
			} else {
				mNextText.setVisibility(View.VISIBLE);
			}
		}
		setTextColor(Color.WHITE);
	}

	/**
	 * Set the alpha value used for non-primary page titles.
	 * 
	 * @param alpha
	 *            Opacity value in the range 0-1f
	 */
	public void setNonPrimaryAlpha(float alpha) {
		mNonPrimaryAlpha = (int) (alpha * 255) & 0xFF;
		final int transparentColor = (mNonPrimaryAlpha << 24)
				| (mTextColor & 0xFFFFFF);
		mPrevText.setTextColor(transparentColor);
		mNextText.setTextColor(transparentColor);
	}

	/**
	 * Set the color value used as the base color for all displayed page titles.
	 * Alpha will be ignored for non-primary page titles. See
	 * {@link #setNonPrimaryAlpha(float)}.
	 * 
	 * @param color
	 *            Color hex code in 0xAARRGGBB format
	 */
	public void setTextColor(int color) {
		mTextColor = color;
		mCurrText.setTextColor(color);
		final int transparentColor = (mNonPrimaryAlpha << 24)
				| (mTextColor & 0xFFFFFF);
		mPrevText.setTextColor(transparentColor);
		mNextText.setTextColor(transparentColor);
	}

	/**
	 * 设置按钮
	 * 
	 * @param textView
	 * @param drawable
	 * @param left
	 */
	public void setDrawable(TextView textView, int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		if (textView == mPrevText) {
			mPrevText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			textView.setCompoundDrawables(drawable, null, null, null);
		} else if (textView == mNextText) {
			mNextText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			textView.setCompoundDrawables(null, null, drawable, null);
		}

	}

	/**
	 * @return the mPrevIcon
	 */
	public int getmPrevIcon() {
		return mPrevIcon;
	}

	/**
	 * @param mPrevIcon
	 *            the mPrevIcon to set
	 */
	public void setmPrevIcon(int mPrevIcon) {
		this.mPrevIcon = mPrevIcon;
	}

	/**
	 * @return the mNextIcon
	 */
	public int getmNextIcon() {
		return mNextIcon;
	}

	/**
	 * @param mNextIcon
	 *            the mNextIcon to set
	 */
	public void setmNextIcon(int mNextIcon) {
		this.mNextIcon = mNextIcon;
	}

}
