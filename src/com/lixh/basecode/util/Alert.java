package com.lixh.basecode.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lixh.basecode.R;
import com.lixh.basecode.adapter.BaseAdapter;
import com.lixh.basecode.base.dataPick.CustomDatePickerDialog;
import com.lixh.basecode.base.dataPick.CustomDatePickerDialog.onDateListener;
import com.lixh.basecode.util.Alert.ListDialog.MultipleChoiceModeitemclicklistener;
import com.lixh.basecode.widget.CityPickerDialog;
import com.lixh.basecode.widget.CityPickerDialog.SelectInfo;

/**
 * @author Administrator 系统提示类
 */
public class Alert {

	/**
	 * 显示Toast,LengthShort
	 * 
	 * @param context
	 * @param text
	 */
	public static void displayToastForLengthShort(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示Toast,LengthLong
	 * 
	 * @param context
	 * @param text
	 */
	public static void displayToastForLengthLong(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示Toast提示
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 *            时间
	 */
	public static void displayToast(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * 显示正在加载对话框
	 * 
	 * @param context
	 * @param message
	 */
	public static void displayProgressDialog(Context context, String message,
			OnCancelListener cancel) {
		ProgressDialog mDialog = ProgressDialog.getInstance(context);
		mDialog.setMessage(message);
		if (null != cancel) {
			mDialog.setOnCancelListener(cancel);
		}
		mDialog.show();
		mDialog.setCancelable(true);

	}

	/**
	 * 隐藏正在加载对话框
	 * 
	 * @param context
	 * @param message
	 */
	public static void dismissProgressDialog(Context context) {
		ProgressDialog mDialog = ProgressDialog.getInstance(context);
		mDialog.dismiss();
	}

	/**
	 * 显示布局
	 * 
	 * @param context
	 * @param message
	 */
	public static void displayAlertLayoutDialog(Context context, int layout) {
		AlertLayoutDialog mDialog = AlertLayoutDialog.getInstance(context);

		mDialog.setlayout(layout);
		mDialog.show();

	}

	public static class AlertLayoutDialog extends Dialog {
		static AlertLayoutDialog mDialog;
		LayoutInflater layoutInflater;

		/**
		 * @param context
		 */
		public AlertLayoutDialog(Context context) {
			this(context, R.style.dialog);
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			initDialogPosition(context);
		}

		// 单例模式
		public static AlertLayoutDialog getInstance(Context context) {
			if (mDialog == null)
				mDialog = new AlertLayoutDialog(context);
			return mDialog;
		}

		public void setlayout(int layout) {

			View v = layoutInflater.inflate(layout, null);

			setContentView(v);
		}

		/**
		 * @param context
		 * @param dialog
		 */
		public AlertLayoutDialog(Context context, int dialog) {
			super(context, dialog);
		}

		public void initDialogPosition(Context context) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 140;
			getWindow().setAttributes(lp);

		}

		/**
		 * 
		 * @param context
		 * @param gravity
		 *            位置
		 */
		public void initDialogPosition(Context context, int gravity) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 140;
			lp.gravity = gravity;
			getWindow().setAttributes(lp);
		}
	}

	/**
	 * 显示提示对话框（填充Message、设置ok按钮点击事件、隐藏cancel按钮）
	 * 
	 * @param context
	 * @param message
	 * @param okBtnOnClickListener
	 *            此参数为空时，触发Dialog执行dismiss()操作
	 */
	public static void displayAlertDialog(Context context, String message,
			View.OnClickListener okBtnOnClickListener) {
		InteractiveDialog mDialog = new InteractiveDialog(context);
		mDialog.setMessage(message);
		mDialog.setOkBtnClickListener(okBtnOnClickListener);
		mDialog.setCancelBtnOfVisibility(View.GONE);
		mDialog.show();
	}

	/**
	 * 显示提示对话框（填充Message、设置ok和cancel按钮点击事件）
	 * 
	 * @param context
	 * @param message
	 * @param okBtnOnClickListener
	 *            此参数为空时，触发Dialog执行dismiss()操作
	 * @param cancelBtnOnClickListener
	 *            此参数为空时，触发Dialog执行dismiss()操作
	 */
	public static void displayAlertDialog(Context context, String message,
			View.OnClickListener okBtnOnClickListener,
			View.OnClickListener cancelBtnOnClickListener) {
		InteractiveDialog mDialog = new InteractiveDialog(context);
		mDialog.setMessage(message);
		mDialog.setOkBtnClickListener(okBtnOnClickListener);
		mDialog.setCancelBtnClickListener(cancelBtnOnClickListener);
		mDialog.show();
	}

	/**
	 * 显示提示对话框
	 * 
	 * @param context
	 * @param top
	 * @param title
	 *            // 默认为“提示”
	 * @param message
	 * @param okBtnStr
	 *            //默认为“确定”
	 * @param cancelBtnStr
	 *            //默认为“取消”，若此设置为null 则隐藏cancelBtn按钮
	 * @param okBtnOnClickListener
	 *            此参数为空时，执行dismiss()操作
	 * @param cancelBtnOnClickListener
	 *            此参数为空时，执行dismiss()操作
	 */
	public static void displayAlertDialog(Context context, int top,
			String title, String message, String okBtnStr, String cancelBtnStr,
			View.OnClickListener okBtnOnClickListener,
			View.OnClickListener cancelBtnOnClickListener) {
		InteractiveDialog mDialog = new InteractiveDialog(context);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setOkBtnText(okBtnStr);
		mDialog.initDialogPosition(context, top);
		mDialog.setOkBtnClickListener(okBtnOnClickListener);
		mDialog.setCancelBtnText(cancelBtnStr);
		mDialog.setCancelBtnClickListener(cancelBtnOnClickListener);
		mDialog.show();
	}

	/**
	 * 显示提示对话框
	 * 
	 * @param context
	 * @param title
	 *            // 默认为“提示”
	 * @param message
	 * @param okBtnStr
	 *            //默认为“确定”
	 * @param cancelBtnStr
	 *            //默认为“取消”，若此设置为null 则隐藏cancelBtn按钮
	 * @param okBtnOnClickListener
	 *            此参数为空时，执行dismiss()操作
	 * @param cancelBtnOnClickListener
	 *            此参数为空时，执行dismiss()操作
	 */
	public static void displayAlertDialog(Context context, String title,
			String message, String okBtnStr, String cancelBtnStr,
			View.OnClickListener okBtnOnClickListener,
			View.OnClickListener cancelBtnOnClickListener) {
		InteractiveDialog mDialog = new InteractiveDialog(context);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setOkBtnText(okBtnStr);
		mDialog.setOkBtnClickListener(okBtnOnClickListener);
		mDialog.setCancelBtnText(cancelBtnStr);
		mDialog.setCancelBtnClickListener(cancelBtnOnClickListener);
		mDialog.show();
	}

	/**
	 * 显示列表对话框
	 * 
	 * @param context
	 * @param title
	 *            // 默认为“提示”
	 * @param message
	 *            []
	 * @param onclick
	 *            //返回点击ID操作
	 */
	public static void displaySinglelistDialog(Context context, String title,
			String[] message, int position, OnItemClickListener mlistListener) {// 点击
		ListDialog mDialog = new ListDialog(context);
		mDialog.setStyle(ListDialog.RADIOMODE);
		mDialog.setMessage(message, position);
		mDialog.setSingleListItemClickListener(mlistListener);
		mDialog.show();
	}

	/**
	 * 显示列表对话框
	 * 
	 * @param context
	 * @param title
	 *            // 默认为“提示”
	 * @param message
	 *            []
	 * @param onclick
	 *            //返回点击ID操作
	 */
	public static void displayMultipleChoicelistDialog(Context context,
			String title, String[] message, int position,
			MultipleChoiceModeitemclicklistener mlistListener) {// 点击
		ListDialog mDialog = new ListDialog(context);
		mDialog.setStyle(ListDialog.MULTIPLECHOICEMODE);
		mDialog.setMessage(message, position);
		mDialog.setListMultipleChoiceItemClickListener(mlistListener);
		mDialog.show();
	}

	/**
	 * 
	 * @param context
	 * @param text
	 */
	public static void displayDataDialog(Activity activity,
			onDateListener onDateListener) {
		CustomDatePickerDialog dialog = new CustomDatePickerDialog(activity,
				Calendar.getInstance());
		dialog.addDateListener(onDateListener);

		dialog.showAtLocation(activity.getCurrentFocus(), Gravity.BOTTOM, 0, 0);

	}

	/**
	 * 
	 * @param context
	 * @param select
	 */
	public static void displayCityDialog(Activity activity, SelectInfo select) {

		CityPickerDialog dialog = new CityPickerDialog(activity);
		dialog.setSelectInfoListener(select);
		dialog.showAtLocation(activity.getCurrentFocus(), Gravity.BOTTOM, 0, 0);
	}

	public static class ListDialog extends Dialog implements
			android.view.View.OnClickListener, OnItemClickListener {
		TextView titleView;
		ListView listView;
		Button okBtn;
		Button cancelBtn;
		LinearLayout btn_pane;
		private View.OnClickListener oKBtnClickListener;
		private View.OnClickListener cancelBtnClickListener;
		private MultipleChoiceModeitemclicklistener multipleItemClickListener;
		private OnItemClickListener singleitemClickListener;
		public final static int RADIOMODE = 0;
		public final static int MULTIPLECHOICEMODE = 1;
		private int MODE = RADIOMODE;
		SparseIntArray clickPositon = new SparseIntArray();

		public interface MultipleChoiceModeitemclicklistener {

			/**
			 * @param clickPositon
			 */
			void onItemClick(SparseIntArray clickPositon);
		};

		/**
		 * @param context
		 */
		public ListDialog(Context context) {
			this(context, R.style.dialog);
			setContentView(R.layout.dialog_singlelist);
			initDialogPosition(context);
			titleView = (TextView) this.findViewById(R.id.titleView);
			listView = (ListView) this.findViewById(R.id.dlist);
			okBtn = (Button) this.findViewById(R.id.okBtn);
			cancelBtn = (Button) this.findViewById(R.id.cancelBtn);
			btn_pane = (LinearLayout) this.findViewById(R.id.btn_pane);
			okBtn.setOnClickListener(this);
			cancelBtn.setOnClickListener(this);
			listView.setOnItemClickListener(this);
		}

		public ListDialog(Context context, int paramInt) {
			super(context, paramInt);

		}

		public void initDialogPosition(Context context) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 140;
			getWindow().setAttributes(lp);
		}

		/**
		 * 
		 * @param context
		 * @param gravity
		 *            位置
		 */
		public void initDialogPosition(Context context, int gravity) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 140;
			lp.gravity = gravity;
			getWindow().setAttributes(lp);
		}

