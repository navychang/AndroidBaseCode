package com.lixh.basecode.widget.ResideMenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * ͨ��ViewDragHelperʵ�ֵĲ໬�ؼ�
 * 
 * @author liujing
 * 
 */
public class DragLayout extends FrameLayout {

	private View mLeftContent;
	private View mMainContent;
	private int mWidth;
	private int mDragRange;
	private ViewDragHelper mDragHelper;
	private int mMainLeft;
	private int mHeight;

	private Status mStatus = Status.Close;
	private GestureDetectorCompat mDetectorCompat;

	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ViewDragHelper.create(forParent, sensitivity, cb);
		// ��Ӧ�����������֡����жȡ��ص�
		mDragHelper = ViewDragHelper.create(this, mCallBack);
		mDetectorCompat = new GestureDetectorCompat(getContext(),
				mGestureListener);

	}

	private boolean isDrag = true;

	public void setDrag(boolean isDrag) {
		this.isDrag = isDrag;
		if (isDrag) {
			// �����и�Bug,��isDrag��false��Ϊtrue�ǣ�mDragHelper��mCallBack��
			// �״λ���ʱ����Ӧ���ٴλ�������Ӧ�������ڴ˵����£���mDragHelper�ָ���״̬
			mDragHelper.abort();
		}
	}

	SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if ((Math.abs(distanceX) > Math.abs(distanceY))
					&& distanceX < 0 && isDrag != false
					&& mStatus == Status.Close) {
				return true;
			} else if ((Math.abs(distanceX) > Math.abs(distanceY))
					&& distanceX > 0 && isDrag != false
					&& mStatus == Status.Open) {
				return true;
			} else {
				return false;
			}
		};
	};

	ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
		public void onEdgeTouched(int edgeFlags, int pointerId) {

		};

		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			mDragHelper.captureChildView(mMainContent, pointerId);
		};

		// ����child�Ƿ�ɱ���ק������true�������ק��
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child == mMainContent || child == mLeftContent;
		}

		// ��capturedChild����קʱ
		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}

		// ������ק�ķ�Χ������0ʱ����ק������0�޷���ק
		// �˷���ֻ���ڼ�����view�ͷ��ٶȣ����жȵ�
		// ʵ����ק��Χ��clampViewPositionHorizontal��������
		@Override
		public int getViewHorizontalDragRange(View child) {
			return mDragRange;
		}

		// �˴�����view����ק��Χ����ʵ���ƶ���δ������
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// �϶�ǰoldLeft + �仯��dx == left
			if (mMainLeft + dx < 0) {
				return 0;
			} else if (mMainLeft + dx > mDragRange) {
				return mDragRange;
			}
			return left;
		}

		// �����˵�Viewλ�øı�ʱ��ϣ���������������顣����ʱ�ƶ��Ѿ�������
		// ��Ƶʵʱ�ĵ��ã�����������������������
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// ����϶����������
			if (changedView == mMainContent) {
				mMainLeft = left;
			} else {
				mMainLeft += dx;
			}

			// ����ֵ������
			if (mMainLeft < 0) {
				mMainLeft = 0;
			} else if (mMainLeft > mDragRange) {
				mMainLeft = mDragRange;
			}
			// �����ק��������壬ǿ����ָ��λ�û���Content
			if (changedView == mLeftContent) {
				layoutContent();
			}

			dispatchDragEvent(mMainLeft);

		}

		// View���ͷ�ʱ,�໬�򿪻�ָ�
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if (xvel > 0) {
				open();
			} else if (xvel == 0 && mMainLeft > mDragRange * 0.5f) {
				open();
			} else {
				close();
			}

		}

		// ����ק״̬�ı��ʱ��IDLE/DRAGGING/SETTLING
		@Override
		public void onViewDragStateChanged(int state) {
			super.onViewDragStateChanged(state);
		}

	};

	private void layoutContent() {
		mMainContent.layout(mMainLeft, 0, mMainLeft + mWidth, mHeight);
		mLeftContent.layout(0, 0, mWidth, mHeight);
	}

	/**
	 * ÿ�θ��¶������ ���ݵ�ǰִ�е�λ�ü���ٷֱ�percent
	 */
	protected void dispatchDragEvent(int mainLeft) {
		float percent = mainLeft / (float) mDragRange;
		animViews(percent);

		if (mListener != null) {
			mListener.onDraging(percent);
		}

		Status lastStatus = mStatus;
		if (updateStatus(mainLeft) != lastStatus) {
			if (mListener == null) {
				return;
			}
			if (lastStatus == Status.Draging) {
				if (mStatus == Status.Close) {
					mListener.onClose();
				} else if (mStatus == Status.Open) {
					mListener.onOpen();
				}

			}
		}
	}

	public static interface OnLayoutDragingListener {
		void onOpen();

		void onClose();

		void onDraging(float percent);
	}

	private OnLayoutDragingListener mListener;

	public void setOnLayoutDragingListener(OnLayoutDragingListener l) {
		mListener = l;
	}

	private Status updateStatus(int mainLeft) {
		if (mainLeft == 0) {
			mStatus = Status.Close;
		} else if (mainLeft == mDragRange) {
			mStatus = Status.Open;
		} else {
			mStatus = Status.Draging;
		}
		return mStatus;
	}

	public static enum Status {
		Open, Close, Draging
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status mStatus) {
		this.mStatus = mStatus;
	}

	/**
	 * ���涯����
	 * 
	 * @param percent
	 */
	private void animViews(float percent) {
		// ����壺����
		float inverse = 1 - percent * 0.2f;
		ViewHelper.setScaleX(mMainContent, inverse);
		ViewHelper.setScaleY(mMainContent, inverse);

		// ����壺���š�ƽ�ơ�͸����
		ViewHelper.setScaleX(mLeftContent, 0.5f + 0.5f * percent);
		ViewHelper.setScaleY(mLeftContent, 0.5f + 0.5f * percent);

		ViewHelper.setTranslationX(mLeftContent, -mWidth / 2.0f + mWidth / 2.0f
				* percent);
		ViewHelper.setAlpha(mLeftContent, percent);
		// ��������ɫ����
		getBackground().setColorFilter(
				evaluate(percent, Color.BLACK, Color.TRANSPARENT),
				PorterDuff.Mode.SRC_OVER);
	}

	private int evaluate(float fraction, int startValue, int endValue) {
		int startInt = (Integer) startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;

		int endInt = (Integer) endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;

		return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
				| (int) ((startR + (int) (fraction * (endR - startR))) << 16)
				| (int) ((startG + (int) (fraction * (endG - startG))) << 8)
				| (int) ((startB + (int) (fraction * (endB - startB))));
	}

	@Override
	public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
		boolean onTouchEvent = mDetectorCompat.onTouchEvent(ev);
		// ��Touch�¼����ݸ�ViewDragHelper
		return mDragHelper.shouldInterceptTouchEvent(ev) & onTouchEvent;
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		try {
			// ��Touch�¼����ݸ�ViewDragHelper
			mDragHelper.processTouchEvent(event);
		} catch (Exception e) {
		}
		return true;
	}

	public void close() {
		close(true);
	};

	public void open() {
		open(true);
	}

	public void close(boolean isSmooth) {
		mMainLeft = 0;
		if (isSmooth) {
			// ִ�ж���������true������δ��ɵĶ���, ��Ҫ����ִ��
			if (mDragHelper.smoothSlideViewTo(mMainContent, mMainLeft, 0)) {
				// ע�⣺�������ݸ�ViewGroup
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			layoutContent();
		}
	}

	public void open(boolean isSmooth) {
		mMainLeft = mDragRange;
		if (isSmooth) {
			if (mDragHelper.smoothSlideViewTo(mMainContent, mMainLeft, 0)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			layoutContent();
		}
	}

	@Override
	public void computeScroll() {
		// ��Ƶ�ʵ��ã������Ƿ�����һ���䶯�ȴ�ִ��
		if (mDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		mMainContent.layout(mMainLeft, 0, mMainLeft + mWidth, mHeight);
		mLeftContent.layout(0, 0, mWidth, mHeight);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// �õ����
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		// �����϶���Χ
		mDragRange = (int) (mWidth * 0.6f);
	}

	/**
	 * ������ʱ��������Ӳ��ֵ�����
	 */
	@Override
	protected void onFinishInflate() {

		int childCount = getChildCount();
		// ��Ҫ�ļ���
		if (childCount < 2) {
			throw new IllegalStateException(
					"You need two childrens in your content");
		}

		if (!(getChildAt(0) instanceof ViewGroup)
				|| !(getChildAt(1) instanceof ViewGroup)) {
			throw new IllegalArgumentException(
					"Your childrens must be an instance of ViewGroup");
		}

		mLeftContent = getChildAt(0);
		mMainContent = getChildAt(1);
	}

}
