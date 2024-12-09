package org.connecttag.utilities.global.encryption

import java.net.URLEncoder.encode

inline fun <reified T> T.toEncode(): String {
    return try {

        encode(this.toString())
    } catch (ex: Exception) {
        ""
    }
}
