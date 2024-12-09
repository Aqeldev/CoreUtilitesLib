package org.connecttag.utilities.global

import java.net.MalformedURLException
import java.net.URL


object UrlUtils {

    private val urlRegex = Regex("((http|https|ftp)://)(www\\.)?[-a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)")
    private val urlDownloadRegex = Regex("""((http|https|ftp)://)(www\.)?(([-a-zA-Z0-9@:%._\+~#?&//=]{2,256}\.[a-z]{2,6})|(\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}))\b([-a-zA-Z0-9@:%._\+~#?&//=]*)\.(apk|rar)""")

    val findUrlLink: (String?) -> String? = {
        it?.split(' ')?.find { str -> str.matches("https?://.+".toRegex()) }
    }

    public fun String.containsLinks(): Boolean {
        val regex = """(^|\s)((http|https|ftp)://)?[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)+(/[a-zA-Z0-9- ;,./?%&=]*)?"""
            .toRegex(RegexOption.IGNORE_CASE)
        return this.contains(regex = regex)
    }

    public fun String.addSchemeToUrlIfNeeded(): String = when {
        this.startsWith("mailto:") -> this
        this.startsWith("http://") -> this
        this.startsWith("https://") -> this
        else -> "http://$this"
    }

    val codeUrl: (String?) -> String?
        get() = {
            it?.replace(
                '\u002f',// -> /
                '\u2204' // -> ∄
            )
                ?.replace(
                    '\u003f',// -> ?
                    '\u2203' // -> 	∃
                )
        }

    val decodeUrl: (String?) -> String?
        get() = {
            it?.replace(
                '\u2204', // -> ∄
                '\u002f' // -> /
            )
                ?.replace(
                    '\u2203', // -> 	∃
                    '\u003f' // -> ?
                )
        }

    fun isValidDownloadUrl(input: String): Boolean {
        if (input.isBlank()) return false
        return urlDownloadRegex.matches(input)
    }

    fun isValidUrl(input: String): Boolean {
        if (input.isBlank()) return false
        return urlRegex.matches(input)
    }

    fun isValidUrl2(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: MalformedURLException) {
            false
        }
    }




    fun getDomainName(url: String): String? {
        return try {
            URL(url).host
        } catch (e: MalformedURLException) {
            null
        }
    }

    fun getScheme(url: String): String? {
        return try {
            URL(url).protocol
        } catch (e: MalformedURLException) {
            null
        }
    }

    fun getPath(url: String): String? {
        return try {
            URL(url).path
        } catch (e: MalformedURLException) {
            null
        }
    }

    fun getQueryParameters(url: String): Map<String, String>? {
        return try {
            val query = URL(url).query
            if (query != null) {
                query.split("&").associate {
                    val (key, value) = it.split("=")
                    key to value
                }
            } else {
                emptyMap()
            }
        } catch (e: MalformedURLException) {
            null
        }
    }

}