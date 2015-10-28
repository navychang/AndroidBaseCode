package com.lixh.basecode.fragment;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.lixh.basecode.app.BaseApplication;
import com.lixh.basecode.base.TitleBarView;
import com.lixh.basecode.base.ioc.ViewInjectUtils;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.http.BaseResult;
import com.lixh.basecode.http.FastJsonUtils;
import com.lixh.basecode.http.RequestClientUtil;
import com.lixh.basecode.http.UserSendV;
import com.lixh.basecode.util.Alert;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-8-10
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class BaseFragment extends Fragment {

	protected Activity instance;
	protected Map<String, Object> strmap;
	protected TitleBarView topbar;
	// 注册进程事件 以便在别的地方调用
	protected InputMethodManager mInputManager;
	public boolean isRefresh = false; // 是否刷新
	public boolean isloading = false;// 是否加载
	public boolean isFirstLoad = true;;// 是否第一次加载
	Bundle mBundle;
	public Gson gson = new Gson();;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mInputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		topbar = BaseApplication.getInstance().getTopbar();
		this.instance = activity;

	}

	public void loginCallback(Intent mIntent) {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("");
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
			Map<String, String> header, RequestParams params, int position) {
		RequestClientUtil.postHeader(url, header, params,
				new AsyncHttpResponse(position));
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
	 * @param params
	 *            请求封装
	 * @param actionId
	 */
	public synchronized void RequestGet(String url, RequestParams params,
			int position) {
		if (params == null) {
			RequestClientUtil.get(url, new AsyncHttpResponse(position));
		} else {
			RequestClientUtil.get(url, params, new AsyncHttpResponse(position));
		}

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
	 * 返回String 型
	 * 
	 * @param object
	 * @return
	 */
	public String getkey(Object object) {
		return object + "";
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
	 * 请求数据返回成功
	 * 
	 * @param object
	 * @param position
	 */
	protected void onResultSuccess(String object, int position)
			throws Exception {

	}

	protected View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) instance
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	protected View inflate(int layoutResID, ViewGroup root) {
		LayoutInflater layoutInflater = (LayoutInflater) instance
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, root, false);
		return view;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(getViewById(), container, false);

		mBundle = getArguments();
		ViewInjectUtils.injectFragment(this, rootView);
		onBindViewHolder(rootView, savedInstanceState);
		return rootView;
	}

	// 初始化数据
	public abstract void onBindViewHolder(View view, Bundle savedInstanceState);

	public abstract int getViewById();

}