		public void setStyle(int mode) {
			this.MODE = mode;
			btn_pane.setVisibility(MODE == RADIOMODE ? View.GONE : View.VISIBLE);
		}

		@Override
		public void setTitle(CharSequence title) {
			if (title != null && !"".equals(title))
				titleView.setText(title);
		}

		// 设置消息
		public void setMessage(String message[], int position) {
			if (message != null && !"".equals(message)) {
				listView.setAdapter(new listItemAdapter(getContext(), message,
						position));
			}
		}

		// 设置OK按钮显示内容
		public void setOkBtnText(String text) {
			if (text != null && !"".equals(text))
				okBtn.setText(text);
		}

		// 设置Cancel按钮显示内容
		public void setCancelBtnText(String text) {
			if (text != null && !"".equals(text))
				cancelBtn.setText(text);
		}

		// 设置Cancel按钮的显示状态
		public void setCancelBtnOfVisibility(int visibility) {
			cancelBtn.setVisibility(visibility);
		}

		// 设置OK按钮监听事件
		public void setOkBtnClickListener(
				View.OnClickListener paramOnClickListener) {
			oKBtnClickListener = paramOnClickListener;
		}

		// 设置CancelBtn按钮监听事件
		public void setCancelBtnClickListener(
				View.OnClickListener paramOnClickListener) {
			cancelBtnClickListener = paramOnClickListener;
		}

