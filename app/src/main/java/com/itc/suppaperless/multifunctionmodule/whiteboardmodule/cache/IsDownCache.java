package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.cache;

import android.content.Context;

import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.cache.BaseDataCache;


/**
 * Created by ghost on 2016/11/15.
 */


/**
 * 应用程序数据缓存
 */
public class IsDownCache extends BaseDataCache {

    private static IsDownCache sDataCache;
    private final static String CACHE_NAME = "IsDownCache";


    public static synchronized IsDownCache getInstance() {
        if (sDataCache == null) {
            Context context = PaperlessApplication.getmContext();
            if (context == null) {
                throw new IllegalArgumentException("context is null!");
            }
            sDataCache = new IsDownCache(context, CACHE_NAME);
        }
        return sDataCache;
    }

    /**
     * @param appContext
     */
    public IsDownCache(Context appContext) {
        super(appContext);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param appContext
     * @param cacheName
     */
    public IsDownCache(Context appContext, String cacheName) {
        super(appContext, cacheName);
    }


    @Override
    public void clear() {
        super.clear();
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