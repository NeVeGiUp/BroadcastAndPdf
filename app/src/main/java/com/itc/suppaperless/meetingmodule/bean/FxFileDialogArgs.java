package com.itc.suppaperless.meetingmodule.bean;


import com.itc.suppaperless.utils.FxHelp;

import java.io.Serializable;

/**
 * 自定义文件对话框传递参数
 */
public class FxFileDialogArgs implements Serializable {
    private static final long serialVersionUID = -2676447116786193367L;

    public FxFileDialogArgs() {

    }

    /**
     * 对话框类型，是打开文件对话框或是保存文件对话框，或是文件夹对话框
     */
    public int DialogType = FxHelp.DLG_OPEN_FILE;
    /**
     * 是否可以多选
     */
    public boolean IsMultiSelect = false;
    /**
     * 文件过滤，每个以分号分隔，如".txt;.jpg;.png"
     */
    public String Filter = null;
    /**
     * 默认文件名后缀，如".txt"
     */
    public String DefaultExtern = null;
    /**
     * 显示对话框的标题
     */
    public String DialogTitle = "文件浏览器";
    /**
     * 初始化路径
     */
    public String InitPath = null;
    /**
     * 是否显示隐藏文件
     */
    public boolean IsShowHiddenFile = false;
    /**
     * 默认的文件名，或者对话框选择的单个文件路径
     */
    public String FileName = "";
    /**
     * 多选时选择的文件列表
     */
    public String[] FileNames = null;
    /**
     * 选择的结果，OK/Cancel
     */
    public int DialogResult = FxHelp.DLGRES_NONE;
}
