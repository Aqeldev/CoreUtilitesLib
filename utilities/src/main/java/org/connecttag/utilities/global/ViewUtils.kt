package org.connecttag.utilities.global

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes


object ViewUtils {

    object VisibilityController {
        fun hideView(view: View) {
            view.visibility = View.GONE
        }

        fun showView(view: View) {
            view.visibility = View.VISIBLE
        }

        fun toggleVisibility(view: View) {
            view.visibility = if (view.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    object StyleApplier {
        fun applyStyle(view: View, style: Int) {
            view.context.theme.applyStyle(style, true)
            view.invalidate()
        }
    }


    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.setVisible(isVisible: Boolean) {
        if (isVisible) show() else hide()
    }

    fun TextView.setDrawableLeft(@DrawableRes id: Int = 0) {
        this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
    }



}