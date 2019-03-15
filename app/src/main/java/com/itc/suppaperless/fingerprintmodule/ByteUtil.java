package com.itc.suppaperless.fingerprintmodule;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 * Created by pengs on 2016/11/2.
 */

public class ByteUtil {
//  e
    /**
     * 按照规则压缩对象   详细字节所代数见注释
     *
     * @param attr StyleObjAttr
     * @return 压缩后的byte
     */
//    public static byte[] getStringToBytes(Context context, StyleObjAttr attr) {
////        byte[] zerobyte = ;
//        Log.e("pds", "发送的数据:"+attr.toString());
//        String styleTag = attr.getStyleTag();
//        byte[] tagBytes = shortToByte(StringToInt(styleTag));//0-1 标志位   e:0  p:1  t:2  o:3  r:4   l:5
//        byte[] bytes;
//        bytes = mergeByte(tagBytes, shortToByte(attr.getFilePage()));//2-3： page 无页码则值为-1
//
//        bytes = mergeByte(bytes, intToByte(attr.getObjId()));//4-7 对象id
//        if (styleTag.equals("e")) {//橡皮擦不需要颜色和画笔大小，用0填充
//            bytes = mergeByte(bytes, new byte[]{0,0,0,0,0,0});//用0填充e没有的6个值
////            return bytes;
//        }else{
//            bytes = mergeByte(mergeByte(bytes, intToByte(attr.getPaintColor())), shortToByte(attr.getPaintSize()));//8-11 color 12-13 size
//        }
//        if (!styleTag.equals("e") && !styleTag.equals("p")) {//橡皮擦和自由画笔不需要初始坐标与结束坐标
//            bytes = mergeByte(mergeByte(bytes, shortToByte(attr.getStartX())), shortToByte(attr.getStartY()));//14-15  16-17  start坐标
//            bytes = mergeByte(mergeByte(bytes, shortToByte(attr.getEndX())), shortToByte(attr.getEndY()));//18-19 20-21 end坐标
//        }else{
//            bytes = mergeByte(bytes, new byte[]{0,0,0,0,0,0,0,0});//用0填充e没有的8个值
//        }
//        //   22 23  屏幕宽度    24  25  屏幕高度
//        short screenWidth = (short) ScreenUtil.getScreenWidth(context);
//        short screenHeight = (short) ScreenUtil.getScreenHeight(context);
//        bytes = mergeByte(mergeByte(bytes, shortToByte(screenWidth)), shortToByte(screenHeight));
//        switch (styleTag) {
//            case "e":
//                //26  27  删除的id的总数量
//                bytes = mergeByte(bytes, shortToByte(attr.getDelNumber()));
//                return bytes;
//            case "l":
//                return bytes;
//            case "o":
//            case "r":
//                byte[] fill = {(byte) (attr.isFill() ? 1 : 0)};
//                bytes = mergeByte(bytes, fill);
//                return bytes;
//            case "p":
//                List<StyleObjAttr.SavePointModel> penPoint = attr.getPenPoint();
//                int penPointSize = penPoint.size();
//                for (int i = 0; i < penPointSize; i++) {
//                    byte[] x = ByteUtil.shortToByte(penPoint.get(i).x);
//                    byte[] y = ByteUtil.shortToByte(penPoint.get(i).y);
//                    bytes = mergeByte(mergeByte(bytes, x), y);
//                }
//                return bytes;
//            case "t":
////                Log.e("pds", "t:" + Arrays.toString(bytes));
////                Log.e("pds", "getbytes:" + Arrays.toString(attr.getEditText().getBytes()));
//                bytes = mergeByte(bytes, attr.getEditText().getBytes());
//                return bytes;
//        }
//        return bytes;
//    }

//    public static StyleObjAttr reBytesToString(Context context, byte[] bytes) {
//        StyleObjAttr attr = new StyleObjAttr();
//        int length = bytes.length-1;
////        String tag = String.valueOf((char) bytes[0]);//tag标志
//        String tag = IntToString(byteToShort(new byte[]{bytes[0], bytes[1]}));//tag标志
//        attr.setStyleTag(tag);
//        short page = byteToShort(new byte[]{bytes[2], bytes[3]});
//        attr.setFilePage(page);
//        byte[] bytesId = {bytes[4], bytes[5], bytes[6], bytes[7]};
//        int id = byteToInt(bytesId);
//        Log.e("pds", length+"长度+bytesId::"+id);
//        attr.setObjId(id);
//        if (!tag.equals("e")) {
//            byte[] bytesColor = {bytes[8], bytes[9], bytes[10], bytes[11]};
//            int color = byteToInt(bytesColor);
//            attr.setPaintColor(color);
//            byte[] bytesSize = {bytes[12], bytes[13]};
//            int size = byteToShort(bytesSize);
//            attr.setPaintSize(size);
//        }
//        float widthF = byteToShort(new byte[]{bytes[22], bytes[23]});
//        float heightF = byteToShort(new byte[]{bytes[24], bytes[25]});
//        float widthC = ScreenUtil.getScreenWidth(context);
//        float heightC = ScreenUtil.getScreenHeight(context);
////        Log.e("pds", "widthF:"+widthF);
////        Log.e("pds", "widthC:"+widthC);
////        Log.e("pds", "heightF:"+heightF);
////        Log.e("pds", "heightC:"+heightC);
////        float widthC = 1920, widthF = 2048, heightC = 1080, heightF = 1536;
//        float scaleW = widthC / widthF;//1920   :   2048
//        float scaleH = heightC / heightF;//1080   :   1536
////        Log.e("pds", "scaleW:"+scaleW);
////        Log.e("pds", "scaleH:"+scaleH);
//        if(!tag.equals("e") && !tag.equals("p")){
//            attr.setStartX(byteToShort(new byte[]{bytes[14], bytes[15]}) * scaleW);
//            attr.setStartY(byteToShort(new byte[]{bytes[16], bytes[17]}) * scaleH);
//            attr.setEndX(byteToShort(new byte[]{bytes[18], bytes[19]}) * scaleW);
//            attr.setEndY(byteToShort(new byte[]{bytes[20], bytes[21]}) * scaleH);
//        }
//        switch (tag) {
//            case "e":
//                attr.setDelNumber(byteToShort(new byte[]{bytes[26], bytes[27]}));
//                break;
//            case "p":
//                List<StyleObjAttr.SavePointModel> pointList = new ArrayList<>();
//                int i = 26;
//                while (i <= length) {
//                    byte[] x = {bytes[i], bytes[i + 1]};
//                    byte[] y = {bytes[i + 2], bytes[i + 3]};
//                    float shortX = byteToShort(x) * scaleW;
////                    Log.e("pds", "shortX:"+shortX);
//                    float shortY = byteToShort(y) * scaleH;
////                    Log.e("pds", "shortY:"+shortY);
////                    float i3 = (shortX * i1);
////                    Log.e("pds", "i3:"+i3);
////                    float i4 = (shortY * i2);
////                    Log.e("pds", "i4:"+i4);
//                    pointList.add(new StyleObjAttr.SavePointModel((int) shortX, (int) shortY));
//                    i += 4;
//                }
//                attr.setPenPoint(pointList);
//                break;
//            case "t":
//                String text;
//                try {
//                    byte[] textBytes = new byte[length - 25];
//                    System.arraycopy(bytes, 26, textBytes, 0, length - 25);
//                    Log.e("pds", "字节:"+ Arrays.toString(textBytes));
//                    text = new String(textBytes, "UTF-8");
//                } catch (Exception e) {
//                    text = " ";
//                    e.printStackTrace();
//                }
////                Log.e("pds","text:::"+text);
//                attr.setEditText(text);
//                break;
////            case "l"://直线在
////                break;
//            case "o":
//            case "r":
////                Log.e("pds", ""+bytes[length]);
////                Log.e("pds", ""+(byte)0x1);
//                attr.setIsFill(bytes[length] == 1);
//                break;
//        }
//        return attr;
//    }

