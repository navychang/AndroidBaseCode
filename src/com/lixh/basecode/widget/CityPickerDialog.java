package com.lixh.basecode.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lixh.basecode.R;
import com.lixh.basecode.widget.CityPicker.OnSelectString;

public class CityPickerDialog extends PopupWindow implements OnSelectString {

	private TextView tv_sure;
	private TextView tv_cancel;
	private CityPicker city_pick;

	public CityPickerDialog(Context context) {
		initView(context);
	}

	private View mPopView;

	protected void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.city_picker_dialog, null);
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

		city_pick = (CityPicker) mPopView.findViewById(R.id.city_pick);
		
		View.OnClickListener clickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == tv_sure) {
					select.selectstring(pro_string, city_string, city_id,
							city_id);
				}
				dismiss();
			}
		};

		tv_sure.setOnClickListener(clickListener);
		tv_cancel.setOnClickListener(clickListener);
		city_pick.selectstring(this);
		city_pick.setdefautSelectText();
	}

	SelectInfo select;

	public void setSelectInfoListener(SelectInfo select) {
		this.select = select;

	}

	public interface SelectInfo {
		void selectstring(String pro_string, String city_string, String pro_id,
				String city_id);

	}

	String pro_string;
	String city_string;

	//
	@Override
	public void selectstring(String pro_string, String city_string) {
		this.pro_string = pro_string;
		this.city_string = city_string;
	}

	private String pro_id;
	private String city_id;

	//
	@Override
	public void selectid(String pro_id, String city_id) {
		this.pro_id = pro_id;
		this.city_id = city_id;
	}

}
