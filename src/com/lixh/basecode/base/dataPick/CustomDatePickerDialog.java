package com.lixh.basecode.base.dataPick;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lixh.basecode.R;
import com.lixh.basecode.base.dataPick.CustomDatePicker.ChangingListener;

@SuppressLint("SimpleDateFormat")
public class CustomDatePickerDialog extends PopupWindow {

	private TextView tv_sure;
	private TextView tv_cancel;
	private onDateListener listener;
	private Calendar c = Calendar.getInstance();
	private CustomDatePicker cdp;
	TextView tv_dialog_title;
	private View mPopView;

	public CustomDatePickerDialog(Context context, Calendar c) {
		super(context);
		this.c = c;
		initView(context);
	}

	protected void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.custom_datepicker_dialog, null);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 点击外面的控件也可以使得PopUpWindow dimiss
		this.setOutsideTouchable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);// 0xb0000000
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);// 半透明颜色
		tv_sure = (TextView) mPopView.findViewById(R.id.tv_sure);
		tv_cancel = (TextView) mPopView.findViewById(R.id.tv_cancel);

		cdp = (CustomDatePicker) mPopView.findViewById(R.id.cdp);

		cdp.setDate(c);
		cdp.addChangingListener(new ChangingListener() {

			@Override
			public void onChange(Calendar c1) {
				c = c1;
			}
		});

		tv_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.dateFinish(c);
				}
				dismiss();
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

			}
		});

	}

	public void setDialogTittle(String tittle) {
		tv_dialog_title.setText(tittle);
	}

	/**
	 * 设置限制日期，设置后，不能选择设置的开始日期以前的日期
	 * 
	 * @param c
	 */
	public void setNowData(Calendar c) {
		cdp.setNowData(c);

	}

	/**
	 * 设置点击确认的事件
	 * 
	 * @param listener
	 */
	public void addDateListener(onDateListener listener) {
		this.listener = listener;
	}

	public interface onDateListener {
		void dateFinish(Calendar c);
	}

}
