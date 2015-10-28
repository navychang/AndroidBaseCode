package com.lixh.basecode.app;

import android.app.Application;
import android.content.Context;

import com.lixh.basecode.base.TitleBarView;
import com.lixh.basecode.db.DataBase;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public abstract class BaseApplication extends Application {
	private static BaseApplication app;
	private TitleBarView topbar;
	public static boolean isDebug = true;

	public void onCreate() {
		super.onCreate();
		app = this;
		initImageLoader(this);
		init();
	}

	public static BaseApplication getInstance() {
		return app;
	}

	/**
	 * 图片加载器初始化
	 * 
	 * @param context
	 */
	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheSize(10 * 1024 * 1024)
				.memoryCacheExtraOptions(500, 500)
				// default = device screen dimensions
				.diskCacheExtraOptions(500, 500, null).build();
		ImageLoader.getInstance().init(config);
	}

	public abstract void init();

	/**
	 * @return the topbar
	 */
	public TitleBarView getTopbar() {
		return topbar;
	}

	/**
	 * @param topbar
	 *            the topbar to set
	 */
	public void setTopbar(TitleBarView topbar) {
		this.topbar = topbar;
	}

	private String dbDirs;

	public DataBase getDataBase() {
		return new DataBase(app, dbDirs);

	}

	/**
	 * 设置数据库的地址
	 * 
	 * @param dbDirs
	 */

	public void setDbDirs(final String dbDirs) {
		this.dbDirs = dbDirs;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 创建数据库
				DataBase db = new DataBase(app, dbDirs);
				db.open();
				db.close();
			}
		}).start();
	}
}