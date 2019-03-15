package com.itc.suppaperless.switch_conference.event;

/**
 * Create by zhengwp on 3/1/19.
 *
 * Base to the idea of AOP. and it can used to send the message from one presenter to another presenter;
 * 基于AOP的思想，ToastMsgEvent 可以用来发送P层到另一个P层的消息;（理论上支持所有操作去操作另一个P层1）
 *
 * @param targetActivityName :  leads to 'Config.ActivityManage' (关联Config类中 ActivityManager 的属性变量)
 * @str String
 */
public class ToastMsgEvent {
    private String targetActivityName;
    private String str;

    public ToastMsgEvent(String targetActivityName, String str) {
        this.targetActivityName = targetActivityName;
        this.str = str;
    }

    public String getTargetActivityName() {
        return targetActivityName;
    }

    public void setTargetActivityName(String targetActivityName) {
        this.targetActivityName = targetActivityName;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
