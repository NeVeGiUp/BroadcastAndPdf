package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itc.suppaperless.R;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.utils.ConfigUtil;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.widget.WhiteBoardView;

public class StyleSelectItemClickListener implements AdapterView.OnItemClickListener {

    private final static int OTHER_TOOLS_SELECT_INDEX = 0;
    private final static int PEN_SIZE_SELECT_INDEX = 1;
    private final static int PEN_COLOR_SELECT_INDEX = 2;

    private boolean isPenClickChangeColor = false;
    private boolean isEraserClickChangeColor = true;

    private WhiteBoardView whiteBoardView;
    private Context context;
    private TextView textView;
    private PopupWindow popupWindow;
    private ImageView penIv;
    private ImageView eraserIv;
    private TextView otherToolsTv;
    private int[] data;
    private int select;

    public StyleSelectItemClickListener(Context context, WhiteBoardView whiteBoardView, ImageView penIv, ImageView eraserIv,
                                        TextView otherToolsTv, TextView textView, PopupWindow popupWindow, int[] data, int select) {
        this.context = context;
        this.whiteBoardView = whiteBoardView;
        this.penIv = penIv;
        this.eraserIv = eraserIv;
        this.otherToolsTv = otherToolsTv;
        this.textView = textView;
        this.popupWindow = popupWindow;
        this.data = data;
        this.select = select;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Drawable bottomDrawable = ContextCompat.getDrawable(context, data[position]);
        bottomDrawable.setBounds(0, 0, 26, 26);  //第一是距左边距离，第二是距上边距离，后俩个分别是长宽
        textView.setCompoundDrawables(null, null, null, bottomDrawable);
        popupWindow.dismiss();
        switch (select) {
            case OTHER_TOOLS_SELECT_INDEX: /** The results of other tool choices */
                switch (position) {
                    case 0:  /** Text editing */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_TEXT);
                        break;
                    case 1:  /** straight line */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_LINE);
                        break;
                    case 2:  /** rectangle */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_RECT);
                        break;
                    case 3:  /** Solid rectangle */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_FILL_RECT);
                        break;
                    case 4:  /** Ellipse */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_OVAL);
                        break;
                    case 5:  /** Solid ellipse */
                        whiteBoardView.setStrokeType(WhiteBoardView.STYLE_FILL_OVAL);
                        break;
                }
                isPenClickChangeColor = true;
                isEraserClickChangeColor = true;
                penIv.setImageResource(R.mipmap.pen_icon_pen_nor);
                eraserIv.setImageResource(R.mipmap.pen_ocon_eraser_nor);
                otherToolsTv.setTextColor(ContextCompat.getColor(context, R.color.style_all_pressed_color));
                break;
            case PEN_SIZE_SELECT_INDEX:  /** Selection of Brush Size */
                whiteBoardView.setPenSize(ConfigUtil.sizeSelctValue[position]);  /** Get the corresponding data from the Map according to position */
                break;
            case PEN_COLOR_SELECT_INDEX:  /** Selection of Brush Colors */
                whiteBoardView.setStrokeColor(ConfigUtil.colorSelctValue[position]);
                break;
        }

    }
}