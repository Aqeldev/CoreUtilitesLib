package org.connecttag.utilities.global

import com.core.utils.utilities.constants.ExceptionDescriptions
import com.core.utils.utilities.constants.ExceptionTitles

object ErrorUtils {

    fun setNetworkError(value: String): String {
        return when (value) {
            ExceptionTitles.GPS_DISABLED -> ExceptionDescriptions.GPS_DISABLED_DESCR
            ExceptionTitles.NO_INTERNET_CONNECTION -> ExceptionDescriptions.NO_INTERNET_CONNECTION_DESCR
            ExceptionTitles.NO_PERMISSION -> ExceptionDescriptions.NO_PERMISSION_DESCR
            else -> ExceptionDescriptions.UNKNOWN_ERROR_DESCR
        }
    }

    fun setApiError(value: Int): String {
        return when (value) {
            501 -> ExceptionDescriptions.GPS_DISABLED_DESCR
            504 -> ExceptionDescriptions.NO_INTERNET_CONNECTION_DESCR
            404 -> ExceptionDescriptions.NO_PERMISSION_DESCR
            else -> ExceptionDescriptions.UNKNOWN_ERROR_DESCR
        }
    }

    fun setLocationError(value: String): String {
        return when (value) {
            ExceptionTitles.GPS_DISABLED -> ExceptionDescriptions.GPS_DISABLED_DESCR
            ExceptionTitles.NO_INTERNET_CONNECTION -> ExceptionDescriptions.NO_INTERNET_CONNECTION_DESCR
            ExceptionTitles.NO_PERMISSION -> ExceptionDescriptions.NO_PERMISSION_DESCR
            else -> ExceptionDescriptions.UNKNOWN_ERROR_DESCR
        }
    }


}