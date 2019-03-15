package com.itc.suppaperless.loginmodule.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.RegisterTerminalEvent;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.loginmodule.bean.LoginResult;
import com.itc.suppaperless.loginmodule.contract.LoginContract;
import com.itc.suppaperless.loginmodule.event.LoginJsonEvent;
import com.itc.suppaperless.loginmodule.event.RegisterTerminalJsonEvent;
import com.itc.suppaperless.loginmodule.model.LoginModelImpl;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.ListSaveUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.itc.suppaperless.global.Config.CMS_ACCOUNT;
import static com.itc.suppaperless.global.Config.CMS_PASSWORD;
import static com.itc.suppaperless.global.Config.USER_ID;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 上午8:59
 * @ desc   : presenter层,逻辑处理层
 */
public class LoginPresenter extends LoginContract.Presenter {

    private String mUserName;
    private String mUserPsw;
    private String strFingerData;

    public LoginPresenter(LoginContract.View loginView) {
        super(loginView);
        mModel = new LoginModelImpl();
    }

    @Override
    public void login(String userName, String userPsw, String ip, String port, Context context) {
        this.mUserName = userName;
        this.mUserPsw = userPsw;
        //检查网络
        if (!AppUtils.isNetworkAvailable(context)) {
            mViewRef.get().checkNetwork(true);
            return;
        }
        //检查IP
        if (AppUtils.ipCheck(ip))
            getView().checkUserIp(true);
        else
            getView().checkUserIp(false);
        //检查端口号
        int portNum = Integer.parseInt(port);
        if (portNum >= 0 && portNum <= 65535)
            getView().checkUserPort(true);
        else
            getView().checkUserPort(false);
        //检查用户名
        boolean isUsername = checkUsername(userName);
        if (!isUsername) {
            getView().checkUserName(false);
            return;
        }
        //检查密码
        boolean isPassword = checkPassword(userPsw);
        if (!isPassword) {
            getView().checkUserPsw(false);
            return;
        }
        //进度条
        getView().showLoading();
        //TcpServer是否连接
        if (!NettyTcpCommonClient.getInstance().isConnecting) {
            NettyTcpCommonClient.getInstance().connServer(ip, Integer.parseInt(port), 1);
        } else {
            mModel.loadLogin(userName, userPsw);
        }

    }

    //指纹登录
    @Override
    public void fingerprintLogin(String ip, String port, String strFingerData, Context context) {
        this.strFingerData = strFingerData;
        //检查网络
        if (!AppUtils.isNetworkAvailable(context)) {
            getView().checkNetwork(true);
            return;
        }
        //检查IP
        if (AppUtils.ipCheck(ip))
            getView().checkUserIp(true);
        else
            getView().checkUserIp(false);
        //检查端口号
        int portNum = Integer.parseInt(port);
        if (portNum >= 0 && portNum <= 65535)
            getView().checkUserPort(true);
        else
            getView().checkUserPort(false);

        //进度条
        getView().showLoading();
        //TcpServer是否连接
        if (!NettyTcpCommonClient.getInstance().isConnecting) {
            NettyTcpCommonClient.getInstance().connServer(ip, Integer.parseInt(port), 1);
        } else {
            Log.i("strFingerData", strFingerData);
            mModel.fingerLogin(strFingerData);
        }
    }

    /**
     * 登录回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginedEvent(LoginJsonEvent event) {
        if (isViewAttach()) {
            getView().stopLoading();
            //保存数据
            LoginResult loginResult = JSON.parseObject(event.getmJsonData(), new TypeReference<LoginResult>() {
            });
            //用户已登录限制不能登录
            int iResult = loginResult.getIResult();
            switch (iResult) {
                case 200:
                    //登录成功
                    getView().complete();
                    break;
                case 401:
                    //账号不存在
                    getView().userExistent(loginResult.getStrErrorMsg());
                    break;
                case 403:
                    //用户是否登录
                    getView().userLogined(loginResult.getStrErrorMsg());
                    break;
                case 404:
                    //会议不存在
                    getView().mettingNoExist(loginResult.getStrErrorMsg());
                    break;
                case 409:
                    //冲突的用户账号
                    getView().accountConflict(loginResult.getStrErrorMsg());
                    break;
                case 504:
                    //服务器断开
                    getView().serverDisconnect(loginResult.getStrErrorMsg());
                    break;
            }
            // TODO: 19-1-22 greendao存储
            // TODO: 19-1-22 版本更新
            AppDataCache.getInstance().putInt(USER_ID, loginResult.getIUserID());
            AppDataCache.getInstance().putString(CMS_ACCOUNT, mUserName);
            AppDataCache.getInstance().putString(CMS_PASSWORD, mUserPsw);
            ListSaveUtil.getInstance().getSaveAccountDataToList(mUserName);
        }
    }

    /**
     * 注册终端回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterTerminalEvent(RegisterTerminalJsonEvent event) {
        if (isViewAttach()) {
            //终端注册完成去登录
            if (strFingerData == null) {
                mModel.loadLogin(mUserName, mUserPsw);
            } else {
                mModel.fingerLogin(strFingerData);
            }
        }
    }
    /**
     * TCP服务已经连接需要去注册终端
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGo2RegisterTerminalEvent(RegisterTerminalEvent event) {
        if (isViewAttach()) {
            //注册终端
                LoginModelImpl.registerTerminal();
        }
    }

    private boolean checkPassword(String userPsw) {
        // TODO: 19-3-1  用户名密码验证
        return true;
    }

    private boolean checkUsername(String userName) {
        return true;
    }

}
