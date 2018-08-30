package com.mobeta.android.dslv;

import android.content.Context;
import android.widget.Checkable;

/**
 * Lightweight ViewGroup that wraps list items obtained from user's
 * ListAdapter. ItemView expects a single child that has a definite
 * height (i.e. the child's layout height is not MATCH_PARENT).
 * The width of
 * ItemView will always match the width of its child (that is,
 * the width MeasureSpec given to ItemView is passed directly
 * to the child, and the ItemView measured width is set to the
 * child's measured width). The height of ItemView can be anything;
 * the 
 * 
 *
 * The purpose of this class is to optimize slide
 * shuffle animations.
 */
public class DragSortItemView3 extends DragSortItemView implements Checkable {


    public DragSortItemView3(Context context) {
        super(context);
    }

    @Override
    public void setChecked(boolean b) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
