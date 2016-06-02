package org.ourcitylove.oclapp.layout;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpaceDecorator extends RecyclerView.ItemDecoration {
    private final int columnCount;
    private final int spaceInPixels;

    public GridSpaceDecorator(int columnCount, int spaceInPixels) {
        this.columnCount = columnCount;
        this.spaceInPixels = spaceInPixels;
    }

    public GridSpaceDecorator(int columnCount, int spaceInPixels, float densityMultiplier) {
        this.columnCount = columnCount;
        this.spaceInPixels = (int) (spaceInPixels * densityMultiplier);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // We always want to add right/bottom spaceInPixels
        outRect.right = spaceInPixels;
        outRect.bottom = spaceInPixels;

        // Only add top margin for the first row
        if (parent.getChildPosition(view) < columnCount) {
            outRect.top = spaceInPixels;
        }

        // Only add left margin for the left-most items
        if (parent.getChildPosition(view) % columnCount == 0) {
            outRect.left = spaceInPixels;
        }
    }
}
