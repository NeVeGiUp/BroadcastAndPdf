package com.itc.suppaperless.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.itc.suppaperless.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pengs on 2016/9/20.
 */
public class UiUtil {
    /**
     * textView代码设置图片改变，默认改变在top
     */
    public static void setTextViewDrawable(Context context, TextView textView, int idRes){
        Drawable drawable = ContextCompat.getDrawable(context, idRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }
    /**
     * textView代码设置图片改变，默认改变在left
     */
    public static void setTextViewLeftDrawable(Context context, TextView textView, int idRes){
        Drawable drawable = ContextCompat.getDrawable(context, idRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }
    /**
     * 动态代码设置textView图片改变
     * @param context 上下文对象
     * @param position 图片显示在文字的方位  0:left 1:top 2:right 3:bottom
     * @param textView 控件
     * @param idRes 资源文件
     */
    public static void setTextViewDrawable(Context context, int position, TextView textView, int idRes){
        Drawable drawable = ContextCompat.getDrawable(context, idRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (position){
            case 0:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case 1:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case 2:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                textView.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

//    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Window window = dialog.getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));
//
//                //底部导航栏
//                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 显示当前文件的icon
     * @param context 上下文
     * @param iv_upload 显示的imageView
     * @param strFileName 文件名
     */
    public static void showCurFileImage(Context context, ImageView iv_upload, String strFileName) {
        if(!StringUtil.isEmpty(strFileName)){
            String getFileSuffix = StringUtil.getsuffix(strFileName).trim().toLowerCase();
//            Log.e("==文件后缀名==", getFileSuffix);
            if(".xls".contains(getFileSuffix) || ".xlsx".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.xls_icon);
            }else if(".doc".contains(getFileSuffix) || ".docx".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.doc_icon);
            } else if(".pdf".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.pdf_icon2);
            }else if(".jpg".contains(getFileSuffix) || ".jpeg".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.jpg_icon);
            } else if(".png".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.png_icon);
            } else if(".ppt".contains(getFileSuffix) || ".pptx".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.ppt_icon);
            } else if(".mp4".contains(getFileSuffix) || ".mov".contains(getFileSuffix) || ".avi".contains(getFileSuffix) || ".rmvb".contains(getFileSuffix)){
                    iv_upload.setImageResource(R.mipmap.video_icon);
            } else{
                    iv_upload.setImageResource(R.mipmap.yichen_icon_2x);
            }
        }else {
                iv_upload.setImageResource(R.mipmap.yichen_icon_2x);
        }
    }

    /**
     * 显示当前文件的icon
     * @param context 上下文
     * @param iv_upload 显示的imageView
     * @param isDir 文件夹?
     */
    public static void showCurFileImage(Context context, ImageView iv_upload, boolean isDir) {
        iv_upload.setImageResource(R.mipmap.icon_folder);
    }

}