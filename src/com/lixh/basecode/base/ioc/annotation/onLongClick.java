package com.lixh.basecode.base.ioc.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View控件的{@link Annotation}<br>
 * 使用范围为全局变量<br>
 * listenerClass一般使用默认值
 * 
 * @author Mr.Zheng
 * @date 2014年8月11日13:27:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
// 这里可以定义多个值
public @interface onLongClick {
	/**
	 * View控件的id<br>
	 * 数量大于一个时如果有监听则全部使用同一个监听实例
	 */
	int[] id();

	/**
	 * 是否添加长按事件监听<br>
	 * {@link android.view.View.OnLongClickListener}
	 */
	boolean onLongClickListener() default false;

	Class listenerClass() default onLongClick.class;
}
