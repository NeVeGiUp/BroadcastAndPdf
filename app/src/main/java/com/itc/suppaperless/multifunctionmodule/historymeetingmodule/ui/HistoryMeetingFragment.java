package com.itc.suppaperless.multifunctionmodule.historymeetingmodule.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.cache.AppDataCache;
import com.itc.suppaperless.global.Config;

import butterknife.BindView;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-8 上午9:10
 * @ desc   : 历史会议
 */
public class HistoryMeetingFragment extends BaseFragment {

    private static String homePage = "";

    @BindView(R.id.webview)
    WebView mWebView;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_meeting;
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

        //获取登录成功后的IP，username以及password
        String ip = AppDataCache.getInstance().getString(Config.IP_ADDRESS);
        String account = AppDataCache.getInstance().getString(Config.CMS_ACCOUNT);
        String password = AppDataCache.getInstance().getString(Config.CMS_PASSWORD);

        homePage = "http://" + ip + "/h5/?username=" + account + "&pwd=" + password;

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
    }

}
