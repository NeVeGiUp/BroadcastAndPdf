package com.itc.suppaperless.meeting_vote.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pengds on 2017/8/3.
 *
 */

public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;

    public HorizontalSpacingItemDecoration(int spanCount) {
        this.spanCount = spanCount;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = spanCount;
            outRect.right = spanCount;
    }
}