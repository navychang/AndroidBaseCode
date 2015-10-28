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
 * @Description: ���⵼���� actionbar
 * @author lixh
 * @date 2015��5��16�� ����11:02:52
 * @version V1.0
 */
public class TitleBarView {
	/**
	 * Ĭ����ʽ ����+����
	 */
	public final static int DEFAULTSTYLE = 0;

	/**
	 * Ĭ����ʽ ����+����+�Ҳ����ְ�ť
	 */
	public final static int RIGHT_TV_STYLE = 1;

	/**
	 * Ĭ����ʽ ����+����+�Ҳ�ͼƬ��ť
	 */
	public final static int RIGHT_IMG_STYLE = 2;

	/**
	 * ��ʽ ֻ�б���
	 */
	public final static int ONLY_TITLE_STYLE = 3;

	/**
	 * Ĭ����ʽ ����+���ͼƬ��ť+�Ҳ�ͼƬ��ť
	 */
	public final static int LEFT_RIGHT_IMG_STYLE = 5;

	/**
	 * ������ְ�ť+����+�Ҳ����ְ�ť
	 */
	public final static int LEFT_RIGHT_FONT_TILTE_STYLE = 6;

	/**
	 * ������ְ�ť+����+�Ҳ�ͼƬ��ť
	 */
	public final static int LEFT_FONT_RIGHT_IMG_TILTE_STYLE = 7;
	/**
	 * ��෵��+����+�Ҳ����ְ�ť
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
	 * ���ñ������Ƿ���ʾ Ĭ����ʾ
	 * 
	 * @param visible
	 */
	public void setVisible(int visible) {
		tittleBar.setVisibility(visible);
	}

	/**
	 * ���÷��ذ�ť�ļ�ͦ�¼� ,�÷�����дĬ�ϵķ����¼�
	 * 
	 * @param listener
	 */
	public void setBackBtnListener(OnClickListener listener) {
		if (null != listener) {
			mTopLeft_img.setOnClickListener(listener);
		}
	}

	/**
	 * ���ñ�����
	 * 
	 * @param title
	 * @return
	 */
	public TitleBarView setTitle(String title) {
		mTopTitle.setText(title);
		return this;
	}

	/**
	 * �����Ҳ����ְ�ť ������
	 * 
	 * @param text
	 * @param textColor
	 *            //��ɫ
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
	 * �����Ҳ�ͼƬ��ť����Դ
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
	 * �����Ҳ����ְ�ť ������
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
	 * �����Ҳ����ְ�ť��ͼƬ
	 * 
	 * @param text
	 * @param direction
	 *            ����
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
	 * ����������ְ�ť��ͼƬ
	 * 
	 * @param text
	 * @param direction
	 *            ����
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
	 * �������ͼƬ��ť����Դ
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
		// / ��һ������Ҫ��,���򲻻���ʾ.
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
	 * ���ñ�������ʽ
	 * 
	 * @param titleStyle
	 */
	public TitleBarView setTitleStyle(int titleStyle) {
		this.titleStyle = titleStyle;
		initStyle();
		return this;
	}

	/**
	 * ������ʽ
	 */
	private int titleStyle = DEFAULTSTYLE;

	/**
	 * ��ʼ����ʽ
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
