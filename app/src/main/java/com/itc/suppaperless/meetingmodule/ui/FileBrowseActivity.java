package com.itc.suppaperless.meetingmodule.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.meetingmodule.adapter.FileBrowseAdapter;
import com.itc.suppaperless.meetingmodule.adapter.FileRecycleViewAdapter;
import com.itc.suppaperless.meetingmodule.bean.FileItemInfo;
import com.itc.suppaperless.meetingmodule.bean.FxFileDialogArgs;
import com.itc.suppaperless.meetingmodule.bean.IFxListener;
import com.itc.suppaperless.meetingmodule.bean.MyAdapterItemListener;
import com.itc.suppaperless.utils.ActivityManageUtil;
import com.itc.suppaperless.utils.AppUtils;
import com.itc.suppaperless.utils.DeviceUtil;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.FxHelp;
import com.itc.suppaperless.utils.ObjectUtil;
import com.itc.suppaperless.utils.StringUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;



/**
 * 自定义文件浏览器和文件对话框
 */
public class FileBrowseActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private boolean isIPad;
    private FxFileDialogArgs _args = new FxFileDialogArgs();
    private File _currentFilePath = null;
    private ArrayList<FileItemInfo> _filelist = null;
    private ArrayList<FileItemInfo> curDirFileLists = null;
    private List<String> formatList;
    private LinearLayout layoutNoFileImage;
    private FileBrowseAdapter _adapter;
    private int _orderBy = ORDER_BY_NAME;	//排序方式，名称/大小/类型/修改时间
    private boolean _orderType = true;   //升序或是降序排列文件
