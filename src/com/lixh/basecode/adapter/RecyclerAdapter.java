package com.lixh.basecode.adapter;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @param <T>
 * @param <T>
 * @Author Zheng Haibo
 * @PersonalWebsite http://www.mobctrl.net
 * @Description
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

	private Context mContext;
	protected Gson gson = new Gson();
	protected List<T> list;

	public RecyclerAdapter(Context context, List<T> list) {
		mContext = context;
		this.list = list;
	}

	/**
	 * <默认构造函数> 因为涉及到theme，布局需要当前activity的content，所以强制传Context
	 * 
	 * @see [BaseAdapter(Context context, List<T> list)]
	 */
	public RecyclerAdapter(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public void onBindViewHolder(BaseViewHolder holder, int position) {
		BindViewHolder(holder,position, getList().get(position));
	}



	public abstract void BindViewHolder(BaseViewHolder holder, int position, T t);

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = onCreateView(parent);
		return getViewHolder(view);
	}

	public abstract BaseViewHolder getViewHolder(View view);

	public abstract View onCreateView(ViewGroup parent);

	protected View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	protected View inflate(int layoutResID, ViewGroup root) {
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, root, false);
		return view;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	/**
	 * 从position开始删除，删除
	 * 
	 * @param position
	 * @param itemCount
	 *            删除的数目
	 */
	protected void removeAll(int position, int itemCount) {
		for (int i = 0; i < itemCount; i++) {
			list.remove(position);
		}
		notifyItemRangeRemoved(position, itemCount);
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	public void add(T t, int position) {
		list.add(position, t);
		notifyItemInserted(position);
	}

	public void addAll(List<T> list, int position) {
		list.addAll(position, list);
		notifyItemRangeInserted(position, list.size());
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
		Alert.displayToastForLengthShort(mContext, msg);
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
		Alert.dismissProgressDialog(mContext);
	}

	/**
	 * 弹出 加载数据的进度progressBar
	 * 
	 * @param text
	 *            提示文字
	 */
	public void displayProgressDialog(String text) {
		Alert.displayProgressDialog(mContext, text, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				RequestClientUtil.cancelRequests(mContext);

			}
		});
	}

	/**
	 * @param string
	 * @param position
	 */
	public void onResultFailure(String msg, int position) {
		Alert.displayToastForLengthShort(mContext, msg);

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
}
