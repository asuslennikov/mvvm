package com.github.asuslennikov.taskman.task.list

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.task.list.item.DateHeaderScreen
import com.github.asuslennikov.taskman.task.list.item.TaskItemScreen

class TasksListItemDecoration : RecyclerView.ItemDecoration() {

    private companion object {
        const val NOT_DEFINED = 0
        const val COLOR_ALPHA_MULTIPLIER = 255
        const val FLOAT_TO_INT_PIXEL_GAP_FIX = 0.5
    }

    private val paint = Paint()
    private var backgroundMainColor: Int = NOT_DEFINED
    private var backgroundCorner: Int = NOT_DEFINED
    private var backgroundStrokeWidth: Int = NOT_DEFINED

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if (childAdapterPosition > 0 && parent.getChildViewHolder(view) is DateHeaderScreen) {
            view.context?.let { context ->
                outRect.top = context.resources.getDimensionPixelSize(R.dimen.tasks_list_date_header_top_separation)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        setFromResourcesIfNeeded(parent)
        for (idx in 0..parent.childCount) {
            parent.getChildAt(idx)?.let { childView ->
                if (isChildViewATaskItem(parent, childView)) {
                    drawTaskItemBackground(c, parent, childView)
                }
            }
        }
    }

    private fun setFromResourcesIfNeeded(parent: RecyclerView) {
        if (backgroundMainColor == NOT_DEFINED) {
            backgroundMainColor = ContextCompat.getColor(parent.context, R.color.task_item_background)
        }
        if (backgroundCorner == NOT_DEFINED) {
            backgroundCorner = parent.resources.getDimension(R.dimen.task_item_background_corner).toInt()
        }
        if (backgroundStrokeWidth == NOT_DEFINED) {
            backgroundStrokeWidth = parent.resources.getDimension(R.dimen.task_item_background_stroke_width).toInt()
        }
    }

    private fun isChildViewATaskItem(parent: RecyclerView, view: View?) =
        view?.run { parent.getChildViewHolder(this) is TaskItemScreen } ?: false

    private fun drawTaskItemBackground(c: Canvas, parent: RecyclerView, view: View) {
        c.run {
            save()
            paint.color = ColorUtils.setAlphaComponent(backgroundMainColor, (COLOR_ALPHA_MULTIPLIER * view.alpha).toInt())
            with(parent) {
                clipRect(Rect(paddingLeft, paddingTop, c.clipBounds.right - paddingRight, c.clipBounds.bottom - paddingBottom))
            }
            with(view) {
                Rect(
                    left + translationX.toInt() + backgroundStrokeWidth,
                    top + translationY.toInt() - backgroundCorner,
                    right + translationX.toInt(),
                    top + (translationY + FLOAT_TO_INT_PIXEL_GAP_FIX).toInt()
                )
            }.apply {
                drawRect(this, paint)
            }
            restore()
        }
    }
}