package com.itc.suppaperless.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.itc.suppaperless.R;


public class ToastUtil {
    private static Toast toast;

    private ToastUtil(Context context, String text, int duration){
        TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.toast_bg);
        textView.setGravity(Gravity.CENTER);
        textView.setAlpha(0.9f);
        textView.setPadding(ScreenUtil.dip2px(20),ScreenUtil.dip2px(10),ScreenUtil.dip2px(20),ScreenUtil.dip2px(10));
        if(AppUtils.isIPad(context))
            textView.setTextSize(ScreenUtil.sp2px(context, 10f));
        textView.setTextColor(Color.WHITE);
        textView.setText(text);
        if(toast == null){
            toast = new Toast(context);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setView(textView);
        toast.show();
    }

    public static ToastUtil show(Context context, String text, int duration) {
        return new ToastUtil(context, text, duration);
    }
    public static ToastUtil show(Context context, int resId, int duration) {
        return new ToastUtil(context, context.getString(resId), duration);
    }
    public static ToastUtil show(Context context, int resId) {//默认是长吐司
        return new ToastUtil(context, context.getString(resId), Toast.LENGTH_LONG);
    }
    public static ToastUtil show(Context context, String text) {
        return new ToastUtil(context, text, Toast.LENGTH_LONG);
    }

}