//    private ArrayList<File> _copyFiles = new ArrayList<>();   //需要复制的文件列表
//    private boolean _isCopyOrCut = false;   //true复制，false剪切
//    private File _copyPath = null;		//文件复制或粘贴的文件夹

    private static final int ORDER_BY_NAME = 0;		//按名称排序方式
    private static final int ORDER_BY_SIZE = 1;		//按大小排序方式
    private static final int ORDER_BY_MODIFY = 2;	//按修改时间排序方式
    private static final int ORDER_BY_TYPE = 3;		//按类型排序方式

    private static final int SELECT_FILE_REQUESTCODE = 101;
    private LinearLayout ll_checkBox_all;
    private CheckBox checkBox_all;
    private RecyclerView recyclerViewFileName;
    private LinearLayout linearLayoutSearch;
    private TextView textQuit;
    private ListView lv_files;
    private RelativeLayout rl_upload;
    private FileFilter _fileFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(DeviceUtil.isCustomizePhone()) {
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
            window.setAttributes(params);
        }
        mContext = this;
        isIPad = AppUtils.isIPad(mContext);
        setContentView(R.layout.fragment_file_browse);
        ObjectUtil.addActivity("FileBrowseActivity",this);
        initView();
        initData();
    }

    private void initView() {
        ActivityManageUtil.insertActivity(Config.ActivityManage.FileBrowseActivity,this);
        layoutNoFileImage = (LinearLayout) findViewById(R.id.layout_no_file_image);
        ll_checkBox_all = (LinearLayout) findViewById(R.id.ll_checkBox_all);
        checkBox_all = (CheckBox) findViewById(R.id.checkBox_all);
        recyclerViewFileName = (RecyclerView) findViewById(R.id.recyclerView_fileName);
        linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayout_search);
        textQuit = (TextView) findViewById(R.id.tv_quit);
        lv_files = (ListView)findViewById(R.id.lv_filelist);
        rl_upload = (RelativeLayout) findViewById(R.id.rl_upload);

        ll_checkBox_all.setOnClickListener(this);
        linearLayoutSearch.setOnClickListener(this);
        textQuit.setOnClickListener(this);
        rl_upload.setOnClickListener(this);
    }

    private void initData() {
        //解析接收传递的参数
        _args = (FxFileDialogArgs) FxHelp.changeActivityObject(this);
        if(_args == null){
            _args = new FxFileDialogArgs();
        }
        this.setTitle(_args.DialogTitle);   //设置标题
        explainFilter();

        _currentFilePath = new File("/./sdcard"); //默认为root/sdcard目录
        if(!StringUtil.isEmpty(_args.InitPath)){
            File file = new File(_args.InitPath);
            if(file.isDirectory() && file.exists()){
                gotoPath(file);
            } else {
                gotoPath(_currentFilePath);
            }
        }else {
            gotoPath(_currentFilePath);
        }
        refreshListView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_checkBox_all://全选按钮
                if(checkBox_all.isChecked()){
                    _adapter.setAllItemChecked(lv_files, false);
                    checkBox_all.setChecked(false);
                }else {
                    _adapter.setAllItemChecked(lv_files, true);
                    checkBox_all.setChecked(true);
                }
                break;
            case R.id.linearLayout_search://搜索按钮
                startActivityForResult(new Intent(mContext, SearchFileActivity.class), SELECT_FILE_REQUESTCODE);
                break;
            case R.id.tv_quit://退出按钮
                clickOKButton(0);
                break;
            case R.id.rl_upload://上传文件按钮
                new DialogNewInterface(mContext).setText("上传后该操作不能取消").setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        clickOKButton(1);
                    }
                }).show();
                break;
        }
    }

    /** 跳转到某个文件夹下 */
    private void gotoPath(File f){
        try {
            _currentFilePath = f;
            String fileExterns = FxHelp.getFileExtern(f, 0);
            setDataToAdapter(fileExterns);
            refreshListView();
        } catch (Exception e) {
            FxHelp.toast(mContext, "跳转路径错误!" + FxHelp.getErrorString(e));
        }
    }

    /** 设置文件夹路径数据到适配器中 */
    private void setDataToAdapter(String fileExterns) {
        if(fileExterns != null){
            formatList = new ArrayList<>();
            final String[] formatStr = fileExterns.split("/");
            for (String mFormatStr : formatStr) {
                if(!mFormatStr.equals("")){
                    if(mFormatStr.equals(".")){
                        formatList.add("根目录");
                    }else {
                        formatList.add(mFormatStr);
                    }
                }
            }
            FileRecycleViewAdapter recycleViewAdapter = new FileRecycleViewAdapter(mContext, formatList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerViewFileName.setLayoutManager(layoutManager);
            layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            //设置增加或删除条目的动画
            recyclerViewFileName.setItemAnimator(new DefaultItemAnimator());
            recyclerViewFileName.setAdapter(recycleViewAdapter);
            recycleViewAdapter.setAdapterItemListener(new MyAdapterItemListener() {
                @Override
                public void myItemListener(int position) {
                    if(formatList != null){
                        if(formatList.size() > 0){
                            //切换文件夹时取消权限按钮
                            if(checkBox_all.isChecked()){
                                checkBox_all.setChecked(false);
                            }
                            if(position == 0){
                                gotoPath(new File("/."));
                            }else {
                                StringBuilder builder = new StringBuilder();
                                for(int i = 1; i < formatList.size(); i++){
                                    if(i <= position){
                                        builder.append("/");
                                        builder.append(formatList.get(i));
                                    }
                                }
                                String s = "/." + builder.toString();
                                gotoPath(new File(s));
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        clickOKButton(0);
    }

    /**点击了确定或取消按钮，则返回主界面 */
    private void clickOKButton(int which){
        if(which == 1){  //确定
            ArrayList<File> ls = _adapter.getItemChecked();
            if(_args.DialogType == FxHelp.DLG_OPEN_FILE || _args.DialogType == FxHelp.DLG_SELECT_FOLDER){
                if(ls.size() == 0){
                    FxHelp.toast(mContext, "当前未选择任何文件!");
                } else {
                    if(!_args.IsMultiSelect){
                        _args.FileName = FxHelp.getFileExtern(ls.get(0), 0);
                    } else {
                        _args.FileNames = new String[ls.size()];
                        for (int i = 0; i < ls.size(); i++) {
                            _args.FileNames[i] = FxHelp.getFileExtern(ls.get(i), 0);
                        }
                    }
                    gotoMainActivity(true);
                }
            } else {
                gotoMainActivity(true);
            }
        } else if(which == 0){  //取消
            gotoMainActivity(false);
        }
    }

    /** 回到主界面 */
    private void gotoMainActivity(boolean isOK){
        _args.DialogResult = isOK ? FxHelp.DLGRES_OK : FxHelp.DLGRES_CANCEL;
        FxHelp.changeActivity(FileBrowseActivity.this, null, _args, true, 2);
    }

    /** 解析过滤文件字符*/
    private void explainFilter(){
        final String[] s = (_args.Filter == null || _args.Filter.length() == 0) ? null : _args.Filter.split(";");
        _fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(!_args.IsShowHiddenFile && pathname.isHidden()){   //是否显示隐藏文件
                    return false;
                }
                if(!pathname.isDirectory() && !pathname.isFile()){   //既不是文件也不是文件夹
                    return false;
                }
                if(pathname.isDirectory()){    //文件夹一定显示
                    return true;
                } else if(_args.DialogType == FxHelp.DLG_SELECT_FOLDER){    //选择文件夹对话框不需要显示文件
                    return false;
                } else {
                    if(s == null){   //没有文件过滤
                        return true;
                    }
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

    /** 刷新文件显示列表*/
    private void refreshListView(){
        File[] fList = _fileFilter == null ? _currentFilePath.listFiles() : _currentFilePath.listFiles(_fileFilter);
        _filelist = new ArrayList<>();
        curDirFileLists = new ArrayList<>();
        if(fList != null){
            for (File f : fList) {
                int[] size = FxHelp.getFileInfo(f);
                String fInfoString;
                if(f.isDirectory()){
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
                _filelist.add(fi);
                if(!f.isDirectory()){
                    curDirFileLists.add(fi);
                }
            }

            //全选按钮的显示和隐藏的处理
            checkBox_all.setVisibility(View.VISIBLE);
            if(_filelist.size() == 0 || curDirFileLists.size() == 0){
                checkBox_all.setVisibility(View.INVISIBLE);
            }

            //空文件夹的处理
            if(_filelist.size() == 0){
                layoutNoFileImage.setVisibility(View.VISIBLE);
                lv_files.setVisibility(View.GONE);
                return;
            }
            layoutNoFileImage.setVisibility(View.GONE);
            lv_files.setVisibility(View.VISIBLE);
            sortFileList();   //将文件列表排序
        }
//        if(_adapter == null){
//        }
        _adapter = new FileBrowseAdapter(this, _checkChanged, _args, _filelist);
        lv_files.setAdapter(_adapter);
//        _adapter.notifyDataSetChanged();
        _adapter.setAdapterItemListener(new MyAdapterItemListener() {
            @Override
            public void myItemListener(int position) {
                onListItemClick(position);
            }
        });
//        lv_files.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                onListItemLongClick(position);
//                return false;
//            }
//        });
    }

    /**将文件列表排序 */
    private void sortFileList(){
        Collections.sort(_filelist, new Comparator<FileItemInfo>() {

            @Override
            public int compare(FileItemInfo lhs, FileItemInfo rhs) {
                File fl = lhs.getFile();
                File fr = rhs.getFile();
                if(fl.isDirectory() && fr.isFile()){		//文件夹排在文件前面
                    return _orderType ? -1 : 1;
                } else if(fl.isFile() && fr.isDirectory()){  //文件夹排在文件前面
                    return _orderType ? 1 : -1;
                } else if((fl.isFile() && fr.isFile()) || (fl.isDirectory() && fr.isDirectory())){
                    if(_orderBy == ORDER_BY_NAME){   //按文件名排序
                        return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                fr.getName().compareToIgnoreCase(fl.getName());
                    } else if(_orderBy == ORDER_BY_MODIFY){   //按最后修改日期排序
                        return _orderType ? (int)(fl.lastModified() - fr.lastModified()) :
                                (int)(fr.lastModified() - fl.lastModified());
                    } else if(_orderBy == ORDER_BY_TYPE){    //按文件类型排序
                        if(fl.isDirectory()){		//如果是文件夹，则按文件名排序
                            return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                    fr.getName().compareToIgnoreCase(fl.getName());
                        } else{
                            return _orderType ? FxHelp.getFileExtern(fl, 3).compareToIgnoreCase(FxHelp.getFileExtern(fr, 3)) :
                                    FxHelp.getFileExtern(fr, 3).compareToIgnoreCase(FxHelp.getFileExtern(fl, 3));
                        }
                    } else {   //按文件大小排序
                        if(fl.isDirectory()){//如果是文件夹，则按文件名排序
                            return _orderType ? fl.getName().compareToIgnoreCase(fr.getName()) :
                                    fr.getName().compareToIgnoreCase(fl.getName());
                        } else {
                            return _orderType ? FxHelp.getFileInfo(fl)[0] - FxHelp.getFileInfo(fr)[0] :
                                    FxHelp.getFileInfo(fr)[0] - FxHelp.getFileInfo(fl)[0];
                        }
                    }
                } else {
                    return 1;
                }
            }
        });
    }

    /** 点击了列表中某个item */
    private void onListItemClick(int position){
        FileItemInfo hs = (FileItemInfo)lv_files.getItemAtPosition(position);
        File file = hs.getFile();
        if(file.isDirectory()){   //如果是文件夹则打开该文件夹
            //切换文件夹时取消权限按钮
            if(checkBox_all.isChecked()){
                checkBox_all.setChecked(false);
            }
            gotoPath(file);
        } else{   //否则选择该文件
            _adapter.setItemChecked(lv_files, position, true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_FILE_REQUESTCODE && resultCode == RESULT_OK){
            if(data != null){
                String currentAbsolutePath = data.getStringExtra("currentAbsolutePath");
                _currentFilePath = new File(currentAbsolutePath);
                if(!_currentFilePath.isDirectory()){
                    currentAbsolutePath = currentAbsolutePath.substring(0, currentAbsolutePath.lastIndexOf("/"));
                    String fileName = FileUtil.getFileName(currentAbsolutePath);
                    for(int i = 0; i < _filelist.size(); i++){
                        if(_filelist.get(i).getTitle().equals(fileName)){
                            lv_files.setSelection(i);
                        }
                    }
                    _currentFilePath = new File(currentAbsolutePath);
                }
                gotoPath(_currentFilePath);
            }
        }
    }

    /** 获取默认文件图标 */
    private int getFileIcon(File f){
        if(f.isDirectory()){
            if(isIPad){//平板
                return R.mipmap.ic_file;
            }else{//手机
                return R.mipmap.ic_file_mobile;
            }
        }
        String exTern = FxHelp.getFileExtern(f, 3).toLowerCase();

        if(exTern.equals(".doc") || exTern.equals(".docx")){
            if(isIPad){//平板
                return R.mipmap.doc_icon;
            }else{//手机
                return R.mipmap.doc_icon_mobile;
            }
        }

        if(exTern.equals(".jpg") || exTern.equals(".gif") || exTern.equals(".jpeg")){
            if(isIPad){//平板
                return R.mipmap.jpg_icon;
            }else{//手机
                return R.mipmap.jpg_icon_mobile;
            }
        }

        if(exTern.equals(".pdf")){
            if(isIPad){//平板
                return R.mipmap.pdf_icon2;
            }else{//手机
                return R.mipmap.pdf_icon2_mobile;
            }
        }

        if(exTern.equals(".png")){
            if(isIPad){//平板
                return R.mipmap.png_icon;
            }else{//手机
                return R.mipmap.png_icon_mobile;
            }
        }

        if(exTern.equals(".ppt")){
            if(isIPad){//平板
                return R.mipmap.ppt_icon;
            }else{//手机
                return R.mipmap.ppt_icon_mobile;
            }
        }

        if(exTern.equals(".mp4") || exTern.equals(".mpeg") || exTern.equals(".avi") || exTern.equals(".rm") || exTern.equals(".rmvb") || exTern.equals(".wmv")
                || exTern.equals(".mkv") || exTern.equals(".flv")){
            if(isIPad){//平板
                return R.mipmap.video_icon;
            }else{//手机
                return R.mipmap.video_icon_mobile;
            }
        }

        if(exTern.equals(".xls")){
            if(isIPad){//平板
                return R.mipmap.xls_icon;
            }else{//手机
                return R.mipmap.xls_icon_mobile;
            }
        }

        if(isIPad){//平板
            return R.mipmap.yichen_icon_2x;
        }else{//手机
            return R.mipmap.yichen_icon_2x_mobile;
        }
    }

    /** 显示的复选框被选择的事件 */
    private final IFxListener _checkChanged = new IFxListener() {
        @Override
        public Object OnTrigger(Object sender, Object... args) {
            int position = Integer.parseInt(args[0].toString());
            boolean isCheck = Boolean.parseBoolean(args[1].toString());
            _adapter.setItemChecked(lv_files, position, isCheck);
            ArrayList<File> files = _adapter.getItemChecked();
            if(files != null && curDirFileLists != null){
                if(files.size() == curDirFileLists.size() && files.size() > 0 && curDirFileLists.size() > 0){
                    checkBox_all.setChecked(true);
                }else {
                    checkBox_all.setChecked(false);
                }
            }
            return null;
        }
    };

//    /** 长按了列表中某个item*/
//    private void onListItemLongClick(final int postion){
//        FileItemInfo hs = (FileItemInfo)lv_files.getItemAtPosition(postion);
//        final File file = hs.getFile();
//        FxHelp.alertItems(this, file.getName(), new String[]{"剪切", "复制", "删除", "重命名", "属性"}, new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:  //剪切
//                        _copyFiles.clear();
//                        _copyFiles.add(file);
//                        _isCopyOrCut = false;
//                        _copyPath = new File(getFileExtern(_currentFilePath, 0));
//                        FxHelp.toast(mContext, "请选择路径粘贴!");
//                        break;
//                    case 1:  //复制
//                        _copyFiles.clear();
//                        _copyFiles.add(file);
//                        _isCopyOrCut = true;
//                        _copyPath = new File(getFileExtern(_currentFilePath, 0));
//                        FxHelp.toast(mContext, "请选择路径粘贴!");
//                        break;
//                    case 2:  //删除
//                        FxHelp.fileDelete(file);
//                        _filelist.remove(postion);
//                        _adapter.notifyDataSetChanged();
//                        FxHelp.toast(mContext, "文件\"" + file.getName() + "\"已经删除!");
//                        break;
//                    case 3:  //重命名
//                        FxHelp.alertTextBox(mContext, file.getName(), 0, new IFxListener() {
//
//                            @Override
//                            public Object OnTrigger(Object sender, Object... args) {
//                                String newName = (String)args[0];
//                                if(new File(getFileExtern(_currentFilePath, 0) + "/" + newName).exists()){
//                                    FxHelp.toast(mContext, "重命名失败!文件名\"" + newName + "\"已经存在!");
//                                }
//                                else {
//                                    file.renameTo(new File(getFileExtern(_currentFilePath, 0) + "/" + newName));
//                                    refreshListView();
//                                    FxHelp.toast(mContext, "文件重命名为\"" + newName + "\"!");
//                                }
//                                return null;
//                            }
//                        });
//                        break;
//                    case 4:  //属性
//                        String string = "当前路径:" + getFileExtern(file, 0) + "\n";
//                        int[] info = FxHelp.getFileInfo(file);
//                        string += "子文件夹数:" + info[1] + "\n";
//                        string += "子文件数:" + info[2] + "\n";
//                        string += "最后修改时间:" + FxHelp.format(new Date(file.lastModified())) + "\n";
//                        string += "其他属性:" + (file.canExecute() ? "可执行 " : "不可执行 ") + (file.canRead() ? "可读 ":"不可读 ")
//                                + (file.canWrite() ? "可写 " : "不可写 ") + (file.isHidden() ? "隐藏" : "");
//                        FxHelp.alert(mContext, string);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }

    //    public boolean onCreateOptionsMenu(Menu m){
//		SubMenu m0 = m.addSubMenu("排序方式");
//		m0.add(1, Menu.FIRST, Menu.NONE, "名称");
//		m0.add(1, Menu.FIRST + 1, Menu.NONE, "大小");
//		m0.add(1, Menu.FIRST + 2, Menu.NONE, "修改日期");
//		m0.add(1, Menu.FIRST + 3, Menu.NONE, "类型");
//
//        m.add(2, Menu.FIRST + 10, Menu.NONE, "刷新");
//        m.add(2, Menu.FIRST + 13, Menu.NONE, "属性");
//        m.add(3, Menu.FIRST + 11, Menu.NONE, "粘贴");
//        m.add(3, Menu.FIRST + 12, Menu.NONE, "新建文件夹");
//		return super.onCreateOptionsMenu(m);

//    public boolean onOptionsItemSelected(MenuItem mi){
//        int id = mi.getItemId();
//        switch (id) {
//            case Menu.FIRST:   //按名称排序，如果已经是名称，则改变升序或降序
//                if(_orderBy == ORDER_BY_NAME){
//                    _orderType = !_orderType;
//                }else {
//                    _orderBy = ORDER_BY_NAME;
//                    _orderType = true;
//                }
//                refreshListView();
//                break;
//            case Menu.FIRST + 1:   //按大小排序，如果已经是，则改变升序或降序
//                if(_orderBy == ORDER_BY_SIZE){
//                    _orderType = !_orderType;
//                }else {
//                    _orderBy = ORDER_BY_SIZE;
//                    _orderType = true;
//                }
//                refreshListView();
//                break;
//            case Menu.FIRST + 2:   //按修改时间排序，如果已经是，则改变升序或降序
//                if(_orderBy == ORDER_BY_MODIFY){
//                    _orderType = !_orderType;
//                }else {
//                    _orderBy = ORDER_BY_MODIFY;
//                    _orderType = true;
//                }
//                refreshListView();
//                break;
//            case Menu.FIRST + 3:   //按文件类型排序，如果已经是，则改变升序或降序
//                if(_orderBy == ORDER_BY_TYPE){
//                    _orderType = !_orderType;
//                }else {
//                    _orderBy = ORDER_BY_TYPE;
//                    _orderType = true;
//                }
//                refreshListView();
//                break;
//            case Menu.FIRST + 10:  //刷新
//                refreshListView();
//                break;
//            case Menu.FIRST + 11:  //粘贴
//                if(_copyPath  != null && getFileExtern(_copyPath, 0).equals(getFileExtern(_currentFilePath, 0))){
//                    FxHelp.toast(mContext, "复制源和目标路径相同!");
//                    return true;
//                }
//                if(_copyFiles != null && _copyFiles.size() > 0){
//                    boolean isExist = false;
//                    for (int i = 0; i < _copyFiles.size(); i++) {
//                        if(!_copyFiles.get(i).exists()){
//                            FxHelp.toast(mContext, "需要粘贴的文件不存在!");
//                            return true;
//                        }
//                        if(new File(getFileExtern(_currentFilePath, 0) + "/" + getFileExtern(_copyFiles.get(i), 4)).exists()){
//                            isExist = true;
//                            break;
//                        }
//                    }
//                    if(isExist){
//                        FxHelp.alertButtons(mContext, "当前文件已经存在，是否覆盖?", new String[]{"是","否"}, new IFxListener() {
//
//                            @Override
//                            public Object OnTrigger(Object sender, Object... args) {
//                                int which = Integer.parseInt(args[0].toString());
//                                if(which == 0){ //是
//                                    for (int i = 0; i < _copyFiles.size(); i++) {
//                                        FxHelp.fileCopy(getFileExtern(_copyFiles.get(i), 0), getFileExtern(_currentFilePath, 0), _isCopyOrCut, false);
//                                    }
//                                    refreshListView();
//                                    FxHelp.toast(mContext, "粘贴成功!");
//                                }
//                                return null;
//                            }
//                        });
//                    }
//                    else {
//                        for (int i = 0; i < _copyFiles.size(); i++) {
//                            FxHelp.fileCopy(getFileExtern(_copyFiles.get(i), 0), getFileExtern(_currentFilePath, 0), _isCopyOrCut, false);
//                        }
//                        refreshListView();
//                        FxHelp.toast(mContext, "粘贴成功!");
//                    }
//                }
//                break;
//            case Menu.FIRST + 12:  //新建文件夹
//                FxHelp.alertTextBox(mContext, "新建文件夹", 0, new IFxListener() {
//
//                    @Override
//                    public Object OnTrigger(Object sender, Object... args) {
//                        String path = (String)args[0];
//                        File newdir = new File(getFileExtern(_currentFilePath, 0) + "/" + path);
//                        if(newdir.isDirectory()){   //文件夹已经存在
//                            newdir = FxHelp.getNoRepeatPath(_currentFilePath, "新建文件夹", "", false);  //生成唯一不重复的文件名
//                            if(newdir.mkdirs() || newdir.isDirectory()){
//                                FxHelp.toast(mContext, "文件夹\"" + getFileExtern(newdir, 0) + "\"创建成功!");
//                                refreshListView();
//                            }
//                            else {
//                                FxHelp.toast(mContext, "文件夹\"" + getFileExtern(newdir, 0) + "\"创建失败!");
//                            }
//                        }
//                        else {
//                            if(newdir.mkdirs() || newdir.isDirectory()){
//                                FxHelp.toast(mContext, "文件夹\"" + getFileExtern(newdir, 0) + "\"创建成功!");
//                                refreshListView();
//                            }
//                            else {
//                                FxHelp.toast(mContext, "文件夹\"" + getFileExtern(newdir, 0) + "\"创建失败!");
//                            }
//                        }
//                        return null;
//                    }
//                });
//
//                break;
//            case Menu.FIRST + 13:  //属性
//                String string = "当前路径:" + getFileExtern(_currentFilePath, 0) + "\n";
//                int[] info = FxHelp.getFileInfo(_currentFilePath);
//                string += "子文件夹数:" + info[1] + "\n";
//                string += "子文件数:" + info[2] + "\n";
//                string += "最后修改时间:" + FxHelp.format(new Date(_currentFilePath.lastModified())) + "\n";
//                string += "其他属性:" + (_currentFilePath.canExecute() ? "可执行 " : "不可执行 ") + (_currentFilePath.canRead() ? "可读 ":"不可读 ")
//                        + (_currentFilePath.canWrite() ? "可写 " : "不可写 ") + (_currentFilePath.isHidden() ? "隐藏" : "");
//                FxHelp.alert(mContext, string);
//                break;
//        }
//        return true;

//    }

}
