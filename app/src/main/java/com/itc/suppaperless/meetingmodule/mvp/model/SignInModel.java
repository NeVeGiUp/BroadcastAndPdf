package com.itc.suppaperless.meetingmodule.mvp.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.meetingmodule.bean.SignThrowingscreen;
import com.itc.suppaperless.meetingmodule.bean.StartOrEndSign;
import com.itc.suppaperless.meetingmodule.bean.UnifySignIn;
import com.itc.suppaperless.meetingmodule.mvp.contract.SignInContract;

/**
 * Created by xiaogf on 19-1-23.
 */

public class SignInModel implements SignInContract.Model{

    @Override
    public void unifySign(int[] aiUserID, String strTime) {
        UnifySignIn unifySignIn=new UnifySignIn();
        unifySignIn.setiCmdEnum(246);
        unifySignIn.setAiUserID(aiUserID);
        unifySignIn.setStrTime(strTime);
        NettyTcpCommonClient.sendPackage(unifySignIn);
    }

    @Override
    public void startOrEndSign(int tyle) {
        StartOrEndSign startOrEndSign=new StartOrEndSign();
        startOrEndSign.setiCmdEnum(245);
        startOrEndSign.setiControlType(tyle);
        NettyTcpCommonClient.sendPackage(startOrEndSign);

    }
    @Override
    public void signScreen(int type) {
        SignThrowingscreen signThrowingscreen=new SignThrowingscreen();
        signThrowingscreen.setiCmdEnum(261);
        signThrowingscreen.setiID(0);
        signThrowingscreen.setiType(type);
        signThrowingscreen.setStrUrl("");
        NettyTcpCommonClient.sendPackage(signThrowingscreen);
    }
}
