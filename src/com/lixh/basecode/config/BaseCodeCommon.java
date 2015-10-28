package com.lixh.basecode.config;

import java.util.Properties;

import android.app.Application;

import com.lixh.basecode.base.logger.Log;
import com.lixh.basecode.base.logger.LogWrapper;
import com.lixh.basecode.util.BaseSharedPreferences;
import com.lixh.basecode.util.Tools_Properties;

public class BaseCodeCommon {
	public final String TAG = "BaseCodeCommon";
	/**
	 * Application����
	 */
	private Application application;
	private static BaseCodeCommon baseCodeCommon;
	private String dbName = "Cihon";

	public Application getApplication() {
		return application;
	}

	public static BaseCodeCommon getInstance() {
		if (baseCodeCommon == null) {
			baseCodeCommon = new BaseCodeCommon();
		}
		return baseCodeCommon;
	}


	/**
	 * ���ݴ洢���������ݿ�
	 * 
	 * @param key
	 * @param value
	 * @return void
	 */
	public boolean setData(String key, Object value) {
		return BaseSharedPreferences.WriteSharedPreferences(dbName, key, value);
	}

	/**
	 * ȡ����������
	 * 
	 * @param key
	 * @return
	 * @return String
	 */
	public <T> T getData(String key, int type) {
		return BaseSharedPreferences.getValueByName(dbName, key, type);
	}

	public void clearData(String key) {
		BaseSharedPreferences.removeSharedPreferences(dbName, key);
	}

	/**
	 * ��ȡ����ͷ
	 * 
	 * @param key
	 * @return
	 */
	public String getProperties(String key) {
		Properties p = null;
		if (null == p) {
			p = Tools_Properties.loadConfigAssets("requestType.properties");
		}

		return p.getProperty(key);
	}

	public void init(Application app) {
		application = app;
		initializeLogging();
		// ��ȡ�����ļ�
	}

	/** Set up targets to receive log data */
	public void initializeLogging() {
		// Using Log, front-end to the logging chain, emulates android.util.log
		// method signatures.
		// Wraps Android's native log framework
		LogWrapper logWrapper = new LogWrapper();
		Log.setLogNode(logWrapper);

		Log.i(TAG, "Ready");
	}
}
