package com.lixh.basecode.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

import com.lixh.basecode.R;

/**
 * 
 * @author xiaanming
 * 
 */
public class SlideSwitch extends View implements OnTouchListener {

	/**
	 * @param context
	 * @param attrs
	 */
	public SlideSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(R.drawable.icon_close, R.drawable.icon_close,
				R.drawable.icon_switch);
	}

	/**
	 * @param context
	 */
	public SlideSwitch(Context context) {
		super(context);
		init(R.drawable.icon_on, R.drawable.icon_close, R.drawable.icon_switch);
	}

	private Bitmap bg_on, bg_off, slipper_btn;
	/**
	 * ����ʱ��x�͵�ǰ��x
	 */
	private float downX, nowX;

	/**
	 * ��¼�û��Ƿ��ڻ���
	 */
	private boolean onSlip = false;

	/**
	 * ��ǰ��״̬
	 */
	private boolean nowStatus = false;
	private int mBmpWidth = 0;
	private int mBmpHeight = 0;
	private int mThumbWidth = 0;
	/**
	 * �����ӿ�
	 */
	private OnChangedListener listener;

	public void init(int bg_on_drawable, int bg_off_drawable,
			int slipper_btn_drawable) {
		if (!isInEditMode()) {
			// ����ͼƬ��Դ
			bg_on = BitmapFactory
					.decodeResource(getResources(), bg_on_drawable);

			bg_off = BitmapFactory.decodeResource(getResources(),
					bg_off_drawable);
			slipper_btn = BitmapFactory.decodeResource(getResources(),
					slipper_btn_drawable);
			mBmpWidth = bg_on.getWidth();
			mBmpHeight = bg_off.getHeight();
			mThumbWidth = slipper_btn.getWidth();
		}
		setOnTouchListener(this);
	}

	@Override
	public void setLayoutParams(LayoutParams params) {
		params.width = mBmpWidth;
		params.height = mBmpHeight;
		super.setLayoutParams(params);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bg_on != null && bg_off != null && slipper_btn != null) {

			Matrix matrix = new Matrix();
			Paint paint = new Paint();
			float x = 0;

			// ����nowX���ñ����������߹�״̬
			if (nowX < (bg_on.getWidth() / 2)) {
				canvas.drawBitmap(bg_off, matrix, paint);// �����ر�ʱ�ı���
			} else {
				canvas.drawBitmap(bg_on, matrix, paint);// ������ʱ�ı���
			}

			if (onSlip) {// �Ƿ����ڻ���״̬,
				if (nowX >= bg_on.getWidth())// �Ƿ񻮳�ָ����Χ,�����û����ܵ���ͷ,����������ж�
					x = bg_on.getWidth() - slipper_btn.getWidth() / 2;// ��ȥ����1/2�ĳ���
				else
					x = nowX - slipper_btn.getWidth() / 2;
			} else {
				if (nowStatus) {// ���ݵ�ǰ��״̬���û����xֵ
					x = bg_on.getWidth() - slipper_btn.getWidth();
				} else {
					x = 0;
				}
			}

			// �Ի��黬�������쳣���������û������
			if (x < 0) {
				x = 0;
			} else if (x > bg_on.getWidth() - slipper_btn.getWidth()) {
				x = bg_on.getWidth() - slipper_btn.getWidth();
			}

			// ��������
			canvas.drawBitmap(slipper_btn, x, 0, paint);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (event.getX() > bg_off.getWidth()
					|| event.getY() > bg_off.getHeight()) {
				return false;
			} else {
				onSlip = true;
				downX = event.getX();
				nowX = downX;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			nowX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP: {
			onSlip = false;
			if (event.getX() >= (bg_on.getWidth() / 2)) {
				nowStatus = true;
				nowX = bg_on.getWidth() - slipper_btn.getWidth();
			} else {
				nowStatus = false;
				nowX = 0;
			}

			if (listener != null) {
				listener.OnChanged(SlideSwitch.this, nowStatus);
			}
			break;
		}
		}
		// ˢ�½���
		invalidate();
		return true;
	}

	/**
	 * ΪWiperSwitch����һ�����������ⲿ���õķ���
	 * 
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * ���û������صĳ�ʼ״̬�����ⲿ����
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		if (checked) {
			nowX = bg_off.getWidth();
		} else {
			nowX = 0;
		}
		nowStatus = checked;
	}

	/**
	 * �ص��ӿ�
	 * 
	 * @author len
	 * 
	 */
	public interface OnChangedListener {
		public void OnChanged(SlideSwitch wiperSwitch, boolean checkState);
	}

}