    /**
     * String转义成 int   e:0  p:1  t:2  o:3  r:4   l:5
     */
    private static int StringToInt(String str){
        switch (str){
            case "e":
                return 0;
            case "p":
                return 1;
            case "t":
                return 2;
            case "o":
                return 3;
            case "r":
                return 4;
            case "l":
                return 5;
            default:
                return 5;
        }
    }
    /**
     * String转义成 int   e:0  p:1  t:2  o:3  r:4   l:5
     */
    private static String IntToString(short str){
        switch (str){
            case 0:
                return "e";
            case 1:
                return "p";
            case 2:
                return "t";
            case 3:
                return "o";
            case 4:
                return "r";
            case 5:
                return "l";
            default:
                return "l";
        }
    }
    public static int checkDataType(byte nalu){
        String tString = Integer.toBinaryString((nalu & 0xFF) + 0x100).substring(1);
//        Log.e("pds", "binStr::"+tString);
//        String substring = tString.substring(3);
//        Log.e("pds", "substring::"+substring);
        int i = Integer.parseInt(tString.substring(3), 2);
//        Log.e("pds", "integer:::"+i);
        if((nalu & 31) == 5){
//            "I帧"
            return 5;
        }else if(i == 7){
            //            "SPS"
            return 7;
        }else if(i == 8){
//            return "PPS";
            return 8;
        }else if(i == 1){
//            return "P帧";
            return 1;
        }else{
//          不知道是啥
            return -1;
        }
    }
    /**
     * 合并byte数组
     *
     * @param bytes  bytes
     * @param bytes1 bytes1
     * @return 合并之后的数组
     */
    public static byte[] mergeByte(byte[] bytes, byte[] bytes1) {
        byte[] content = new byte[bytes.length + bytes1.length];
        System.arraycopy(bytes, 0, content, 0, bytes.length);
        System.arraycopy(bytes1, 0, content, bytes.length, bytes1.length);
        return content;
    }
    /**
     * 注释：int到字节数组的转换！
     *
     * @param number
     * @return
     */
    public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 小端byte转大端int
     * 注释：字节数组到int的转换！
     *
     * @param
     * @return
     */
    public static int byteToInt(byte[] b) {
        int s;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }


