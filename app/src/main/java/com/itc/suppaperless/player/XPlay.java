/*******************************************************************************
**                                                                            **
**                     Jiedi(China nanjing)Ltd.                               **
**	               创建：夏曹俊，此代码可用作为学习参考                       **
*******************************************************************************/

/*****************************FILE INFOMATION***********************************
**
** Project       : FFmpeg
** Description   : FFMPEG项目创建示例
** Contact       : xiacaojun@qq.com
**        博客   : http://blog.csdn.net/jiedichina
**		视频课程 : 网易云课堂	http://study.163.com/u/xiacaojun		
				   腾讯课堂		https://jiedi.ke.qq.com/				
				   csdn学院		http://edu.csdn.net/lecturer/lecturer_detail?lecturer_id=961	
**                 51cto学院	http://edu.51cto.com/lecturer/index/user_id-12016059.html	
** 				   下载最新的ffmpeg版本 ffmpeg.club
**                 
**   安卓流媒体播放器 课程群 ：23304930 加入群下载代码和交流
**   微信公众号  : jiedi2007
**		头条号	 : 夏曹俊
**
*******************************************************************************/
//！！！！！！！！！ 加群23304930下载代码和交流


package com.itc.suppaperless.player;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2018-03-04.
 */

public class XPlay extends GLSurfaceView implements SurfaceHolder.Callback,GLSurfaceView.Renderer,View.OnClickListener {
    private OnGLSurfaceViewLife onGLSurfaceViewLife;

    public XPlay(Context context, AttributeSet attrs) {
        super( context, attrs );
        //android 8.0 需要设置
        setRenderer( this );
        setOnClickListener(this);
    }

    public void setOnGLSurfaceViewLife(OnGLSurfaceViewLife onGLSurfaceViewLife) {
        this.onGLSurfaceViewLife = onGLSurfaceViewLife;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化opengl egl 显示
        InitView(holder.getSurface());

        //只有在绘制数据改变时才绘制view，可以防止GLSurfaceView帧重绘
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        Log.i("GLSurfaceView","surfaceCreated");

        onGLSurfaceViewLife.GLSurfaceCreated();
    }

    @Override
    public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
        Log.i("GLSurfaceView","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder var1) {
        Log.i("GLSurfaceView","surfaceDestroyed");

        onGLSurfaceViewLife.GLSurfaceDestroyed();
    }
    public native void InitView(Object surface);


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Log.i("GLSurfaceView","onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        Log.i("GLSurfaceView","onSurfaceChanged");
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    interface OnGLSurfaceViewLife{
        void GLSurfaceCreated();
        void GLSurfaceDestroyed();
    }
}
