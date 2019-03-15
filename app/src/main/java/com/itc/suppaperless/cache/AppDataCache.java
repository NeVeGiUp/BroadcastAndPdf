package com.itc.suppaperless.cache;

import android.content.Context;

import com.itc.suppaperless.application.PaperlessApplication;
/**
 * 应用程序数据缓存
 */
public class AppDataCache extends BaseDataCache {

	private static AppDataCache sDataCache;
	private final static String CACHE_NAME = "AppDataCache";
	public final static String APP_USER = "app_user";
	public final static String REALNUMBER = "RealNumber";
	public final static String PASSWORD = "password";
	public final static String VOTECONTROL= "voteControl";

	public static synchronized AppDataCache getInstance() {
		if (sDataCache == null) {
			Context context = PaperlessApplication.getmContext();
			if (context == null) {
				throw new IllegalArgumentException("context is null!");
			}
			sDataCache = new AppDataCache(context, CACHE_NAME);
		}
		return sDataCache;
	}



	/**
	 * @param appContext
	 */
	public AppDataCache(Context appContext) {
		super(appContext);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param appContext
	 * @param cacheName
	 */
	public AppDataCache(Context appContext, String cacheName) {
		super(appContext, cacheName);
	}
	
	/**
	 * 保存用户登录信息
	 * @param app_user
	 */

	public void setAppUser(String app_user) {
		super.putString(APP_USER, app_user);
	}


	@Override
	public void setPreferences(String tinydbname) {
		super.setPreferences(tinydbname);
	}

	@Override
	public void putString(String key, String value) {
		super.putString(key, value);
	}

	@Override
	public String getString(String key) {
		return super.getString(key);
	}
}