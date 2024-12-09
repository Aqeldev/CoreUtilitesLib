package org.connecttag.utilities.global

import org.json.JSONObject
import java.text.Normalizer
import java.util.Locale


object StringUtils {

    fun toBooleanFromString(value: String?): Boolean {
        return when (value?.lowercase(Locale.ROOT)) {
            "true" -> true
            "false" -> false
            else -> false // قيمة افتراضية إذا كانت القيمة غير صحيحة أو فارغة
        }
    }

    fun toValidType(value: Any?): Any {
        val result = when (value) {
            is Int -> value.toInt()
            is Long -> value.toLong()
            is Float -> value.toFloat()
            is Double -> value.toDouble()
            is Boolean -> if (value) true else false
            is JSONObject -> value.toString()
            is String -> value
            else -> {
                value.toString()
            }
        }
        return result
    }

    fun defaultValue(value: String?, defaultValue: String = "", trim: Boolean = true): String {
        return if (value.isNullOrBlank()) {
            if (trim) defaultValue.trim() else defaultValue
        } else {
            value
        }
    }


    fun nonVL(input: String?): String {
        return input ?: ""
    }

    fun checkLanguage(value: String): String {
        val arabicPattern = "^[\u0621-\u064A ]+\$".toRegex()
        val englishPattern = "^[a-zA-Z ]+\$".toRegex()

        return when {
            arabicPattern.matches(value) -> "Ar"
            englishPattern.matches(value) -> "En"
            else -> ""
        }
    }

    fun countWords(value: String): Int {
        val words = value.split(" ")
        return words.size
    }

    fun getWordFromText(value: String, pos: Int): String {

        val textParts = value.split(" ")

        val word = textParts.getOrElse(pos) { "" }


        return word
    }

    fun removeFirstChar(value: String, Count: Int): String {
        return value.substring(Count)
    }

    fun getStartWord(value: String): String {

        val textParts = value.split(" ")

        val word = textParts.getOrElse(0) { "" }

        return word
    }

    fun getEndWord(value: String): String {

        val textParts = value.split(" ")

        val word = textParts.last()

        return word
    }

    fun getMiddleWord(value: String): String {

        val textParts = value.split(" ")

        val word = textParts.getOrElse(1) { "" }


        return word
    }


    fun removeFirstAndLastChar(value: String?): String {
        var str = value
        str = removeFirstChar(str.toString())
        str = removeLastChar(str.toString())
        return str
    }

    fun removeLastChar(str: String): String {
        return str.substring(0, str.length - 1)
    }

    fun removeFirstChar(s: String): String {
        return s.substring(1)
    }

    fun getFristChar(value: String, endNo: Int = 2): String {
        return value.substring(0, endNo)
    }

    fun removeFromStartAndEnd(value: String): String {
        val trimmedText = value.trim { it <= ' ' }
        return trimmedText
    }

    fun removeSpeas(value: String): String {
        var output = value.replace(" ", "")
        output = output.replace("  ", "")
        output = output.replace("   ", "")
        return output
    }

    fun removeRemozFromNumber(value: String): String {
        var output = value.replace("-", "")
        output = output.replace("(", "")
        output = output.replace(")", "")
        return output
    }

    // التحقق من اللغة (عربي أو إنجليزي)
    fun isArabic(text: String): Boolean {
        return text.matches(Regex("^[\\p{IsArabic}\\s]+$"))
    }

    fun isEnglish(text: String): Boolean {
        return text.matches(Regex("^[\\p{IsLatin}\\s]+$"))
    }

    // عدد الكلمات
    fun wordCount(text: String): Int {
        return text.trim().split("\\s+".toRegex()).size
    }

    // إزالة الرموز والأرقام
    fun removeSymbolsAndNumbers(text: String): String {
        return text.replace(Regex("[^\\p{L}\\s-]"), "")
    }

    // إزالة الفراغات في البداية والنهاية
    fun trimSpaces(text: String): String {
        return text.trim()
    }

    // تطبيع النص العربي (اختياري)
    fun normalizeArabic(text: String): String {
        val normalized = Normalizer.normalize(text, Normalizer.Form.NFKD)
        return normalized.replace(Regex("\\p{Mn}"), "")
    }


    fun replaceSpecialCharacters(text: String): String {
        return text
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
            .replace("ب", "ا") // إذا كنت تقصد استبدال "ب" بـ "ا"
            .replace("ة", "ه")
            .replace("ئ", "ى")
    }


}