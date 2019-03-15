package com.itc.suppaperless.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itc.suppaperless.R;


/**
 * 自定义 对话框
 */
public class FxDialog extends Dialog {

    public FxDialog(Context context) {
        super(context);
        _Context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题栏

        View _layout = LayoutInflater.from(_Context).inflate(R.layout.fxdialoglayout, null);
        _tvTitle = (TextView) _layout.findViewById(R.id.label_title);
        _panelTop = (LinearLayout) _layout.findViewById(R.id.panel_top);
        _panelView = (LinearLayout) _layout.findViewById(R.id.panel_control);
        _rtButtons = (FxRadioButton) _layout.findViewById(R.id.frbt_buttons);

        _rtButtons.SetParam(false, FxRadioButton.STYLE_MENU);
        _x = -1;
        _y = -1;
        _w = -1;
        _h = -1;
        _alpha = 1;

        setContentView(_layout);   //加载对话框布局文件
    }

    private Context _Context;
    private TextView _tvTitle;
    private LinearLayout _panelTop;
    private LinearLayout _panelView;
    private FxRadioButton _rtButtons;

    private int _x, _y, _w, _h;
    private float _alpha;

    /**
     * 设置标题栏
     *
     * @param title 为null或""则隐藏标题栏
     */
    public FxDialog SetTitle(String title) {
        if (title == null || title.length() == 0) {
            _panelTop.setVisibility(View.GONE);
        } else {
            _tvTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置显示内容为字符串
     */
    public FxDialog SetViewMessage(Object msg) {
        TextView tView = new TextView(_Context);
        tView.setText(msg.toString());
        setView(tView);
        return this;
    }

    /**
     * 设置显示的内容
     */
    public FxDialog setView(View v) {
        _panelView.addView(v);
        return this;
    }

    /**
     * 设置显示的内容
     */
    public FxDialog setView(int layoutResID) {
        _panelView.addView(LayoutInflater.from(_Context).inflate(layoutResID, null));
        return this;
    }

    /**
     * 设置按钮
     *
     * @param btns                  显示的按钮
     * @param onChoiceClickListener 点击按钮触发事件
     */
    public FxDialog setButton(String[] btns, OnMultiChoiceClickListener onChoiceClickListener) {
        if (btns == null || btns.length == 0) {
            _rtButtons.setVisibility(View.GONE);
        } else {
            _rtButtons.SetValue(btns, null, FxRadioButton.TYPE_TAB, onChoiceClickListener);
        }
        return this;
    }

    /**
     * 设置显示的位置和大小
     *
     * @param x      -1表示水平居中,-2表示居右，0居左
     * @param y      -1表示垂直居中，-2表示居下，0居上
     * @param width  -1表示自适应宽度
     * @param height -1表示自适应高度
     * @param alpha  对话框透明度，0~1
     */
    public FxDialog setLayout(int x, int y, int width, int height, float alpha) {
        _x = x;
        _y = y;
        _w = width;
        _h = height;
        _alpha = alpha;
        return this;
    }

    /**
     * 显示对话框
     */
    public void show() {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow != null ? dialogWindow.getAttributes() : null;
        int ll = Gravity.LEFT, tt = Gravity.TOP;
        if (_x == -1) {
            ll = Gravity.CENTER_HORIZONTAL;
            _x = 0;
        } else if (_x == -2) {
            ll = Gravity.RIGHT;
            _x = 0;
        }
        if (_y == -1) {
            tt = Gravity.CENTER_VERTICAL;
            _y = 0;
        } else if (_y == -2) {
            tt = Gravity.BOTTOM;
            _y = 0;
        }

        if (dialogWindow != null) {
            dialogWindow.setGravity(ll | tt);
        }

        if (lp != null) {
            lp.alpha = _alpha;
            lp.x = _x;
            lp.y = _y;
            if (_w == -1) {
                lp.width = _panelTop.getWidth();
            } else {
                lp.width = _w;
            }
            if (_h == -1) {
                lp.height = _panelTop.getHeight() + _panelView.getHeight() + _rtButtons.getHeight();
            } else {
                lp.height = _h;
            }

            dialogWindow.setAttributes(lp);
        }
        this.show();
    }

    /**
     * 关闭对话框
     */
    public void close() {
        this.dismiss();
    }
}
