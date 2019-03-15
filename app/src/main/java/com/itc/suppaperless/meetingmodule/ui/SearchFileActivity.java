package com.itc.suppaperless.meetingmodule.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.SearchFileAdapter;
import com.itc.suppaperless.meetingmodule.bean.FileItemInfo;
import com.itc.suppaperless.meetingmodule.bean.FxFileDialogArgs;
import com.itc.suppaperless.utils.ActivityManageUtil;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.FxHelp;
import com.itc.suppaperless.utils.ObjectUtil;
import com.itc.suppaperless.utils.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;

/**
 * 文件搜索界面
 */

public class SearchFileActivity extends Activity {
    private Context mContext;
    private EditText editSearch;
    private TextView tvExit, searchShowText;
    private ListView listViewSearch;
    private SearchFileAdapter mAdapter;
    private FxFileDialogArgs _args;
    private FileFilter _fileFilter;
    private File _currentFilePath = new File("/./sdcard");//默认搜索的位置
    private String input_info;
    private ArrayList<FileItemInfo> fileItemInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_file);
        mContext = this;
        ObjectUtil.addActivity("SearchFileActivity", this);
        initView();
        initData();
    }

    private void initView() {
        ActivityManageUtil.insertActivity(Config.ActivityManage.SearchFileActivity,this);
        editSearch = (EditText) findViewById(R.id.edit_search);
        tvExit = (TextView) findViewById(R.id.tv_quit);
        searchShowText = (TextView) findViewById(R.id.search_show_text);
        listViewSearch = (ListView) findViewById(R.id.lv_filelist);

        //退出按钮
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        //解析接收传递的参数
        _args = (FxFileDialogArgs) FxHelp.changeActivityObject(this);
        if (_args == null) {
            _args = new FxFileDialogArgs();
        }
        explainFilter();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fileItemInfos.clear();
                input_info = editSearch.getText().toString();
                if (StringUtil.isEmpty(input_info)) {
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                        searchShowText.setText("");
                    }
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final ArrayList<FileItemInfo> fileItemInfos = getFileSearchList(_currentFilePath);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mAdapter == null) {
                                        mAdapter = new SearchFileAdapter(mContext, fileItemInfos);
                                        listViewSearch.setAdapter(mAdapter);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    String str = "包含" + "“" + input_info + "”" + "关键字的文件" + "(" + fileItemInfos.size() + "项" + ")";
                                    searchShowText.setText(str);
                                    listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent();
                                            intent.putExtra("currentAbsolutePath", fileItemInfos.get(position).getAbsolutePath());
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 获取文件夹所有的文件
     */
    private ArrayList<FileItemInfo> getFileSearchList(File _currentFilePath) {
        File[] fList = _fileFilter == null ? _currentFilePath.listFiles() : _currentFilePath.listFiles(_fileFilter);
        if (fList != null) {
            for (File f : fList) {
                int[] size = FxHelp.getFileInfo(f);
                String fInfoString;
                if (f.isDirectory()) {
                    fInfoString = "文件夹：" + size[1] + "   " + "文件：" + size[2];
                } else {//文件
                    String[] result = new String[2];
                    FxHelp.formatDouble(size[0], 3, "", result);
                    fInfoString = "大小：" + result[0] + result[1] + "B";
                }
                FileItemInfo fi = new FileItemInfo();
                fi.setFile(f);
                fi.setIcon(getFileIcon(f));
                fi.setTitle(f.getName());
                fi.setInfo("修改时间：" + FxHelp.format(new Date(f.lastModified())) + "   " + fInfoString);
                fi.setCheck(false);
                //获取包含关键字的文件
                if (fi.getTitle().contains(input_info)) {
                    fi.setAbsolutePath(f.getAbsolutePath());
                    fileItemInfos.add(fi);
                    if (f.isDirectory()) {
                        getFileSearchList(f);
                    }
                }
            }
        }
        return fileItemInfos;
    }

    /**
     * 获取默认文件图标
     */
    private int getFileIcon(File f) {
        if (f.isDirectory()) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.ic_file;
            } else {//手机
                return R.mipmap.ic_file_mobile;
            }
        }
        String exTern = FxHelp.getFileExtern(f, 3).toLowerCase();
        if (exTern.equals(".doc")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.doc_icon;
            } else {//手机
                return R.mipmap.doc_icon_mobile;
            }
        }

        if (exTern.equals(".jpg") || exTern.equals(".gif") || exTern.equals(".jpeg")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.jpg_icon;
            } else {//手机
                return R.mipmap.jpg_icon_mobile;
            }
        }

        if (exTern.equals(".pdf")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.pdf_icon2;
            } else {//手机
                return R.mipmap.pdf_icon2_mobile;
            }
        }

        if (exTern.equals(".png")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.png_icon;
            } else {//手机
                return R.mipmap.png_icon_mobile;
            }
        }

        if (exTern.equals(".ppt")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.ppt_icon;
            } else {//手机
                return R.mipmap.ppt_icon_mobile;
            }
        }

        if (exTern.equals(".mp4") || exTern.equals(".mpeg") || exTern.equals(".avi") || exTern.equals(".rm") || exTern.equals(".rmvb") || exTern.equals(".wmv")
                || exTern.equals(".mkv") || exTern.equals(".flv")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.video_icon;
            } else {//手机
                return R.mipmap.video_icon_mobile;
            }
        }

        if (exTern.equals(".xls")) {
            if (AppUtils.isIPad(mContext)) {//平板
                return R.mipmap.xls_icon;
            } else {//手机
                return R.mipmap.xls_icon_mobile;
            }
        }

        if (AppUtils.isIPad(mContext)) {//平板
            return R.mipmap.yichen_icon_2x;
        } else {//手机
            return R.mipmap.yichen_icon_2x_mobile;
        }
    }

    /**
     * 解析过滤文件字符
     */
    private void explainFilter() {
        final String[] s = Config.SELECTFILESUFFIX.split(";");
        _fileFilter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (!_args.IsShowHiddenFile && pathname.isHidden()) {   //是否显示隐藏文件
                    return false;
                }
                if (!pathname.isDirectory() && !pathname.isFile()) {   //既不是文件也不是文件夹
                    return false;
                }
                if (pathname.isDirectory()) {    //文件夹一定显示
                    return true;
                } else if (_args.DialogType == FxHelp.DLG_SELECT_FOLDER) {    //选择文件夹对话框不需要显示文件
                    return false;
                } else {
                    for (String value : s) {
                        if (pathname.getName().endsWith(value)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

}
