package com.itc.suppaperless.utils;


import com.itc.suppaperless.cache.AppDataCache;

import java.util.ArrayList;
import java.util.Collections;

import static com.itc.suppaperless.global.Config.SAVE_ACCOUNT_LIST;
import static com.itc.suppaperless.global.Config.SAVE_IP_LIST;
import static com.itc.suppaperless.global.Config.SAVE_PORT_LIST;


public class ListSaveUtil {
    private static ListSaveUtil listSaveUtil;
    private ArrayList<String> ipStrList;
    private ArrayList<String> portStrList;
    private ArrayList<String> accountStrList;
    public static ListSaveUtil getInstance() {
        if(listSaveUtil == null){
            listSaveUtil = new ListSaveUtil();
        }
        return listSaveUtil;
    }

    public void getSaveIpDataToList(String ipStr){
        if(ipStrList == null){
            ipStrList = new ArrayList<>();
        }
        ipStrList = AppDataCache.getInstance().getList(SAVE_IP_LIST);
        if(!ipStrList.contains(ipStr) && ipStrList.size() < 4){
            ipStrList.add(ipStr);
        }else if(!ipStrList.contains(ipStr) && ipStrList.size() >= 4){
            ipStrList.remove(0);
            ipStrList.add(ipStr);
        }
        AppDataCache.getInstance().putList(SAVE_IP_LIST, ipStrList);
        Collections.reverse(ipStrList);
    }

    public void getSavePortDataToList(String portStr){
        if(portStrList == null){
            portStrList = new ArrayList<>();
        }
        portStrList = AppDataCache.getInstance().getList(SAVE_PORT_LIST);
        if(!portStrList.contains(portStr) && portStrList.size() < 2){
            portStrList.add(portStr);
            AppDataCache.getInstance().putList(SAVE_PORT_LIST, portStrList);
        }
        Collections.reverse(portStrList);
    }

    public void getSaveAccountDataToList(String accountStr){
        if(accountStrList == null){
            accountStrList = new ArrayList<>();
        }
        accountStrList = AppDataCache.getInstance().getList(SAVE_ACCOUNT_LIST);
        if(!accountStrList.contains(accountStr) && accountStrList.size() < 4){
            accountStrList.add(accountStr);
            AppDataCache.getInstance().putList(SAVE_ACCOUNT_LIST, accountStrList);
        }
        Collections.reverse(accountStrList);
    }

}
