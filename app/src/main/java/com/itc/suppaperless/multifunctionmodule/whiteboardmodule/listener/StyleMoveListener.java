package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itc.suppaperless.R;
import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.utils.WhiteBoardUtil;
import com.itc.suppaperless.utils.ScreenUtil;

/**
 * @ author : lgh_ai
 * @ e-mail : lgh_developer@163.com
 * @ date   : 19-3-15 下午3:37
 * @ desc   : whiteboard toolbar move listener
 */

public class StyleMoveListener implements View.OnTouchListener {

    private Context context;
    private ImageView moveIv;
    private LinearLayout content;
    private int startX;
    private int startY;
    private int statusBarHeight = 0;

    public StyleMoveListener(boolean pizhuIn, Context context, ImageView moveIv, LinearLayout content) {
        this.context = context;
        this.moveIv = moveIv;
        this.content = content;
        if (pizhuIn)
            statusBarHeight = WhiteBoardUtil.getStatusBarHeight(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveIv.setImageResource(R.mipmap.pen_icon_move_hig);
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (event.getRawX() - startX);
                int y = (int) (event.getRawY() - startY);
                int left = content.getLeft();
                int top = content.getTop();
                int screenWidth = ScreenUtil.getScreenWidth(context);
                int screenHeight = ScreenUtil.getScreenHeight(context);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) content
                        .getLayoutParams();
                int moveX = x + left;
                int moveY = y + top;
                if (moveX < 0) {
                    moveX = 1;
                } else if (moveX > screenWidth - content.getWidth()) {
                    moveX = screenWidth - content.getWidth();
                }
                if (moveY < 0) {
                    moveY = 1;
                } else if (moveY > screenHeight - content.getHeight() - statusBarHeight) {
                    moveY = screenHeight - content.getHeight() - statusBarHeight;
                }
                params.leftMargin = moveX;
                params.topMargin = moveY;
                content.setLayoutParams(params);
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                moveIv.setImageResource(R.mipmap.pen_icon_move_nor);
                break;
        }
        return true;
    }

    private GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("pds", "返回了true");
            return true;
        }
    });
}