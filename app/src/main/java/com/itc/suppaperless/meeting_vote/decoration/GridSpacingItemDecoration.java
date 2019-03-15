package com.itc.suppaperless.meeting_vote.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pengds on 2017/8/3.
 *
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;

    public GridSpacingItemDecoration(int spanCount) {
        this.spanCount = spanCount;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spanCount;
            outRect.top = spanCount;
            outRect.bottom = spanCount;
    }
}