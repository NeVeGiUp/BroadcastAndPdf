package com.itc.suppaperless.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.itc.suppaperless.global.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by xiaogf on 19-1-14.
 */
public class FileUtil {

    // 把bitmao图片保存到对应的SD卡路径中
    public static void saveToSD(Bitmap bmp, String dirName, String fileName) throws IOException {
        // 判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(dirName);
            // 判断文件夹是否存在，不存在则创建
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(dirName + fileName);
            // 判断文件是否存在，不存在则创建
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                if (fos != null) {
                    // 第一参数是图片格式，第二个是图片质量，第三个是输出流
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    // 用完关闭
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //判断文件是否存在
    public static int fileIsExists(int iFileID) {

        String pathname = Config.downloadpath + iFileID;

        try {
            File f = new File(pathname);
            if (!f.exists()) {
                return fileIsExists(iFileID*100);
            }

        } catch (Exception e) {
            return fileIsExists(iFileID*100);
        }

        return iFileID;
    }


    /*
     *获取文件名
     */
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return null;
        }
    }

    /**
     * 取得./data/data/...下所有文件的名称
     *
     * @param fileName 比如     /images/
     * @return 文件名称的数组
     */
    public static String[] getStrokeFileNames(Context context, String fileName) {
        String strDir = getStrokeFilePath(context, fileName);
        Log.e("tag", "str:" + strDir);
        File file = new File(strDir);
        return file.list();
    }

    /**
     * 取得指定目录的路径
     *
     * @param context  （存储在应用中）
     * @param fileName ./data/data/...下的文件 比如     /images/
     * @return 路径
     */
    public static String getStrokeFilePath(Context context, String fileName) {
        String filePath = context.getFilesDir().getPath() + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filePath;
    }

    public static String[] getFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.list();
    }

    /**
     * 去掉文件名的后缀
     *
     * @param fileName fileName
     * @return 无后缀文件名
     */
    public static String delFileSuffix(String fileName) {
        if (!fileName.isEmpty() && fileName.length() > 1)
            return fileName.substring(0, fileName.lastIndexOf("."));
        else
            return "";
    }

    //将文件管理器返回的路径转化成真正的文件路径
    //@SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    //获取文件名字
    public String getdownFileName(String pathandname) {

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    /**
     * 这个路径被Android系统认定为应用程序的缓存路径，当程序被卸载的时候，这里的数据也会一起被清除掉
     *
     * @return 路径例如： /SD卡/Android/data/程序的包名/cache/uniqueName
     */
    public static String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath + File.separator + uniqueName;
    }


    /**
     * 创建文件夹
     */
    public static void makeFile(String pathandname) {
        File file1 = new File(pathandname);
        if (!file1.exists()) {
            file1.mkdir();
        }

        //aaa文件夹下创建.nomedia文件，防止相册缓存显示会议文件图片
        if(Config.downloadpath.equals(pathandname)&& file1.exists()){
            File nomedia = new File(Config.downloadpath + ".nomedia");
            if (!nomedia.exists()) {
                try {
                    nomedia.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 创建文件
     */
    public static void makeFiles(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 删除本地图片，成功后刷新数据库中的缩略图
     *
     * @param context  context
     * @param filePath 完整路径
     */
    public static void deleteLocalImage(Context context, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(new File(filePath)));
                context.sendBroadcast(scanIntent);
            }
        }
    }

    /**
     * 刷新数据库中保存的缩略图
     *
     * @param context  context
     * @param filePath filePath 完整路径
     */
    public void scanFileAsync(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    /*
    * 删除文件夹和文件
    * */
    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();

        }
    }


    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath, Context context) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
        deleteImageFile(newFile, context);
    }

    /*
    * 删除图片文件夹和文件
    * */
    public static void deleteImageFile(File file, final Context context) {
        if (file.isFile()) {
            Uri uri = getImageContentUri(context, file);
            ContentResolver mContentResolver = context.getContentResolver();
            String where = MediaStore.Images.Media.DATA + "='" + file.getPath() + "'";
            //删除
            mContentResolver.delete(uri, where, null);

            file.delete();//删除SD中图片
            //发送广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteImageFile(f, context);
            }
            file.delete();
        }

    }

    /**
     * 获取图片的uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /*
    * 判断文件是否为空
    * */
    public static boolean isFileNull(String str) {
        File file = new File(str);
        Boolean result;
        result = file.exists() && file.isDirectory() && file.list().length <= 0;
        return result;
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    public static String getFileExtension(String filePath) {
        if (StringUtil.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除制定文件列表上的文件
     */
    public static void deleteListFile(final List<String> list) {
        File downfile = new File(Config.downloadpath);
        File[] files = downfile.listFiles();
        //文件为空
        if (files == null) {
            return;
        }
        //遍历当前文件下的所有文件
        for (File file : files) {
            //如果是文件夹
            if (file.isDirectory()) {
                Log.i("kk", list.toString());
                if (!list.contains(file.getName())) {
                    deleteFile(file);
                    FileDaoUtil.deleteDownFile(Integer.parseInt(file.getName())); //删除保存的文件数据
                }
            }
        }
    }

}