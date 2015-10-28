package com.lixh.basecode.util.time;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-6-19
 * Copyright @ 2014 BU
 * Description: 绫绘弿杩?
 *
 * History:
 */
public class TimeTextView extends TextView implements Observer {

	private long startTime = 0;
	private long limitTime = 0;
	private TimeObservableValue ov = null;

	public TimeObservableValue getOv() {
		return ov;
	}

	public void setOv(TimeObservableValue ov) {
		this.ov = ov;
	}

	public TimeTextView(Context context) {
		super(context);
	}

	public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!isInEditMode()) {
			ov.addObserver(this);
		}
	
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		ov.deleteObserver(this);
	}

	public void setLimtTime(long startTime, long limitTime) {
		this.startTime = startTime;
		this.limitTime = limitTime;
		showTime();
	}

	public void setEndTime(long startTime, long endTime) {
		this.startTime = startTime;
		limitTime = endTime - startTime;
		showTime();
	}

	private void showTime() {
		if (startTime == 0 || limitTime == 0) {
			return;
		}
		long s = (startTime + limitTime) - System.currentTimeMillis();
		if (s < 0) {
			if (finish != null) {
				finish.finished(this);
			}
			setText("时间已到");
			return;
		}
		setText(formatDuring(s));
	}

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String time = "";
		if (seconds != 0) {
			time = seconds + "秒";
		}
		if (minutes != 0) {
			time = minutes + "分" + time;
		}
		if (hours != 0) {
			time = hours + "小时" + time;
		}
		if (days != 0) {
			time = days + "天" + time;
		}
		return time;
	}

	public interface Finish {
		public void finished(TextView view);
	}

	public Finish getFinish() {
		return finish;
	}

	public void setFinish(Finish finish) {
		this.finish = finish;
	}

	private Finish finish;

	//
	@Override
	public void update(Observable obs, Object obj) {
		if (obs == ov) {
			showTime();
		}

	}
}
