package org.connecttag.utilities.global

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle

object ActivityUtils {

    fun Activity.launchActivity(
        packageName: String,
        className: String,
        flags: Int = -1,
        bundle: Bundle? = null,
    ) {
        val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
        if (flags != -1) {
            intent.flags = flags
        }
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun Context.findActivity2(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("no activity")
    }
}