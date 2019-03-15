package com.itc.suppaperless.loginmodule.contract;

import android.content.Context;

import com.itc.suppaperless.base.BaseView;
import com.itc.suppaperless.base.mvp.BaseEventPresenter;
import com.itc.suppaperless.loginmodule.model.LoginModelImpl;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-8 下午5:41
 * @ desc   : view层与presenter层契约类
 */
public interface LoginContract {

    interface View extends BaseView {

        void showLoading();

        void stopLoading();

        void complete();

        void userLogined(String msg);

        void userExistent(String msg);

        void checkUserName(boolean bol);

        void checkUserPsw(boolean bol);

        void checkUserIp(boolean bol);

        void checkUserPort (boolean bol);

        void checkNetwork(boolean bol);

        void serverDisconnect(String strErrorMsg);

        void mettingNoExist(String strErrorMsg);

        void accountConflict(String strErrorMsg);
    }

    abstract class Presenter extends BaseEventPresenter<View, LoginModelImpl> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void login(String userName, String userPsw, String ip, String port, Context context);

        public abstract void fingerprintLogin(String ip, String port,String strFingerData,Context context);
    }

}