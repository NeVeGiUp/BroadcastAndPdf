package com.itc.suppaperless.utils;

import android.app.Activity;
import android.util.Log;

import com.itc.suppaperless.pdfmodule.configure.PdfConfigure;
import com.itc.suppaperless.pdfmodule.ui.PdfBrowseActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Create by zhengwp on 19-2-22.
 * <p>
 * Activity管理
 */
public class ActivityManageUtil {
    private static Map<String, Activity> activityMap = new HashMap<>();
    private static  Activity activity;

    public static Activity getMainActivity() {
        return activity;
    }

    public static void setMainActivity(Activity activity) {
        ActivityManageUtil.activity = activity;
    }

    //closePdfBrowseActivity
    public static void closePdfBrowseActivity(String str) {
        for (int i = 0; i < activityMap.size(); i++) {
            if (activityMap.get(str) instanceof PdfBrowseActivity) {
                activityMap.get(str).finish();
                activityMap.remove(str);
            }
        }
    }

    public static void insertActivity(String aName, Activity activity) {
        activityMap.put(aName, activity);
    }
    public static Activity getActivity(String aName){
         return activityMap.get(aName);
    }

    public static void deleteActivity(String aName) {
        activityMap.remove(aName);
    }

    public static void delAllActivity() {// 清空栈内的上下文对象
        for (Map.Entry<String, Activity> entry : activityMap.entrySet()) {
            entry.getValue().finish();
        }
        activityMap.clear();
    }
}
