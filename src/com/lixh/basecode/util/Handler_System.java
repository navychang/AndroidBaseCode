package com.lixh.basecode.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.lixh.basecode.app.BaseApplication;

/**
 * ��ȡ���ϵͳ��Ϣ
 * 
 * @author gdpancheng@gmail.com 2013-10-12 ����2:26:34
 */
public class Handler_System {

	public static String UA = Build.MODEL;
	private static String mIMEI;
	private static String mSIM;
	private static String mMobileVersion;
	private static String mNetwrokIso;
	private static String mNetType;
	private static String mDeviceID;
	private static List<NeighboringCellInfo> mCellinfos;

	public static final String systemWidth = "width";
	public static final String systemHeight = "height";
	private static HashMap<String, Integer> map;

	static {
		init();
	}

	/**
	 * ��ȡӦ�ó�������
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:30:56
	 * @return
	 * @return String
	 */
	public static String getAppName() {
		return getAppName(null);
	}

	/**
	 * ��ȡӦ�ó�������
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:30:43
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppName(String packageName) {
		String applicationName;

		if (packageName == null) {
			packageName = BaseApplication.getInstance().getPackageName();
		}

		try {
			PackageManager packageManager = BaseApplication.getInstance()
					.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			applicationName = BaseApplication.getInstance().getString(
					packageInfo.applicationInfo.labelRes);
		} catch (Exception e) {
			applicationName = "";
		}

		return applicationName;
	}

	/**
	 * ��ȡ�汾����
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:30:37
	 * @return
	 * @return String
	 */
	public static String getAppVersionNumber() {
		return getAppVersionNumber(null);
	}

	/**
	 * ��ȡ�汾����
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:30:27
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppVersionNumber(String packageName) {
		String versionName;

		if (packageName == null) {
			packageName = BaseApplication.getInstance().getPackageName();
		}

		try {
			PackageManager packageManager = BaseApplication.getInstance()
					.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			versionName = packageInfo.versionName;
		} catch (Exception e) {
			versionName = "";
		}

		return versionName;
	}

	/**
	 * ��ȡӦ�ó���İ汾��
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:30:12
	 * @return
	 * @return String
	 */
	public static String getAppVersionCode() {
		return getAppVersionCode(null);
	}

	/**
	 * ��ȡָ��Ӧ�ó���İ汾��
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:29:51
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppVersionCode(String packageName) {
		String versionCode;

		if (packageName == null) {
			packageName = BaseApplication.getInstance().getPackageName();
		}

		try {
			PackageManager packageManager = BaseApplication.getInstance()
					.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			versionCode = Integer.toString(packageInfo.versionCode);
		} catch (Exception e) {
			versionCode = "";
		}

		return versionCode;
	}

	/**
	 * ��ȡSDK�汾
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:29:13
	 * @return
	 * @return int
	 */
	public static int getSdkVersion() {
		try {
			return Build.VERSION.class.getField("SDK_INT").getInt(null);
		} catch (Exception e) {
			return 3;
		}
	}

