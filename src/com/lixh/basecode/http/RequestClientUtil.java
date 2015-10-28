package com.lixh.basecode.http;

import java.util.Map;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * android-async-http���
 * 
 * @author zjy
 * 
 */
public class RequestClientUtil {
	public static int timeout = 60 * 1000;
	/**
	 * ʵ��������
	 */
	private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

	/**
	 * GET ��һ������url��ȡһ��string����
	 * 
	 * @param url
	 * @param res
	 */
	public static void get(String url, AsyncHttpResponseHandler res) {
		asyncHttpClient.get(url, res);
	}

	/**
	 * POST ��һ������url��ȡһ��string����
	 * 
	 * @param url
	 * @param res
	 */
	public static void post(String url, AsyncHttpResponseHandler res) {
		asyncHttpClient.post(url, res);

	}

	/**
	 * GET url���������
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		asyncHttpClient.get(urlString, params, res);
	}

	/**
	 * POST url���������
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		asyncHttpClient.post(urlString, params, res);
	}

	/**
	 * POST ����������ȡjson�����������
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void post(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		asyncHttpClient.post(urlString, params, res);
	}

	/**
	 * @param url
	 * @param headers
	 * @param params
	 *            ����ͷ ��Ӧ json ��
	 * @param asyn
	 */
	public static void requestGetHeader(String url, String token,
			String headers, RequestParams params, AsyncHttpResponseHandler res) {
		if (null != headers) {
			asyncHttpClient.addHeader(token, headers);
		}
		//asyncHttpClient.setTimeout(timeout);
		asyncHttpClient.get(url, params, res);

	}

	/**
	 * @param url
	 * @param headers
	 * @param params
	 *            ����ͷ ��Ӧ json ��
	 * @param asyn
	 */
	public static void requestGetHeader(String url,
			Map<String, String> headers, RequestParams params,
			AsyncHttpResponseHandler res) {
		if (null != headers) {
			for (String key : headers.keySet()) {
				asyncHttpClient.addHeader(key, headers.get(key));
			}

		}
		//asyncHttpClient.setTimeout(timeout);
		asyncHttpClient.get(url, params, res);

	}

	/**
	 * @param url
	 * @param headers
	 * @param params
	 *            ����ͷ ��Ӧ json ��
	 * @param asyn
	 */
	public static void postHeader(String url, String token, String headers,
			RequestParams params, AsyncHttpResponseHandler res) {
		if (null != headers) {
			asyncHttpClient.addHeader(token, headers);
		}
		asyncHttpClient.setTimeout(timeout);
		asyncHttpClient.post(url, params, res);

	}

	/**
	 * @param url
	 * @param headers
	 * @param params
	 *            ����ͷ ��Ӧ json ��
	 * @param asyn
	 */
	public static void postHeader(String url, Map<String, String> headers,
			RequestParams params, AsyncHttpResponseHandler res) {
		if (null != headers) {
			for (String key : headers.keySet()) {
				asyncHttpClient.addHeader(key, headers.get(key));
			}

		}
		asyncHttpClient.setTimeout(timeout);
		if (params == null) {
			asyncHttpClient.post(url, res);
		} else {
			asyncHttpClient.post(url, params, res);
		}
	}

	public static void cancelRequests(Context mContext) {
		asyncHttpClient.cancelRequests(mContext, true);
	}

	public static void cancelAllRequests() {
		asyncHttpClient.cancelAllRequests(true);
	}
}
