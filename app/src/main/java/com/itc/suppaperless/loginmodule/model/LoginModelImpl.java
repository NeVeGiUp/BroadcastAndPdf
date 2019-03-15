package com.itc.suppaperless.loginmodule.model;

import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.loginmodule.bean.FingerLogin;
import com.itc.suppaperless.loginmodule.bean.Login;
import com.itc.suppaperless.loginmodule.bean.RegisterTermital;
import com.itc.suppaperless.switch_conference.bean.SendRegisterServerBean;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 上午9:02
 * @ desc   : model实现类,获取网络数据
 */
public class LoginModelImpl implements LoginModel {

    /**
     * 发送注册终端请求
     */
    public static void registerTerminal() {
        RegisterTermital data = new RegisterTermital();
        data.setiCmdEnum(601);
        data.setiTerminalID(1);
        data.setiRoomID(0);
        data.setiTerminalType(2);
        data.setStrGUID(0);
        NettyTcpCommonClient.sendPackage(data);

    }
    //测试指纹登录用的注册201指令
    public static void registerServer() {
        SendRegisterServerBean mRegisterServerBean = new SendRegisterServerBean();
        mRegisterServerBean.setiCmdEnum(201);
        mRegisterServerBean.setiTerminalType(2);
        mRegisterServerBean.setiTerminalID(100000);
        mRegisterServerBean.setiUserID(100000);
        mRegisterServerBean.setiMeetingID(1);
        mRegisterServerBean.setiMeetingRoomID(1);
        mRegisterServerBean.setiChannelType(1);
        NettyTcpCommonClient.sendPackage(mRegisterServerBean);
    }


    /**
     *  发送登录请求
     * @param userName
     * @param userPsw
     */
    @Override
    public void loadLogin(String userName, String userPsw) {
        Login data = new Login();
        data.setStrUserName(userName);
        data.setStrPassWord(userPsw);
        data.setiCmdEnum(603);
        data.setiConferenceID(1);
        NettyTcpCommonClient.sendPackage(data);
    }
    /**
     *  发送指纹登录请求
     * @param strFingerData
     */
    @Override
    public void fingerLogin(String strFingerData) {
        FingerLogin data = new FingerLogin();
        data.setiBusiType(1);
        data.setStrFingerData(strFingerData);
        data.setiCmdEnum(702);
        NettyTcpCommonClient.sendPackage(data);
    }
}