	/*
	 * �ж��Ƿ��Ǹ�ǩ�����
	 */
	public static boolean isRelease(String signatureString) {
		final String releaseSignatureString = signatureString;
		if (releaseSignatureString == null
				|| releaseSignatureString.length() == 0) {
			throw new RuntimeException(
					"Release signature string is null or missing.");
		}

		final Signature releaseSignature = new Signature(releaseSignatureString);
		try {
			PackageManager pm = BaseApplication.getInstance()
					.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance()
					.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature sig : pi.signatures) {
				if (sig.equals(releaseSignature)) {
					return true;
				}
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��Ƿ���ģ����
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 ����2:28:40
	 * @return
	 * @return boolean
	 */
	public static boolean isEmulator() {
		return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
	}

	/**
	 * @author �˳� gdpancheng@gmail.com 2012-5-7 ����1:21:48
	 * @Title: getMobileInfo
	 * @Description: ��ȡ�ֻ���Ӳ����Ϣ
	 * @param @return �趨�ļ�
	 * @return String ��������
	 */
	public static String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		/**
		 * ͨ�������ȡϵͳ��Ӳ����Ϣ ��ȡ˽�е���Ϣ
		 */
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append("\n");
			}
		} catch (Exception e) {
		}
		return sb.toString();
	}

	/**
	 * @author �˳� gdpancheng@gmail.com 2012-5-7 ����5:18:34
	 * @Title: getDisplayMetrics
	 * @Description: ��ȡ��Ļ�ķֱ���
	 * @param @param cx
	 * @param @return �趨�ļ�
	 * @return HashMap<String,Integer> ��������
	 */
	public static HashMap<String, Integer> getDisplayMetrics() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) BaseApplication.getInstance()
					.getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		return map;
	}

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasGingerbreadMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return hasHoneycomb() && isTablet(context);
	}

	public static boolean isGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null || !activeNetwork.isConnected()) {
			return false;
		}
		return true;
	}

	public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

	/**
	 * get recommend default thread pool size
	 * 
	 * @return if 2 * availableProcessors + 1 less than 8, return it, else
	 *         return 8;
	 * @see {@link #getDefaultThreadPoolSize(int)} max is 8
	 */
	public static int getDefaultThreadPoolSize() {
		return getDefaultThreadPoolSize(8);
	}

	/**
	 * get recommend default thread pool size
	 * 
	 * @param max
	 * @return if 2 * availableProcessors + 1 less than max, return it, else
	 *         return max;
	 */
	public static int getDefaultThreadPoolSize(int max) {
		int availableProcessors = 2 * Runtime.getRuntime()
				.availableProcessors() + 1;
		return availableProcessors > max ? max : availableProcessors;
	}

	/**
	 * 
	 * �����ֻ�������
	 * */
	public static void Vibrate(long milliseconds) {
		Vibrator vib = (Vibrator) BaseApplication.getInstance()
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	static TelephonyManager mTm = null;

	/**
	 * �ڻ�ȡϵͳ��Ϣǰ��ʼ��
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:14:12
	 * @return void
	 */
	public static void init() {
		mTm = (TelephonyManager) BaseApplication.getInstance()
				.getSystemService(Context.TELEPHONY_SERVICE);
		mIMEI = mTm.getDeviceId();
		mMobileVersion = mTm.getDeviceSoftwareVersion();
		mCellinfos = mTm.getNeighboringCellInfo();
		mNetwrokIso = mTm.getNetworkCountryIso();
		mSIM = mTm.getSimSerialNumber();
		mDeviceID = getDeviceId();
		try {
			ConnectivityManager cm = (ConnectivityManager) BaseApplication
					.getInstance().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				mNetType = info.getTypeName();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * ���android�豸-Ψһ��ʶ��android2.2 ֮ǰ�޷��ȶ�����.
	 * */
	public static String getDeviceId(Context mCm) {
		return Secure.getString(mCm.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * ��ȡ�豸�� TODO(������һ�仰�����������������)
	 * 
	 * @author gdpancheng@gmail.com 2013-10-15 ����10:31:48
	 * @return
	 * @return String
	 */
	private static String getDeviceId() {
		return Secure.getString(BaseApplication.getInstance()
				.getContentResolver(), Secure.ANDROID_ID);
	}

	public static String getImei() {
		return mIMEI;
	}

	public static String getSIM() {
		return mSIM;
	}

	public static String getUA() {
		return UA;
	}

	/**
	 * ��ȡ�豸��Ϣ ���ַ�������ʽ
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:14:30
	 * @return String
	 */
	public static String getDeviceInfo() {
		StringBuffer info = new StringBuffer();
		info.append("IMEI:").append(getImei());
		info.append("\n");
		info.append("SIM:").append(getSIM());
		info.append("\n");
		info.append("UA:").append(getUA());
		info.append("\n");
		info.append("MobileVersion:").append(mMobileVersion);

		info.append("\n");
		info.append("SDK: ").append(android.os.Build.VERSION.SDK);
		info.append("\n");
		info.append(getCallState());
		info.append("\n");
		info.append("SIM_STATE: ").append(getSimState());
		info.append("\n");
		info.append("SIM: ").append(getSIM());
		info.append("\n");
		info.append(getSimOpertorName());
		info.append("\n");
		info.append(getPhoneType());
		info.append("\n");
		info.append(getPhoneSettings());
		info.append("\n");
		return info.toString();
	}

	/**
	 * ���sim��״̬
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:15:25
	 * @return String
	 */
	public static String getSimState() {
		switch (mTm.getSimState()) {
		case android.telephony.TelephonyManager.SIM_STATE_UNKNOWN:
			return "δ֪SIM״̬_"
					+ android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;
		case android.telephony.TelephonyManager.SIM_STATE_ABSENT:
			return "û��SIM��_"
					+ android.telephony.TelephonyManager.SIM_STATE_ABSENT;
		case android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED:
			return "����SIM״̬_��Ҫ�û���PIN�����_"
					+ android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED;
		case android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED:
			return "����SIM״̬_��Ҫ�û���PUK�����_"
					+ android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED;
		case android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			return "����SIM״̬_��Ҫ�����PIN�����_"
					+ android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED;
		case android.telephony.TelephonyManager.SIM_STATE_READY:
			return "����SIM״̬_"
					+ android.telephony.TelephonyManager.SIM_STATE_READY;
		default:
			return "δ֪SIM״̬_"
					+ android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;

		}
	}

	/**
	 * ��ȡ�ֻ��ź�״̬
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:15:37
	 * @return String
	 */
	public static String getPhoneType() {
		switch (mTm.getPhoneType()) {
		case android.telephony.TelephonyManager.PHONE_TYPE_NONE:
			return "PhoneType: ���ź�_"
					+ android.telephony.TelephonyManager.PHONE_TYPE_NONE;
		case android.telephony.TelephonyManager.PHONE_TYPE_GSM:
			return "PhoneType: GSM�ź�_"
					+ android.telephony.TelephonyManager.PHONE_TYPE_GSM;
		case android.telephony.TelephonyManager.PHONE_TYPE_CDMA:
			return "PhoneType: CDMA�ź�_"
					+ android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
		default:
			return "PhoneType: ���ź�_"
					+ android.telephony.TelephonyManager.PHONE_TYPE_NONE;
		}
	}

	/**
	 * ���������ƣ����磺�й��ƶ�����ͨ ���� SIM����״̬������ SIM_STATE_READY(ʹ��getSimState()�ж�). ����
	 */
	public static String getSimOpertorName() {
		if (mTm.getSimState() == android.telephony.TelephonyManager.SIM_STATE_READY) {
			StringBuffer sb = new StringBuffer();
			sb.append("SimOperatorName: ").append(mTm.getSimOperatorName());
			sb.append("\n");
			sb.append("SimOperator: ").append(mTm.getSimOperator());
			sb.append("\n");
			sb.append("Phone:").append(mTm.getLine1Number());
			return sb.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("SimOperatorName: ").append("δ֪");
			sb.append("\n");
			sb.append("SimOperator: ").append("δ֪");
			return sb.toString();
		}
	}

	/**
	 * ��ȡ�ֻ�����״̬ ���������������
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:16:02
	 * @return String
	 */
	public static String getPhoneSettings() {
		StringBuffer buf = new StringBuffer();
		String str = Secure.getString(BaseApplication.getInstance()
				.getContentResolver(), Secure.BLUETOOTH_ON);
		buf.append("����:");
		if (str.equals("0")) {
			buf.append("����");
		} else {
			buf.append("����");
		}
		//
		str = Secure.getString(BaseApplication.getInstance()
				.getContentResolver(), Secure.BLUETOOTH_ON);
		buf.append("WIFI:");
		buf.append(str);

		str = Secure.getString(BaseApplication.getInstance()
				.getContentResolver(), Secure.INSTALL_NON_MARKET_APPS);
		buf.append("APPλ����Դ:");
		buf.append(str);

		return buf.toString();
	}

	/**
	 * ��ȡ�绰״̬
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 ����1:16:37
	 * @return String
	 */
	public static String getCallState() {
		switch (mTm.getCallState()) {
		case android.telephony.TelephonyManager.CALL_STATE_IDLE:
			return "�绰״̬[CallState]: �޻";
		case android.telephony.TelephonyManager.CALL_STATE_OFFHOOK:
			return "�绰״̬[CallState]: �޻";
		case android.telephony.TelephonyManager.CALL_STATE_RINGING:
			return "�绰״̬[CallState]: �޻";
		default:
			return "�绰״̬[CallState]: δ֪";
		}
	}

	public static String getNetwrokIso() {
		return mNetwrokIso;
	}

	/**
	 * @return the mDeviceID
	 */
	public String getmDeviceID() {
		return mDeviceID;
	}

	/**
	 * @return the mNetType
	 */
	public String getNetType() {
		return mNetType;
	}

}
