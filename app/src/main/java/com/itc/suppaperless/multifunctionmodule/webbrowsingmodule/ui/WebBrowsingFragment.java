package com.itc.suppaperless.multifunctionmodule.webbrowsingmodule.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.utils.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-7 上午9:10
 * @ desc   : 网络浏览
 */
public class WebBrowsingFragment extends BaseFragment implements TextView.OnEditorActionListener {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.net_back_rl)
    RelativeLayout mNetBackRl;
    @BindView(R.id.net_home_rl)
    RelativeLayout mNetHomeRl;
    @BindView(R.id.net_go_rl)
    RelativeLayout mGoRl;
    @BindView(R.id.net_open_rl)
    RelativeLayout mOpenRl;
    @BindView(R.id.net_edittext)
    EditText mNetEditEt;
    @BindView(R.id.net_edittext_clear)
    ImageView mNetEditClearIv;
    @BindView(R.id.net_back_img)
    ImageView mNetBackIv;
    @BindView(R.id.net_go_img)
    ImageView mNetGoIv;
    @BindView(R.id.net_home_img)
    ImageView mNetHomeIv;
    @BindView(R.id.net_open_img)
    ImageView mNetOpenIv;

    private Context mContext;
    private static String homePage = "http://www.baidu.com";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_browsing;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
        initView();
    }


    /**
     * 输入网址配置属性
     * webview加载网页
     */
    private void initView() {

        mContext = getActivity();
        mNetEditEt.setText(homePage);
        mNetEditEt.setSelection(mNetEditEt.getText().length());  //设置光标在文字的后面
        mNetEditEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO)
                    refresh();
                return false;
            }
        });
        mNetEditEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mNetEditClearIv.setVisibility(View.VISIBLE);
                } else {
                    mNetEditClearIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //webview属性
        mWebView.loadUrl(homePage);
        mWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                String url = request.getUrl().toString();
                try {
                    if (url.startsWith("baiduboxapp://") ||
                            url.startsWith("baiduboxlite://")  //防止小米，vivo之类手机会直接提示调用app，导致net::ERR_UNKNOWN_URL_SCHEME
                            ) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;  //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //处理http和https开头的url
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings websettings = mWebView.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setSupportZoom(true);
        websettings.setUseWideViewPort(true);
        websettings.setBuiltInZoomControls(true);
        websettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (!AppUtils.isIPad(mContext)) {
            mNetBackIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.btn_left01_n_m));
            mNetBackIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.btn_right01_n_m));
            mNetBackIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.btn_shouye_n_m));
            mNetBackIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.btn_shuaxin_n_m));
            mNetEditClearIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.btn_quxiao_n_m));
        }

    }

    /**
     * 刷新网页
     */
    private void refresh() {
        String webStr = "http://";
        if (mNetEditEt.getText().toString().contains(webStr)) {
            mWebView.loadUrl(mNetEditEt.getText().toString());
        } else {
            String addStr = webStr + mNetEditEt.getText().toString();
            mNetEditEt.setText(addStr);
            mWebView.loadUrl(addStr);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.net_open_rl, R.id.net_back_rl, R.id.net_go_rl, R.id.net_home_rl, R.id.net_edittext_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.net_open_rl:  //刷新
                refresh();
                break;
            case R.id.net_back_rl:  //返回
                mWebView.goBack();
                break;
            case R.id.net_go_rl:  //前进
                mWebView.goForward();
                break;
            case R.id.net_home_rl:  //主页
                mWebView.loadUrl(homePage);
                mNetEditEt.setText(homePage);
                break;
            case R.id.net_edittext_clear: //清除
                mNetEditEt.setText("");
                break;
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            if (mNetEditEt.getText().length() > 0)
                mWebView.goForward();
            return true;
        }
        return false;
    }
}
