package com.itc.suppaperless.loginmodule.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.channels.common.NettyTcpCommonClient;
import com.itc.suppaperless.fingerprintmodule.ByteUtil;
import com.itc.suppaperless.fingerprintmodule.FingerprintUtil;
import com.itc.suppaperless.loginmodule.adapter.SetIpDialogAdapter;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.loginmodule.bean.FingergetDataEvent;
import com.itc.suppaperless.loginmodule.contract.LoginContract;
import com.itc.suppaperless.loginmodule.presenter.LoginPresenter;
import com.itc.suppaperless.switch_conference.ui.PageConferenceActivity;
import com.itc.suppaperless.utils.DeviceUtil;
import com.itc.suppaperless.utils.UiUtil;
import com.itc.suppaperless.widget.CommonProgressDialog;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.ListSaveUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.ClearEditText;
import com.itc.suppaperless.widget.DialogNewInterface;
import com.itc.suppaperless.widget.FingerLoginDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.itc.suppaperless.global.Config.CMS_ACCOUNT;
import static com.itc.suppaperless.global.Config.CMS_PASSWORD;
import static com.itc.suppaperless.global.Config.IP_ADDRESS;
import static com.itc.suppaperless.global.Config.PORT_ADDRESS;
import static com.itc.suppaperless.global.Config.SAVE_ACCOUNT_LIST;
import static com.itc.suppaperless.global.Config.SAVE_IP_LIST;
import static com.itc.suppaperless.global.Config.SAVE_PORT_LIST;
import static com.itc.suppaperless.utils.AppUtils.checkUserAccountSymbol;
import static com.itc.suppaperless.utils.AppUtils.containChinese;
import static com.itc.suppaperless.utils.AppUtils.containsEmoji;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-1-7 下午5:09
 * @ desc   : 登录界面
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, IAdapterClickListener {

    @BindView(R.id.paperless_meeting_icon)
    TextView paperlessMeetingIcon;
    @BindView(R.id.tv_setting_info)
    TextView tvSettingInfo;
    @BindView(R.id.tv_ipaddress)
    TextView tvIpaddress;
    @BindView(R.id.edit_ip_address)
    ClearEditText editIpAddress;
    @BindView(R.id.tv_ip_list)
    TextView tvIpList;
    @BindView(R.id.rl_ip_address)
    RelativeLayout rlIpAddress;
    @BindView(R.id.tv_port_num)
    TextView tvPortNum;
    @BindView(R.id.edit_port_num)
    ClearEditText editPortNum;
    @BindView(R.id.tv_port_list)
    TextView tvPortList;
    @BindView(R.id.rl_port)
    RelativeLayout rlPort;
    @BindView(R.id.tv_back_to_login)
    TextView tvBackToLogin;
    @BindView(R.id.tv_setting_sure)
    TextView tvSettingSure;
    @BindView(R.id.rl_setting_ip)
    RelativeLayout rlSettingIp;
    @BindView(R.id.tv_account_login)
    TextView tvAccountLogin;
    @BindView(R.id.img_account)
    ImageView imgAccount;
    @BindView(R.id.edit_account)
    ClearEditText editAccount;
    @BindView(R.id.tv_account_list)
    TextView tvAccountList;
    @BindView(R.id.rel_login_account)
    RelativeLayout relLoginAccount;
    @BindView(R.id.img_password)
    ImageView imgPassword;
    @BindView(R.id.edit_password)
    ClearEditText editPassword;
    @BindView(R.id.rel_login_password)
    RelativeLayout relLoginPassword;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.below_login_btn_divider)
    ImageView belowLoginBtnDivider;
    @BindView(R.id.img_login_zhiwen)
    ImageView imgLoginZhiwen;
    @BindView(R.id.rel_fingerprint_login_tip)
    RelativeLayout relFingerprintLoginTip;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.text_ip_setting)
    TextView textIpSetting;
    @BindView(R.id.login_view_background)
    RelativeLayout loginViewBackground;
    @BindView(R.id.btn_fingerlogin)
    TextView btn_fingerlogin;

    private Context mContext;
    private boolean mIsPad;
    private boolean mIsCheckIPImage = false;
    private boolean mIsCheckPortImage = false;
    private List<String> mAccountStrList;
    private List<String> mIpStrList;
    private List<String> mPortStrList;
    private String mIpStr;
    private String mPortStr;
    private SetIpDialogAdapter mSetIpDialogAdapter;
    private PopupWindow mPopupWindow;
    private CommonProgressDialog mProgressDialog;
    private FingerLoginDialog fingerLoginDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);
        mContext = this;
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //程序启动界面
        initSplash();
        initEdit();
        initView();
    }

    private void initSplash() {
        if (!AppUtils.isCustomizePhone()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                }
            }).start();
        }
    }

    private void initView() {
        if (DeviceUtil.isCustomizePhone()){
            btn_fingerlogin.setVisibility(View.VISIBLE);
            FingerprintUtil.getInstance().init(this);
        }
        mIpStr = AppDataCache.getInstance().getString(IP_ADDRESS);
        mPortStr = AppDataCache.getInstance().getString(PORT_ADDRESS);
        mProgressDialog = new CommonProgressDialog(mContext);
        mAccountStrList = AppDataCache.getInstance().getList(SAVE_ACCOUNT_LIST);
        editAccount.setText(AppDataCache.getInstance().getString(CMS_ACCOUNT));
        editPassword.setText(AppDataCache.getInstance().getString(CMS_PASSWORD));
        editIpAddress.setText(AppDataCache.getInstance().getString(IP_ADDRESS));
        editPortNum.setText("88");
        mIsPad = AppUtils.isIPad(mContext);
        imgAccount.setBackgroundResource(mIsPad ? R.mipmap.ico_user_n : R.mipmap.icon_user_m);
        imgPassword.setBackgroundResource(mIsPad ? R.mipmap.ico_password_n : R.mipmap.icon_password_m);
        UiUtil.setTextViewDrawable(mContext, 2, tvAccountList, mIsPad ? R.mipmap.ico_xiala_n : R.mipmap.icon_bottom_n_m);
        UiUtil.setTextViewDrawable(mContext, 2, tvIpList, mIsPad ? R.mipmap.ico_xiala_n : R.mipmap.icon_bottom_n_m);
        UiUtil.setTextViewDrawable(mContext, 2, tvPortList, mIsPad ? R.mipmap.ico_xiala_n : R.mipmap.icon_bottom_n_m);
        UiUtil.setTextViewDrawable(mContext, 2, textIpSetting, mIsPad ? R.mipmap.icon_set_n_z : R.mipmap.icon_set_n_m);
    }


    /**
     * 界面点击事件
     *
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.btn_login, R.id.text_ip_setting, R.id.tv_back_to_login, R.id.tv_setting_sure, R.id.tv_account_list,
            R.id.tv_ip_list, R.id.tv_port_list,R.id.btn_fingerlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_ip_setting:
                //设置ip
                ipSetting();
                break;
            case R.id.tv_back_to_login:
                //返回登录
                backToLogin();
                break;
            case R.id.tv_setting_sure:
                //保存设置
                saveSetting();
                break;
            case R.id.tv_account_list:
                mIsCheckIPImage = false;
                mIsCheckPortImage = false;
                showpopupwindow(mAccountStrList, editAccount, tvAccountList, relLoginAccount);
                break;
            case R.id.tv_ip_list:
                mIsCheckIPImage = true;
                mIsCheckPortImage = false;
                showpopupwindow(mIpStrList, editIpAddress, tvIpList, rlIpAddress);
                break;
            case R.id.tv_port_list:
                mIsCheckIPImage = false;
                mIsCheckPortImage = true;
                showpopupwindow(mPortStrList, editPortNum, tvPortList, rlPort);
                break;
            case R.id.btn_login:
                //登录
                if (editAccount.getText().toString().equals("setting") &&
                        editPassword.getText().toString().equals("666666")) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                } else {
                    login();
                }
                break;
            case R.id.btn_fingerlogin://指纹登录
                if (mIpStr==null||mPortStr==null){
                    ToastUtil.show(mContext, "请输入服务器IP地址和端口号");
                }else {
                    fingerLoginDialog=new FingerLoginDialog(this);
                    fingerLoginDialog.show();
                    fingerLoginDialog.getTv_entryfinger_cancle().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fingerLoginDialog.dismiss();
                            FingerprintUtil.getInstance().settimerCancel();
                        }
                    });
                    FingerprintUtil.getInstance().getFeatures();
                }
                break;
        }
    }

    private void backToLogin() {
        rlLogin.setVisibility(View.VISIBLE);
        textIpSetting.setVisibility(View.VISIBLE);
        rlSettingIp.setVisibility(View.GONE);
        editIpAddress.setText(AppDataCache.getInstance().getString(IP_ADDRESS));
        editPortNum.setText(AppDataCache.getInstance().
                getString(PORT_ADDRESS).equals("") ? "88" : AppDataCache.getInstance().getString(PORT_ADDRESS));
    }

    private void ipSetting() {
        mIsCheckIPImage = false;
        mIsCheckPortImage = false;
        rlLogin.setVisibility(View.GONE);
        textIpSetting.setVisibility(View.GONE);
        rlSettingIp.setVisibility(View.VISIBLE);
        mIpStrList = AppDataCache.getInstance().getList(SAVE_IP_LIST);
        mPortStrList = AppDataCache.getInstance().getList(SAVE_PORT_LIST);
    }

    private void saveSetting() {
        if (!checkInfoIsEmpty()) {
            AppDataCache.getInstance().putString(IP_ADDRESS, mIpStr);
            AppDataCache.getInstance().putString(PORT_ADDRESS, mPortStr);
            mIpStr = AppDataCache.getInstance().getString(IP_ADDRESS);
            mPortStr = AppDataCache.getInstance().getString(PORT_ADDRESS);
            ListSaveUtil.getInstance().getSaveIpDataToList(mIpStr);
            ListSaveUtil.getInstance().getSavePortDataToList(mPortStr);
            ToastUtil.show(mContext, "保存成功");
            rlLogin.setVisibility(View.VISIBLE);
            textIpSetting.setVisibility(View.VISIBLE);
            rlSettingIp.setVisibility(View.GONE);
        }
    }

    private void login() {
        Log.i("dffdfdfdf",mIpStr);
        String userName = editAccount.getText().toString().trim();
        String userPsw = editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mIpStr) || TextUtils.isEmpty(mPortStr)) {
            new DialogNewInterface(LoginActivity.this).setText("登录失败\n请设置IP号和端口号").
                    setConfirmStr("现在设置").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                @Override
                public void onClick() {
                    rlLogin.setVisibility(View.GONE);
                    textIpSetting.setVisibility(View.GONE);
                    rlSettingIp.setVisibility(View.VISIBLE);
                }
            }).show();
            return;
        }

        getPresenter().login(userName, userPsw, mIpStr, mPortStr, this);
    }


    private boolean checkInfoIsEmpty() {
        if (StringUtil.isEmpty(mIpStr = editIpAddress.getText().toString())) {
            ToastUtil.show(mContext, "请输入服务器IP地址");
            return true;
        }
        if (StringUtil.isEmpty(mPortStr = editPortNum.getText().toString())) {
            ToastUtil.show(mContext, "请输入服务器端口号");
            return true;
        }
        if (!AppUtils.ipCheck(mIpStr)) {
            ToastUtil.show(mContext, "请输入正确的服务器IP地址");
            return true;
        }
        return false;
    }


    /**
     * 限制账号密码以及ip地址输入表情
     */
    private void initEdit() {
        editAccount.addTextChangedListener(new TextWatcher() {
            String beforeChangeStr;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeChangeStr = s.toString();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editAccount.getText().length() < 5) {
                    btnLogin.setClickable(false);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_login_unclick_btn));
                } else {
                    btnLogin.setClickable(true);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_login_btn));
                }

                //输入表情重置
                if (containsEmoji(editAccount.getText().toString())) {
                    editAccount.setText(beforeChangeStr);
                    editAccount.setSelection(editAccount.getText().toString().length());
                }
                //账号检测
                if (editAccount.getText().toString() != null && !editAccount.getText().toString().equals("") &&
                        editAccount.getText().length() > beforeChangeStr.length()) {
                    if (!checkUserAccountSymbol(editAccount.getText().toString())) {
//                        ToastUtil.show(mContext, "无效账号名：请输入英文字母数字以及下划线组合，不包含空格等特殊字符，且第一个字母为英文字母");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            String beforeStr;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeStr = s.toString();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editPassword.getText().length() < 5) {
                    btnLogin.setClickable(false);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_login_unclick_btn));
                } else {
                    btnLogin.setClickable(true);
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.bg_login_btn));
                }
                //密码输入表情/中文重置
                if (containsEmoji(editPassword.getText().toString()) || containChinese(editPassword.getText().toString())) {
                    editPassword.setText(beforeStr);
                    editPassword.setSelection(editPassword.getText().toString().length());
                }
                if (editPassword.getText().toString() != null && !editPassword.getText().toString().equals("") &&
                        editPassword.getText().length() > beforeStr.length()) {
                    {
                        if (!checkUserAccountSymbol(editPassword.getText().toString())) {
//                            ToastUtil.show(mContext, "无效密码：请输入英文字母数字组合，不包含空格等特殊字符");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editIpAddress.addTextChangedListener(new TextWatcher() {
            String beforeStr;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入表情重置
                if (containsEmoji(editIpAddress.getText().toString()) || containChinese(editIpAddress.getText().toString())) {
                    editIpAddress.setText(beforeStr);
                    editIpAddress.setSelection(editIpAddress.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 下拉列表
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showpopupwindow(final List<String> curListData, final ClearEditText editText,
                                 final TextView textView, RelativeLayout relativeLayout) {
        UiUtil.setTextViewDrawable(mContext, 2, textView, mIsPad ? R.mipmap.ico_xiala_h : R.mipmap.icon_top_n_m);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        ListView listView = new ListView(mContext);
        listView.setPadding(10, 0, 10, 0);
        listView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_login_editbox));
        listView.setDividerHeight(1);
        listView.setLayoutParams(params);
        if (curListData != null) {
            mSetIpDialogAdapter = new SetIpDialogAdapter(mContext, curListData);
            listView.setAdapter(mSetIpDialogAdapter);
            mSetIpDialogAdapter.setIAdapterClickListener(this);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (curListData.size() == 0) return;
                    editText.setText(curListData.get(position));
                    mPopupWindow.dismiss();
                }
            });
        }
        mPopupWindow = new PopupWindow(listView, rlLogin.getMeasuredWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));// 设置一个透明背景
        mPopupWindow.setOutsideTouchable(true);//设置点击PopupWindow以外的地方关闭PopupWindow
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(relativeLayout, 0, mIsPad ? 40 : 20);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UiUtil.setTextViewDrawable(mContext, 2, textView, mIsPad ? R.mipmap.icon_bottom_n_z : R.mipmap.icon_bottom_n_m);
            }
        });
    }

    ///////////////////////////////////////////////  view callback  //////////////////////////////////////////////

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void complete() {
        go2(PageConferenceActivity.class);
        ToastUtil.show(mContext, "登录成功");
    }

    @Override
    public void checkUserName(boolean bol) {
        if (!bol)
            ToastUtil.show(this, "用户名不规范");
    }

    @Override
    public void checkUserPsw(boolean bol) {
        if (!bol)
            ToastUtil.show(this, "密码不规范");
    }

    @Override
    public void checkUserIp(boolean bol) {
        if (!bol)
            ToastUtil.show(this, "请输入正确的服务器IP地址");
    }

    @Override
    public void checkUserPort(boolean bol) {
        if (!bol)
            ToastUtil.show(mContext, "无效端口号：请输入0～65535之间的端口号");

    }

    @Override
    public void checkNetwork(boolean bol) {
        if (bol)
            ToastUtil.show(mContext, "网络连接失败，请检查网络！");
    }

    @Override
    public void serverDisconnect(String strErrorMsg) {
        //服务器断开
        NettyTcpCommonClient.getInstance().onStop();
        ToastUtil.show(mContext, strErrorMsg);
    }

    @Override
    public void mettingNoExist(String strErrorMsg) {
        ToastUtil.show(mContext, strErrorMsg);
    }

    @Override
    public void accountConflict(String strErrorMsg) {
        ToastUtil.show(mContext, strErrorMsg);
    }

    @Override
    public void userLogined(String msg) {
        ToastUtil.show(mContext, msg);
    }

    @Override
    public void userExistent(String msg) {
        ToastUtil.show(mContext, msg);
    }

    @Override
    public void adapterClick(int id, int position) {
        if (mIsCheckIPImage) {
            deleteitemclick((ArrayList<String>) mIpStrList, SAVE_IP_LIST, editIpAddress, position);
        } else if (mIsCheckPortImage) {
            deleteitemclick((ArrayList<String>) mPortStrList, SAVE_PORT_LIST, editPortNum, position);
        } else {
            deleteitemclick((ArrayList<String>) mAccountStrList, SAVE_ACCOUNT_LIST, editAccount, position);
        }
    }


    /**
     * 下拉列表item删除按钮点击事件
     **/
    private void deleteitemclick(ArrayList<String> list, String key, ClearEditText editText, int position) {
        if (position == 0) {
            mPopupWindow.dismiss();
        }
        AppDataCache.getInstance().putList(key, list);
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            if (mIsPad) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * onResume彻底执行完毕的回调
     * 第一次登录或者退出登录时断开tcp通道
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        NettyTcpCommonClient.getInstance().onStop();
        Log.d("aaaa", "onPostResume: tcp已断开连接");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public Activity getActivity() {
        return null;
    }

    // 得到将要对比指纹的特征
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getFingerData(FingergetDataEvent event){
        if (fingerLoginDialog!=null){
            fingerLoginDialog.dismiss();
        }
        byte[] data=ByteUtil.hexString2Bytes(event.getStr());
        if (data.length!=494){
            ToastUtil.show(this,"请重新录入指纹");
        }else {
            byte[] datas=ByteUtil.subBytes(data, data.length - 481, 480);
            getPresenter().fingerprintLogin(mIpStr,mPortStr,ByteUtil.bytes2HexStringtwo(datas),this);
        }

    }
}
