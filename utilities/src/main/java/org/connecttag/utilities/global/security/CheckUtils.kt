package org.connecttag.utilities.global.security

import android.app.Activity
import android.os.Build
import android.os.Debug
import android.view.WindowManager
import java.io.File

object CheckUtils {


    fun isRunningOnEmulator(): Boolean {
        val buildProperties = Build.getRadioVersion()
        if (buildProperties.contains("sdk") || buildProperties.contains("generic")) {
            return true // Running on an emulator
        } else {
            return false // Running on a physical device
        }
    }

    fun isRooted(): Boolean {
        val pathsToCheck = listOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in pathsToCheck) {
            if (File(path).exists()) return true
        }
        return false
    }




    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v ushr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }


    fun isDebuggerConnected(): Boolean {
        return Debug.isDebuggerConnected()
    }
 /*
    fun isSignatureValid(context: Context): Boolean {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        for (signature in packageInfo.signatures) {
            // قارن توقيع التطبيق بالتوقيع الأصلي المحفوظ لديك
            if (!signature.equals(originalSignature)) {
                return false
            }
        }
        return true
    }
*/

 fun hasSuspiciousFiles(): Boolean {
     val pathsToCheck = listOf(
         "/data/local/tmp/re.frida.server",
         "/data/data/{package_name}/lib/libfrida-gadget.so"
     )
     for (path in pathsToCheck) {
         if (File(path).exists()) return true
     }
     return false
 }


    fun preventScreenshots(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun stopPreventScreenshots(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)

    }

}