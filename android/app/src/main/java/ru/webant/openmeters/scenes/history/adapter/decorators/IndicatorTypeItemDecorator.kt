package ru.webant.openmeters.scenes.history.adapter.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class IndicatorTypeItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = 32
        } else if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = 6
        }
    }
}