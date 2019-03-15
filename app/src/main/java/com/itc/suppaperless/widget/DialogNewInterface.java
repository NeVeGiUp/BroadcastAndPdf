package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.utils.AppUtils;


public class DialogNewInterface extends Dialog {
    private Context context;
    private DialogNewInterface.OnOkClickListener mOkListener;
    private DialogNewInterface.OnCancleClickListener mCancleListener;
    private String text, cancleStr, confirmStr;
    private Spannable spannable;
    private TextView dialog_new_content;

    public DialogNewInterface(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_new_dialog);

        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        ImageView dialog_new_img = (ImageView) findViewById(R.id.dialog_new_img);
        dialog_new_content = (TextView) findViewById(R.id.dialog_new_content);
        dialog_new_content.setText(text == null? spannable : text);
//        if(idRes != 0)dialog_new_img.setImageDrawable(ContextCompat.getDrawable(context, idRes));
        TextView dialog_new_confirm = (TextView) findViewById(R.id.dialog_new_confirm);
        dialog_new_confirm.setOnClickListener(OnOkClickListener);
        TextView dialog_new_cancle = (TextView) findViewById(R.id.dialog_new_cancle);
        dialog_new_cancle.setOnClickListener(OnCancelClickListener);

        if(cancleStr != null){//主要做按钮控制
            if(confirmStr == null){//单取消按钮
                dialog_new_confirm.setVisibility(View.GONE);
                findViewById(R.id.dialog_new_line).setVisibility(View.GONE);
                dialog_new_cancle.setText(cancleStr);
            }else {
                dialog_new_confirm.setText(confirmStr);
                dialog_new_cancle.setText(cancleStr);
            }
        }
        if(!AppUtils.isIPad(context)){//主要是适配
            dialog_new_img.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_tongzhi_n_m));
        }
    }
    public DialogNewInterface setText(String text){
        this.text = text;
        if(dialog_new_content != null){//第一次显示的时候控件是空, 第二次调用更新dialog上的content文本显示
            dialog_new_content.setText(text);
            dialog_new_content.postInvalidate();
        }
        return this;
    }
    public DialogNewInterface setText(Spannable spannable){
        this.spannable = spannable;
        if(dialog_new_content != null){
            dialog_new_content.setText(spannable);
            dialog_new_content.postInvalidate();
        }
        return this;
    }


    public DialogNewInterface showDilaog(){
        show();
        return this;
    }


    /**
     * 只剩下 取消按钮
     * @param str 取消按钮的文字
     */
    public DialogNewInterface setSingleCancle(String str){
        this.cancleStr = str;
        return this;
    }
    /**
     * @param str 确定按钮的文字
     */
    public DialogNewInterface setConfirmStr(String str){
        this.confirmStr = str;
        return this;
    }
    /**
     * @param str 取消按钮的文字
     */
    public DialogNewInterface setCancleStr(String str){
        this.cancleStr = str;
        return this;
    }

    public DialogNewInterface setOnTouchOutside(boolean isOutside){
        setCanceledOnTouchOutside(isOutside);
        return this;
    }
    public DialogNewInterface setOnOkClickListener(OnOkClickListener mOkListener){
        this.mOkListener = mOkListener;
        return this;
    }
    public DialogNewInterface setOnCancleClickListener(OnCancleClickListener mCancleListener){
        this.mCancleListener = mCancleListener;
        return this;
    }
    public interface OnOkClickListener {
        void onClick();
    }
    public interface OnCancleClickListener {
        void onClick();
    }

    private View.OnClickListener OnCancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
            if (mCancleListener != null){
                mCancleListener.onClick();
            }
        }
    };
    private View.OnClickListener OnOkClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
            if (mOkListener != null) {
                mOkListener.onClick();
            }
        }
    };
}
