package com.github.asuslennikov.taskman.task.list

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.RecyclerView
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.task.list.item.DateHeaderScreen
import com.github.asuslennikov.taskman.task.list.item.TaskItemScreen
import java.lang.ref.WeakReference

interface ItemDecorationTranslateListener {
    fun onViewTranslated(view: View, deltaX: Float, deltaY: Float)

}

class TasksListItemDecoration : RecyclerView.ItemDecoration(), ItemDecorationTranslateListener {

    private companion object {
        const val NOT_DEFINED = 0
        const val COLOR_ALPHA_MULTIPLIER = 255
    }

    private val paint = Paint()
    private val draggingViewOffset = object {
        var view: WeakReference<View>? = null
        var deltaX: Int = 0
        var deltaY: Int = 0
    }
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
        parent.layoutManager?.let { layoutManager ->
            for (idx in 0 until layoutManager.childCount) {
                layoutManager.getChildAt(idx)?.let child@{ currentChild ->
                    if (!isChildViewATaskItem(parent, currentChild)) {
                        return@child
                    }
                    layoutManager.getChildAt(idx)?.let { nextChild ->
                        if (isChildViewATaskItem(parent, nextChild) && isChildViewVisible(layoutManager, currentChild)) {
                            drawTaskItemBackground(c, parent, nextChild)
                        }
                    }
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

    private fun isChildViewVisible(layoutManager: RecyclerView.LayoutManager, view: View) =
        layoutManager.isViewPartiallyVisible(view, true, true)
                || layoutManager.isViewPartiallyVisible(view, false, true)

    private fun drawTaskItemBackground(c: Canvas, parent: RecyclerView, view: View) {
        val deltaX = if (draggingViewOffset.view?.get() == view) draggingViewOffset.deltaX else 0
        val deltaY = if (draggingViewOffset.view?.get() == view) draggingViewOffset.deltaY else 0
        c.run {
            save()
            paint.color = ColorUtils.setAlphaComponent(backgroundMainColor, (COLOR_ALPHA_MULTIPLIER * view.alpha).toInt())
            val itemRect = Rect(
                view.left,
                view.top - backgroundCorner,
                view.right,
                view.top
            )
            clipRect(itemRect)
            itemRect.left += backgroundStrokeWidth + deltaX
            itemRect.top += deltaY
            itemRect.right += deltaX
            itemRect.bottom += deltaY
            drawRect(itemRect, paint)
            restore()
        }
    }

    override fun onViewTranslated(view: View, deltaX: Float, deltaY: Float) {
        if (draggingViewOffset.view?.get() != view) {
            draggingViewOffset.view = WeakReference(view)
        }
        draggingViewOffset.deltaX = deltaX.toInt()
        draggingViewOffset.deltaY = deltaY.toInt()
    }
}