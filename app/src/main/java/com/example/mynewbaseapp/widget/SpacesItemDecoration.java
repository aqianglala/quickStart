package com.example.mynewbaseapp.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zy1584 on 2017-8-7.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceHorizontal;
    private int spaceVertical;

    public SpacesItemDecoration(int space) {
        this.spaceHorizontal = space;
        this.spaceVertical = space;
    }

    public SpacesItemDecoration(int spaceHorizontal, int spaceVertical) {
        this.spaceHorizontal = spaceHorizontal;
        this.spaceVertical = spaceVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.left = spaceHorizontal;
        outRect.right = spaceHorizontal;
        outRect.top = spaceVertical;

    }
}