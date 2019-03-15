package com.itc.suppaperless.channels.common;

import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.RegisterMediaEvent;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.loginmodule.event.LoginJsonEvent;
import com.itc.suppaperless.loginmodule.event.RegisterTerminalJsonEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingInfoEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingIssueList;
import com.itc.suppaperless.meetingmodule.eventbean.YitiEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiupdataEvent;
import com.itc.suppaperless.switch_conference.event.ApplicationScreenBroadcastEvent;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.GetMeetingListEvent;
import com.itc.suppaperless.switch_conference.event.GetUserInformationEvent;
import com.itc.suppaperless.switch_conference.event.RegisterMediaServerEvent;
import com.itc.suppaperless.switch_conference.event.RegisterServerEvent;
import com.itc.suppaperless.switch_conference.event.ScreenBroadcastReceptionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import static com.itc.suppaperless.global.Config.USER_ID;
import static com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent.SERVER_DISCONNECT;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-10 下午3:27
 * @ desc   :  Common Handler 处理数据回调类 (登录,常规,媒体)
 */
public class MediaNettyTcpCommonClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    List<byte[]> buffList = new ArrayList<>();
    /**
     * tcp数据回调
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        CommandData nettyData = (CommandData) msg;
        short strType = nettyData.getStrType();
        String jsonData = new String(nettyData.getContent()).trim();
        switch (strType) {
            case 220://申请屏幕广播
                EventBus.getDefault().post(new ApplicationScreenBroadcastEvent(jsonData));
                break;
            case 94://接收屏幕广播的byte数据
                EventBus.getDefault().post(new ScreenBroadcastReceptionEvent(nettyData.getContent()));
                break;
            case 202:   //后台Server与终端(客户端)之间消息
                EventBus.getDefault().post(new RegisterMediaServerEvent(jsonData));
                break;
        }
        Log.i("媒体通道数据", nettyData.getStrType() + jsonData);
    }


    /**
     * TCP通道是否开启
     *
     * @param ctx 通道对象
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        Log.e(MediaNettyTcpCommonClient.TAG, "TCP通道已经开启 ");
        buffList.clear();
    }


    /**
     * TCP通道连接成功
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        Log.e(MediaNettyTcpCommonClient.TAG, "TCP通道连接成功 ");
        MediaNettyTcpCommonClient.getInstance().isConnecting = true;
        EventBus.getDefault().post(new RegisterMediaEvent());
    }


    /**
     * 断开连接触发channelInactive
     * <p>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MediaNettyTcpCommonClient instance = MediaNettyTcpCommonClient.getInstance();
        if (!instance.isStop) {
            int userId = AppDataCache.getInstance().getInt(USER_ID);
            instance.connServer(instance.host, instance.port, userId);
            Log.e("pds", "cms重连" + String.valueOf(instance.mMediaChannel == null));
        }
        Log.e(MediaNettyTcpCommonClient.TAG, "断开连接");
        EventBus.getDefault().post(new ConnectionStatusEvent(SERVER_DISCONNECT));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

    }


}
