package com.itc.suppaperless.utils;

import com.google.gson.Gson;

/**
 * Created by xiaogf on 19-1-23.
 */

public class GsonUtil {
    public static<T> T getJsonObject(String result,Class<T> ct){
        T t=null;
        if (result.isEmpty()){
            return t;
        }else {
            Gson gson=new Gson();
            t=gson.fromJson(result,ct);
        }
        return t;

    }
}
