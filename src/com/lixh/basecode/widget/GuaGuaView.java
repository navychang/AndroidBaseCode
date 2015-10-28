package com.lixh.basecode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import com.lixh.basecode.R;
import com.lixh.basecode.base.logger.Log;
import com.lixh.basecode.util.Alert;

public class GuaGuaView extends TextView {

	private int widget, height;

	private Context mContext;
	private Paint mPaint;
	private Canvas tempCanvas;
	private Bitmap mBitmap;
	private float x, y, ox, oy;
	private Path mPath;
	Handler mHandler;
	MyThread mThread;

	LotteryInfo info;
	int messageCount;

	int[] pixels;

	int color = 0xFFD6D6D6;

	public GuaGuaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(attrs);
	}

	/**
	 * ��һ�γ齱
	 */
	public void againLotter() {
		messageCount = 0;
		info = LotteryManage.getRandomLottery();
		tempCanvas.drawColor(color);
		setText(info.getText());
	}

	public LotteryInfo getLotterInfo() {
		return info;
	}

	private void init(AttributeSet attrs) {
		if (!isInEditMode()) {
			// ��ȡ�ؼ���Сֵ
			TypedArray a = mContext.obtainStyledAttributes(attrs,
					R.styleable.lotter);
			widget = (int) a.getDimension(R.styleable.lotter_widget, 400);
			height = (int) a.getDimension(R.styleable.lotter_height, 200);
			DisplayMetrics dm = getResources().getDisplayMetrics();
			widget = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, widget, dm);
			height = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, height, dm);
			a.recycle();

			// ��ʼ��·��
			mPath = new Path();

			// ��ʼ������
			mPaint = new Paint();
			mPaint.setColor(Color.BLACK);
			mPaint.setAlpha(0);
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			mPaint.setAntiAlias(true);
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeWidth(50);// ���ʿ��

			// ��ʼ��Bitmap������������ʱ������
			mBitmap = Bitmap.createBitmap(widget, height,
					Bitmap.Config.ARGB_4444);
			tempCanvas = new Canvas();
			tempCanvas.setBitmap(mBitmap);
			againLotter();

			// �����߳��д���Handler����������Ϣ
			mThread = new MyThread();
			mThread.start();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ���������bitmap����ȥ
		if (!isInEditMode()) {
			canvas.drawBitmap(mBitmap, 0, 0, null);

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			touchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			touchMove(event);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			touchUp();
			break;
		}
		return true;
	}

	private void touchUp() {
		LotteryInfo info = getLotterInfo();
		if (info.getScratchPercentage() == 0) {
			Alert.displayToastForLengthShort(mContext, "��ô�õĽ�������ô����");
		} else if (info.getScratchPercentage() < 0.3) {
			Alert.displayToastForLengthShort(mContext, "�ٹο�һ�����ôһ��㶼�������ص�");

		} else {
			result.end(info);
		}
	}

	GuaguaResult result;

	public void setGuaGuaResultListener(GuaguaResult l) {
		this.result = l;
	}

	public interface GuaguaResult {
		public void end(LotteryInfo info);
	}

	/**
	 * �ƶ���ʱ��
	 * 
	 * @param event
	 */
	private void touchMove(MotionEvent event) {
		x = event.getX();
		y = event.getY();
		// ���α�������ʵ��ƽ�����ߣ�oX, oYΪ������ x,yΪ�յ�
		mPath.quadTo((x + ox) / 2, (y + oy) / 2, x, y);
		tempCanvas.drawPath(mPath, mPaint);
		ox = x;
		oy = y;
		invalidate();
		computeScale();
	}

	/**
	 * ��һ�ΰ�����
	 * 
	 * @param event
	 */
	private void touchDown(MotionEvent event) {
		ox = x = event.getX();
		oy = y = event.getY();
		mPath.reset();
		mPath.moveTo(ox, oy);
	}

	/**
	 * ����ٷֱ�
	 */
	private void computeScale() {
		Message msg = mHandler.obtainMessage(0);
		msg.obj = ++messageCount;
		mHandler.sendMessage(msg);
	}

	/**
	 * �첽�̣߳������Ǵ���handler���մ�����Ϣ��
	 * 
	 * @author Administrator
	 * 
	 */
	class MyThread extends Thread {

		public MyThread() {
		}

		@Override
		public void run() {
			super.run();
			/*
			 * ���� handlerǰ�ȳ�ʼ��Looper.
			 */
			Looper.prepare();

			mHandler = new Handler() {
				@Override
				public void dispatchMessage(Message msg) {
					super.dispatchMessage(msg);
					// ֻ�������һ�εİٷֱ�
					if ((Integer) (msg.obj) != messageCount) {
						return;
					}
					// ȡ�����ص�
					synchronized (mBitmap) {
						if (pixels == null) {
							pixels = new int[mBitmap.getWidth()
									* mBitmap.getHeight()];
						}
						mBitmap.getPixels(pixels, 0, widget, 0, 0, widget,
								height);
					}

					int sum = pixels.length;
					int num = 0;
					for (int i = 0; i < sum; i++) {
						if (pixels[i] == 0) {
							num++;
						}
					}
					info.setScratchPercentage(num / (double) sum);
					Log.e("�ٷֱ�:", info.getScratchPercentage() * 100+"");
				}
			};
			/*
			 * �������̵߳���Ϣ����
			 */
			Looper.loop();
		}
	}
}
