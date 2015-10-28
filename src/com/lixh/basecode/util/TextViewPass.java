/**  
 * @Title: TextViewRes.java 
 * @Package com.cihon.util 
 * @Description: TODO  
 * @author lixh 
 * @date 2015年7月17日 上午10:53:16 
 * @version V1.0  
 */
package com.lixh.basecode.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lixh.basecode.R;

/**
 * @Title: TextViewRes.java
 * @Package com.cihon.util
 * @Description: TODO
 * @author lixh
 * @date 2015年7月17日 上午10:53:16
 * @version V1.0
 */
public class TextViewPass extends TextView {
	private static final String TAG = "TextViewRes";
	private boolean partially_hidden = true;
	private String text = "";

	@Override
	public CharSequence getText() {
		return text;
	}

	public void setText(String text, int start, int end) {
		this.text = text;
		CharSequence s = text;
		if (!TextUtils.isEmpty(text)) {

			if (partially_hidden) {
				s = PasswordTransformationMethod.getInstance()
						.getTransformation(text, start, end);
			}

		}
		super.setText(s);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TextViewPass(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.TextViewPass);
			partially_hidden = a.getBoolean(R.styleable.TextViewPass_Gone,
					partially_hidden);
		}

	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TextViewPass(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 */
	public TextViewPass(Context context) {
		this(context, null);
	}

}
