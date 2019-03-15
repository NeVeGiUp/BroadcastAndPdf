package com.itc.suppaperless.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.itc.suppaperless.R;
import com.itc.suppaperless.meetingmodule.bean.IFxListener;
import com.itc.suppaperless.widget.FxDialog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用操作类
 */
public class FxHelp {
    private static final int NOTIFY_ID = 0x1123;
    private static final String DATE_FORMAT_STRING = "yyyy.MM.dd";
    public static final String ACTIVITY_ARG_PARAM_NAME = "extradata";    //activity切换时传递参数的名称

    static final int YEAR = 0;
    static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOUR = 3;
    private static final int MINUTE = 4;
    private static final int SECOND = 5;
    private static final int MS = 6;
    private static final int WEEK = 7;

    private static final int LAY_STRECH = 0;        //填充模式 -- 拉伸
    private static final int LAY_FIT = 1;        //填充模式 -- 适应
    private static final int LAY_FILL = 2;        //填充模式 -- 填充
    private static final int LAY_CENTER = 3;        //填充模式 -- 居中
    private static final int LAY_TILE = 4;        //填充模式 -- 平铺

    private static final int DT_NORMAL = 0;            //表示阳历
    private static final int DT_LUNAR = 1;            //表示农历
    private static final int DT_WEEKDAY = 2;        //表示星期计算的日期

    private static final int TRUE = 1;            //表示真或有效
    private static final int FALSE = 0;            //表示假或无效

    /**
     * 打开文件对话框
     */
    public static final int DLG_OPEN_FILE = 0;
    /**
     * 保存文件对话框
     */
    static final int DLG_SAVE_FILE = 1;
    /**
     * 选择文件夹对话框
     */
    public static final int DLG_SELECT_FOLDER = 2;
    /**
     * 文件浏览器
     */
    public static final int DLG_BROWSE_FILE = 3;

    public static final int DLGRES_NONE = 0;//对话框返回结果
    public static final int DLGRES_OK = 1;
    public static final int DLGRES_CANCEL = 2;
    private static final int DLGRES_YES = 3;
    private static final int DLGRES_NO = 4;

