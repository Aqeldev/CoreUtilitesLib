package org.connecttag.utilities.global

import kotlin.math.roundToInt

object MapUtils {

    fun isValidLatitude(latitude: Double): Boolean {
        return latitude >= -90.0 && latitude <= 90.0
    }

    fun isValidLongitude(longitude: Double): Boolean {
        return longitude >= -180.0 && longitude <= 180.0
    }

    fun isValidCoordinates(latitude: Double, longitude: Double): Boolean {
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }

    // تنظيف الإحداثيات (إزالة الأحرف غير الرقمية والنقاط والعلامات السالبة)
    fun cleanCoordinates(coordinates: String): String {
        return coordinates.replace(Regex("[^-\\d.]"), "")
    }

    // تحديد نوع الإحداثيات (درجات عشرية، درجات/دقائق/ثوان)
    fun getCoordinateType(coordinates: String): String {
        return when {
            coordinates.contains("°") && coordinates.contains("'") && coordinates.contains("\"") -> "DMS"
            coordinates.contains(".") -> "Decimal Degrees"
            else -> "Unknown"
        }
    }

    // تحويل الإحداثيات من درجات/دقائق/ثوان إلى درجات عشرية
    fun convertDMSToDecimalDegrees(dms: String): Double {
        val parts = dms.split("[°'\"]".toRegex())
        val degrees = parts[0].toDouble()
        val minutes = parts[1].toDouble()
        val seconds = parts[2].toDouble()
        return degrees + minutes / 60 + seconds / 3600

    }

    // تحويل الإحداثيات من درجات عشرية إلى درجات/دقائق/ثوان
    fun convertDecimalDegreesToDMS(dd: Double): String {
        val degrees = dd.toInt()
        val minutes = ((dd - degrees) * 60).toInt()
        val seconds = ((dd - degrees - minutes / 60.0) * 3600).roundToInt()
        return String.format("%d°%d'%d\"", degrees, minutes, seconds)
    }


}