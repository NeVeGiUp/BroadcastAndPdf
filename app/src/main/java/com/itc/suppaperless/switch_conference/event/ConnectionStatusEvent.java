package com.itc.suppaperless.switch_conference.event;

/**
 * Created by cong on 19-1-24.
 */

public class ConnectionStatusEvent {
    /**
     * 1001 服务器断开
     * 1002 wifi断开
     */
    public final static int SERVER_DISCONNECT = 1001;//断开服务器连接
    public final static int WIFI_DISCONNECT = 1002;//断开网络
    public final static int ISDISCONNECT = 1000;//1000是连接上网络
    public final static int SERVER_ISDISCONNECT = 1003;//服务器连接成功

    private int mConnectionStatus;//
    public ConnectionStatusEvent(int mConnectionStatus) {
        this.mConnectionStatus = mConnectionStatus;
    }

    public int getConnectionStatus() {
        return mConnectionStatus;
    }
}
