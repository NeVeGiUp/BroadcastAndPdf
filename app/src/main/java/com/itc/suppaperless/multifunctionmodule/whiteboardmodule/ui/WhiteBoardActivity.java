package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseActivity;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.adapter.StyleSelectBaseAdapter;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.ISketchpadDraw;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.StyleMoveListener;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener.StyleSelectItemClickListener;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.model.MoveEvent;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.contract.WhiteBoardContract;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.mvp.presenter.WhiteBoardPresenter;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.utils.ConfigUtil;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.utils.WhiteBoardUtil;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.widget.WhiteBoardView;
import com.itc.suppaperless.switch_conference.widget.RightNavigationPopView;
import com.itc.suppaperless.utils.BitmapUtil;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.ToastUtil;
import com.itc.suppaperless.widget.DialogNewInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-7 下午3:18
 * @ desc   : Whiteboard and Annotation Pages
 */

public class WhiteBoardActivity extends BaseActivity<WhiteBoardPresenter> implements WhiteBoardContract.View {

    private static final String TAG = "WhiteBoardActivity";

    @BindView(R.id.whiteboard_view)
    WhiteBoardView whiteBoardView;
    @BindView(R.id.iv_style_move)
    ImageView moveIv;
    @BindView(R.id.iv_style_pen)
    ImageView penIv;
    @BindView(R.id.iv_style_eraser)
    ImageView eraserIv;
    @BindView(R.id.tv_style_other_tools)
    TextView otherToolsTv;
    @BindView(R.id.tv_style_pen_size)
    TextView penSizeTv;
    @BindView(R.id.tv_style_pen_color)
    TextView penColorTv;
    @BindView(R.id.ll_style_controller)
    LinearLayout controllerLl;

    private boolean isPenClickChangeColor = false;
    private boolean isEraserClickChangeColor = true;

    private final static int OTHER_TOOLS_SELECT_INDEX = 0;
    private final static int PEN_SIZE_SELECT_INDEX = 1;
    private final static int PEN_COLOR_SELECT_INDEX = 2;

    private Bitmap savedBitmap;
    private Bitmap openBgBitmap;
    private Canvas canvas;

