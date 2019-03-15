package com.itc.suppaperless.channels.common;

import android.util.Log;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.loginmodule.bean.FingerLoginEvent;
import com.itc.suppaperless.loginmodule.event.LoginJsonEvent;
import com.itc.suppaperless.loginmodule.event.RegisterTerminalJsonEvent;
import com.itc.suppaperless.meeting_vote.event.MeetingVoteEvent;
import com.itc.suppaperless.meetingmodule.eventbean.BraodCastEvent;
import com.itc.suppaperless.meeting_vote.event.UserVoteEvent;
import com.itc.suppaperless.meeting_vote.event.VotingControlEvent;
import com.itc.suppaperless.meeting_vote.event.VotingUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.ConferenceSoganEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileEvent;
import com.itc.suppaperless.meetingmodule.eventbean.FileUpdateEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JiaoLiuUserEvent;
import com.itc.suppaperless.meetingmodule.eventbean.JizhongControlEvent;
import com.itc.suppaperless.meetingmodule.eventbean.LiveEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingInfoEvent;
import com.itc.suppaperless.meetingmodule.eventbean.MeetingIssueList;
import com.itc.suppaperless.meetingmodule.eventbean.QiandaoControlEvent;
import com.itc.suppaperless.meetingmodule.eventbean.SoganAddOrDEvent;
import com.itc.suppaperless.meetingmodule.eventbean.SoganListEvent;
import com.itc.suppaperless.meetingmodule.eventbean.StartOrEndSignEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiEvent;
import com.itc.suppaperless.meetingmodule.eventbean.YitiupdataEvent;
import com.itc.suppaperless.pdfmodule.eventbean.SpeakData2QueueEvent;
import com.itc.suppaperless.switch_conference.event.ConnectionStatusEvent;
import com.itc.suppaperless.switch_conference.event.GetMeetingListEvent;
import com.itc.suppaperless.switch_conference.event.GetUserInformationEvent;
import com.itc.suppaperless.switch_conference.event.MeetingMessageEvent;
import com.itc.suppaperless.switch_conference.event.MeetingStatusChangeEvent;
import com.itc.suppaperless.switch_conference.event.RegisterServerEvent;
import com.itc.suppaperless.switch_conference.event.StartBroadcastEvent;
import com.itc.suppaperless.switch_conference.event.StopBroadcastEvent;

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
public class NettyTcpCommonClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    List<byte[]> buffList = new ArrayList<>();

    private ChannelHandlerContext lpctx;


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
            case 602:  //注册终端
                EventBus.getDefault().post(new RegisterTerminalJsonEvent(jsonData));
                break;
            case 604:  //登录
                EventBus.getDefault().post(new LoginJsonEvent(jsonData));
                break;
            case 608:   //会议终端用户查询会议列表请求
                EventBus.getDefault().post(new GetMeetingListEvent(jsonData));
                break;
            case 613:   //会议状态更新
                EventBus.getDefault().post(new MeetingStatusChangeEvent());
                break;
            case 202:   //后台Server与终端(客户端)之间消息
                EventBus.getDefault().post(new RegisterServerEvent(jsonData));
                break;
            case 209:   //获取用户信息
                EventBus.getDefault().post(new GetUserInformationEvent(jsonData));
                break;
            case 207:  //下发会议信息
                EventBus.getDefault().post(new MeetingInfoEvent(jsonData));
                break;
            case 241: //文件更新
                EventBus.getDefault().post(new FileUpdateEvent(jsonData));
                break;
            case 211://用户列表
                EventBus.getDefault().post(new JiaoLiuUserEvent(jsonData));
                break;
            case 213://会议文件列表数量
                EventBus.getDefault().post(new FileEvent(jsonData));
                break;
            case 215://会议议题资料列表
                EventBus.getDefault().post(new MeetingIssueList(jsonData));
                break;
            case 272://议题状态通知
                EventBus.getDefault().post(new YitiEvent(jsonData));
                break;
            case 270://议题更新
                EventBus.getDefault().post(new YitiupdataEvent(jsonData));
                break;
            case 803://重置人员
                EventBus.getDefault().post(new JiaoLiuUserEvent(jsonData));
                break;
            case 222://开始广播
                EventBus.getDefault().post(new StartBroadcastEvent(jsonData));
                break;
            case 221://停止广播
                EventBus.getDefault().post(new StopBroadcastEvent(jsonData));
                break;
            case 703://指纹登录
                EventBus.getDefault().post(new FingerLoginEvent(jsonData));
                break;
            case 72://Document speaker(Document presentation) by using pure channel.
                EventBus.getDefault().post(new SpeakData2QueueEvent(jsonData));
                break;
            case 260://集中控制
                EventBus.getDefault().post(new JizhongControlEvent(jsonData));
                break;
            case 244://转发签到消息到主持人
                EventBus.getDefault().post(new QiandaoControlEvent(jsonData));
                break;
            case 245://签到控制
                EventBus.getDefault().post(new StartOrEndSignEvent(jsonData));
                break;
            case 555://会议标语
                EventBus.getDefault().post(new ConferenceSoganEvent(jsonData));
                break;
            case 553://会议标语列表
                EventBus.getDefault().post(new SoganListEvent(jsonData));
                break;
            case 554://会议标语新增和删除
                EventBus.getDefault().post(new SoganAddOrDEvent(jsonData));
                break;
            case 226://会议消息
                EventBus.getDefault().post(new MeetingMessageEvent(jsonData));
                break;
            case 217://会议投票信息
                EventBus.getDefault().post(new MeetingVoteEvent(jsonData));
                break;
            case 231://投票更新
                EventBus.getDefault().post(new VotingUpdateEvent(jsonData));
                break;
            case 290://直播流是否可用
                EventBus.getDefault().post(new LiveEvent(jsonData));
                break;
            case 236://会议投票实时状态
                EventBus.getDefault().post(new UserVoteEvent(jsonData));
                break;
            case 232://投票控制
                EventBus.getDefault().post(new VotingControlEvent(jsonData));
                break;

            case 800://大屏广播
                EventBus.getDefault().post(new BraodCastEvent(jsonData));
                break;
        }
        Log.i("CMS通道数据", nettyData.getStrType() + jsonData);
    }


    /**
     * TCP通道是否开启
     *
     * @param ctx 通道对象
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Log.e(NettyTcpCommonClient.TAG, "TCP通道已经开启 ");
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
        Log.e(NettyTcpCommonClient.TAG, "TCP通道连接成功 ");
        lpctx = ctx;
        NettyTcpCommonClient.getInstance().isConnecting = true;
        EventBus.getDefault().post(new ConnectionStatusEvent(1003));
        EventBus.getDefault().post(new RegisterTerminalEvent());
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
        lpctx = null;
        NettyTcpCommonClient instance = NettyTcpCommonClient.getInstance();
        instance.isConnecting = false;
        if (!instance.isStop) {
            int userId = AppDataCache.getInstance().getInt(USER_ID);
            instance.connServer(instance.host, instance.port, userId);
            Log.e("pds", "cms重连" + String.valueOf(instance.channel == null));
        }
        Log.e(NettyTcpCommonClient.TAG, "断开连接");
        EventBus.getDefault().post(new ConnectionStatusEvent(SERVER_DISCONNECT));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

    }


}
