package com.itc.suppaperless.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hxm on 2016/9/7.
 *
 */
public class LayoutUtil {
    private static ViewGroup.LayoutParams params;
    /**
     * 设置View的宽度
     */
    public static void SetLayoutWidth(View view, int w) {
        params= view.getLayoutParams();
        params.width=w;
        view.setLayoutParams(params);
    }
    public static void SetLayoutWidth(View view, long w) {
        params= view.getLayoutParams();
        params.width= (int) w;
        view.setLayoutParams(params);
    }
    /**
     * 设置View的高度
     */
    public static void SetLayoutHeight(final View view, int h) {
        params= view.getLayoutParams();
        params.height = h;
        view.setLayoutParams(params);
    }
}