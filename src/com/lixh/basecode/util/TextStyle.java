package com.lixh.basecode.util;

import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class TextStyle {
	private SpannableString spannableString;

	public TextStyle() {
	}

	public TextStyle(String str) {
		this.spannableString = new SpannableString(str);
	}

	public void setString(String str) {
		this.spannableString = new SpannableString(str);
	}

	public SpannableString getSpannableString() {
		return this.spannableString;
	}

	public TextStyle setAbsoluteSize(int size, int start, int end, boolean dp) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new AbsoluteSizeSpan(size, dp), start,
				end, 33);
		return this;
	}

	public TextStyle setRelativeSize(float size, int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString
				.setSpan(new RelativeSizeSpan(size), start, end, 33);
		return this;
	}

	public TextStyle setForegroundColor(int Color, int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new ForegroundColorSpan(Color), start,
				end, 33);
		return this;
	}

	public TextStyle setBackgroundColor(int Color, int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new BackgroundColorSpan(Color), start,
				end, 33);
		return this;
	}

	public static void setFakeBold(TextView textView, boolean isBold) {
		TextPaint tp = textView.getPaint();
		tp.setFakeBoldText(isBold);
	}

	public TextStyle setUnderlineSpan(int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new UnderlineSpan(), start, end, 33);
		return this;
	}

	public TextStyle setStrikethroughSpan(int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new StrikethroughSpan(), start, end, 33);
		return this;
	}

	public TextStyle setSubscriptSpan(int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new SubscriptSpan(), start, end, 33);
		return this;
	}

	public TextStyle setSuperscriptSpan(int start, int end) {
		if (this.spannableString == null) {
			return this;
		}
		this.spannableString.setSpan(new SuperscriptSpan(), start, end, 33);
		return this;
	}
}