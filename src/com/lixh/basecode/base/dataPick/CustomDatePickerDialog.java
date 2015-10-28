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
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		// �������Ŀؼ�Ҳ����ʹ��PopUpWindow dimiss
		this.setOutsideTouchable(true);
		// ����SelectPicPopupWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.PopupAnimation);
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);// 0xb0000000
		// ����SelectPicPopupWindow��������ı���
		this.setBackgroundDrawable(dw);// ��͸����ɫ
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
	 * �����������ڣ����ú󣬲���ѡ�����õĿ�ʼ������ǰ������
	 * 
	 * @param c
	 */
	public void setNowData(Calendar c) {
		cdp.setNowData(c);

	}

	/**
	 * ���õ��ȷ�ϵ��¼�
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
