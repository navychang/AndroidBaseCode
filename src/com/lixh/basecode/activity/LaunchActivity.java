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
 * @Description: 欢迎界面
 * @author lixh
 * @date 2015年5月12日 上午10:25:24
 * @version V1.0
 */
public abstract class LaunchActivity extends BaseActivity {
	public boolean isFirstIn = false;
	/**
	 * Handler:跳转到不同界面
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
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		isFirstIn = BaseCodeCommon.getInstance().getData(
				BaseConfig.Welcome.FIRST_LOGIN_SHARE,
				BaseSharedPreferences.BOOLEAN);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
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

	// 跳转到引导页
	public abstract void goGuide();

}
