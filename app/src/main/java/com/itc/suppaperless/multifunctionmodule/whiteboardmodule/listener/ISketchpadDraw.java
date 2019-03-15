package com.itc.suppaperless.multifunctionmodule.whiteboardmodule.listener;

import android.graphics.Canvas;

import com.itc.suppaperless.multifunctionmodule.whiteboardmodule.model.StyleObjAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * 白板触摸回调接口
 */
public interface ISketchpadDraw {
	List<StyleObjAttr> attrStack = new ArrayList<>();

	void draw(Canvas canvas);

	boolean hasDraw();

	void touchDown(float x, float y);

	void touchMove(float x, float y);

	void touchUp(float x, float y);

}