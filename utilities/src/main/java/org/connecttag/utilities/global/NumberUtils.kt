package org.connecttag.utilities.global

object NumberUtils {


    fun isNumeric(value: String): Boolean {

        return value.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    fun removeRemozFromNumber(txt: String): String {
        var output = txt.replace("-", "")
        output = output.replace("(", "")
        output = output.replace(")", "")


        return output
    }

    fun convertArNumberToEn(value: String): String {
        var output = value.replace("١", "1")
        output = output.replace("٢", "2")
        output = output.replace("٣", "3")
        output = output.replace("٤", "4")
        output = output.replace("٥", "5")
        output = output.replace("٦", "6")
        output = output.replace("٧", "7")
        output = output.replace("٨", "8")
        output = output.replace("٩", "9")
        output = output.replace("٠", "0")
        return output
    }


    fun convertIranianNumberToEn(value: String): String {
        var output = value.replace("۱", "1")
        output = output.replace("۲", "2")
        output = output.replace("۳", "3")
        output = output.replace("۴", "4")
        output = output.replace("۵", "5")
        output = output.replace("۶", "6")
        output = output.replace("۷", "7")
        output = output.replace("۸", "8")
        output = output.replace("۹", "9")
        output = output.replace("۰", "0")
        return output
    }

}