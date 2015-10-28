package com.lixh.basecode.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.lixh.basecode.config.BaseCodeCommon;
import com.lixh.basecode.config.BaseConfig;
import com.lixh.basecode.util.BaseSharedPreferences;

/**
 * 
 * @Title: WelcomeActivity.java
 * @Package com.cihon.activities
 * @Description: ��ӭ����
 * @author lixh
 * @date 2015��5��12�� ����10:25:24
 * @version V1.0
 */
public abstract class LaunchActivity extends BaseActivity {
	public boolean isFirstIn = false;
	/**
	 * Handler:��ת����ͬ����
	 */
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BaseConfig.Welcome.GO_HOME:
				goHome();
				break;
			case BaseConfig.Welcome.GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void initView() {
		// ��ȡSharedPreferences����Ҫ������
		// ʹ��SharedPreferences����¼�����ʹ�ô���
		isFirstIn = BaseCodeCommon.getInstance().getData(
				BaseConfig.Welcome.FIRST_LOGIN_SHARE,
				BaseSharedPreferences.BOOLEAN);
		// �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������
		if (isFirstIn) {
			BaseCodeCommon.getInstance().setData(
					BaseConfig.Welcome.FIRST_LOGIN_SHARE, false);
			mHandler.sendEmptyMessageDelayed(BaseConfig.Welcome.GO_GUIDE,
					BaseConfig.Welcome.SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(BaseConfig.Welcome.GO_HOME,
					BaseConfig.Welcome.SPLASH_DELAY_MILLIS);
		}

	}

	public abstract void goHome();

	// ��ת������ҳ
	public abstract void goGuide();

}
