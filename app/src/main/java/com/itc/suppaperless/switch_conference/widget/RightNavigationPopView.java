package com.itc.suppaperless.switch_conference.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.application.PaperlessApplication;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.asy.BitmapSaveToLocalTask;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.BitmapSaveResult;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.ui.WhiteBoardActivity;
import com.itc.suppaperless.pdfmodule.eventbean.ApplyDocSpeakerEvent;
import com.itc.suppaperless.pdfmodule.eventbean.RightBarTrackSpeakEvent;
import com.itc.suppaperless.screen_record.ScreenReceiveActivity;
import com.itc.suppaperless.screen_record.ScreenRecord;
import com.itc.suppaperless.utils.BitmapUtil;
import com.itc.suppaperless.utils.FileUtil;
import com.itc.suppaperless.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


import static com.itc.suppaperless.global.Config.REQUEST_MEDIA_PROJECTION;


/**
 * Created by cong on 19-1-17.
 */

public class RightNavigationPopView extends PopupWindow implements View.OnClickListener {
    private View mParentView;
    private ImageView ivSpeaker;
    private TextView tvSpeaker;
    private ImageView ivTrackSpeaker;
    private TextView tvTrackSpeaker;
    private ImageView ivHandwrittenComment;
    private TextView tvHandwrittenComment;
    private ImageView ivScreenBroadcast;
    private TextView tvScreenBroadcast;
    private LinearLayout linWin;

    private View mRightView;
    private MediaProjectionManager mMediaProjectionManager;
    private Context mContext;

    public static boolean isClickPizhu;

    public TextView getTvScreenBroadcast() {
        return tvScreenBroadcast;
    }

    public RightNavigationPopView(Context context, MediaProjectionManager mMediaProjectionManager, View parentview) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRightView = layoutInflater.inflate(R.layout.layout_right_navigation_popview, null);
        setContentView(mRightView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);

        initView();

        this.mMediaProjectionManager = mMediaProjectionManager;
        mContext = context;
        this.mParentView = parentview;
    }

    private void initView() {
        ivSpeaker = mRightView.findViewById(R.id.iv_speaker);
        tvSpeaker = mRightView.findViewById(R.id.tv_speaker);
        ivTrackSpeaker = mRightView.findViewById(R.id.iv_track_speaker);
        tvTrackSpeaker = mRightView.findViewById(R.id.tv_track_speaker);
        ivHandwrittenComment = mRightView.findViewById(R.id.iv_handwritten_comment);
        tvHandwrittenComment = mRightView.findViewById(R.id.tv_handwritten_comment);
        ivScreenBroadcast = mRightView.findViewById(R.id.iv_screen_broadcast);
        tvScreenBroadcast = mRightView.findViewById(R.id.tv_screen_broadcast);
        linWin = mRightView.findViewById(R.id.lin_win);

        ivSpeaker.setOnClickListener(this);
        ivTrackSpeaker.setOnClickListener(this);
        ivHandwrittenComment.setOnClickListener(this);
        ivScreenBroadcast.setOnClickListener(this);
        linWin.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startCaptureIntent() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        ((Activity) mContext).startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_speaker:
                //Net work Judgement.
                if (PaperlessApplication.getGlobalConstantsBean().getConnect()) {
                    //回调,EventBus -> P层
                    EventBus.getDefault().post(new ApplyDocSpeakerEvent());
                } else {
                    ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network));
                }
                break;
            case R.id.iv_track_speaker:
                //Net work Judgement.
                if (PaperlessApplication.getGlobalConstantsBean().getConnect()) {
                    EventBus.getDefault().post(new RightBarTrackSpeakEvent());
                } else {
                    ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network));
                }
                break;
            case R.id.iv_handwritten_comment:
                //handwriting annotation
                startHandAnnotation();
                break;
            case R.id.iv_screen_broadcast:
                if (tvScreenBroadcast.getText().equals(mContext.getString(R.string.menu_track_screen))) {
                    //Net work Judgement.
                    if (PaperlessApplication.getGlobalConstantsBean().getConnect()) {
                        mContext.startActivity(new Intent(mContext, ScreenReceiveActivity.class));
                    } else {
                        ToastUtil.show(mContext, mContext.getResources().getString(R.string.null_network));
                    }
                } else {
                    if (ScreenRecord.getInstance().getMediaCodec() == null) {
                        startCaptureIntent();
                        ivScreenBroadcast.setImageResource(R.mipmap.but_gbgb_n);
                    } else {
                        ScreenRecord.getInstance().stop();
                        ivScreenBroadcast.setImageResource(R.mipmap.but_pmgb_n);
                    }
                }
                dismiss();
                break;
            case R.id.lin_win:
                dismiss();
                break;
        }
    }

    public void setBroadcasting(boolean isBroadcasting) {
        if (isBroadcasting) {
            tvScreenBroadcast.setText(R.string.menu_track_screen);
        } else {
            tvScreenBroadcast.setText(R.string.menu_screen);
        }
    }

    public void setReceiveDocSpeakerView(boolean isSpeaker) {
        if (isSpeaker) {
            ivSpeaker.setImageResource(R.mipmap.but_sqfy_b);
        } else {
            ivSpeaker.setImageResource(R.mipmap.but_sqfy_n);
        }
    }

    public void setSendDocSpeakerView(boolean isSpeaker) {
        if (isSpeaker) {
            ivSpeaker.setImageResource(R.mipmap.but_gbfy_n);
            tvSpeaker.setText(R.string.menu_exit_zhujiang);
        } else {
            ivSpeaker.setImageResource(R.mipmap.but_sqfy_n);
            tvSpeaker.setText(R.string.menu_zhujiang);
        }
    }

    //是否可以跟踪主讲.
    public void setTrackStatus(boolean status) {
        if (status) {
            ivTrackSpeaker.setImageResource(R.mipmap.but_gzzj_n);
        } else {
            ivTrackSpeaker.setImageResource(R.mipmap.but_gzzj_b);
        }
    }

    //是否在跟踪主讲.
    public void setisTracking(boolean status) {
        if (status) {
            ivTrackSpeaker.setImageResource(R.mipmap.but_ybll_n);
            tvTrackSpeaker.setText(R.string.menu_liulan);
        } else {
            ivTrackSpeaker.setImageResource(R.mipmap.but_gzzj_n);
            tvTrackSpeaker.setText(R.string.menu_genzong);
        }
    }

    //手写批注
    private void startHandAnnotation() {
        if (!isClickPizhu) {
            dismiss();
            isClickPizhu = true;
            final String filePath = FileUtil.getStrokeFilePath(mContext, Config.imageFileLocationTemporary) + Config.SIGNIFILEMAGE;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new BitmapSaveToLocalTask(mContext, BitmapUtil.getSnapshot(mParentView), filePath, new BitmapSaveResult() {
                        @Override
                        public void onPostExecute() {
                            Intent intent = new Intent(mContext, WhiteBoardActivity.class);
                            intent.putExtra("filePath", filePath);
                            intent.putExtra("operationType", "handWritingAnnotation");
                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(R.anim.alpha_activity_out,
                                    R.anim.alpha_activity_in);
                        }
                    }, true).execute();
                }
            }, 300);
        }

    }

}
