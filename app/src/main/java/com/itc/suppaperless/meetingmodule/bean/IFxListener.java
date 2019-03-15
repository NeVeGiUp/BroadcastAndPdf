package com.itc.suppaperless.meetingmodule.bean;

/**
 * 自定义接口，用于传递
 */
public interface IFxListener {
    /**
     * 事件
     */
    Object OnTrigger(Object sender, Object... args);
}
