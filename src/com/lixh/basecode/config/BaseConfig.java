/**  
 * @Title: Config.java 
 * @Package com.lixh.basecode.config 
 * @Description: TODO  
 * @author lixh 
 * @date 2015年7月19日 下午1:53:02 
 * @version V1.0  
 */
package com.lixh.basecode.config;

/**
 * @Title: Config.java
 * @Package com.lixh.basecode.config
 * @Description: TODO
 * @author lixh
 * @date 2015年7月19日 下午1:53:02
 * @version V1.0
 */
public class BaseConfig {
	private static final String TAG = "Config";
	public static final String IMAGEURL = "imageurl";
	public static final String RESULT_OK = "200";
	public static final String RESULT_FAIL = "500";

	public static class Welcome {
		public final static int GO_HOME = 0;
		public final static int GO_GUIDE = 1;
		public static final String FIRST_LOGIN_SHARE = "first";
		public static final long SPLASH_DELAY_MILLIS = 2000;

	}

	// 启动带返回值的Intent RequestCode
	public static class IntentRequestCodes {
		public static final int PHOTOINTENTREQUESTCODE = 1015;// 照相
		public static final int PHOTOZOOMQUESTCODE = 1016;// 照册
		public static final int PHOTORESOULTQUESTCODE = 1017;// 裁剪功能

	}
}
