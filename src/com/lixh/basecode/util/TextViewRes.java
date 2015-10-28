package com.lixh.basecode.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.lixh.basecode.R;

/**
 * @Title: TextViewRes.java
 * @Package com.cihon.util
 * @Description: TODO
 * @author lixh
 * @date 2015年7月17日 上午10:53:16
 * @version V1.0
 */
public class TextViewRes extends EditText {
	private static final String TAG = "TextViewRes";
	private boolean partially_hidden = true;
	private String text = "";

	public String getSubText() {
		return text;
	}

	public void setText(String text, int start, int end) {
		this.text = text;
		this.str_start = start;
		this.str_end = end;
		CharSequence s = text;
		if (!TextUtils.isEmpty(text)) {

			if (partially_hidden) {
				s = PasswordTransformationMethod.getInstance()
						.getTransformation(text, start, end);
			}

		}
		super.setText(s);
	}

	private int str_start;
	private int str_end;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TextViewRes(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.TextViewPass);
			partially_hidden = a.getBoolean(R.styleable.TextViewPass_Gone,
					partially_hidden);
		}
		setFocusable(true);
		setFocusableInTouchMode(true);
		addTextChangedListener(new TextWatcherImpl());
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				partially_hidden = false;
				setText(text);
				return false;
			}
		});
	}

	/**
	 * 重新设置为隐藏的文字
	 */
	public void resetTextView() {
		partially_hidden = true;
		setText(text, str_start, str_end);
	}

	/**
	 * @param s
	 */
	public void setSubText(Editable s) {
		this.text = s.toString();

	}

	// 当输入结束后判断是否显示右边clean的图标
	private class TextWatcherImpl implements TextWatcher {
		@Override
		public void afterTextChanged(Editable s) {
			if (!partially_hidden) {
				setSubText(s);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TextViewRes(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 */
	public TextViewRes(Context context) {
		this(context, null);
	}

}
