package com.itc.suppaperless.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;

import org.greenrobot.eventbus.EventBus;

import static com.itc.suppaperless.global.Config.IS_CONNECTED;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.WIFI_DISCONNECT;

/**
 * Created by cong on 19-1-23.
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
//            //获取联网状态的NetworkInfo对象
//            NetworkInfo info = intent
//                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//            if (info != null) {
//                //如果当前的网络连接成功并且网络连接可用
//                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
//                    if (info.getType() == ConnectivityManager.TYPE_WIFI
//                            || info.getType() == ConnectivityManager.TYPE_MOBILE) {
//
//                    }
//                } else {
//
//                }
//            }
//        }

        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())|| intent.getAction().equals(
                "android.net.conn.CONNECTIVITY_CHANGE")){
            if (isNetworkAvailable(context)==0){//没网络
                EventBus.getDefault().post(new ConnectionStatusEvent(WIFI_DISCONNECT));
            }else if (isNetworkAvailable(context)==1||(isNetworkAvailable(context)==2)){
                EventBus.getDefault().post(new ConnectionStatusEvent(ConnectionStatusEvent.ISDISCONNECT));
            }

        }

    }
    public int isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (ethNetInfo != null && ethNetInfo.isConnected()) {
            return 1;
        } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
            return 2;
        } else {
            return 0;
        }
    }

}
