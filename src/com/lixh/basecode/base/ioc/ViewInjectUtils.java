package com.lixh.basecode.base.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.lixh.basecode.base.ioc.annotation.InjectView;
import com.lixh.basecode.base.ioc.annotation.OnClick;

public class ViewInjectUtils {
	private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

	public static void inject(Activity activity) {
		injectViews(activity);
		// injectMethod(activity);
	}

	/**
	 * @param activity
	 */
	private static void injectMethod(Activity activity) {
		final Class<? extends Activity> clazz = activity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		// 遍历所有成员变量
		for (final Method method : methods) {
			Log.e("TAG", method.getName() + "");
			OnClick onClickAnnotation = method.getAnnotation(OnClick.class);
			if (onClickAnnotation != null) {
				method.setAccessible(true);
				int viewId = onClickAnnotation.value();
				View v = activity.findViewById(viewId);
				v.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						try {
							method.invoke(clazz, method.getName(), v);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}

					}
				});
			}
		}

	}

	/**
	 * 注入所有的控件
	 * 
	 * @param activity
	 */
	public static void injectFragment(Fragment fragment, View v) {
		Class<? extends Fragment> clazz = fragment.getClass();
		if (clazz.getSuperclass() != null) {
			Field[] parentfield = clazz.getSuperclass().getDeclaredFields();
			// 遍历所有成员变量
			for (Field field : parentfield) {
				Log.e("TAG", field.getName() + "");
				InjectView viewInjectAnnotation = field
						.getAnnotation(InjectView.class);
				if (viewInjectAnnotation != null) {
					int viewId[] = viewInjectAnnotation.value();
					for (int id : viewId) {
						if (id != -1) {
							Log.e("TAG", viewId + "");
							// 初始化View
							try {
								field.setAccessible(true);
								field.set(fragment, v.findViewById(id));

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}

				}
			}
		}
		Field[] fields = clazz.getDeclaredFields();
		// 遍历所有成员变量
		for (Field field : fields) {
			Log.e("TAG", field.getName() + "");
			InjectView viewInjectAnnotation = field
					.getAnnotation(InjectView.class);
			if (viewInjectAnnotation != null) {
				int viewId[] = viewInjectAnnotation.value();
				for (int id : viewId) {
					if (id != -1) {
						Log.e("TAG", viewId + "");
						// 初始化View
						try {
							field.setAccessible(true);
							field.set(fragment, v.findViewById(id));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

			}
		}
	}

	/**
	 * 注入所有的控件
	 * 
	 * @param activity
	 */
	public static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		if (clazz.getSuperclass() != null) {
			Field[] parentfield = clazz.getSuperclass().getDeclaredFields();
			for (Field field : parentfield) {
				InjectView viewInject = field.getAnnotation(InjectView.class);
				if (viewInject != null) {
					int viewId[] = viewInject.value();
					for (int id : viewId) {
						if (id != -1) {
							Log.e("TAG", viewId + "");
							// 初始化View
							try {
								Method method = clazz.getMethod(
										METHOD_FIND_VIEW_BY_ID, int.class);
								Object resView = method.invoke(activity, id);
								field.setAccessible(true);
								field.set(activity, resView);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}

			}
			Field[] fields = clazz.getDeclaredFields();
			// 遍历所有成员变量
			for (Field field : fields) {
				Log.e("TAG", field.getName() + "");
				InjectView viewInjectAnnotation = field
						.getAnnotation(InjectView.class);
				if (viewInjectAnnotation != null) {
					int viewId[] = viewInjectAnnotation.value();
					for (int id : viewId) {
						if (id != -1) {
							Log.e("TAG", viewId + "");
							// 初始化View
							try {
								Method method = clazz.getMethod(
										METHOD_FIND_VIEW_BY_ID, int.class);
								Object resView = method.invoke(activity, id);
								field.setAccessible(true);
								field.set(activity, resView);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}

				}
			}

		}

	}

}