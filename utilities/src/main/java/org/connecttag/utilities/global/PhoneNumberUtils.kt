package org.connecttag.utilities.global

import org.connecttag.utilities.global.NumberUtils.convertArNumberToEn
import org.connecttag.utilities.global.NumberUtils.removeRemozFromNumber
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.util.regex.Matcher
import java.util.regex.Pattern

object PhoneNumberUtils {


    fun isPhoneNumberWithCountryCode(value: String): Boolean {

        val keyPattern = "(\\+|00)"
        val pattern: Pattern = Pattern.compile(keyPattern)
        val matcher: Matcher = pattern.matcher(value)

        return matcher.matches()
    }

    fun removeNonNumberFrmPhone(inputPhone: String): String {
        var phone = inputPhone
        phone = phone.replace("[^\\d+]".toRegex(), "")
        phone = phone.replace(" ", "")
        phone = phone.trim { it <= ' ' }
        phone = convertArNumberToEn(phone)
        phone = removeRemozFromNumber(phone)
        return phone
    }

    fun clearYemenCodeFromNumber(number: String) :String{
        var phoneNumber = number

        phoneNumber = removeNonNumberFrmPhone(phoneNumber).toString()
        if (phoneNumber.startsWith("00967"))

            phoneNumber = phoneNumber.replaceFirst("00967".toRegex(), "")

        else if (phoneNumber.startsWith("+967"))
            phoneNumber = phoneNumber.replaceFirst("\\+967".toRegex(), "")
        else if (phoneNumber.startsWith("967"))
            phoneNumber = phoneNumber.replaceFirst("967".toRegex(), "")
        return phoneNumber
    }

  // التحقق من صحة الرقم الدولي
    fun isValidPhoneNumber(phoneNumber: String, defaultRegion: String = "US"): Boolean {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, defaultRegion)
            return phoneUtil.isValidNumber(numberProto)
        } catch (e: Exception) {
            return false
        }
    }

    // الحصول على مفتاح الدولة
    fun getCountryCode(phoneNumber: String, defaultRegion: String = "US"): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, defaultRegion)
            return "+" + numberProto.countryCode
        } catch (e: Exception) {
            return null
        }
    }

    // الحصول على الرقم بدون مفتاح الدولة
    fun getNationalNumber(phoneNumber: String, defaultRegion: String = "US"): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, defaultRegion)
            return numberProto.nationalNumber.toString()
        } catch (e: Exception) {
            return null
        }
    }

    // إزالة الرموز والحروف والمسافات وتحويل الأرقام إلى إنجليزية
    fun cleanPhoneNumber(phoneNumber: String): String {
        return phoneNumber
            .replace(Regex("[^\\d+]"), "") // إزالة الرموز والحروف
            .replace(" ", "") // إزالة المسافات
            .replace(
                Regex("[٠-٩]")
            ) { matchResult ->
                (matchResult.value[0].toInt() - '٠'.toInt() + '0'.toInt()).toChar().toString()
            } // تحويل الأرقام إلى إنجليزية
    }
}