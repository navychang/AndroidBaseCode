package com.lixh.basecode.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtil {
	public static int screenWidth;
	public static int screenHeight;
	public static float density;

	/**
	 * 隐藏标题栏
	 * 
	 * @param app
	 */
	public static void HideTitle(Activity app) {
		/* 设置为无标题栏 */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 全屏显示
	 */
	public static void FullScreen(Activity app) {
		/* 设置为无标题栏 */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 设置为全屏模式 */
		app.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 横屏显示
	 */
	public static void HorizontalScreen(Activity app) {
		/* 设置为横屏 */
		app.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * 横屏全屏显示
	 */
	public static void HorizontalFullScreen(Activity app) {
		/* 设置为无标题栏 */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 设置为全屏模式 */
		app.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/* 设置为横屏 */
		app.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * 取得窗口宽度和高度
	 * 
	 * @param app
	 * @return
	 */
	public static DisplayMetrics GetScreenPixels(Activity app) {
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		app.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 取得窗口宽度和高度
	 * 
	 * @param app
	 * @return
	 */
	public static void getDeviceInfo(Activity app) {
		DisplayMetrics dm = GetScreenPixels(app);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		density = dm.densityDpi;
	}

	/**
	 * 获得标题栏的高度
	 */
	public static int getTitleHeight(View app) {
		Rect outRect = new Rect();
		app.getWindowVisibleDisplayFrame(outRect);
		return 0;
	}

	/**
	 * 获得应用程序内容的顶部距屏幕顶部的距离
	 * 
	 * @return
	 */
	public static int getContentViewHeight(View mView, Activity app) {
		// Window window= app.getWindow();
		// int value = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// Log.i("", "value=="+value);
		ScreenUtil util = new ScreenUtil();
		MyRunnable mRunnable = util.new MyRunnable(1, app);
		mView.post(mRunnable);
		return mRunnable.getValue();
	}

	class MyRunnable implements Runnable {

		private int action = 0;
		private int value = 0;
		private Activity app = null;

		public MyRunnable(int action, Activity app) {
			super();
			this.action = action;
			this.app = app;
		}

		public int getValue() {
			return value;
		}

		@Override
		public void run() {
			Window window = app.getWindow();
			switch (action) {
			case 1:
				value = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
				break;
			case 2:
				break;
			}
		}
	};

}
