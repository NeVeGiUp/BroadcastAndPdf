package com.itc.suppaperless.global;

import static android.media.MediaFormat.MIMETYPE_VIDEO_AVC;

/**
 * Created by xiaogf on 19-1-14.
 * 全局参数管理类
 */

public class Config {

    public final static String downloadpath = "/sdcard/paperless/";                 //文件下载的路径
    public static String strFileServerIP = "";                                      //文件服务器ip
    public static final int CMD_T2S_MEETINGSERVER = 250;                            //send meet server to paperless server.
    public static final int CMD_S2T_MEETINGINFO = 207;                                //后台服务器下发会议信息
    public static final int CMD_S2T_FILEUPDATENOTICE = 241;                           //文件更新
    public static final int CMD_T2S_ISSUELIST_RSP = 215;                              //返回会议议题资料列表
    public static final int CMD_S2T_ISSUESTATENOTICE = 272;                           //议题状态通知
    public static final int CMD_S2T_ISSUEUPDATE = 270;                                //议题更新
    public static final int CMD_T2S_FILELIST_RSP = 213;                               //会议文件列表
    public static final int CMD_DOWNPROGRESS = 1;                                     //下载进度通知
    public static final int CMD_DOWNFINISH = 2;                                       //下载完成通知
    public static final int CMD_DOWNFAIL = 3;                                         //下载失败
    public static final String IP_ADDRESS = "IP_ADDRESS";                            //登录ip地址
    public static final String PORT_ADDRESS = "PORT_ADDRESS";                        //登录端口号
    public static final String IP_MEETING = "IP";                                    //会议ip地址
    public static final String PORT_MEETING = "PORT";                                //会议端口号
    public static final String MEETING_ID = "MEETING_ID";                            //会议ID
    public static final String MEETING_ROOM_ID = "MEETING_ROOM_ID";                  //roomID
    public static final String USER_ID = "UserID";                                   //用户ID
    public static final String BROADCAST = "BROADCAST";                                   //大屏广播
    public static final String USER_NAME = "UserIName";                              //用户昵称
    public static final String CMS_ACCOUNT = "CMS_ACCOUNT";                          //用户名
    public static final String CMS_PASSWORD = "CMS_PASSWORD";                        //用户密码
    public static final String WELCOME_URL = "WELCOME_URL";                          //欢迎界面URL
    public static final String MEETING_NAME = "MEETING_NAME";                        //会议名称
    public static final String IS_FIRST = "IS_FIRST";                                //是不是第一次进去Mainactivity
    public static final String SAVE_ACCOUNT_LIST = "SAVE_ACCOUNT_LIST";              //保存用户名列表
    public static final String SAVE_PORT_LIST = "SAVE_PORT_LIST";                    //保存端口号列表
    public static final String SAVE_IP_LIST = "SAVE_IP_LIST";                        //保存IP列表
    public static final String SAVE_USER = "userlist";                               //保存参会人员列表
    public static final String ConferenceSogan = "ConferenceSogan";                  //会议标语的URL
    public static final String CHAIRMAN = "Chairman";                                //是否主席
    public static final String SECRETARY = "Secretary";                              //是否秘书

    public static final String DocAnnotation = "DocAnnotation";                     //Document Annotation
    public static final String HandwriteAnnotation = "HandwriteAnnotation";         //Handwrite Annotation
    public static final String ElectronAnnotation = "ElectronAnnotation";           //Electron Annotation
    public static final int DocTemporaryFileType = 4;                               //Temporary file int paperless server
    public static final int DocYitiFileType = 2;                                    //Yiti file int paperless server
    public static final int DocAnnotationType = 5;                                  //Document annotation file in paperless server
    public static final int HandwriteAnnotationType = 12;                           //Handwrite annotation file in paperless server
    public static final int ElectronAnnotationType = 13;                            //Electron annotation file in paperless server

    public static final int meetserver = 21;                                        //Define the constant of meet server
    public static final int morefunction = 22;                                      //Define the constant of more function

    public static final String IS_RECONNECT = "IS_RECONNECT";
    public static final String IS_CONNECTED = "IS_CONNECTED";                        //是否连接网络
    public static final String IS_INFRAGMENT = "IS_INFRAGMENT";                      //判断是否在fragment里面
    public static final String FILE_PATH = "FILE_PATH";                              //文件地址
    public static final String FILE_NAME = "FILE_NAME";                              //文件名字

    public static String SIGNIPATHMAGE = "/sdcard/paperless/";                      //签到文件路径
    public static String SIGNIFILEMAGE = "comment.png";
    public static final int REQUEST_MEDIA_PROJECTION = 101;                          //请求媒体投射
    public static final String VIDEO_AVC = MIMETYPE_VIDEO_AVC;                      // H.264 Advanced Video Coding
    public final static int[] sizeSelctValue = new int[]{3, 10, 15, 20, 25, 30, 35, 40, 45};
    public final static String LST_VOTE = "LSTVOTE";

    public final static String imageFileLocationTemporary = "/screenshot/";          //截图批注的临时存储位置, 退出白板时即删除
    public final static String imageFileLocationSnapshot = "/snapshot/";             //批注存储文件夹
    public final static String imageFileLocation = "/images/";                       //画板存储文件夹
    public final static String imageMutualFileLocation = "/mutual/";                 //交互画板存储文件夹
    public final static String imagePngFomat = ".png";                               //图片文件格式

    public final static String SWITCHFILETYPE = ".jpg;.png;.bmp;.pdf;.jpeg;.xls;.ppt;;.doc;.docx;.xlsx;" +
            ".JPG;.PNG;.BMP;.PDF;.JPEG;.XLS;.PPT;.DOC;.DOCX;.XLSX";
    public final static String SELECTFILESUFFIX = ".jpg;.png;.bmp;.txt;.pdf;.jpeg;.mp4;.avi;.rmvb;.mov;.apk;.xls;.ppt;.mp3;.doc;.docx;.xlsx;" +
            ".JPG;.PNG;.BMP;.TXT;.PDF;.JPEG;.MP4;.AVI;.RMVB;.MOV;.APK;.XLS;.PPT;.MP3;.DOC;.DOCX;.XLSX";

    public static class On_Off {
        public static final int HighLevel = 1;                                     //'1' means the high level and '0' means the low level which means two operations,
        public static final int LowLevel = 0;                                      // one is 'switch-on', and another is 'switch-off'.
    }

    public final static String IS_COMPLETE = "IS_COMPLETE";

    public static class ActivityManage {
        public final static String MainActivity = "MainActivity";
        public final static String PdfBrowseActivity = "PdfBrowseActivity";
        public final static String YitiDetailActivity = "YitiDetailActivity";
        public final static String SearchFileActivity = "SearchFileActivity";
        public final static String FileBrowseActivity = "FileBrowseActivity";
        public final static String WelcomeActivity = "WelcomeActivity";
        public final static String DisplayNameActivity = "DisplayNameActivity";
        public final static String ConferenceSoganActivity = "ConferenceSoganActivity";

    }
}
