package com.lixh.basecode.adapter;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.lixh.basecode.app.BaseApplication;
import com.lixh.basecode.base.logger.Log;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.http.BaseResult;
import com.lixh.basecode.http.FastJsonUtils;
import com.lixh.basecode.http.RequestClientUtil;
import com.lixh.basecode.http.UserSendV;
import com.lixh.basecode.util.Alert;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义的baseAdapter
 * 
 * @author Administrator
 * @param <T>
 * 
 * @param <T>
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
	protected List<T> list;

	protected Context context;

	protected Gson gson = new Gson();

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * <默认构造函数> 因为涉及到theme，布局需要当前activity的content，所以强制传Context
	 * 
	 * @see [BaseAdapter(Context context, List<T> list)]
	 */
	public BaseAdapter(List<T> list) {
		this.list = list;
	}

	public BaseAdapter(Context context, List<T> list) {
		this.list = list;
		this.context = context;
	}

	public void clear() {
		this.list.clear();
		notifyDataSetChanged();
	}

	public void addAll(List<T> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	protected View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	protected View inflate(int layoutResID, ViewGroup root) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, root, false);
		return view;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = onCreateView(parent);
		}
		ViewHolder viewHolder = ViewHolder.get(convertView);
		onBindViewHolder(viewHolder, position, getItem(position));
		return convertView;
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param header
	 *            多个请求头 key -value 请求封装
	 * @param actionId
	 */
	public synchronized void RequestHealderPost(String url, Map<String, String> header, RequestParams params,
			int position) {
		RequestClientUtil.postHeader(url, header, params, new AsyncHttpResponse(position));
	}

	/**
	 * 
	 * @param POST_URL
	 *            地址
	 * @param UserSendV
	 *            请求封装
	 * @param actionId
	 */
	public synchronized void RequestPost(String url, RequestParams params, int position) {
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
	public synchronized void requestGetHeader(String url, Map<String, String> header, RequestParams params,
			int position) {
		if (params == null) {
			RequestClientUtil.requestGetHeader(url, header, params, new AsyncHttpResponse(position));
		} else {
			RequestClientUtil.requestGetHeader(url, header, params, new AsyncHttpResponse(position));
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
		public void onSuccess(int arg0, org.apache.http.Header[] arg1, byte[] arg2) {
			onSuccessed(arg0, arg1, arg2, position);

		}

		@Override
		public void onFailure(int arg0, org.apache.http.Header[] arg1, byte[] arg2, Throwable arg3) {
			disMissProgressDialog();
			onResultFailure("网络不给力！", position);

		}

	}

	/**
	 * @param arg2
	 */
	public void onSuccessed(int arg0, org.apache.http.Header[] arg1, byte[] arg2, int position) {
		String jsonString = new String(arg2);
		if (BaseApplication.isDebug) {
			Log.e("result", jsonString);
		}
		try {
			BaseResult result = FastJsonUtils.getObject(jsonString, BaseResult.class);

			if (null == result || "".equals(result)) {
				onResultFailure("返回数据错误", position);
			} else if (BaseConfig.RESULT_OK.equals(result.getStatus())) {
				onResultSuccessed(getkey(result.getData()), result.getMsg(), position);
			} else {
				onResultFailure(result.getMsg(), position);
			}

		} catch (Exception e) {
			if (BaseApplication.isDebug) {
				Log.e("resultError", getkey(e.getMessage()));
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
	private void onResultSuccessed(String object, String msg, int position) throws Exception {
		Alert.displayToastForLengthShort(context, msg);
		onResultSuccess(object, position);
	}

	/**
	 * 请求数据返回成功
	 * 
	 * @param object
	 * @param position
	 */
	protected void onResultSuccess(String object, int position) throws Exception {

	}

	/**
	 * 取消加载数据的进度progressBar
	 */

	public void disMissProgressDialog() {
		Alert.dismissProgressDialog(context);
	}

	/**
	 * 弹出 加载数据的进度progressBar
	 * 
	 * @param text
	 *            提示文字
	 */
	public void displayProgressDialog(String text) {
		Alert.displayProgressDialog(context, text, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				RequestClientUtil.cancelRequests(context);

			}
		});
	}

	/**
	 * @param string
	 * @param position
	 */
	public void onResultFailure(String msg, int position) {
		Alert.displayToastForLengthShort(context, msg);

	}

	/**
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
		return TextUtils.isEmpty(object + "") ? 0 : Integer.parseInt(object + "");
	}

	public abstract void onBindViewHolder(ViewHolder viewHolder, int position, T t);

	public abstract View onCreateView(ViewGroup parent);

	public static class ViewHolder {
		private final SparseArray<View> views;
		private View convertView;

		private ViewHolder(View convertView) {
			this.views = new SparseArray<View>();
			this.convertView = convertView;
			convertView.setTag(this);
		}

		public static ViewHolder get(View convertView) {
			if (convertView.getTag() == null) {
				return new ViewHolder(convertView);
			}
			ViewHolder existedHolder = (ViewHolder) convertView.getTag();
			return existedHolder;
		}

		public <T extends View> T getView(int viewId) {
			View view = views.get(viewId);
			if (view == null) {
				view = convertView.findViewById(viewId);
				views.put(viewId, view);
			}
			return (T) view;
		}
	}

}
