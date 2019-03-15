package com.itc.suppaperless.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;

import com.itc.suppaperless.global.GlobalConstantsBean;
import com.itc.suppaperless.meetingmodule.bean.DaoMaster;
import com.itc.suppaperless.meetingmodule.bean.DaoSession;
import com.itc.suppaperless.widget.NetworkConnectChangedReceiver;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-7 上午11:23
 * @ desc   : application
 */
public class PaperlessApplication extends Application {
    private static Context mContext;
    private static DaoSession daoSession;
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;
    //全局变量类，非静态类可修改值
    private static GlobalConstantsBean globalConstantsBean;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        setupDatabase();
        initOkGo();
    }

    private void init() {
        mContext = this;
        globalConstantsBean = new GlobalConstantsBean();
        //注册网络监听的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(mNetworkConnectChangedReceiver, filter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mNetworkConnectChangedReceiver);
    }

    public static GlobalConstantsBean getGlobalConstantsBean() {
        return globalConstantsBean;
    }

    public static Context getmContext() {
        return mContext;
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "suppaperless.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0

    }

}