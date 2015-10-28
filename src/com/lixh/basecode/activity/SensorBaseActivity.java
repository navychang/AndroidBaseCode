package com.lixh.basecode.activity;

import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.lixh.basecode.R;
import com.lixh.basecode.widget.sensor.ShakeListener;
import com.lixh.basecode.widget.sensor.ShakeListener.OnShakeListener;

/**
 * 
 * @Title: SensorBaseActivity.java
 * @Package com.lixh.basecode.activity
 * @Description: 摇一摇的基类布局参照（sersorXmldemo.bak）
 * @author lixh
 * @date 2015年8月7日 下午4:38:07
 * @version V1.0
 */
public abstract class SensorBaseActivity extends BaseActivity {
	ShakeListener mShakeListener = null;
	Vibrator mVibrator;

	//
	@Override
	public void initView() {
		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);
		mShakeListener = new ShakeListener(instance);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {

				initStarAnim(); // 开始 摇一摇手掌动画
				startVibrato(); // 开始 震动
				startShakeAction();
			}

		});
		initData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	/**
	 * 开始执行摇一摇后的事件
	 */
	protected abstract void startShakeAction();

	/**
	 * 开始动画的初始
	 */
	protected abstract void initStarAnim();

	/**
	 * 布局的动画
	 * 
	 * @param up
	 * @param down
	 */
	public void startAnim(View up, View down) { // 定义摇一摇动画动画
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		up.startAnimation(animup);

		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
				-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		down.startAnimation(animdn);
	}

	// 开始振动
	public void startVibrato() {
		MediaPlayer player;
		player = MediaPlayer.create(this, R.raw.awe);
		player.setLooping(false);
		player.start();

		// 定义震动
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // 第一个｛｝里面是节奏数组，
																	// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复

	}

	public abstract void initData();
}