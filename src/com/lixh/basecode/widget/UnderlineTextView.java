package com.lixh.basecode.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 
 * @Title: UnderlineTextView.java
 * @Package com.cihon.widget
 * @Description: �Զ����»��ߵ�textView
 * @author lixh
 * @date 2015��5��19�� ����11:33:24
 * @version V1.0
 */

public class UnderlineTextView extends TextView {

	private final Paint mPaint = new Paint();
	private int mUnderlineHeight = 0;

	public UnderlineTextView(Context context) {
		this(context, null);
	}

	public UnderlineTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UnderlineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		Resources r = getResources();
		// mUnderlineHeight == 6
		mUnderlineHeight = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom + mUnderlineHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Draw the underline the same color as the text

		mPaint.setColor(getTextColors().getDefaultColor());
		// �Խ����ϵ����㡣
		canvas.drawRect(0, getHeight() - mUnderlineHeight, getWidth(),
				getHeight(), mPaint);
	}
}