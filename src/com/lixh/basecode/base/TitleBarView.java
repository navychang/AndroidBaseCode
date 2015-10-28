package com.lixh.basecode.base;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lixh.basecode.R;

/**
 * 
 * @Title: TopBarView.java
 * @Package com.cihon.widget
 * @Description: 标题导航栏 actionbar
 * @author lixh
 * @date 2015年5月16日 上午11:02:52
 * @version V1.0
 */
public class TitleBarView {
	/**
	 * 默认样式 标题+返回
	 */
	public final static int DEFAULTSTYLE = 0;

	/**
	 * 默认样式 标题+返回+右侧文字按钮
	 */
	public final static int RIGHT_TV_STYLE = 1;

	/**
	 * 默认样式 标题+返回+右侧图片按钮
	 */
	public final static int RIGHT_IMG_STYLE = 2;

	/**
	 * 样式 只有标题
	 */
	public final static int ONLY_TITLE_STYLE = 3;

	/**
	 * 默认样式 标题+左侧图片按钮+右侧图片按钮
	 */
	public final static int LEFT_RIGHT_IMG_STYLE = 5;

	/**
	 * 左侧文字按钮+标题+右侧文字按钮
	 */
	public final static int LEFT_RIGHT_FONT_TILTE_STYLE = 6;

	/**
	 * 左侧文字按钮+标题+右侧图片按钮
	 */
	public final static int LEFT_FONT_RIGHT_IMG_TILTE_STYLE = 7;
	/**
	 * 左侧返回+标题+右侧文字按钮
	 */
	public final static int LEFT_IMG_RIGHT_FONT_TILTE_STYLE = 8;
	public TextView mTopLeft;
	public TextView mTopTitle;
	public TextView mTopRight;
	public RelativeLayout tittleBar;
	public Resources mres;
	public ImageButton mTopLeft_img;
	public ImageButton mTopRight_img;
	private Activity mContext;

	public static enum DrawableDirection {
		LEFT, RIGHT
	}

	public TitleBarView(Activity context) {
		this.mContext = context;
		mres = mContext.getResources();
		init();

	}

