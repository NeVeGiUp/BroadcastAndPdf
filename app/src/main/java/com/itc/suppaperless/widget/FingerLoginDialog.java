package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.itc.suppaperless.R;


/**
 * Created by xiaogf on 19-2-16.
 */

public class FingerLoginDialog extends Dialog {
    private TextView tv_entryfinger_cancle;

    public FingerLoginDialog( Context context) {
        super(context);
    }

    public FingerLoginDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_fingerprint);
        setCanceledOnTouchOutside(false);//点击外侧不消失属性
        tv_entryfinger_cancle=findViewById(R.id.tv_entryfinger_cancle);
    }

    public TextView getTv_entryfinger_cancle() {
        return tv_entryfinger_cancle;
    }
}
