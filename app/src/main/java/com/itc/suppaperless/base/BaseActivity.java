package com.itc.suppaperless.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itc.suppaperless.base.mvp.IBaseXPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cong on 19-1-17.
 */

public abstract class BaseActivity<P extends IBaseXPresenter> extends AppCompatActivity {
    public P mPresenter;

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract P createPresenter();
    public abstract void init();

    private Unbinder bind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        // TODO: add setContentView(...) invocation
        bind = ButterKnife.bind(this);

        init();
    }


    /**
     * 获取 Presenter 对象，在需要获取时才创建`Presenter`，起到懒加载作用
     */
    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        return mPresenter;
    }


    /**
     * 跳转页面
     * @param clazz
     */
    public void go2(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if (mPresenter != null) mPresenter.detachView();
    }
}

