package com.itc.suppaperless.pdfmodule.configure;

/**
 * Create by zhengwp on 19-2-18.
 */
public class PdfConfigure {
    //The number for the protocol in channel.
    public static int channelNum = 72;
    //The frequency when handle data in document speaking.
    public static int sendFrequency = 20;
    //Whether send the doc-speaking data to all user.
    public static int isSpeakAllSend = 1;
    //Force user into the doc-speaken status. If user didnt hold the dco-source, we should help to down it
    // and then open activity when downloading to be finished.
    public static boolean forceTrackSpeak = false;
    //This configure which means backing to last document if executed after closing speaking.
    public static boolean backToLastDoc = false;
    /** Night mode of pdf view.*/
    public static boolean nightMode = false;

    //////////////  LoadMode   //////////////
    public final static int pdfmode = 11;
    public final static int imagemode = 12;

    //////////////  RequestCode   （序号：13 - 29）//////////////
    public final static int switchFile = 13;


    //optCode
    public static class OptCode {
        public final static int CloseSpeaker = 2000;
        //We can define that 'changing file' equals to 'openning a new file'.
        public final static int OpenFile = 2001;
        //public final static int ChangeFile = 2002;
        public final static int Zoom = 2003;
        //Remainning the 2004 operation code for zoom-out.
        public final static int Zoom_out = 2004;
        public final static int Scroll = 2005;
        public final static int TurnPage = 2006;

    }

    //The configure about zoom-zoomout when double click.
    public static class ZoomScale {
        public final static float minScale = 1f;
        public final static float midScale = 1.75f;
        public final static float maxScale = 3f;
    }

    public static final int initpage = 0;
    public static final float initscale = 1.0f;


    public static final String optCodeStr = "optCode";
    public static final String fid = "fid";
    public static final String fileId = "fileId";
    public static final String filename = "filename";
    public static final String filesystemname = "filesystemname";
    public static final String filedownpath = "filedownpath";
    public static final String isAgenda = "isAgenda";
    public static final String speakbean = "speakbean";
    public static final String loadtype = "loadtype";

}
