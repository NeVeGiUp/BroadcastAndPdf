package com.itc.suppaperless.utils;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO<Object的工具类>
 */
public class ObjectUtil {
	private static Map<String,Activity> arryActivity = new HashMap<>();
	public static void addActivity(String ActivityName, Activity activity) {
		arryActivity.put(ActivityName, activity);
	}


	public static void delActivityStr(){//每一次回到首页, 清空栈内的上下文对象
		arryActivity.clear();
	}
}