    /**
     * 屏幕的大小
     */
    private static Point ScreenSize = new Point(0, 0);
    static final String[] WEEKDAY_STRINGS = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static final String[] NUMBER_STRINGS = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] NUMBER_ARY = new String[]{"零", "十", "百", "千", "万", "亿", "万亿", "亿亿"};
    private static final String[] NUMBER_UNIT = new String[]{"T", "G", "M", "k", "", "m", "u", "n", "p", ""};
    private static final int _number_unit_center = 4;  //表示NUMBER_UNIT中单位为普通单位时的序号
    private static final String ZZZ = "00000000000000000000";   //表示小数点后面的位数，不足按0添加
    private static final String JJJ = "####################";   //表示浮点数小数点前面的位数，不足按空格添加

    /**
     * 按下这个按钮进行的颜色过滤
     */
    private final static float[] VIEW_FILTER_SELECTED = new float[]{
            1, 0, 0, 0, 40,
            0, 1, 0, 0, 80,
            0, 0, 1, 0, 120,
            0, 0, 0, 1, 0};

    /**
     * 按钮恢复原状的颜色过滤
     */
    private final static float[] VIEW_FILTER_NORMAL = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0};

    private static int[] _tickcount = new int[16];        //用于计算时间差值

    /**
     * 格式化日期
     */
    public static String format(Date date) {
        return format(date, DATE_FORMAT_STRING);
    }

    /**
     * 格式化日期
     //* @param format 格式字符串
     */
    public static String format(Date date, String format) {
        SimpleDateFormat s = new SimpleDateFormat(format);
        return s.format(date);
    }

    /**
     * byte数组转字符串
     * @param bs         byte数组
     * @param separator   分隔符，如"-"
     * @param startIndex 起始位置
     * @param length     结束位置
     */
    private static String format(byte[] bs, String separator, int startIndex, int length) {
        StringBuilder sBuilder = new StringBuilder();
        int last = Math.min(bs.length, startIndex + length);
        for (int i = startIndex; i < last; i++) {
            sBuilder.append(format(bs[i]));
            if (separator.length() > 0 && i != last - 1) {
                sBuilder.append(separator);
            }
        }
        return sBuilder.toString();
    }

    /**
     * byte数组转字符串
     * @param bs byte数组
     * //* @param seprator 分隔符，如"-"
     * // * @param startIndex 起始位置
     * //* @param length 结束位置
     */
    public static String format(byte[] bs) {
        return format(bs, "-", 0, bs.length);
    }

    /**
     * byte转字符串，如 234 转换为 "EA"
     */
    private static String format(byte b) {
        String s = "";
        if ((b & 0xff) < 0x10)// 0~F前面不零
            s += "0";
        s += (Integer.toHexString(0xFF & b));
        return s.toUpperCase();
    }

    /**
     * 格式化整数
     * @param digit      位数
     * @param isZeroFill 位数不足是否以0填充
     */
    static String format(int i, int digit, boolean isZeroFill) {
        if (isZeroFill) {
            return String.format("%0" + digit + "d", i);
        } else {
            return String.format("%" + digit + "d", i);
        }
    }

    static String formatChineseString(int i, boolean isIncludeUnit) {
        String str = "";
        if (!isIncludeUnit) {
            while (i >= 1) {
                str = NUMBER_STRINGS[i % 10] + str;
                i = i / 10;
            }
        } else {
            int k = 0;
            while (i >= 1) {
                int tmpV = i % 10;
                if (k % 4 > 0)
                    str = NUMBER_ARY[k % 4] + str; //加上进制，如"百","十"
                if (k % 4 == 0 && k > 0)
                    str = NUMBER_ARY[k / 4 + 3] + str; //加上进制，如"万","亿"
                k++;
                i = i / 10;
                str = NUMBER_STRINGS[tmpV] + str; //加上实际的数字，如"一"，"三"
            }
        }
        return str;
    }

    /**
     * 格式化浮点数
     * @param precision   精度，3/4
     * @param currentUnit 当前的单位,x表示不需要转换单位
     * @param result      结果，第0个表示格式化后的字符，第1个表示单位
     */
    public static boolean formatDouble(double value, int precision, String currentUnit, String[] result) {
        if (Double.isNaN(value)) {
            result[0] = "NaN";
            result[1] = currentUnit;
            return false;
        }
        boolean isSmallZero = false;
        if (value < 0) //添加小于0时的精度
        {
            value = -value;
            isSmallZero = true;
        }

        if (currentUnit.equals("x")) //表示不需要转换单位
        {
            result[1] = "x";
            int[] kkks = new int[]{_number_unit_center};
            value = getNumByPrecision(value, precision, kkks);
            if (isSmallZero)
                value = -value;
            result[0] = formatDouble(value, precision, false);
            return true;
        }
        int ckk = _number_unit_center;
        for (int i = 0; i < NUMBER_UNIT.length; i++)
            if (NUMBER_UNIT[i].equals(currentUnit)) {
                ckk = i;
                break;
            }
        value *= Math.pow(10, 3 * (_number_unit_center - ckk));     //转换为普通单位对应的值
        int kkk;

        if (value >= 1000000000000L) {
            value = value / 1000000000000L;
            kkk = 0;
        } else if (value >= 1000000000) {
            value = value / 1000000000;
            kkk = 1;
        } else if (value >= 1000000) {
            value = value / 1000000;
            kkk = 2;
        } else if (value >= 1000) {
            value = value / 1000;
            kkk = 3;
        } else if (value >= 1) {
            kkk = 4;
        } else if (value >= 0.001) {
            value = value * 1000;
            kkk = 5;
        } else if (value >= 0.000001) {
            value = value * 1000000;
            kkk = 6;
        } else if (value >= 0.000000001) {
            value = value * 1000000000;
            kkk = 7;
        } else if (value >= 0.000000000001) {
            value = value * 1000000000000L;
            kkk = 8;
        } else {
            value = 0;
            kkk = ckk;
        }
        int[] kkks = new int[]{kkk};
        value = getNumByPrecision(value, precision, kkks);
        kkk = kkks[0];
        if (isSmallZero)
            value = -value;
        result[1] = NUMBER_UNIT[kkk];
        result[0] = formatDouble(value, precision, false);
        return true;
    }

    /**
     * 按需要保留多少位有效数字，也即精度
     * <param name="precision">精度值</param>
     */
    private static double getNumByPrecision(double value, int precision, int[] resultkkk) {
        if (value >= 1000) {
            value = Math.round(value);
        } else if (value >= 100) {
            int digit = precision - 3;
            value = Double.parseDouble(formatDouble(value, digit, true));
            if (isEqual(value, 1) == 0 && resultkkk[0] > 0) {
                value = 1;
                resultkkk[0]--;
            }
        } else if (value >= 10) {
            int digit = precision - 2;
            value = Double.parseDouble(formatDouble(value, digit, true));
        } else if (value >= 1) {
            int digit = precision - 1;
            value = Double.parseDouble(formatDouble(value, digit, true));
        } else {
            int digit = precision - 1;
            value = Double.parseDouble(formatDouble(value, digit, true));
        }
        return value;
    }

    /**
     * 格式化浮点数
     * @param digit 位数，1~20
     * @param isDot true表示固定保留digit位小数,false表示为digit位有效数字
     */
    private static String formatDouble(double d, int digit, boolean isDot) {
        boolean isNeg = d < 0;
        d = d < 0 ? -d : d;
        String fstr = "" + d;
        int intcount = fstr.indexOf(".");
        if (intcount == -1)
            intcount = fstr.length();
        if (isDot) {   //表示digit为小数点后面固定几位小数
            fstr = digit > 0 ? JJJ.substring(0, intcount) + "." + ZZZ.substring(0, digit) :
                    JJJ.substring(0, intcount);
        } else {    ///表示总共有效数字位数
            fstr = digit - intcount > 0 ? JJJ.substring(0, intcount) + "." + ZZZ.substring(0, digit - intcount) :
                    JJJ.substring(0, intcount);
        }
        DecimalFormat dFormat = new DecimalFormat(fstr);
        fstr = dFormat.format(d);
        if (d < 1)
            fstr = "0" + fstr;
        if (isNeg)
            fstr = "-" + fstr;
        return fstr;
    }

    /**
     * 读取文件，返回文件内容
     * @return null表示读取失败
     */
    private static String readFile(String filePath) {
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String s;
            StringBuilder sBuilder = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sBuilder.append(s);
            }
            fr.close();
            return sBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件内容，返回字节数组
     */
    public static byte[] readFileBytes(String filePath) {
        String string = readFile(filePath);
        if (string == null) {
            return null;
        }
        return string.getBytes();
    }

    /**
     * 写入文件
     * @return 0成功1失败
     */
    public static int writeFile(String filePath, String content, boolean isAppend) {
        try {
            File f = new File(filePath);
            FileWriter fw = new FileWriter(f, isAppend);
            fw.write(content);
            fw.close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 写入文件
     * @return 0成功1失败
     */
    public static int writeFile(String filePath, byte[] content, boolean isAppend) {
        try {
            File f = new File(filePath);
            FileWriter fw = new FileWriter(f, isAppend);
            String str = new String(content);
            fw.write(str);
            fw.close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 得到两个时间的差值，d2-d1
     * @param returnType 返回类型，如DAY
     */
    private static int isEqual(Date d1, Date d2, int returnType) {
        int r = 0;
        switch (returnType) {
            case YEAR:
                r = getTime(d2, YEAR) - getTime(d1, YEAR);
                break;
            case MONTH:
                r = (getTime(d2, YEAR) - getTime(d1, YEAR)) * 12 + getTime(d2, MONTH) - getTime(d1, MONTH);
                break;
            case DAY:
                r = (int) ((d2.getTime() - d1.getTime()) / (3600 * 24 * 1000));
                break;
            case HOUR:
                r = (int) ((d2.getTime() - d1.getTime()) / (3600 * 1000));
                break;
            case MINUTE:
                r = (int) ((d2.getTime() - d1.getTime()) / (60 * 1000));
                break;
            case SECOND:
                r = (int) ((d2.getTime() - d1.getTime()) / (1000));
                break;
            case MS:
                r = (int) ((d2.getTime() - d1.getTime()));
                break;
            case WEEK:
                r = (int) ((d2.getTime() - d1.getTime()) / (3600 * 24 * 1000 * 7));
                break;
            default:
                break;
        }
        return r;
    }

    /**
     * 比较两个日期(默认返回天数)，d2-d1
     */
    static int isEqual(Date d1, Date d2) {
        return isEqual(clearDate(d1), clearDate(d2), DAY);
    }

    /**
     * 判断两个浮点数是否相等，d1>d2返回1,d1=d2返回0,否则返回-1
     */
    private static int isEqual(double d1, double d2) {
        if (Math.abs(d1 - d2) < 0.000001) {
            return 0;
        } else {
            return d1 > d2 ? 1 : -1;
        }
    }

    /**
     * 将日期的小时/分钟/秒/毫秒清空，年月日保持不变
     */
    private static Date clearDate(Date dt) {
        Calendar c1 = Calendar.getInstance();
        c1.clear();
        c1.set(getTime(dt, YEAR), getTime(dt, MONTH), getTime(dt, DAY));
        return c1.getTime();
    }

    /**
     * 为某个数组赋初值
     */
    public static <T> void initArray(T[] array, T v) {
        for (int i = 0; i < array.length; i++) {
            array[i] = v;
        }
    }

    /**
     * 为某个数组赋初值
     */
    public static void initArray(boolean[] array, boolean v) {
        for (int i = 0; i < array.length; i++) {
            array[i] = v;
        }
    }

    /**
     * 切换到某个Activity
     *
     * @param thisclass      表示当前activity，比如 MainActivity.this
     * @param destActivity   表示目标activity，
     * @param extraData      表示传递的参数
     * @param isSerialObject extraData是否为可序列化对象，是则必须继承自Serializable，否则传递为字符串
     * @param isForResult    是否等待弹出activity结果，
     *                       <br>=0则表示等待对话框返回结果(startActivityForResult,重写onActivityResult接收)；
     *                       <br>=1将弹出新activity时关闭当前activity(startActivity)；
     *                       <br>=2表示是弹出对话框返回主窗口(setResult)
     */
    public static void changeActivity(Activity thisclass, Class<?> destActivity, Object extraData, boolean isSerialObject, int isForResult) {
        Intent it = new Intent();
        if (isSerialObject) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ACTIVITY_ARG_PARAM_NAME, (Serializable) extraData);
            it.putExtras(bundle);
        } else {
            it.putExtra(ACTIVITY_ARG_PARAM_NAME, extraData.toString());
        }

        if (isForResult == 0) {
            it.setClass(thisclass, destActivity);
            thisclass.startActivityForResult(it, 1);
        } else if (isForResult == 1) {
            it.setClass(thisclass, destActivity);
            thisclass.startActivity(it);
            thisclass.finish();
        } else {
            thisclass.setResult(1, it);
            thisclass.finish();
        }
    }

    /**
     * 获取切换Activity时传递的数据，为可序列话对象
     */
    public static Object changeActivityObject(Activity thisclass) {
        Intent intent = thisclass.getIntent();
        return intent.getSerializableExtra(ACTIVITY_ARG_PARAM_NAME);
    }

    /**
     * 获取错误的信息文本
     */
    public static String getErrorString(Exception e) {
        String string = "错误：" + e.getMessage() + "\n";
        string += "错误位置：" + "\n";
        StackTraceElement[] s = e.getStackTrace();
        for (int i = 0; i < s.length; i++) {
            string += "(" + i + ") 文件:" + s[i].getFileName();
            string += "\n    类名:" + s[i].getClassName();
            string += "\n    方法名:" + s[i].getMethodName();
            string += "\n    行号:" + s[i].getLineNumber();
            string += "\n\n";
        }
        return string;
    }

    /**
     * 获取窗口大小
     * @param a 当前的界面
     * @return 界面尺寸，像素
     */
    private static Point getWindowSize(Activity a) {
        WindowManager wm = a.getWindowManager();
        Display dp = wm.getDefaultDisplay();
        dp.getSize(ScreenSize);
        return ScreenSize;
    }

    /**
     * 获取窗口大小
     * @param returnType 0状态栏高度,1标题栏高度,2窗口大小
     */
    public static Point getWindowSize(Activity a, int returnType) {
        if (returnType == 0) {    //状态栏高度
            Rect rt = new Rect();
            a.getWindow().getDecorView().getWindowVisibleDisplayFrame(rt);
            return new Point(rt.top, 0);
        } else if (returnType == 1) {  //标题栏高度
            int contentTop = a.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  //程序不包括标题栏的部分
            Rect rt = new Rect();
            a.getWindow().getDecorView().getWindowVisibleDisplayFrame(rt);
            return new Point(contentTop - rt.top, 0);
        } else { //窗口大小
            return getWindowSize(a);
        }
    }

    /**
     * 获取文件的信息
     *
     * @param file  文件
     * @param field 返回类型：
     *              <br> 0	返回全文件名，=文件夹+文件名+后缀名，如/sdcard/images/heart2.jpg
     *              <br> 1	返回路径名，=文件夹名，如/sdcard/images
     *              <br> 2	返回文件名，=文件名，如heart2
     *              <br> 3	返回后缀名，=后缀名，如.jpg
     *              <br> 4	返回文件名，=文件名+后缀名，heart2.jpg
     */
    public static String getFileExtern(File file, int field) {
        if (field == 0) {
            return file.getAbsolutePath();
        } else if (field == 1) {
            return file.getParent();
        } else if (field == 2) {
            String filename = file.getName();
            int dot = filename.lastIndexOf(".");
            if (dot >= 0) {
                filename = filename.substring(0, dot);
            }
            return filename;
        } else if (field == 3) {
            if (file.isFile()) {
                int dot = file.getName().lastIndexOf(".");
                if (dot >= 0) {
                    return file.getName().substring(dot);
                }
            }
        } else if (field == 4) {
            return file.getName();
        }
        return "";
    }

    /**
     * 获取文件信息
     * @return int[3]的数组，int[0]表示文件/文件夹大小(bytes)；int[1]表示文件夹下子文件夹个数；int[2]表示文件夹下子文件个数
     */
    public static int[] getFileInfo(File f) {
        int[] result = new int[]{0, 0, 0};
        if (f.isFile()) {
            result[0] = (int) f.length();
        } else {
            File[] fsFiles = f.listFiles();
            if (fsFiles != null && fsFiles.length > 0) {
                for (File file : fsFiles) {
                    if (file.isDirectory()) {
                        result[1]++;
                    } else {
                        //文件数量筛选
                        String suffixStr = StringUtil.getsuffix(file.getAbsolutePath());
                        switch (suffixStr){
                            case "jpg":
                            case "jpeg":
                            case "png":
                            case "pdf":
                            case "ppt":
                            case "mp3":
                            case "mp4":
                            case "avi":
                            case "bmp":
                            case "mov":
                            case "rmvb":
                            case "apk":
                            case "txt":
                            case "xls":
                            case "xlsx":
                            case "doc":
                            case "docx":
                                result[2]++;
                                break;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取该目录下的唯一文件名或文件夹名，最后生成如"XD211_1.data"格式的File
     * @param rootDir   当前根目录
     * @param preString 前缀，如"XD211"
     * @param extString 后缀，如".data"
     * @param isFile    是文件或是文件夹
     */
    public static File getNoRepeatPath(File rootDir, String preString, String extString, boolean isFile) {
        String pathString = getFileExtern(rootDir, 0);
        pathString += "/" + preString;
        int index = 0;
        File file = new File(pathString + extString);
        if ((isFile && file.isFile()) || (!isFile && file.isDirectory())) {
            index++;
            file = new File(pathString + "_" + index + extString);
        }
        return file;
    }

    /**
     * 删除文件或文件夹
     * @return 0成功，1失败
     */
    public static int fileDelete(File root) {
        if (!root.exists())
            return 0;
        if (root.isFile()) {
            if (root.delete()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            File[] subfiles = root.listFiles();   //先删除子文件
            if (subfiles != null) {
                for (File subfile : subfiles) {
                    fileDelete(subfile);
                }
            }
            if (root.delete()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 复制文件或文件夹
     * @param sourceFile        需要复制的文件或文件夹，如"/mnt/sdcard/A/"
     * @param destDir           目标文件夹，如"/mnt/sdcard/A/"
     * @param isCopyOrCut       true复制，false剪切
     * @param isDestContainName 调用时恒为false
     * @return 0成功，1失败
     */
    public static int fileCopy(String sourceFile, String destDir, boolean isCopyOrCut, boolean isDestContainName) {
        //要复制的文件目录
        File[] currentFiles = null;
        File source = new File(sourceFile);

        if (!destDir.endsWith("/"))
            destDir += "/";

        if (!source.exists())   //如同判断SD卡是否存在或者文件是否存在
            return 1;
        if (source.isFile()) {  //如果存在则获取当前目录下的全部文件 填充数组
            currentFiles = new File[]{source};
        } else if (source.isDirectory()) {
            currentFiles = source.listFiles();
            if (!isDestContainName) {    //如果是第一次调用，则添加源文件夹名，否则不添加
                destDir += source.getName() + "/";
            }
        }

        File targetDir = new File(destDir);   //目标目录
        if (!targetDir.exists())  //创建目录
            targetDir.mkdirs();
        //遍历要复制该目录下的全部文件
        if (currentFiles != null) {
            for (File currentFile : currentFiles) {
                if (currentFile.isDirectory())    //如果当前项为子目录 进行递归
                {
                    fileCopy(currentFile.getPath() + "/", destDir + currentFile.getName() + "/", isCopyOrCut, true);
                    if (!isCopyOrCut) {
                        fileDelete(currentFile);
                    }
                } else if (currentFile.isFile())    //如果当前项为文件则进行文件拷贝
                {
                    copySdcardFile(currentFile.getPath(), destDir + currentFile.getName(), isCopyOrCut);
                }
            }
        }
        if (!isCopyOrCut) {
            fileDelete(source);
        }
        return 0;
    }

    /**
     * 复制单个文件
     * @return 0成功，1失败
     */
    private static int copySdcardFile(String fromFile, String toFile, boolean isCopyOrCut) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(fromFile);
            fo = new FileOutputStream(toFile);
            in = fi.getChannel(); //得到对应的文件通道
            out = fo.getChannel(); //得到对应的文件通道
            in.transferTo(0, in.size(), out); //连接两个通道，并且从in通道读取，然后写入out通道

            if (!isCopyOrCut) {
                fileDelete(new File(fromFile));
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fo != null) {
                    fo.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float screenDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * screenDensity + 0.5f);
    }

    /**
     * 获取控件的大小，在初始化界面时使用getWidth（）会获取失败，这时可以调用该函数
     */
    public static void getControlSize(final View v, final int[] ctrlsize) {
        if (ctrlsize == null || ctrlsize.length != 2) {
            return;
        }
        v.post(new Runnable() {

            @Override
            public void run() {
                ctrlsize[0] = v.getWidth();
                ctrlsize[1] = v.getHeight();
            }
        });
    }

    /**
     * 设置某个控件获取焦点
     */
    public static void setViewFocus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
    }

    /**
     * 对控件截图
     * @param v 需要进行截图的控件
     * @return 该控件截图的Bitmap对象。
     */
    public static Bitmap printScreen(View v) {
        v.setDrawingCacheEnabled(false);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * 对控件截图
     * @param v       需要进行截图的控件
     * @param quality 截图质量，0-100
     */
    public static byte[] printScreen(View v, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap bitmap = v.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    /**
     * 设置控件的图片按下时的滤镜
     */
    public static void setControlPressStatus(View inView) {
        inView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView ivImageView = (ImageView) v;
                Drawable drawable = ivImageView.getDrawable();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable.setColorFilter(new ColorMatrixColorFilter(VIEW_FILTER_SELECTED));
                    ivImageView.setImageDrawable(drawable);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    drawable.setColorFilter(new ColorMatrixColorFilter(VIEW_FILTER_NORMAL));
                    ivImageView.setImageDrawable(drawable);
                }
                return false;
            }
        });
    }

    /**
     * 弹出信息文本对话框，包括了一个确定按钮
     * @param c 当前的activity
     * @param s 显示内容
     */
    public static void alert(Context c, Object s) {
        new AlertDialog.Builder(c)
                .setTitle("提示")
                .setMessage(s.toString())
                .setPositiveButton("确定", null)
                .show();
    }

    /**
     * 弹出信息文本对话框，包括了一个确定和取消按钮
     * @param c        当前的avtivity
     * @param s        显示内容
     * @param btns     按钮文本，如"是/否",最多两个按钮
     * @param callback 按钮回调函数，arg=0表示点击了第一个按钮;arg=1表示点击了第2个按钮
     */
    public static void alertButtons(Context c, Object s, String[] btns, final IFxListener callback) {
        new AlertDialog.Builder(c)
                .setTitle("提示")
                .setMessage(s.toString())
                .setPositiveButton(btns[0], new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.OnTrigger(dialog, 0);
                    }
                })
                .setNegativeButton(btns[1], new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.OnTrigger(dialog, 1);
                    }

                })
                .show();
    }

    /**
     * 弹出编辑控件的对话框，包括了一个编辑框和确定/取消按钮
     * @param c            当前的avtivity
     * @param defaultValue 默认显示内容
     * @param valueType    数据类型，0字符串，1整数，2浮点数
     * @param callback     回调函数
     */
    public static void alertTextBox(Context c, Object defaultValue, final int valueType, final IFxListener callback) {
        alertTextBox(c, defaultValue, valueType, Double.NaN, Double.NaN, callback);
    }

    /**
     * 弹出编辑控件的对话框，包括了一个编辑框和确定/取消按钮
     * @param c            当前的avtivity
     * @param defaultValue 默认显示内容
     * @param valueType    数据类型，0字符串，1整数，2浮点数
     * @param valueMin     数据的范围，Double.NaN表示范围无效
     * @param valueMax     数据的范围，Double.NaN表示范围无效
     * @param callback     回调函数
     */
    private static void alertTextBox(final Context c, Object defaultValue, final int valueType, final double valueMin, final double valueMax, final IFxListener callback) {
        final EditText eText = new EditText(c);
        eText.setText(defaultValue != null ? defaultValue.toString() : "");
        eText.setBackgroundResource(R.drawable.bg_edittext);
        eText.setPadding(3, 3, 3, 3);
        eText.setTextSize(dip2px(c, 12));

        new AlertDialog.Builder(c)
                .setTitle("请输入")
                .setView(eText)
                .setPositiveButton("确定", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String string = eText.getText().toString();
                        if (valueType == 0) {   //字符串
                            callback.OnTrigger(dialog, string);
                        } else if (valueType == 1) {  //整数
                            try {
                                int i = Integer.parseInt(string);
                                if (!Double.isNaN(valueMin) && i < valueMin) {
                                    throw new Exception();
                                } else if (!Double.isNaN(valueMax) && i > valueMax) {
                                    throw new Exception();
                                }
                                callback.OnTrigger(dialog, i);
                            } catch (Exception e) {
                                String ws = "输入为整数";
                                if (!Double.isNaN(valueMin) && !Double.isNaN(valueMax)) {
                                    ws += "并且在" + valueMin + "到" + valueMax + "之间";
                                } else if (!Double.isNaN(valueMin)) {
                                    ws += "并且大于等于" + valueMin;
                                } else if (!Double.isNaN(valueMax)) {
                                    ws += "并且小于等于" + valueMax;
                                }
                                toast(c, "转换错误！" + ws);
                            }
                        } else if (valueType == 2) {  //浮点数
                            try {
                                float i = Float.parseFloat(string);
                                if (!Double.isNaN(valueMin) && i < valueMin) {
                                    throw new Exception();
                                } else if (!Double.isNaN(valueMax) && i > valueMax) {
                                    throw new Exception();
                                }
                                callback.OnTrigger(dialog, i);
                            } catch (Exception e) {
                                String ws = "输入为浮点数";
                                if (!Double.isNaN(valueMin) && !Double.isNaN(valueMax)) {
                                    ws += "并且在" + valueMin + "到" + valueMax + "之间";
                                } else if (!Double.isNaN(valueMin)) {
                                    ws += "并且大于等于" + valueMin;
                                } else if (!Double.isNaN(valueMax)) {
                                    ws += "并且小于等于" + valueMax;
                                }
                                toast(c, "转换错误！" + ws);
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 弹出一个列表对话框，包括了一个点击列表项和一个取消按钮
     * @param c        当前的avtivity
     * @param title    标题
     * @param items    列表
     * @param callback 点击列表的回调事件
     */
    public static void alertItems(Context c, String title, String[] items, OnClickListener callback) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setItems(items, callback)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 弹出一个单选列表对话框，包括了一个单选列表项和一个取消按钮
     * @param c         当前的avtivity
     * @param title     标题
     * @param items     列表
     * @param checkItem 当前选择的项序号
     * @param callback  点击列表的回调事件
     */
    public static void alertSingleChoice(Context c, String title, String[] items, int checkItem, OnClickListener callback) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setSingleChoiceItems(items, checkItem, callback)
                .setNegativeButton("关闭", null)
                .show();
    }

    /**
     * 弹出一个多选列表对话框，包括了一个多选列表项和确定和取消按钮
     *
     * @param c         当前的avtivity
     * @param title     标题
     * @param items     列表
     * @param checkItem 当前选择的项，必须和items长度一致
     * @param callback  点击确定后的回调事件
     */
    public static void alertMultiChoice(Context c, String title, String[] items, final boolean[] checkItem, OnClickListener callback) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMultiChoiceItems(items, checkItem, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkItem[which] = isChecked;
                    }
                })
                .setPositiveButton("确定", callback)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 弹出日期选择对话框
     * @param c        当前的avtivity
     * @param curdate  当前选择的日期
     * @param callback 选择回调函数
     */
    public static void alertDatePicker(Context c, Date curdate, final IFxListener callback) {
        if (curdate == null)
            curdate = new Date();

        final TextView tView = new TextView(c);
        final FxLunar fl = new FxLunar();
        fl.setSolarDate(curdate);
        tView.setPadding(15, 5, 5, 5);
        String time = "农历:" + fl.toString("CCCC年CM月CD 星期WK");
        tView.setText(time);

        final DatePicker dpDatePicker = new DatePicker(c);
        dpDatePicker.init(getTime(curdate, YEAR), getTime(curdate, MONTH) - 1, getTime(curdate, DAY), new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fl.setSolarDate(year, monthOfYear + 1, dayOfMonth);
                String time = "农历:" + fl.toString("CCCC年CM月CD 星期WK");
                tView.setText(time);
            }
        });
        dpDatePicker.setCalendarViewShown(false);

        final FxDialog dlg = new FxDialog(c);
        dlg.SetTitle("设置日期")
                .setLayout(-1, -1, dip2px(c, 300), dip2px(c, 300), 0.9f)
                .setButton(new String[]{"未设置", "今日", "取消", "确定"}, new OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which == 0) {  //未设置
                            callback.OnTrigger(dpDatePicker, errorDate());
                            dlg.close();
                        } else if (which == 1) {  //今日
                            dpDatePicker.init(getTime(YEAR), getTime(MONTH) - 1, getTime(DAY), null);
                        } else if (which == 2) {  //取消
                            dlg.close();
                        } else if (which == 3) {  //确定
                            callback.OnTrigger(dpDatePicker, createDate(dpDatePicker.getYear(), dpDatePicker.getMonth() + 1, dpDatePicker.getDayOfMonth()));
                            dlg.close();
                        }
                    }
                })
                .setView(tView)
                .setView(dpDatePicker)
                .show();
    }

    /**
     * 弹出时间选择对话框
     */
    public static void alertTimePicker(Context c, final Date curdate, final IFxListener callback) {
        final TimePicker dpDatePicker = new TimePicker(c);
        dpDatePicker.setIs24HourView(true);
        dpDatePicker.setCurrentHour(getTime(curdate, HOUR));
        dpDatePicker.setCurrentMinute(getTime(curdate, MINUTE));

        final FxDialog dlg = new FxDialog(c);
        dlg.SetTitle("设置时间")
                .setLayout(-1, -1, 300, 300, 1)
                .setButton(new String[]{"未设置", "当前", "取消", "确定"}, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (which == 0) {  //未设置
                            callback.OnTrigger(dpDatePicker, errorDate());
                            dlg.close();
                        } else if (which == 1) {  //今日
                            dpDatePicker.setCurrentHour(getTime(HOUR));
                            dpDatePicker.setCurrentMinute(getTime(MINUTE));
                        } else if (which == 2) {  //取消
                            dlg.close();
                        } else if (which == 3) {  //确定
                            callback.OnTrigger(dpDatePicker, createDate(curdate, dpDatePicker.getCurrentHour(), dpDatePicker.getCurrentMinute(), 0));
                            dlg.close();
                        }
                    }
                })
                .setView(dpDatePicker)
                .show();
    }

    /**
     * 弹出一个自定义界面对话框，包括了一个自定义界面和确定和取消按钮
     * @param c        当前的avtivity
     * @param title    标题
     * @param v        可以是个控件，也可以是通过LayoutInflater.from加载的布局文件
     * @param callback 点击确定后的回调事件
     */
    private static void alertView(Context c, String title, View v, OnClickListener callback) {
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setView(v)
                .setPositiveButton("确定", callback)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 在界面上显示提示信息
     * @param c   当前的avtivity
     * @param obj 显示内容
     */
    public static void toast(Context c, Object obj) {
        Toast.makeText(c, obj.toString(), Toast.LENGTH_LONG).show();
    }

    /**
     * 在状态栏上显示信息
     * @param c           当前的activity
     * @param dstActivity 当点击了状态栏信息时进入的界面
     * @param iconId      显示的图标
     * @param title       下拉后显示的标题
     * @param msg         下拉后显示的信息
     * @param tickerText  在状态栏上显示的内容
     */
//    public static void notify(Context c, Class<?> dstActivity, int iconId, String title, String msg, String tickerText) {
//        Intent it = new Intent(c, dstActivity);
//        PendingIntent pi = PendingIntent.getActivity(c, 0, it, 0);
//
//        Notification nn = new Notification();
//        nn.icon = iconId;
//        nn.tickerText = tickerText;
//        nn.when = System.currentTimeMillis();  //发送时间
//        nn.defaults = Notification.DEFAULT_ALL;  //使用默认铃声、默认振动、默认闪光灯
//        nn.setLatestEventInfo(c, title, msg, pi);
//        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notify(NOTIFY_ID, nn);
//    }

    /**
     * 隐藏取消状态栏显示
     * @param c 当前的avtivity
     */
    public static void hideNotify(Context c) {
        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFY_ID);
    }

    /**
     * 获取当前时间信息
     */
    private static int getTime(int returnType) {
        return getTime(new Date(), returnType);
    }

    /**
     * 获取时间信息
     */
    private static int getTime(Date d, int returnType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int r = -1;
        switch (returnType) {
            case YEAR:
                r = calendar.get(Calendar.YEAR);
                break;
            case MONTH:
                r = calendar.get(Calendar.MONTH) + 1;
                break;
            case DAY:
                r = calendar.get(Calendar.DAY_OF_MONTH);
                break;
            case HOUR:
                r = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case MINUTE:
                r = calendar.get(Calendar.MINUTE);
                break;
            case SECOND:
                r = calendar.get(Calendar.SECOND);
                break;
            case MS:
                r = calendar.get(Calendar.MILLISECOND);
                break;
            case WEEK:
                r = calendar.get(Calendar.DAY_OF_WEEK);
                break;
            default:
                break;
        }
        return r;
    }

    /**
     * 在日期前面增加一段时间后的日期
     *
     * @param offset  添加的时间
     * @param setType 添加的类型，如YEAR表示+1年
     * @return 新时间
     */
    static Date addTime(Date date, int offset, int setType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (setType) {
            case YEAR:
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + offset);
                break;
            case MONTH:
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + offset);
                break;
            case DAY:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + offset);
                break;
            case HOUR:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + offset);
                break;
            case MINUTE:
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + offset);
                break;
            case SECOND:
                calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + offset);
                break;
            case MS:
                calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + offset);
                break;
            case WEEK:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + offset * 7);
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

    /**
     * 新建时间
     */
    private static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0, 0);
    }

    /**
     * 新建时间
     */
    private static Date createDate(Date curDate, int hour, int minute, int second) {
        return createDate(getTime(curDate, YEAR), getTime(curDate, MONTH), getTime(curDate, DAY), hour, minute, second);
    }

    /**
     * 新建时间
     */
    private static Date createDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }

    /**
     * 返回错误的日期
     */
    private static Date errorDate() {
        return new Date(0);
    }

    /**
     * 判断该日期是否是错误的日期格式
     */
    public static boolean errorDate(Date date) {
        return clearDate(date).getTime() == clearDate(errorDate()).getTime();
    }

    /**
     * 获取圆角矩形路径
     */
    public static Path getRoundRectPath(Rect rt, float ox, float oy) {
        Path path = new Path();
        path.addRoundRect(new RectF(rt), ox, oy, Path.Direction.CW);
        return path;
    }

    /**
     * 绘制单条直线
     */
    public static void drawLine(int x0, int y0, int x1, int y1, Canvas canvas, Paint paint) {
        canvas.drawLine(x0 + 0.5f, y0 + 0.5f, x1 + 0.5f, y1 + 0.5f, paint);
    }

    /**
     * 实现居中绘制字符串
     * @param targetRect 目标区域
     */
    public static void drawString(String text, Rect targetRect, Canvas canvas, Paint paint) {
        DrawString(text, targetRect, canvas, paint, 1);
    }

    /**
     * 实现垂直居中绘制字符串
     * @param targetRect 目标区域
     * @param align      0左，1中，2右
     */
    public static void DrawString(String text, Rect targetRect, Canvas canvas, Paint paint, int align) {
        drawString(text, targetRect, canvas, paint, align, 0);
    }

    /**
     * 实现垂直居中绘制字符串
     * @param targetRect 目标区域
     * @param align      0左，1中，2右
     * @param padding    表示和矩形区域的间隔
     */
    private static void drawString(String text, Rect targetRect, Canvas canvas, Paint paint, int align, float padding) {
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

        Paint.Align aa = align == 0 ? Align.LEFT : (align == 1 ? Align.CENTER : Align.RIGHT);
        float xx = align == 0 ? targetRect.left + padding : (align == 1 ? targetRect.centerX() : targetRect.right - padding);
        paint.setTextAlign(aa);
        canvas.drawText(text, xx, baseline, paint);
    }

    public static void drawBitmap(Canvas canvas, Paint paint, Bitmap bmp, Rect rt, int layout) {
        int w = bmp.getWidth(), h = bmp.getHeight();
        int w2 = rt.width(), h2 = rt.height();
        if (layout == LAY_STRECH) {   //拉伸方式
            Rect srcrt = new Rect(0, 0, w, h);
            canvas.drawBitmap(bmp, srcrt, rt, paint);
        } else if(layout == LAY_TILE){   //平铺方式
			Rect srcrt = new Rect(0,  0, w > w2 ? w2 : w, h > h2 ? h2 : h);
			canvas.drawBitmap(bmp, srcrt, rt, paint);
		} else if(layout == LAY_FIT){   //适应方式
			float wxs = (float)w / w2, hxs = (float)h / h2;

			Rect srcrt = new Rect(0,  0, w > w2 ? w2 : w, h > h2 ? h2 : h);
			canvas.drawBitmap(bmp, srcrt, rt, paint);
		}
    }

    /**
     * 初始化一个矩形
     */
    public static Rect createRect(int left, int top, int width, int height) {
        return new Rect(left, top, left + width, top + height);
    }

}