	private void init() {
		tittleBar = (RelativeLayout) mContext.findViewById(R.id.tittleBar);
		mTopLeft = (TextView) mContext.findViewById(R.id.top_left_tv);
		mTopLeft_img = (ImageButton) mContext.findViewById(R.id.top_left_img);
		mTopTitle = (TextView) mContext.findViewById(R.id.top_title);
		mTopRight = (TextView) mContext.findViewById(R.id.top_right_tv);
		mTopRight_img = (ImageButton) mContext.findViewById(R.id.top_right_img);
		mTopLeft_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mContext.onBackPressed();
			}
		});
	}

	/**
	 * 设置标题栏是否显示 默认显示
	 * 
	 * @param visible
	 */
	public void setVisible(int visible) {
		tittleBar.setVisibility(visible);
	}

	/**
	 * 设置返回按钮的坚挺事件 ,该方法重写默认的返回事件
	 * 
	 * @param listener
	 */
	public void setBackBtnListener(OnClickListener listener) {
		if (null != listener) {
			mTopLeft_img.setOnClickListener(listener);
		}
	}

	/**
	 * 设置标题栏
	 * 
	 * @param title
	 * @return
	 */
	public TitleBarView setTitle(String title) {
		mTopTitle.setText(title);
		return this;
	}

	/**
	 * 设置右侧文字按钮 的文字
	 * 
	 * @param text
	 * @param textColor
	 *            //颜色
	 * @param listener
	 * @return
	 */
	public TitleBarView setRightTVBtn(String text, int textColor,
			OnClickListener listener) {

		mTopRight.setText(text);
		if (textColor != 0) {
			mTopRight.setTextColor(textColor);
		}
		if (null != listener) {
			mTopRight.setOnClickListener(listener);
		}
		return this;
	}

	/**
	 * 设置右侧图片按钮的资源
	 * 
	 * @param iconResource
	 * @param listener
	 * @return
	 */
	public TitleBarView setRightImgBtn(int righticonResource,
			OnClickListener listener) {
		mTopRight_img.setImageResource(righticonResource);
		if (null != listener) {
			mTopRight_img.setOnClickListener(listener);
		}
		return this;

	}

	/**
	 * 设置右侧文字按钮 的文字
	 * 
	 * @param text
	 * @param listener
	 * @return
	 */
	public TitleBarView setLeftTVBtn(String text, OnClickListener listener) {
		mTopLeft.setText(text);
		if (null != listener) {
			mTopLeft.setOnClickListener(listener);
		}
		return this;
	}

	/**
	 * 设置右侧文字按钮加图片
	 * 
	 * @param text
	 * @param direction
	 *            方向
	 * @param righticonResource
	 */
	public TitleBarView setRightTV_IMGBtn(String text,
			DrawableDirection direction, int righticonResource,
			OnClickListener listener) {
		setDrawable(mTopRight, direction, text, righticonResource);
		if (null != listener) {
			mTopRight.setOnClickListener(listener);
		}
		return this;
	}

	/**
	 * 设置左侧文字按钮加图片
	 * 
	 * @param text
	 * @param direction
	 *            方向
	 * @param lefticonResource
	 */
	public TitleBarView setLeftTV_IMGBtn(String text,
			DrawableDirection direction, int lefticonResource,
			OnClickListener listener) {
		setDrawable(mTopLeft, direction, text, lefticonResource);
		if (null != listener) {
			mTopLeft.setOnClickListener(listener);
		}
		return this;
	}

	/**
	 * 设置左侧图片按钮的资源
	 * 
	 * @param iconResource
	 * 
	 */
	public TitleBarView setLeftImgBtn(int lefticonResource,
			OnClickListener listener) {
		mTopLeft_img.setImageResource(lefticonResource);
		if (null != listener) {
			mTopLeft_img.setOnClickListener(listener);
		}

		return this;

	}

	public void setDrawable(TextView textView, DrawableDirection direction,
			String text, int drawable) {
		Drawable drawable1 = mres.getDrawable(drawable);
		// / 这一步必须要做,否则不会显示.
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getMinimumHeight());
		textView.setText(text);
		if (direction == DrawableDirection.LEFT) {
			textView.setGravity(Gravity.LEFT);
			textView.setCompoundDrawables(drawable1, null, null, null);
		} else {
			textView.setCompoundDrawables(null, null, drawable1, null);
		}

	}

	/**
	 * 设置标题栏样式
	 * 
	 * @param titleStyle
	 */
	public TitleBarView setTitleStyle(int titleStyle) {
		this.titleStyle = titleStyle;
		initStyle();
		return this;
	}

	/**
	 * 标题样式
	 */
	private int titleStyle = DEFAULTSTYLE;

	/**
	 * 初始化样式
	 */
	public void initStyle() {
		for (int i = 0; i < tittleBar.getChildCount(); i++) {
			tittleBar.getChildAt(i).setVisibility(View.GONE);
		}
		mTopTitle.setVisibility(View.VISIBLE);
		switch (titleStyle) {
		case RIGHT_TV_STYLE:
			mTopLeft_img.setVisibility(View.VISIBLE);
			mTopRight.setVisibility(View.VISIBLE);
			break;
		case RIGHT_IMG_STYLE:
			mTopRight_img.setVisibility(View.VISIBLE);
			break;
		case LEFT_RIGHT_IMG_STYLE:
			mTopLeft_img.setVisibility(View.VISIBLE);

			mTopRight_img.setVisibility(View.VISIBLE);
			break;
		case LEFT_RIGHT_FONT_TILTE_STYLE:
			mTopLeft.setVisibility(View.VISIBLE);
			mTopRight.setVisibility(View.VISIBLE);
			break;
		case ONLY_TITLE_STYLE:
			break;
		case LEFT_FONT_RIGHT_IMG_TILTE_STYLE:
			mTopLeft.setVisibility(View.VISIBLE);
			mTopRight_img.setVisibility(View.VISIBLE);
			break;
		case LEFT_IMG_RIGHT_FONT_TILTE_STYLE:
			mTopLeft_img.setVisibility(View.VISIBLE);
			mTopRight.setVisibility(View.VISIBLE);
			break;
		case DEFAULTSTYLE:
			mTopLeft_img.setVisibility(View.VISIBLE);
		default:
			break;
		}
		tittleBar.setVisibility(View.VISIBLE);
	}

}
