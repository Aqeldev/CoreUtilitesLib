package org.connecttag.utilities.global

import android.net.InetAddresses
import android.os.Build
import android.util.Patterns
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress

object IPAddressUtils {

    fun isValidIPv4Address(ipAddress: String): Boolean {
        return ipAddress.matches(Regex("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\$"))
    }

    fun isValidIPv6Address(ipAddress: String): Boolean {
        return ipAddress.matches(Regex("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\$"))
    }

    // التحقق من صحة عنوان IP
    fun isValidIPAddress(ipAddress: String): Boolean {
        return try {
            InetAddress.getByName(ipAddress) != null
        } catch (e: Exception) {
            false
        }
    }



    // تحديد نوع عنوان IP (IPv4 أو IPv6)
    fun getIPAddressType(ipAddress: String): String {
        return try {
            val inetAddress = InetAddress.getByName(ipAddress)
            when (inetAddress) {
                is Inet4Address -> "IPv4"
                is Inet6Address -> "IPv6"
                else -> "Unknown"
            }
        } catch (e: Exception) {
            "Invalid"
        }
    }

    // تنظيف عنوان IP (إزالة المسافات الزائدة والأحرف غير الصالحة)
    fun cleanIPAddress(ipAddress: String): String {
        return ipAddress.trim().replace(Regex("[^0-9a-fA-F.:]"), "")
    }

    // الحصول على الأوكتات (الأجزاء الرقمية) لعنوان IPv4
    fun getIPv4Octets(ipAddress: String): List<Int>? {
        return if (isValidIPv4Address(ipAddress)) {
            ipAddress.split(".").map { it.toInt() }
        } else {
            null
        }
    }

    // الحصول على المجموعات السداسية لعنوان IPv6
    fun getIPv6Groups(ipAddress: String): List<String>? {
        return if (isValidIPv6Address(ipAddress)) {
            ipAddress.split(":")
        } else {
            null
        }
    }

    fun F_Is_Ip_h_Valid(IpAdress: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            InetAddresses.isNumericAddress(IpAdress)
        } else {
            Patterns.IP_ADDRESS.matcher(IpAdress).matches()
        }
    }


}