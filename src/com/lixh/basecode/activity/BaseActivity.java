package com.lixh.basecode.activity;

import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixh.basecode.R;
import com.lixh.basecode.app.BaseApplication;
import com.lixh.basecode.base.TitleBarView;
import com.lixh.basecode.base.ioc.ViewInjectUtils;
import com.lixh.basecode.base.logger.Log;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.http.BaseResult;
import com.lixh.basecode.http.FastJsonUtils;
import com.lixh.basecode.http.RequestClientUtil;
import com.lixh.basecode.http.UserSendV;
import com.lixh.basecode.util.ActivityStackControlUtil;
import com.lixh.basecode.util.Alert;
import com.lixh.basecode.util.Exit;
import com.lixh.basecode.util.Handler_System;
import com.lixh.basecode.util.ScreenUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @Title: BaseActivity.java
 * @Package com.lixh.ativity
 * @Description: TODO
 * @author lixh
 * @date 2015年7月13日 下午2:43:30
 * @version V1.0
 */
public abstract class BaseActivity extends FragmentActivity {
	public Exit exit = new Exit();// 双击退出 封装

	public TitleBarView topbar;
	public Activity instance;
	LinkedList<Activity> exitactivity = new LinkedList<Activity>();
	View v;
	RelativeLayout common;
	View sub;
	public Gson gson = new Gson();;
	public Bundle mBundle;
	public Bundle savedInstanceState;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		instance = this;
		ScreenUtil.getDeviceInfo(instance);
		onCreatView(arg0);
		ActivityStackControlUtil.getInstance().addActivity(instance);
	}

	private void onCreatView(Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		v = inflate(R.layout.activity_basic);
		setContentView(v);
		common = (RelativeLayout) v.findViewById(R.id.common);
		sub = inflate(getLayoutById(), common);
		common.addView(sub);
		topbar = new TitleBarView(instance);
		ViewInjectUtils.injectViews(instance);
		mBundle = getIntent().getExtras();
		Handler_System.init();
		setTitleBar();
		initView();
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param UserSendV
	 *            请求封装
	 * @param actionId
	 */
	public synchronized void RequestHealderPost(String url,
			Map<String, String> header, UserSendV userSendV, int position) {
		String gsonheader = gson.toJson(header);
		String gsonStr = gson.toJson(userSendV);
		RequestParams params = new RequestParams();
		params.put("paraminfo", gsonStr);
		RequestClientUtil.postHeader(url, "header", gsonheader, params,
				new AsyncHttpResponse(position));
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param header
	 *            多个请求头 key -value 请求封装
	 * @param actionId
	 */
	public synchronized void RequestHealderPost(String url,
			Map<String, String> header, int position) {
		RequestClientUtil.postHeader(url, header, null, new AsyncHttpResponse(
				position));
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param header
	 *            多个请求头 key -value 请求封装
	 * @param actionId
	 */
	public synchronized void RequestHealderPost(String url,
			Map<String, String> header, RequestParams params, int position) {

		RequestClientUtil.postHeader(url, header, params,
				new AsyncHttpResponse(position));
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param UserSendV
	 *            请求封装
	 * @param actionId
	 */
	public synchronized void RequestPost(String url, RequestParams params,
			int position) {
		RequestClientUtil.post(url, params, new AsyncHttpResponse(position));
	}

	/**
	 * 
	 * @param get_URL
	 *            地址
	 * @param UserSendV
	 *            请求封装
	 * @param actionId
	 */
	public synchronized void requestGetHeader(String url,
			Map<String, String> header, RequestParams params, int position) {
		if (params == null) {
			RequestClientUtil.requestGetHeader(url, header, params,
					new AsyncHttpResponse(position));
		} else {
			RequestClientUtil.requestGetHeader(url, header, params,
					new AsyncHttpResponse(position));
		}
		;

	}

	public void RequestPost(String url, UserSendV userSendV) {
		this.RequestPost(url, userSendV, 0);
	}

	/**
	 * 请求服务器数据
	 * 
	 * @param url
	 * @param userSendV
	 */
	public void RequestPost(String url, UserSendV userSendV, int position) {
		String gsonStr = gson.toJson(userSendV);
		RequestParams params = new RequestParams();
		params.add("paraminfo", gsonStr);
		RequestClientUtil.post(url, params, new AsyncHttpResponse(position));
	}

	/**
	 * 请求服务器数据
	 * 
	 * @param url
	 * 
	 */
	public void RequestPost(String url, int position) {

		RequestClientUtil.post(url, new AsyncHttpResponse(position));
	}

	class AsyncHttpResponse extends AsyncHttpResponseHandler {
		int position;

		/**
		 * @param position
		 */
		public AsyncHttpResponse(int position) {
			this.position = position;
		}

		@Override
		public void onSuccess(int arg0, org.apache.http.Header[] arg1,
				byte[] arg2) {
			onSuccessed(arg0, arg1, arg2, position);

		}

		@Override
		public void onFailure(int arg0, org.apache.http.Header[] arg1,
				byte[] arg2, Throwable arg3) {
			disMissProgressDialog();
			onResultFailure("网络不给力！", position);

		}

	}

	/**
	 * @param arg2
	 */
	public void onSuccessed(int arg0, org.apache.http.Header[] arg1,
			byte[] arg2, int position) {
		String jsonString = new String(arg2);
		if (BaseApplication.isDebug) {
			Log.e("result", jsonString);
		}
		try {
			BaseResult result = FastJsonUtils.getObject(jsonString,
					BaseResult.class);

			if (null == result || "".equals(result)) {
				onResultFailure("返回数据错误", position);
			} else if (BaseConfig.RESULT_OK.equals(result.getStatus())) {
				onResultSuccessed(getkey(result.getData()), result.getMsg(),
						position);
			} else {
				onResultFailure(result.getMsg(), position);
			}

		} catch (Exception e) {
			if (BaseApplication.isDebug) {
				Log.e("resultError",getkey( e.getMessage()));
			}
			onResultFailure("数据格式出错", position);
		}
		disMissProgressDialog();

	}

	/**
	 * @param getkey
	 * @param msg
	 * @param position
	 */
	private void onResultSuccessed(String object, String msg, int position)
			throws Exception {
		Alert.displayToastForLengthShort(instance, msg);
		onResultSuccess(object, position);
	}

	@Override
	protected void onPause() {
		disMissProgressDialog();
		super.onPause();
	}

	/**
	 * 取消加载数据的进度progressBar
	 */

	public void disMissProgressDialog() {
		Alert.dismissProgressDialog(instance);
	}

	/**
	 * 弹出 加载数据的进度progressBar
	 * 
	 * @param text
	 *            提示文字
	 */
	public void displayProgressDialog(String text) {
		Alert.displayProgressDialog(instance, text, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				RequestClientUtil.cancelRequests(instance);

			}
		});
	}

	/**
	 * @param string
	 * @param position
	 */
	public void onResultFailure(String msg, int position) {
		Alert.displayToastForLengthShort(instance, msg);

	}

	/**
	 * 返回整型
	 * 
	 * @param object
	 * @return
	 */
	public int getInt(Object object) {
		return TextUtils.isEmpty(object + "") ? 0 : Integer.parseInt(object
				+ "");
	}

	/**
	 * @param object
	 * @return
	 */
	public String getkey(Object object) {
		return object + "";
	}

	/**
	 * 请求数据返回成功
	 * 
	 * @param object
	 * @param position
	 */
	protected void onResultSuccess(String object, int position)
			throws Exception {

	}

	/**
	 * 将需要点击退出的啊activity 加入list中
	 * 
	 * @param activities
	 */
	public void addExitActivity(Activity... activities) {
		for (Activity activity : activities) {
			exitactivity.add(activity);

		}
	}

	/**
	 * @return 布局文件
	 */
	public abstract int getLayoutById();

	public abstract void setTitleBar();

	public abstract void initView();

	protected View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	protected View inflate(int layoutResID, ViewGroup root) {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, root, false);
		return view;
	}

	@Override
	public void onBackPressed() {
		if (exitactivity.contains(instance)) {
			if (exit.isExit()) {
				ActivityStackControlUtil.getInstance().NotifyExitApp();
			} else {
				exit.doExitInOneSecond();
				Toast.makeText(instance, "再按一次退出", 0).show();
			}
		} else {
			super.onBackPressed();
		}
	}
}
