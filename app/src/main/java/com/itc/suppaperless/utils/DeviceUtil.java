package com.itc.suppaperless.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by pengs on 2017/9/2.
 *
 */

public class DeviceUtil {
    /**
     * 是否是定制机
     * @return true 是  false 不是定制机
     */
    public static boolean isCustomizePhone(){
        return "itc3288_aio".equals(Build.MODEL) && "EMA".equals(Build.MANUFACTURER);
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 判断是否有虚拟按键
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

//    /**
//     * 唤醒设备
//     */
//    public void wakeup(){
//        try {
//            Process process = Runtime.getRuntime().exec("su");
//            DataOutputStream out = new DataOutputStream(
//                    process.getOutputStream());
//            out.writeBytes("echo on > /sys/power/state \n");
//            out.writeBytes("exit\n");
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
//        keyguardLock.disableKeyguard();
//    }
    //设备关机
//    private void shutdown() {
//        try {
//            Process process = Runtime.getRuntime().exec("su");
//            DataOutputStream out = new DataOutputStream(
//                    process.getOutputStream());
//            out.writeBytes("reboot -p\n");
//            out.writeBytes("exit\n");
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
