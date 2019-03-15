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


#include <jni.h>
#include <string>
#include <android/native_window_jni.h>

#include "XLog.h"
#include "IPlayerPorxy.h"
extern "C"
JNIEXPORT
jint JNI_OnLoad(JavaVM *vm,void *res)
{
    IPlayerPorxy::Get()->Init(vm);

    /*IPlayerPorxy::Get()->Open("/sdcard/v1080.mp4");
    IPlayerPorxy::Get()->Start();


    IPlayerPorxy::Get()->Open("/sdcard/1080.mp4");
    IPlayerPorxy::Get()->Start();*/

    return JNI_VERSION_1_4;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_itc_suppaperless_player_XPlay_InitView(JNIEnv *env, jobject instance, jobject surface) {

    // TODO
    ANativeWindow *win = ANativeWindow_fromSurface(env,surface);
    IPlayerPorxy::Get()->InitView(win);
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_PlayPos(JNIEnv *env, jobject instance) {
    // TODO
    return IPlayerPorxy::Get()->PlayPos();
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_TotalDuration(JNIEnv *env, jobject instance) {
    // TODO
    return IPlayerPorxy::Get()->TotalDuration();
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_PosTime(JNIEnv *env, jobject instance) {
    // TODO
    return IPlayerPorxy::Get()->PosTime();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_Seek(JNIEnv *env, jobject instance, jdouble pos,jboolean isPlay) {

    IPlayerPorxy::Get()->Seek(pos,isPlay);

}
extern "C"
JNIEXPORT void JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_PlayOrPause(JNIEnv *env, jobject instance) {

    IPlayerPorxy::Get()->SetPause(!IPlayerPorxy::Get()->IsPause());

}
extern "C"
JNIEXPORT void JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_Open(JNIEnv *env, jobject instance, jstring url_) {
    const char *url = env->GetStringUTFChars(url_, 0);
    IPlayerPorxy::Get()->Open(url);
    IPlayerPorxy::Get()->Start();
    env->ReleaseStringUTFChars(url_, url);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_itc_suppaperless_player_FFmpegPlayActivity_SetPause(JNIEnv *env, jobject instance,jboolean isPause) {
    IPlayerPorxy::Get()->SetPause(isPause);
}