    private Context context;
    private String operationType;
    private DialogNewInterface dialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_whiteboard;
    }


    @Override
    public WhiteBoardPresenter createPresenter() {
        return new WhiteBoardPresenter(this);
    }


    @Override
    public void init() {
        initView();
        initIntent();
        // TODO: 19-3-7 添加右滑菜单功能，个人白板只有屏幕广播功能
    }


    /**
     * Handwritten annotations enter the Sketchpad
     */
    private void initIntent() {
        operationType = getIntent().getStringExtra("operationType");
        String filepath = getIntent().getStringExtra("filePath");
        if ("handWritingAnnotation".equals(operationType))
            openBgBitmap = getPresenter().openBgSketchpad(context, filepath, whiteBoardView);
    }


    @SuppressLint("WrongViewCast")
    private void initView() {
        context = this;
        EventBus.getDefault().register(this);

        /** horizontal screen */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /** init status */
        WhiteBoardUtil.setTextViewBitmap(otherToolsTv, ContextCompat.getDrawable(context, R.mipmap.pen_icon_tool_int_0_nor));
        WhiteBoardUtil.setTextViewBitmap(penSizeTv, ContextCompat.getDrawable(context, R.mipmap.pen_icon_pen_int_1_nor));
        WhiteBoardUtil.setTextViewBitmap(penColorTv, ContextCompat.getDrawable(context, R.mipmap.pen_icon_cool_int_2_nor));

        /** move event listener */
        moveIv.setOnTouchListener(new StyleMoveListener(getIntent().
                getBooleanExtra("pizhuIn", false), context, moveIv, controllerLl));

        /** Overall Shadow Settings */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            controllerLl.setElevation(9);
        }

        /** Initialization of Sketchpad Parameters */
        canvas = new Canvas();
        whiteBoardView.initialize(ScreenUtil.getScreenWidth(this),
                ScreenUtil.getScreenHeight(this), canvas, new Paint(Paint.ANTI_ALIAS_FLAG));

    }


    /**
     * Double finger sliding monitor callback
     * pdfmodule interactive
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoveEvent(MoveEvent event) {
        // TODO: 19-3-7 根据传递过来的手势操作来做对应的功能
        Log.d(TAG, "onMoveEvent: 监听到双指操作！！！！！");

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            String string;
            if ("handWritingAnnotation".equals(operationType))
                string = getResources().getString(R.string.pizhu_prompt);
            else string = getResources().getString(R.string.file_prompt);
            if (dialog == null) {
                dialog = new DialogNewInterface(context).setText(string).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        exitActivity();
                    }
                });
            }
            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }


    /////////////////////////////////////////////  onViewClicked  ////////////////////////////////////////////////

    @OnClick({R.id.iv_style_pen, R.id.iv_style_eraser, R.id.tv_style_other_tools, R.id.tv_style_pen_size, R.id.tv_style_pen_color,
            R.id.btn_style_save, R.id.btn_style_clear, R.id.btn_style_shut_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_style_pen:   /** Paint brush */
                if (isPenClickChangeColor) {
                    penIv.setImageResource(R.mipmap.pen_icon_pen_hig);
                    eraserIv.setImageResource(R.mipmap.pen_ocon_eraser_nor);
                    otherToolsTv.setTextColor(penSizeTv.getTextColors());
                    isPenClickChangeColor = false;
                    isEraserClickChangeColor = true;
                }
                whiteBoardView.setStrokeType(WhiteBoardView.STYLE_PEN);
                break;

            case R.id.iv_style_eraser:   /** Eraser */
                if (isEraserClickChangeColor) {
                    eraserIv.setImageResource(R.mipmap.pen_ocon_eraser_hig);
                    penIv.setImageResource(R.mipmap.pen_icon_pen_nor);
                    otherToolsTv.setTextColor(penSizeTv.getTextColors());
                    isEraserClickChangeColor = false;
                    isPenClickChangeColor = true;
                }
                whiteBoardView.setStrokeType(WhiteBoardView.STYLE_ERASER);
                break;

            case R.id.tv_style_other_tools:   /** Other tools */
                showPupopWindow(otherToolsTv, R.layout.style_pupopwindow_grid, OTHER_TOOLS_SELECT_INDEX, ConfigUtil.styleSelect);
                break;

            case R.id.tv_style_pen_size:   /** Brush Size */
                showPupopWindow(otherToolsTv, R.layout.style_pupopwindow_grid, PEN_SIZE_SELECT_INDEX, ConfigUtil.sizeSelct);
                break;

            case R.id.tv_style_pen_color:  /** stroke color */
                showPupopWindow(penColorTv, R.layout.style_pupopwindow_grid, PEN_COLOR_SELECT_INDEX, ConfigUtil.colorSelct);
                break;

            case R.id.btn_style_save:  /** Save button */
                if (ISketchpadDraw.attrStack.size() == 0) {
                    ToastUtil.show(context, "没有可以保存的图片", 1);
                    return;
                }
                savedBitmap = BitmapUtil.getSnapshot(whiteBoardView);
                getPresenter().saveScreenshot(context, operationType, savedBitmap);
                break;

            case R.id.btn_style_clear:   /** Clean screen */
                whiteBoardView.objStack.clearPageDraw(-1, true);
                break;

            case R.id.btn_style_shut_down:   /** close */
                String str = "handWritingAnnotation".equals(operationType) ? getResources().getString(R.string.pizhu_prompt) :
                        getResources().getString(R.string.file_prompt);
                new DialogNewInterface(context).setText(str).setOnOkClickListener(new DialogNewInterface.OnOkClickListener() {
                    @Override
                    public void onClick() {
                        exitActivity();
                    }
                }).show();
                break;
        }
        WhiteBoardView.requestFocus(controllerLl);
    }


    /**
     * Display optional styles
     *
     * @param textView    Text view
     * @param layoutResID view id
     */
    private void showPupopWindow(final TextView textView, int layoutResID, int select, int[] data) {
        View contentView = LayoutInflater.from(context).inflate(layoutResID, null);
        GridView grid_style_pup = (GridView) contentView.findViewById(R.id.list_style_pup);
        grid_style_pup.setAdapter(new StyleSelectBaseAdapter(context, data));
        int dimensionWidth;
        int dimensionHeight;
        if (select == OTHER_TOOLS_SELECT_INDEX) {
            dimensionWidth = (int) getResources().getDimension(R.dimen.style_select_grid);
            dimensionHeight = (int) getResources().getDimension(R.dimen.style_select_grid_samll) * 2;  /** Two rows, double height */
        } else {
            dimensionWidth = (int) getResources().getDimension(R.dimen.style_select_grid);
            dimensionHeight = (int) getResources().getDimension(R.dimen.style_select_grid_samll) * 3;  /** Three rows, three times the height */
        }
        final PopupWindow popupWindow = new PopupWindow(contentView, dimensionWidth, dimensionHeight,
                true);
        popupWindow.setFocusable(true);  /** Focus of acquisition */
        popupWindow.setOutsideTouchable(true);  /** Set up to click outside of Popup Window to close Popup Window */
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.mipmap.style_popupwindow_bg));
        grid_style_pup.setTag(popupWindow);
        grid_style_pup.setOnItemClickListener(
                new StyleSelectItemClickListener(context, whiteBoardView, penIv, eraserIv, otherToolsTv, textView, popupWindow, data, select));
        popupWindow.showAsDropDown(textView, 0, 5);  /** Y-axis offset */
        popupWindow.update();
    }


    ///////////////////////////////////////////  view callback  //////////////////////////////////////////////////////
    @Override
    public void screenshotSaveSuccess() {
        ToastUtil.show(context, getString(R.string.save_suc_prompt), Toast.LENGTH_SHORT);
    }

    @Override
    public void screenshotSaveFailed() {
        ToastUtil.show(context, getString(R.string.save_failed_prompt));
    }

    @Override
    public void screenshotUploadSuccess() {

    }

    @Override
    public void screenshotUploadFailed() {

    }

    @Override
    public Activity getActivity() {
        return null;
    }


    ///////////////////////////////////////////  finish activity  //////////////////////////////////////////////////////

    private void exitActivity() {
        /** Releasing resources */
        whiteBoardView.recycleBitmap();
        whiteBoardView.objStack.clearAll(false);
        RightNavigationPopView.isClickPizhu = false;
        this.finish();
        overridePendingTransition(R.anim.alpha_activity_out, R.anim.alpha_activity_in);
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (whiteBoardView != null) {
            whiteBoardView.recycleBitmap();
            whiteBoardView.objStack.clearAll(false);
            whiteBoardView = null;
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        if (openBgBitmap != null) {
            openBgBitmap.recycle();
            openBgBitmap = null;
        }

        if (savedBitmap != null) {
            savedBitmap.recycle();
            savedBitmap = null;
        }
    }


}
