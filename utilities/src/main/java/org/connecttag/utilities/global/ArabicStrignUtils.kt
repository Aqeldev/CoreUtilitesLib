package org.connecttag.utilities.global

object ArabicStrignUtils {


    fun replaceLetters(input: String): String {
        var output = input.replace("أ".toRegex(), "ا")
        output = output.replace("إ".toRegex(), "ا")
        output = output.replace("ى".toRegex(), "ي")
        output = output.replace("ة".toRegex(), "ه")
        output = output.replace("آ".toRegex(), "ا")
        output = output.replace("ٱ".toRegex(), "ا")
        return output
    }

    fun removeArHamzh(txt: String): String {
        var output = txt.replace("أ".toRegex(), "ا")
        output = output.replace("آ".toRegex(), "ا")
        output = output.replace("إ".toRegex(), "ا")
        output = output.replace("ٱ".toRegex(), "ا")
        output = output.replace("ة".toRegex(), "ه")
        return output
    }

    fun removeArTashkel(txt: String): String {
        var output = txt.replace("أ".toRegex(), "ا")
        output = output.replace("آ".toRegex(), "ا")
        output = output.replace("إ".toRegex(), "ا")
        output = output.replace("ٱ".toRegex(), "ا")
        output = output.replace("ئ".toRegex(), "ي")
        output = output.replace("ى".toRegex(), "ي")
        output = output.replace("ة".toRegex(), "ه")
        return output
    }

    fun replaceArSomeWord(txt: String): String {
        var output = txt.replace("  ".toRegex(), " ")
        output = output.replace("أ".toRegex(), "ا")
        output = output.replace("آ".toRegex(), "ا")
        output = output.replace("إ".toRegex(), "ا")
        output = output.replace("ٱ".toRegex(), "ا")
        output = output.replace("ئ".toRegex(), "ي")
        output = output.replace("ى".toRegex(), "ي")
        output = output.replace("ؤ".toRegex(), "و")
        output = output.replace("ة".toRegex(), "ه")
        output = output.replace("ال".toRegex(), "")
        output = output.replace("ء".toRegex(), "")
        return output
    }

    fun replaceArabicTaWithHa2(text: String): String {
        return text.split(" ").joinToString(" ") { word ->
            val lastCharIndex = word.length - 1
            if (lastCharIndex >= 0 && word[lastCharIndex] == 'ة') {
                word.substring(0, lastCharIndex) + 'ه'
            } else {
                word
            }
        }
    }
}