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
 * @Description: ҡһҡ�Ļ��಼�ֲ��գ�sersorXmldemo.bak��
 * @author lixh
 * @date 2015��8��7�� ����4:38:07
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

				initStarAnim(); // ��ʼ ҡһҡ���ƶ���
				startVibrato(); // ��ʼ ��
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
	 * ��ʼִ��ҡһҡ����¼�
	 */
	protected abstract void startShakeAction();

	/**
	 * ��ʼ�����ĳ�ʼ
	 */
	protected abstract void initStarAnim();

	/**
	 * ���ֵĶ���
	 * 
	 * @param up
	 * @param down
	 */
	public void startAnim(View up, View down) { // ����ҡһҡ��������
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

	// ��ʼ��
	public void startVibrato() {
		MediaPlayer player;
		player = MediaPlayer.create(this, R.raw.awe);
		player.setLooping(false);
		player.start();

		// ������
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1); // ��һ�����������ǽ������飬
																	// �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�

	}

	public abstract void initData();
}