		public void setSingleListItemClickListener(
				OnItemClickListener listItemClickListener1) {
			singleitemClickListener = listItemClickListener1;
		}

		public void setListMultipleChoiceItemClickListener(
				MultipleChoiceModeitemclicklistener listItemClickListener) {
			multipleItemClickListener = listItemClickListener;
		}

		// // 拦截返回按钮
		// @Override
		// public void onBackPressed() {
		// return;
		// }

		@Override
		public void onClick(View v) {
			dismiss();
			int vId = v.getId();
			if (vId == R.id.okBtn) {
				if (oKBtnClickListener != null) {
					multipleItemClickListener.onItemClick(clickPositon);
				}
			} else if (vId == R.id.cancelBtn) {
				if (cancelBtnClickListener != null) {
					cancelBtnClickListener.onClick(v);
				}
			}

		}

		//
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int which,
				long arg3) {
			if (MODE == RADIOMODE) {
				dismiss();
				if (singleitemClickListener != null) {
					singleitemClickListener
							.onItemClick(arg0, arg1, which, arg3);
				}
			} else if (MODE == MULTIPLECHOICEMODE) {
				if (-1 == clickPositon.get(which, -1)) {
					clickPositon.put(which, which);
				} else {
					clickPositon.removeAt(which);
				}

			}

		}

		static class listItemAdapter extends BaseAdapter<String> {
			int index = 0;

			/**
			 * @param context
			 * @param list
			 */
			public listItemAdapter(Context context, String list[], int position) {
				super(context, getList(list));
				this.index = position;
			}

			/**
			 * @param list
			 * @return
			 */
			private static List<String> getList(String[] list) {
				List<String> data = new ArrayList<String>();
				for (String string : list) {
					data.add(string);
				}
				return data;
			}

			//
			@Override
			public void onBindViewHolder(BaseAdapter.ViewHolder viewHolder,
					int position, String t) {
				TextView tv = viewHolder.getView(R.id.tv_content);
				TextView box = viewHolder.getView(R.id.cb_box);
				if (index == position) {
					box.setSelected(true);
				} else {
					box.setSelected(false);
				}
				tv.setText(t);
			}

			//
			@Override
			public View onCreateView(ViewGroup parent) {
				return inflate(R.layout.dialog_singlelist_item);
			}

		}
	}

	/**
	 * 交互式对话框
	 * 
	 * @author Administrator
	 * 
	 */
	public static class InteractiveDialog extends Dialog implements
			View.OnClickListener {

		private TextView titleView;
		private TextView messageView;
		private Button okBtn;
		private Button cancelBtn;
		private View.OnClickListener oKBtnClickListener;
		private View.OnClickListener cancelBtnClickListener;

		public InteractiveDialog(Context context, int paramInt) {
			super(context, paramInt);
		}

		private InteractiveDialog(Context context) {
			this(context, R.style.dialog);
			setContentView(R.layout.dialog_interactive);
			initDialogPosition(context);
			titleView = (TextView) this.findViewById(R.id.titleView);
			messageView = (TextView) this.findViewById(R.id.messageView);
			okBtn = (Button) this.findViewById(R.id.okBtn);
			cancelBtn = (Button) this.findViewById(R.id.cancelBtn);
			okBtn.setOnClickListener(this);
			cancelBtn.setOnClickListener(this);
		}

		public void initDialogPosition(Context context) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 100;
			getWindow().setAttributes(lp);
		}

		/**
		 * 
		 * @param context
		 * @param gravity
		 *            位置
		 */
		public void initDialogPosition(Context context, int gravity) {
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 100;
			lp.gravity = gravity;
			getWindow().setAttributes(lp);
		}

		@Override
		public void setTitle(CharSequence title) {
			if (title != null && !"".equals(title))
				titleView.setText(title);
		}

		// 设置消息
		public void setMessage(String message) {
			if (message != null && !"".equals(message))
				messageView.setText(message);
		}

		// 设置OK按钮显示内容
		public void setOkBtnText(String text) {
			if (text != null && !"".equals(text))
				okBtn.setText(text);
		}

		// 设置Cancel按钮显示内容
		public void setCancelBtnText(String text) {
			if (text != null && !"".equals(text))
				cancelBtn.setText(text);
		}

		// 设置Cancel按钮的显示状态
		public void setCancelBtnOfVisibility(int visibility) {
			cancelBtn.setVisibility(visibility);
		}

		// 设置OK按钮监听事件
		public void setOkBtnClickListener(
				View.OnClickListener paramOnClickListener) {
			oKBtnClickListener = paramOnClickListener;
		}

		// 设置CancelBtn按钮监听事件
		public void setCancelBtnClickListener(
				View.OnClickListener paramOnClickListener) {
			cancelBtnClickListener = paramOnClickListener;
		}

		// 拦截返回按钮
		@Override
		public void onBackPressed() {
			return;
		}

		@Override
		public void onClick(View v) {
			dismiss();
			int vId = v.getId();
			if (vId == R.id.okBtn) {
				if (oKBtnClickListener != null) {
					oKBtnClickListener.onClick(v);
				}
			} else if (vId == R.id.cancelBtn) {
				if (cancelBtnClickListener != null) {
					cancelBtnClickListener.onClick(v);
				}
			}

		}

	}

	/**
	 * 加载对话框
	 * 
	 * @author Administrator
	 * 
	 */
	public static class ProgressDialog extends Dialog {

		private TextView messageView;
		// private ImageView progressView;
		// private AnimationDrawable frameAnimation = null;
		private static ProgressDialog mDialog;

		// 单例模式
		public static ProgressDialog getInstance(Context context) {
			if (mDialog == null)
				mDialog = new ProgressDialog(context);
			return mDialog;
		}

		private ProgressDialog(Context context, int paramInt) {
			super(context, paramInt);
		}

		private ProgressDialog(Context context) {
			this(context, R.style.dialog);
			setContentView(R.layout.dialog_progress);
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			getWindow().setAttributes(lp);
			View progressLoadingRootLy = findViewById(R.id.progress_loading_ly);
			messageView = (TextView) progressLoadingRootLy
					.findViewById(R.id.progress_message);
			// progressView = (ImageView)
			// this.findViewById(R.id.progress_image);
			// frameAnimation = (AnimationDrawable)
			// progressView.getBackground();
		}

		// 设置消息
		public void setMessage(String message) {
			if (message != null && !"".equals(message))
				messageView.setText(message);
		}

		// 显示Dialog
		@Override
		public void show() {
			// if (frameAnimation != null)
			// frameAnimation.start();
			if (mDialog != null)
				super.show();
		}

		// 消失Dialog
		@Override
		public void dismiss() {
			// if (frameAnimation != null)
			// frameAnimation.stop();
			if (mDialog != null)
				super.dismiss();
			mDialog = null;
		}

		// 拦截返回按钮
		@Override
		public void onBackPressed() {
			dismiss();
			super.onBackPressed();
		}

	}

	public interface OnOKDialogClickLisener {

		void okOnClick(String name);
	}

	public interface OnCancelDialogClickLisener {
		void cancelOnClick(String name);
	}

	/**
	 * 显示编辑框对话框
	 * 
	 * @param <AddContactInterfaceListener>
	 * 
	 * @param context
	 * @param title
	 * @param okString
	 * @param cancelBtnStr
	 * @param oKBtnClickListener
	 * @param cancelBtnClickListener
	 * @param addContactInterfaceListener
	 */
	public static void displayEditTextDialog(Context context, String title,
			String hint, String okString, String cancelBtnStr,
			OnOKDialogClickLisener oKBtnClickListener,
			OnCancelDialogClickLisener oncancelDialogClickListener) {
		EdittextDialog mDialog = new EdittextDialog(context);
		mDialog.setTitle(title);
		mDialog.setHint(hint);
		mDialog.setOkBtnText(okString);
		mDialog.setCancelBtnText(cancelBtnStr);
		mDialog.setOkBtnClickListener(oKBtnClickListener);
		mDialog.setCancelBtnClickListener(oncancelDialogClickListener);
		mDialog.show();
	}

	/**
	 * 编辑框式对话框
	 * 
	 * @author Administrator
	 * 
	 */
	public static class EdittextDialog extends Dialog implements
			View.OnClickListener {

		private TextView titleView;
		private EditText messageView;
		private Button okBtn;
		private Button cancelBtn;
		private OnOKDialogClickLisener oKBtnClickListener;
		private OnCancelDialogClickLisener cancelBtnClickListener;

		public EdittextDialog(Context context, int paramInt) {
			super(context, paramInt);
		}

		private EdittextDialog(Context context) {
			this(context, R.style.dialog);
			setContentView(R.layout.dialog_edittext);
			int screenWidth = ScreenUtil.GetScreenPixels((Activity) context).widthPixels;
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.y = 0;
			lp.width = screenWidth - 140;
			getWindow().setAttributes(lp);
			titleView = (TextView) this.findViewById(R.id.titleView);
			messageView = (EditText) this.findViewById(R.id.messageView);
			okBtn = (Button) this.findViewById(R.id.okBtn);
			cancelBtn = (Button) this.findViewById(R.id.cancelBtn);
			okBtn.setOnClickListener(this);
			cancelBtn.setOnClickListener(this);
		}

		public void setHint(String hint) {
			if (hint != null && !"".equals(hint))
				messageView.setHint(hint);
		}

		// 设置Edittext的内容
		public void setMessage(CharSequence message) {
			if (message != null && !"".equals(message))
				messageView.setText(message);
		}

		// 设置Edittext的内容
		public String getMessage() {

			return messageView.getText().toString();

		}

		@Override
		public void setTitle(CharSequence title) {
			System.out.println(title + "标题");
			if (title != null && !"".equals(title))
				titleView.setText(title);
		}

		// 设置OK按钮显示内容
		public void setOkBtnText(String text) {
			if (text != null && !"".equals(text))
				okBtn.setText(text);
		}

		// 设置Cancel按钮显示内容
		public void setCancelBtnText(String text) {
			if (text != null && !"".equals(text))
				cancelBtn.setText(text);
		}

		// 设置Cancel按钮的显示状态
		public void setCancelBtnOfVisibility(int visibility) {
			cancelBtn.setVisibility(visibility);
		}

		// 设置OK按钮监听事件
		public void setOkBtnClickListener(
				OnOKDialogClickLisener oKBtnClickListener2) {
			oKBtnClickListener = oKBtnClickListener2;
		}

		// 设置CancelBtn按钮监听事件
		public void setCancelBtnClickListener(
				OnCancelDialogClickLisener paramOnClickListener) {
			cancelBtnClickListener = paramOnClickListener;
		}

		// 拦截返回按钮
		@Override
		public void onBackPressed() {
			return;
		}

		@Override
		public void onClick(View v) {

			int vId = v.getId();
			if (vId == R.id.okBtn) {
				if (oKBtnClickListener != null) {
					oKBtnClickListener.okOnClick(getMessage());
				}
			} else if (vId == R.id.cancelBtn) {
				if (cancelBtnClickListener != null) {
					cancelBtnClickListener.cancelOnClick(getMessage());
				}
			}
			dismiss();
		}

	}

}