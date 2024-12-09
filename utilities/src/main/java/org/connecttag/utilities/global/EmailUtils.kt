package org.connecttag.utilities.global

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

object EmailUtils {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun isValidEmail(email: CharSequence?): Boolean {
        return if (!TextUtils.isEmpty(email)) {
            Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
        } else false
    }

    fun isEmailValid(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email.toString())
        return matcher.matches()
    }

    fun F_Is_Email_Valid(Email: String): Boolean {

        val EMAIL_Pattern = "^[_A-Za-z0-9-]+(\\.[_A-za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val Pattern: Pattern = Pattern.compile(EMAIL_Pattern)
        val Matcher: Matcher = Pattern.matcher(Email)

        return Matcher.matches()
    }

}