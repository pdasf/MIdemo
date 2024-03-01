package com.xiaomi.midemo.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;
    private final int space;
    private final int orientation;
    private int gridLines = 1;

    public SpacesItemDecoration(int orientation, int space) {
        this.space = space;
        this.orientation = orientation;
    }

    public SpacesItemDecoration(int orientation, int space, int gridLines) {
        this.space = space;
        this.orientation = orientation;
        this.gridLines = gridLines;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int density = (int) view.getContext().getResources().getDisplayMetrics().density;
        switch (orientation) {
            case HORIZONTAL:
                if (parent.getChildLayoutPosition(view) != 0) outRect.left = space * density;
                break;
            case VERTICAL:
                if (parent.getChildLayoutPosition(view) != 0) outRect.top = space * density;
                break;
            case GRID:
                int position = parent.getChildAdapterPosition(view);
                outRect.left = space * density;
                outRect.top = space * density;
                outRect.bottom = space * density;
                outRect.right = space * density;
                if (position % gridLines == 0) {
                    outRect.left = 0;
                }
                if (position < gridLines) {
                    outRect.top = 0;
                }
                if ((position + 1) % gridLines == 0) {
                    outRect.right = 0;
                }
        }
    }
}
