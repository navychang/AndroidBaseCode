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
	 * ���ر�����
	 * 
	 * @param app
	 */
	public static void HideTitle(Activity app) {
		/* ����Ϊ�ޱ����� */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * ȫ����ʾ
	 */
	public static void FullScreen(Activity app) {
		/* ����Ϊ�ޱ����� */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* ����Ϊȫ��ģʽ */
		app.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * ������ʾ
	 */
	public static void HorizontalScreen(Activity app) {
		/* ����Ϊ���� */
		app.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * ����ȫ����ʾ
	 */
	public static void HorizontalFullScreen(Activity app) {
		/* ����Ϊ�ޱ����� */
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* ����Ϊȫ��ģʽ */
		app.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/* ����Ϊ���� */
		app.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * ȡ�ô��ڿ�Ⱥ͸߶�
	 * 
	 * @param app
	 * @return
	 */
	public static DisplayMetrics GetScreenPixels(Activity app) {
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		app.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * ȡ�ô��ڿ�Ⱥ͸߶�
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
	 * ��ñ������ĸ߶�
	 */
	public static int getTitleHeight(View app) {
		Rect outRect = new Rect();
		app.getWindowVisibleDisplayFrame(outRect);
		return 0;
	}

	/**
	 * ���Ӧ�ó������ݵĶ�������Ļ�����ľ���
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
