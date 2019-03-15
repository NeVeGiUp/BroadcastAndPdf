
package com.itc.suppaperless.widget;

import android.content.Context;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.itc.suppaperless.utils.FxHelp;


/**
 * 自定义radio组控件
 */
public class FxRadioButton extends android.view.View {

    public static final int TYPE_SINGLE = 0;        //该类型为单选模式
    public static final int TYPE_MULTI = 1;        //多选模式
    public static final int TYPE_TAB = 2;          //选项卡模式

    public static final int STYLE_RADIO = 0;        //显示风格为按钮风格
    public static final int STYLE_MENU = 1;            //显示风格为底部菜单风格

    public int _mode = TYPE_SINGLE;
    public String[] _btList = new String[]{"radio1", "radio2"};
    public boolean[] _valueList = new boolean[]{true, false};
    private Rect[] _rtList;
    Rect _rtClient;
    private LinearGradient _linearGradient;            //渐变的背景色
    private boolean _isRoundBound = true;        //是否是圆角边框
    private int _style = STYLE_RADIO;        //显示风格
    private Paint paint = new Paint();
    private Context _Context;
    private OnMultiChoiceClickListener _onItemCheckChanged;

    public FxRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        _Context = context;
        Init();
    }

    public FxRadioButton(Context context) {
        super(context);
        _Context = context;
        Init();
    }

    public FxRadioButton(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        _Context = context;
        Init();
    }

    private void Init() {
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
    }

    /**
     * 设置参数
     *
     * @param isRoundBound 是否是圆角边框
     */
    public void SetParam(boolean isRoundBound, int style) {
        _isRoundBound = isRoundBound;
        _style = style;
    }

    /**
     * 为选项设置值
     *
     * @param btns                  显示的内容
     * @param values                是否选中
     *                              //* @param isSingleChoose 是否是单选
     * @param onChoiceClickListener 选择内容改变事件
     */
    public void SetValue(String[] btns, boolean[] values, int mode, OnMultiChoiceClickListener onChoiceClickListener) {
        if (values == null || mode == TYPE_TAB) {   //选项卡模式不需要选择状态
            values = new boolean[btns.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = false;
            }
        }

        if (mode == TYPE_SINGLE) {   //单选模式下，如果没有任何选中的，则默认选择第一个;如果有多选的，则取消其他选择项
            boolean ischecked = false;
            for (int i = 0; i < values.length; i++) {
                if (ischecked) {
                    values[i] = false;
                } else if (values[i]) {
                    ischecked = true;
                }
            }
            if (!ischecked) {
                values[0] = true;
            }
        }

        _btList = btns;
        _valueList = values;
        _mode = mode;
        _onItemCheckChanged = onChoiceClickListener;

        invalidate();
    }

    /**
     * 单选模式下返回序号，多选模式下返回-1
     */
    public int GetValue() {
        if (_mode == TYPE_SINGLE) {
            for (int i = 0; i < _valueList.length; i++) {
                if (_valueList[i])
                    return i;
            }
        }
        return -1;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < _rtList.length; i++) {
                if (_rtList[i].contains((int) event.getX(), (int) event.getY())) {
                    if (_mode == TYPE_SINGLE) { // 单选模式
                        int lastsel = GetValue();
                        for (int j = 0; j < _valueList.length; j++) {
                            _valueList[j] = i == j;
                        }
                        int cursel = GetValue();
                        if (lastsel != cursel && _onItemCheckChanged != null) {
                            _onItemCheckChanged.onClick(null, i, _valueList[i]);
                        }
                    } else if (_mode == TYPE_MULTI) { // 多选模式
                        _valueList[i] = !_valueList[i];
                        if (_onItemCheckChanged != null) {
                            _onItemCheckChanged.onClick(null, i, _valueList[i]);
                        }
                    } else if (_mode == TYPE_TAB) { // 选项卡模式
                        _valueList[i] = !_valueList[i];
                        if (_onItemCheckChanged != null) {
                            _onItemCheckChanged.onClick(null, i, _valueList[i]);
                        }
                    }
                    invalidate();
                    break;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (_mode == TYPE_TAB) { // 选项卡模式下，鼠标弹起时恢复状态
                for (int i = 0; i < _valueList.length; i++) {
                    _valueList[i] = false;
                }
                invalidate();
            }
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        _rtClient = FxHelp.createRect(0, 0, this.getWidth(), this.getHeight());
        _rtList = new Rect[_btList.length];
        for (int i = 0; i < _rtList.length; i++) {
            _rtList[i] = FxHelp.createRect(_rtClient.left + _rtClient.width() * i / _rtList.length, _rtClient.top, _rtClient.width() / _rtList.length, _rtClient.height());
        }
        canvas.save();
        if (_isRoundBound) {
            canvas.clipPath(FxHelp.getRoundRectPath(_rtClient, PX(4), PX(4)), Op.REPLACE);
        }

        paint.setColor(Color.argb(244, 252, 252, 252));
        canvas.drawRect(_rtClient, paint);            //绘制背景色

        if (_linearGradient == null) {
            //背景渐变色
            int[] gradientColors = new int[5];
            gradientColors[0] = Color.argb(255, 254, 254, 255);
            gradientColors[1] = Color.argb(255, 252, 252, 252);
            gradientColors[2] = Color.argb(255, 245, 245, 245);
            gradientColors[3] = Color.argb(255, 239, 239, 239);
            gradientColors[4] = Color.argb(255, 237, 237, 237);
            float[] gradientPositions = new float[]{0, 0.25f, 0.50f, 0.75f, 1};
            _linearGradient = new LinearGradient(_rtClient.left, _rtClient.top, _rtClient.left, _rtClient.bottom,
                    gradientColors, gradientPositions, TileMode.CLAMP);
        }

        paint.setStyle(Style.FILL);//实心矩形框
        paint.setColor(Color.BLACK);
        paint.setTextSize(PX(14));
        paint.setTypeface(Typeface.DEFAULT);
        for (int i = 0; i < _rtList.length; i++) {
            if (!_valueList[i]) {    //未选择状态
                if (_style == STYLE_RADIO) {
                    paint.setShader(_linearGradient);
                    canvas.drawRect(_rtList[i], paint);            //绘制背景色
                    paint.setShader(null);
                    paint.setColor(Color.BLACK);
                } else if (_style == STYLE_MENU) {
                    paint.setColor(Color.argb(255, 245, 245, 240));
                    canvas.drawRect(_rtList[i], paint);            //绘制背景色
                    paint.setColor(Color.BLACK);
                }
            } else {   //选择状态
                paint.setColor(Color.argb(255, 0, 163, 240));
                canvas.drawRect(_rtList[i], paint);            //绘制背景色
                paint.setColor(Color.WHITE);
            }

            FxHelp.DrawString(_btList[i], _rtList[i], canvas, paint, 1);
        }

        //绘制边框
        paint.setColor(Color.argb(200, 192, 192, 192));
        paint.setStrokeWidth(1);
        paint.setStyle(Style.STROKE);    //空心矩形框
        if (_isRoundBound)
            canvas.drawRoundRect(new RectF(_rtClient), PX(4), PX(4), paint); //圆角矩形
        else {
            canvas.drawRect(_rtClient, paint);
        }
        paint.setStyle(Style.FILL);
        //绘制分隔条
        for (int i = 0; i < _rtList.length - 1; i++) {
            FxHelp.drawLine(_rtList[i].right, _rtList[i].top, _rtList[i].right, _rtList[i].bottom, canvas, paint);
        }

        canvas.restore();
    }

    /**
     * dip值转换为像素值，绘图中使用像素
     */
    private int PX(int dp) {
        return FxHelp.dip2px(_Context, dp);
    }
}
