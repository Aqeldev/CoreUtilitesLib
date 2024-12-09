package org.connecttag.utilities.global



import android.annotation.SuppressLint

import java.text.SimpleDateFormat
import java.time.*
import java.util.*




object DateUtils {

    fun dateTimeFormat(timestamp: Long?): Pair<String, String> {
        val date = Date(timestamp ?: 0L)
        val formattedDate = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date)
        val formattedTime = SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(date)
        return Pair(formattedDate, formattedTime)
    }

    fun dateTimeFormat(timestamp: String?): Pair<String?, String?> {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(timestamp ?: "")
        val formattedDate = date?.let { SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(it) }
        val formattedTime = date?.let { SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(it) }
        return Pair(formattedDate, formattedTime)
    }

    fun convertHour(hour: String): String {
        return if (hour.toInt() in 1..11) {
            "$hour AM"
        } else {
            when (hour) {
                "00" -> {
                    "12 AM"
                }

                "12" -> {
                    "12 PM"
                }

                else -> {
                    "0${hour.toInt() - 12} PM"
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun readTimestamp(timestamp: Long): String {
        val formatter = SimpleDateFormat("hh:mm")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000L
        return formatter.format(calendar.time)
    }

    fun getSecond(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.SECOND]
    }

    fun getMinute(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.MINUTE]
    }

    fun getHour(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.HOUR_OF_DAY]
    }

    fun getDay(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.DAY_OF_MONTH]
    }

    fun getMonth(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.MONTH]
    }

    fun getYear(date: Date?): Int {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        return cal[Calendar.YEAR]
    }

    @JvmOverloads
    fun newTimestamp(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0
    ): Long {
        return GregorianCalendar(year, month, day, hour, minute, second).timeInMillis
    }

    fun newDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date {
        return Date(newTimestamp(year, month, day, hour, minute, second))
    }

    fun newDate(year: Int, month: Int, day: Int): Date {
        return Date(newTimestamp(year, month, day))
    }

    fun setClock(date: Date?, hour: Int, minute: Int, second: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal[Calendar.MILLISECOND] = 0
        if (hour > -1) cal[Calendar.HOUR] = hour
        if (minute > -1) cal[Calendar.MINUTE] = minute
        if (second > -1) cal[Calendar.SECOND] = second
        return cal.time
    }

    fun setDate(date: Date?, month: Int, day: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        if (month > -1) cal[Calendar.MONTH] = month
        if (day > -1) cal[Calendar.DAY_OF_MONTH] = day
        return cal.time
    }

    fun setDay(date: Date?, day: Int): Date {
        return setDate(date, -1, day)
    }

    fun setWeekDay(date: Date?, weekDay: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal[Calendar.DAY_OF_WEEK] = weekDay
        return cal.time
    }

    fun setMonth(date: Date?, month: Int): Date {
        return setDate(date, month, -1)
    }

    fun rollDays(date: Date?, days: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.DAY_OF_MONTH, days)
        return cal.time
    }

    fun rollMonths(date: Date?, months: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.MONTH, months)
        return cal.time
    }


    fun F_Get_Current_Time_InMillis(): Long {
        return System.currentTimeMillis()
    }

    fun getTodayDateTimeToString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val currentDate = sdf.format(Date())
        return currentDate
    }


    fun F_Get_ToDay_Date_ToString(): String {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val currentDate = dateFormat.format(Date())

        return currentDate
    }
    fun rollYears(date: Date?, years: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.YEAR, years)
        return cal.time
    }

    fun rollHours(date: Date?, hours: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.HOUR_OF_DAY, hours)
        return cal.time
    }

    fun rollMinutes(date: Date?, minutes: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.MINUTE, minutes)
        return cal.time
    }

    fun rollSeconds(date: Date?, seconds: Int): Date {
        val cal = GregorianCalendar()
        if (date != null) {
            cal.time = date
        }
        cal.roll(Calendar.SECOND, seconds)
        return cal.time
    }

    fun now(): Date {
        return Date()
    }

    @JvmOverloads
    fun today(hour: Int = 0, minute: Int = 0, second: Int = 0): Date {
        return setClock(Date(), hour, minute, second)
    }

    fun yesterday(): Date {
        val cal = GregorianCalendar()
        cal.time = today(0, 0, 0)
        cal.roll(Calendar.DAY_OF_MONTH, -1)
        return cal.time
    }

    fun yesterday(hour: Int, minute: Int, second: Int): Date {
        return setClock(yesterday(), hour, minute, second)
    }

    fun tomorrow(): Date {
        val cal = GregorianCalendar()
        cal.roll(Calendar.DAY_OF_MONTH, 1)
        return cal.time
    }

    fun tomorrow(hour: Int, minute: Int, second: Int): Date {
        return setClock(tomorrow(), hour, minute, second)
    }



  //  fun getTimeStamp(): String? {
 //       return SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())
   // }








    fun getCurrentDateTime(zoneId: ZoneId = ZoneId.systemDefault()): ZonedDateTime {
        return ZonedDateTime.now(zoneId)
    }
}