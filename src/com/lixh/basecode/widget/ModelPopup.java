package com.lixh.basecode.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.lixh.basecode.R;

public class ModelPopup extends PopupWindow implements OnClickListener {

	private View mPopView;
	Button btn_cancel;
	public ModelPopup(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.dialog_get_model, null);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		btn_cancel= (Button) mPopView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
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
	}

	public void setOnOneClick(String oneText, OnClickListener click) {
		Button one = (Button) mPopView.findViewById(R.id.btn_one);
		one.setVisibility(View.VISIBLE);
		one.setText(oneText);
		one.setOnClickListener(click);
	}

	public void setOnTwoClick(String twoText, OnClickListener onclick) {
		Button two = (Button) mPopView.findViewById(R.id.btn_two);
		two.setVisibility(View.VISIBLE);
		two.setText(twoText);
		two.setOnClickListener(onclick);
	}

	public void setOnThreeClick(String threeText, OnClickListener onclick) {
		Button three = (Button) mPopView.findViewById(R.id.btn_three);
		three.setVisibility(View.VISIBLE);
		three.setText(threeText);
		three.setOnClickListener(onclick);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_cancel) {
			dismiss();
		}

	}
}
