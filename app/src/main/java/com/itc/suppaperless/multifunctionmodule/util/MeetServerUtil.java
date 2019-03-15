package com.itc.suppaperless.multifunctionmodule.util;

import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.utils.HttpGsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhengwp on 19-3-7.
 */
public class MeetServerUtil {

    public static void seneMeetServer2PS(String str){
        final Map<String, Object> map = new HashMap<>();
        map.put("iSendUserID", -1);
        map.put("iUserID", AppDataCache.getInstance().getInt(Config.USER_ID));
        map.put("iMsgType", 0);
        map.put("iContentType", 0);
        map.put("strContent", str);
        map.put("strSendTime", "");
        JSONObject params = (JSONObject) HttpGsonUtil.getInstance().jsonEnclose(map);//把json参数封装起来
        String strContent = params.toString();
        map.clear();

        map.put("iCmdEnum", Config.CMD_T2S_MEETINGSERVER);
        map.put("iMeetingID", AppDataCache.getInstance().getInt(Config.MEETING_ID));
        map.put("iUserID", AppDataCache.getInstance().getInt(Config.USER_ID));
        map.put("iServiceID", 0);
        map.put("strContent", strContent);
        map.put("strTime", "");
        params = (JSONObject) HttpGsonUtil.getInstance().jsonEnclose(map);
        NettyTcpCommonClient.sendPackage(Config.CMD_T2S_MEETINGSERVER, params.toString().getBytes());
    }

}