    /**
     * 大端short转小端byte
     * 注释：字节数组到int的转换！
     *
     * @param
     * @return
     */
    public static byte[] shortTobyte(short iValue) {
        byte[] result = new byte[2];
        //由高位到低位
        // 先写short的最后一个字节
        result[0] = (byte)(iValue & 0xFF);
        // short 倒数第二个字节
        result[1] = (byte)((iValue & 0xFF00) >> 8 );
        return result;
    }

    /**
     * 注释：short到字节数组的转换！
     *
     * @param temp temp
     * @return
     */
    public static byte[] shortToByte(int temp) {
//        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 注释：字节数组到short的转换！
     *
     * @param b b
     * @return
     */
    public static short byteToShort(byte[] b) {
        short s;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }
    public static String FormetFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / 1024) + "KB";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) file / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static byte[] byteUtf8ToGbk(byte[] bytes){
        byte[] voteDdata = null;
        try {
            voteDdata = new String(bytes).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return voteDdata;
    }
//    /**
//     * int整数转换为 4 字节的byte数组
//     *
//     * @param i 整数
//     * @return byte数组
//     */
//    public static byte[] intToByte4(int i) {
//        byte[] targets = new byte[4];
//        targets[3] = (byte) (i & 0xFF);
//        targets[2] = (byte) (i >> 8 & 0xFF);
//        targets[1] = (byte) (i >> 16 & 0xFF);
//        targets[0] = (byte) (i >> 24 & 0xFF);
//        return targets;
//    }
//
//    /**
//     * short整数转换为 2 字节的byte数组
//     *
//     * @param s short整数
//     * @return byte数组
//     */
//    public static byte[] shortToByte2(int s) {
//        byte[] targets = new byte[2];
//        targets[0] = (byte) (s >> 8 & 0xFF);
//        targets[1] = (byte) (s & 0xFF);
//        return targets;
//    }
//
//    /**
//     * byte数组转换为无符号short整数
//     *
//     * @param bytes byte数组
//     * @return short整数
//     */
//    public static short byte2ToShort(byte[] bytes) {
//        int high = bytes[0];
//        int low = bytes[1];
//        return (short) ((high << 8 & 0xFF00) | (low & 0xFF));
//    }
//
//    /**
//     * byte数组转换为int整数
//     *
//     * @param bytes byte数组
//     * @return int整数
//     */
//    public static int byte4ToInt(byte[] bytes) {
//        int b0 = bytes[0] & 0xFF;
//        int b1 = bytes[1] & 0xFF;
//        int b2 = bytes[2] & 0xFF;
//        int b3 = bytes[3] & 0xFF;
//        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
//    }

    /**
     * 转换byte数组为short
     */
    public static short Bytes2Short_LE(byte[] bytes){
        if(bytes.length < 2)
            return -1;
        short iRst = (short) ((bytes[0] & 0xFF) << 8);
        iRst |= (short)((bytes[1] & 0xFF));
        return iRst;
    }

    /**
     * 转换short为byte
     */
    public static byte[] ShortToByte_LE(short iValue){
        byte[] bytes1 = new byte[2];
        bytes1[0] =(byte)((iValue&0xFF00)>>8);
        bytes1[1] =  (byte)(iValue&0xFF);
        return bytes1;
    }
    /**
     * 大端int转小端byte
     * 注释：字节数组到int的转换！
     *
     * @param
     * @return
     */
    public static byte[] IntToBytes_LE(int iValue){
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte)(iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte)((iValue & 0xFF00) >> 8 );
        // int 倒数第三个字节
        rst[2] = (byte)((iValue & 0xFF0000) >> 16 );
        // int 第一个字节
        rst[3] = (byte)((iValue & 0xFF000000) >> 24 );
        return rst;
    }

    /**
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))){
            return null;
        }
        else if (hex.length()%2 != 0){
            return null;
        }
        else{
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
            return b;
        }

    }

    /*
    * 字节数组转16进制字符串
    */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }
    public static String bytes2HexStringtwo(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            if (i==0){
                r += hex.toUpperCase();
            }else {
                r += " "+hex.toUpperCase();
            }
        }

        return r;
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }

   /**
    * 异或校验和
    **/
   public static byte getXor(byte[] datas){

       byte temp=datas[0];

       for (int i = 1; i <datas.length; i++) {
           temp ^=datas[i];
       }

       return temp;
   }

    /**
     *校验和
     * 除去起始码之外的所有字节按照8bit 单位相加的结果(溢出部分将被无视)
     */
    public static byte checkSum(byte[] bytes) {
        int sum = 0;
        if (bytes != null) {
            for (int i = 1; i < bytes.length; i++) {
                sum += bytes[i];
            }

        }
        return (byte)(sum & 0xff);
    }
}



