package com.lixh.basecode.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.lixh.basecode.R;
import com.lixh.basecode.app.BaseApplication;
import com.lixh.basecode.bean.BasicInfoModel;
import com.lixh.basecode.db.DataBase;
import com.lixh.basecode.util.CitycodeUtil;
import com.lixh.basecode.widget.ScrollerNumberPicker.OnSelectListener;

/**
 * 城市Picker
 * 
 * @author zd
 * 
 */
public class CityPicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerNumberPicker provincePicker;
	private ScrollerNumberPicker cityPicker;

	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	private OnSelectString onSelectString;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private Context context;
	private List<BasicInfoModel> province_list = new ArrayList<BasicInfoModel>();
	private HashMap<String, List<BasicInfoModel>> city_map = new HashMap<String, List<BasicInfoModel>>();
	private DataBase Db;
	private CitycodeUtil citycodeUtil;
	private String city_code_string;
	private String city_string;
	private String cityone_id;
	private String citytwo_id;
	private String citythree_id;

	public CityPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		getaddressinfo(context);
		// TODO Auto-generated constructor stub
	}

	public CityPicker(Context context) {
		super(context);
		this.context = context;
		getaddressinfo(context);
	}

	// 获取城市信息
	private void getaddressinfo(Context context) {
		if (!isInEditMode()) {
			Db = BaseApplication.getInstance().getDataBase();
			Db.open();
			province_list = Db.getSysProvince(1);
			city_map = Db.getCitynew(2, province_list);
			Db.close();
		}

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (!isInEditMode()) {
			LayoutInflater.from(getContext()).inflate(R.layout.city_picker,
					this);
			citycodeUtil = CitycodeUtil.getSingleton();
			// 获取控件引用
			provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
			cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
			provincePicker.setData(citycodeUtil.getProvince(province_list));
			provincePicker.setDefault(0);
			cityPicker.setData(citycodeUtil.getCity(city_map, citycodeUtil
					.getProvince_list_code().get(0)));

			cityPicker.setDefault(0);
			cityone_id = province_list.get(0).getCity_id();
			citytwo_id = city_map.get(cityone_id).get(0).getCity_id();

			provincePicker.setOnSelectListener(new OnSelectListener() {

				@Override
				public void endSelect(int id, String text) {
					if (text.equals("") || text == null)
						return;
					if (tempProvinceIndex != id) {
						String selectDay = cityPicker.getSelectedText();
						if (selectDay == null || selectDay.equals(""))
							return;
						// 城市数组
						cityPicker.setData(citycodeUtil.getCity(city_map,
								citycodeUtil.getProvince_list_code().get(id)));
						cityPicker.setDefault(0);
						int lastDay = Integer.valueOf(provincePicker
								.getListSize());
						if (id > lastDay) {
							provincePicker.setDefault(lastDay - 1);
						}
					}
					tempProvinceIndex = id;
					cityone_id = province_list
							.get(provincePicker.getSelected()).getCity_id();
					citytwo_id = city_map.get(cityone_id)
							.get(cityPicker.getSelected()).getCity_id();
					System.out.println("cityone_id-->" + cityone_id
							+ "text----->" + text);
					onSelectString.selectid(cityone_id, citytwo_id);
					onSelectString.selectstring(
							provincePicker.getSelectedText(),
							cityPicker.getSelectedText());
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
				}

				@Override
				public void selecting(int id, String text) {
				}
			});
			cityPicker.setOnSelectListener(new OnSelectListener() {

				@Override
				public void endSelect(int id, String text) {
					if (text.equals("") || text == null)
						return;
					if (temCityIndex != id) {
						String selectDay = provincePicker.getSelectedText();
						if (selectDay == null || selectDay.equals(""))
							return;
						int lastDay = Integer.valueOf(cityPicker.getListSize());
						if (id > lastDay) {
							cityPicker.setDefault(lastDay - 1);
						}
					}
					temCityIndex = id;
					cityone_id = province_list
							.get(provincePicker.getSelected()).getCity_id();
					citytwo_id = city_map.get(cityone_id).get(id).getCity_id();
					onSelectString.selectstring(
							provincePicker.getSelectedText(),
							cityPicker.getSelectedText());
					onSelectString.selectid(cityone_id, citytwo_id);
					System.out.println("citytwo_id-->" + citytwo_id
							+ "text----->" + text);
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
				}

				@Override
				public void selecting(int id, String text) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_VIEW:
				if (onSelectingListener != null)
					onSelectingListener.selected(true);
				break;
			default:
				break;
			}
		}

	};

	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	public void selectstring(OnSelectString onSelectString) {
		this.onSelectString = onSelectString;
	}

	public String getCity_code_string() {
		return city_code_string;
	}

	public String getcityone_id() {
		return cityone_id;
	}

	public String getcitytwo_id() {
		return citytwo_id;
	}

	public String getcitythree_id() {
		return citythree_id;
	}

	public String getCity_string() {
		city_string = provincePicker.getSelectedText()
				+ cityPicker.getSelectedText();
		return city_string;
	}

	public interface OnSelectString {
		public void selectstring(String cityone_string, String citytwo_string);

		public void selectid(String cityone_id, String citytwo_id);

	}

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}

	/**
	 * 
	 */
	public void setdefautSelectText() {
		onSelectString.selectstring(province_list.get(0).getCity_nm(), city_map
				.get(cityone_id).get(0).getCity_nm());

	}
}
