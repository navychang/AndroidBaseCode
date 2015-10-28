package com.lixh.basecode.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

import com.lixh.basecode.app.BaseApplication;
import com.lixh.basecode.base.logger.Log;
import com.lixh.basecode.config.BaseCodeCommon;

/**
 * �����ļ�������
 * 
 * @author gdpancheng@gmail.com 2013-10-22 ����1:08:52
 */
public class Tools_Properties {
	public final static String TAG = "Tools_Properties";

	/**
	 * �����ļ������ļ�·�� ��ȡProperties�ļ�
	 * 
	 * @author �˳� gdpancheng@gmail.com 2012-6-27 ����12:46:05
	 * @param fileName
	 * @param dirName
	 * @return �趨�ļ�
	 */
	public static Properties loadProperties(String fileName, String dirName) {
		Properties props = new Properties();
		try {
			int id = BaseApplication
					.getInstance()
					.getResources()
					.getIdentifier(fileName, dirName,
							BaseApplication.getInstance().getPackageName());
			props.load(BaseApplication.getInstance().getResources()
					.openRawResource(id));
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return props;
	}

	/**
	 * ��ȡProperties�ļ�(ָ��Ŀ¼)
	 * 
	 * @author �˳� gdpancheng@gmail.com 2012-6-27 ����12:46:51
	 * @param file
	 * @return �趨�ļ�
	 */
	public static Properties loadConfig(String file) {
		Properties properties = new Properties();
		try {
			FileInputStream s = new FileInputStream(file);
			properties.load(s);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return properties;
	}

	/**
	 * ����Properties(ָ��Ŀ¼)
	 * 
	 * @author �˳� gdpancheng@gmail.com 2012-6-27 ����12:48:57
	 * @param file
	 * @param properties
	 *            �趨�ļ�
	 */
	public static void saveConfig(String file, Properties properties) {
		try {
			FileOutputStream s = new FileOutputStream(file, false);
			properties.store(s, "");
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * ��ȡ�ļ� �ļ���/data/data/package_name/files�� �޷�ָ��λ��
	 * 
	 * @author �˳� gdpancheng@gmail.com 2012-6-27 ����12:49:08
	 * @param fileName
	 * @return �趨�ļ�
	 */
	public static Properties loadConfigNoDirs(String fileName) {
		Properties properties = new Properties();
		try {
			FileInputStream s = BaseApplication.getInstance().openFileInput(
					fileName);
			properties.load(s);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return properties;
	}

	/**
	 * �����ļ���/data/data/package_name/files�� �޷�ָ��λ��
	 * 
	 * @author �˳� gdpancheng@gmail.com 2012-6-27 ����12:49:55
	 * @param fileName
	 * @param properties
	 *            �趨�ļ�
	 */
	public static void saveConfigNoDirs(String fileName, Properties properties) {
		try {
			FileOutputStream s = BaseApplication.getInstance().openFileOutput(
					fileName, Context.MODE_PRIVATE);
			properties.store(s, "");
		} catch (Exception e) {
			Log.e(TAG, e.toString());

		}
	}

	public static Properties loadConfigAssets(String fileName) {

		Properties properties = new Properties();
		try {
			InputStream is = BaseApplication.getInstance().getAssets()
					.open(fileName);
			properties.load(is);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return properties;
	}
}
