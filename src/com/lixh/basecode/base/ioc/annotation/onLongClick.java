package com.lixh.basecode.base.ioc.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View�ؼ���{@link Annotation}<br>
 * ʹ�÷�ΧΪȫ�ֱ���<br>
 * listenerClassһ��ʹ��Ĭ��ֵ
 * 
 * @author Mr.Zheng
 * @date 2014��8��11��13:27:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
// ������Զ�����ֵ
public @interface onLongClick {
	/**
	 * View�ؼ���id<br>
	 * ��������һ��ʱ����м�����ȫ��ʹ��ͬһ������ʵ��
	 */
	int[] id();

	/**
	 * �Ƿ���ӳ����¼�����<br>
	 * {@link android.view.View.OnLongClickListener}
	 */
	boolean onLongClickListener() default false;

	Class listenerClass() default onLongClick.class;
}
