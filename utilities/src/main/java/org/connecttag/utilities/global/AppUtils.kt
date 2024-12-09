package org.connecttag.utilities.global


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.InstallSourceInfo

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    fun isInstalledFromPlayStore(context: Context): Boolean {
        val installerPackageName = context.packageManager.getInstallerPackageName(context.packageName)
        return installerPackageName != null && installerPackageName == "com.android.vending"
    }



    fun getInstallerPackageName(context: Context): String? {
        return try {
            val packageManager = context.packageManager
            val installSourceInfo: InstallSourceInfo =
                packageManager.getInstallSourceInfo(context.packageName)
            installSourceInfo.installingPackageName
        } catch (e: Exception) {
            null // تعامل مع الاستثناءات حسب الحاجة
        }
    }


    fun getAppInstallTime(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val installTime = packageInfo.firstInstallTime
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.format(Date(installTime))
        } catch (e: PackageManager.NameNotFoundException) {
            "N/A" // في حالة عدم العثور على معلومات الحزمة
        }
    }



    fun getAppUpdateTime(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val updateTime = packageInfo.lastUpdateTime
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.format(Date(updateTime))
        } catch (e: PackageManager.NameNotFoundException) {
            "N/A" // في حالة عدم العثور على معلومات الحزمة
        }
    }



    // جلب مصدر تثبيت التطبيق
    fun getAppInstallerSource(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.packageManager.getInstallSourceInfo(context.packageName).installingPackageName ?: "Unknown"
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getInstallerPackageName(context.packageName) ?: "Unknown"
        }
    }

    // جلب اسم التطبيق
    fun getAppName(context: Context): String {
        val applicationInfo = context.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(stringId)
    }

    // جلب إصدار التطبيق
    fun getAppVersionName(context: Context): String {
        return try {
          context.packageManager.getPackageInfo(context.packageName, 0).toString()

        } catch (e: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }

    // جلب رقم إصدار التطبيق (Code)
    fun getAppVersionCode(context: Context): Long {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            -1
        }
    }

    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun changeAppIcon(context: Context, aliasName: String) {
        val packageManager = context.packageManager
        val componentName = ComponentName(context, aliasName)

        packageManager.setComponentEnabledSetting(componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)

        // تعطيل الأيقونات الأخرى
        val disableAliasNames = listOf(".MainActivityAlias1", ".MainActivityAlias2") // أضف أسماء جميع الأيقونات البديلة
            .filter { it != aliasName }
        for (disableAlias in disableAliasNames) {
            packageManager.setComponentEnabledSetting(
                ComponentName(context, disableAlias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
        }
    }
    fun clearAppCache(context: Context) {
        val cacheDir = context.cacheDir
        cacheDir.deleteRecursively()
    }




    fun calculateOptimalTextSize(context: Context, baseTextSize: Float): Float {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val screenDensity = displayMetrics.densityDpi

        // يمكنك ضبط هذه المعاملات لتناسب احتياجاتك
        val widthFactor = 0.05f
        val heightFactor = 0.03f
        val densityFactor = 0.01f

        val widthRatio = screenWidth / 1080f // نسبة العرض مقارنة بشاشة 1080p
        val heightRatio = screenHeight / 1920f // نسبة الارتفاع مقارنة بشاشة 1080p

        val adjustedTextSize = baseTextSize * (1 + widthFactor * widthRatio + heightFactor * heightRatio + densityFactor * (screenDensity - DisplayMetrics.DENSITY_DEFAULT))

        return adjustedTextSize
    }


    /*
        fun isAppInDebugMode(context: Context): Boolean {
            return (context.applicationInfo.flags and applicationInfo.FLAG_DEBUGGABLE) != 0
        }*/

}