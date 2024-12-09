package org.connecttag.utilities.global

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.admin.DevicePolicyManager
import android.bluetooth.BluetoothAdapter
import android.os.Build
import java.io.File
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.wifi.WifiManager
import android.nfc.NfcAdapter
import android.os.BatteryManager
import android.os.Environment
import android.os.PowerManager
import android.os.StatFs
import android.os.SystemClock
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.telephony.euicc.EuiccManager

import java.io.RandomAccessFile
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*



object DeviceUtils {

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

    fun getOperatingSystemName(): String {
        val osName = System.getProperty("os.name") ?: "Unknown OS"

        return when {
            osName.startsWith("Windows") -> "Windows"
            osName.startsWith("Mac OS X") -> "macOS"
            osName.startsWith("Linux") -> {
                // التحقق من البيئات الافتراضية الشائعة
                if (System.getenv("ANDROID_ROOT") != null || System.getenv("ANDROID_DATA") != null) {
                    "Android Emulator"
                } else {
                    "Linux"
                }
            }
            else -> osName // إرجاع اسم نظام التشغيل كما هو إذا لم يتم التعرف عليه
        }
    }



    fun getAndroidVersionName(apiLevel: Int): String {
        return when (apiLevel) {
            1 -> "Android 1.0 (Base)"
            2 -> "Android 1.1 (Petit Four)"
            3 -> "Android 1.5 (Cupcake)"
            4 -> "Android 1.6 (Donut)"
            5 -> "Android 2.0 (Eclair)"
            6 -> "Android 2.0.1 (Eclair)"
            7 -> "Android 2.1 (Eclair)"
            8 -> "Android 2.2 (Froyo)"
            9 -> "Android 2.3 (Gingerbread)"
            10 -> "Android 2.3.3 (Gingerbread)"
            11 -> "Android 3.0 (Honeycomb)"
            12 -> "Android 3.1 (Honeycomb)"
            13 -> "Android 3.2 (Honeycomb)"
            14 -> "Android 4.0 (Ice Cream Sandwich)"
            15 -> "Android 4.0.3 (Ice Cream Sandwich)"
            16 -> "Android 4.1 (Jelly Bean)"
            17 -> "Android 4.2 (Jelly Bean)"
            18 -> "Android 4.3 (Jelly Bean)"
            19 -> "Android 4.4 (KitKat)"
            20 -> "Android 4.4W (KitKat Wear)"
            21 -> "Android 5.0 (Lollipop)"
            22 -> "Android 5.1 (Lollipop)"
            23 -> "Android 6.0 (Marshmallow)"
            24 -> "Android 7.0 (Nougat)"
            25 -> "Android 7.1 (Nougat)"
            26 -> "Android 8.0 (Oreo)"
            27 -> "Android 8.1 (Oreo)"
            28 -> "Android 9 (Pie)"
            29 -> "Android 10 (Q)"
            30 -> "Android 11 (R)"
            31 -> "Android 12 (S)"
            32 -> "Android 12L (S)"
            33 -> "Android 13 (Tiramisu)"
            else -> "Unknown" // for future or unknown API levels
        }
    }

    fun isDeviceOld(context: Context): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val releaseYear = Build.VERSION.SDK_INT.let {
            if (it >= Build.VERSION_CODES.R) currentYear - 2 // Android 11+ released in 2020
            else if (it >= Build.VERSION_CODES.Q) currentYear - 3 // Android 10 released in 2019
            else if (it >= Build.VERSION_CODES.P) currentYear - 4 // Android 9 released in 2018
            // ... وهكذا للإصدارات الأقدم
            else currentYear - 10 // افتراض أن الأجهزة الأقدم من 10 سنوات تعتبر قديمة
        }
        return currentYear - releaseYear >= 5 // اعتبار الأجهزة التي مضى على إصدارها 5 سنوات أو أكثر قديمة
    }

    fun isDeviceLowEnd(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)


        // يمكنك تحديد معايير أخرى مثل عدد النواة، دقة الشاشة، إلخ.
        return memoryInfo.totalMem < 2 * 1024 * 1024 * 1024 // أقل من 2GB RAM
    }

    fun isDeviceOutdated(context: Context): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val lastSecurityPatchYear = Build.VERSION.SECURITY_PATCH.substring(0, 4).toIntOrNull() ?: currentYear
        return currentYear - lastSecurityPatchYear >= 2 // اعتبار الأجهزة التي لم تتلق تحديثات أمنية لمدة عامين قديمة
    }

    fun areNotificationsEnabled(context: Context): Boolean {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationManager.areNotificationsEnabled()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
    }
    fun showNotificationPermissionSettings(context: Context) {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        } else {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        context.startActivity(intent)
    }


    fun openWifiSettings(context: Context) {
        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    fun getWifiNetworkName(context: Context): String? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo


        return wifiInfo?.ssid?.replace("\"", "") // إزالة علامات الاقتباس المزدوجة
    }


    fun getIPAddress(): String? {
        NetworkInterface.getNetworkInterfaces().toList().forEach { networkInterface ->
            networkInterface.inetAddresses.toList().forEach { inetAddress ->
                if (!inetAddress.isLoopbackAddress && inetAddress.hostAddress.indexOf(':')
                    < 0) {
                    return inetAddress.hostAddress
                }
            }
        }
        return null
    }

    fun isBluetoothEnabled(): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled

    }



    fun getCarrierName(context: Context): String? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkOperatorName

    }

    fun getNumberOfSimSlots(context: Context): Int {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            telephonyManager.phoneCount
        } else {
            // For older versions, we can use reflection to get the value
            try {
                val telephonyManagerClass = Class.forName(telephonyManager.javaClass.name)
                val method = telephonyManagerClass.getMethod("getPhoneCount")
                method.invoke(telephonyManager) as Int
            } catch (e: Exception) {
                // If reflection fails, assume single SIM
                1
            }
        }
    }



    fun isEsimSupported(context: Context): Boolean {
        val euiccManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.getSystemService(Context.EUICC_SERVICE) as EuiccManager
        } else {
            TODO("VERSION.SDK_INT < P")
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            euiccManager.isEnabled()
        } else {
            TODO("VERSION.SDK_INT < P")
        }
    }


    fun isEsimSupported2(context: Context): Boolean {
        val packageManager = context.packageManager
        val esimPackages = listOf("com.google.android.apps.carriersetup", "com.android.carrierconfig") // أمثلة لتطبيقات eSIM

        for (packageName in esimPackages) {
            try {
                packageManager.getPackageInfo(packageName, 0)
                return true // تم العثور على تطبيق eSIM مثبت
            } catch (e: PackageManager.NameNotFoundException) {
                // لم يتم العثور على التطبيق، استمر في البحث
            }
        }
        return false // لم يتم العثور على أي تطبيق eSIM مثبت
    }
    fun hasGPS(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    }

    fun openLocationSettings(context: Context) {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun hasCamera(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }


    fun isDeviceOwner(context: Context): Boolean {
        val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return devicePolicyManager.isDeviceOwnerApp(context.packageName)

    }

    fun getSystemLanguage(): String {
        return Locale.getDefault().language
    }

    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openApp(context: Context, packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            // التطبيق غير مثبت أو لا يمكن فتحه
        }
    }

    fun getAvailableRAM(context: Context): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem

    }



    fun isExternalStorageAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadOnly(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY
    }

    fun getTotalInternalStorage(): Long {
        val stat = StatFs(Environment.getDataDirectory().path)
        return stat.totalBytes
    }

    fun getAvailableInternalStorage(): Long {
        val stat = StatFs(Environment.getDataDirectory().path)
        return stat.availableBytes
    }
    fun getTotalExternalStorage(): Long {
        return if (isExternalStorageAvailable()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().path)
            stat.totalBytes
        } else {
            0L
        }
    }

    fun getAvailableExternalStorage(): Long {
        return if (isExternalStorageAvailable()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().path)
            stat.availableBytes
        } else {
            0L
        }
    }

    fun getUsedExternalStorage(): Long {
        return getTotalExternalStorage() - getAvailableExternalStorage()
    }

    fun getUsedInternalStorage(): Long {
        return getTotalInternalStorage() - getAvailableInternalStorage()
    }

    fun isBatteryOptimizationEnabled(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            powerManager.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            false // لا يوجد وضع تقييد البطارية على إصدارات Android الأقدم
        }
    }

    fun getBatteryLevel(context: Context): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }
        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        if (level == -1 || scale == -1) {
            return   50 // قيمة افتراضية في حالة عدم القدرة على الحصول على مستوى البطارية
        } else {
            return  (level * 100 / scale.toFloat()).toInt()
        }
    }


    fun getBatteryInfo(context: Context): Triple<Int, Float, String> {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val temperature: Float = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
        ?.div(10f) ?: -1f
        val technology: String = batteryStatus?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"

        return Triple(status, temperature, technology)
    }

    fun isNFCAvailable(context: Context): Boolean {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        return nfcAdapter != null && nfcAdapter.isEnabled

    }

    fun getDeviceUptime(): String {
        val uptimeMillis = SystemClock.elapsedRealtime()
        val uptimeSeconds = uptimeMillis / 1000
        val uptimeMinutes = uptimeSeconds / 60
        val uptimeHours = uptimeMinutes / 60
        val uptimeDays = uptimeHours / 24

        return String.format("%d days, %d hours, %d minutes, %d seconds", uptimeDays, uptimeHours % 24, uptimeMinutes % 60, uptimeSeconds % 60)
    }

    fun isDeveloperModeEnabled(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
        0) != 0
    }

    fun getLastLaunchTime(context: Context): String {
        val sharedPrefs: SharedPreferences = context.getSharedPreferences("app_launch_prefs", Context.MODE_PRIVATE)
        val lastLaunchTime = sharedPrefs.getLong("last_launch_time", 0)

        // تحديث وقت التشغيل الأخير
        with(sharedPrefs.edit()) {
            putLong("last_launch_time", System.currentTimeMillis())
            apply()
        }

        return if (lastLaunchTime == 0L) {
            "This is the first launch."
        } else {
            val date = Date(lastLaunchTime)
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            format.format(date)
        }
    }
/*
    fun isFingerprintSensorAvailable(context: Context): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(context)
        return fingerprintManager.isHardwareDetected

    }

*/

    fun getCellularSignalStrength(context: Context, callback: (Int) -> Unit) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val phoneStateListener = object : PhoneStateListener()
        {
            @Deprecated("Deprecated in Java")
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength?) {
                super.onSignalStrengthsChanged(signalStrength)

                val strength = signalStrength?.level ?: 0
                callback(strength)
            }
        }
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
    }

    fun getNumberOfCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    fun getCpuInfo(): String {
        try {
            val reader = RandomAccessFile("/proc/cpuinfo", "r")
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line?.startsWith("model name") == true) {
                    return line?.substring(line!!.indexOf(":") + 2)?.trim() ?: "Unknown"
                }
            }
            reader.close()
        } catch (e: Exception) {
            // تعامل مع الخطأ
        }
        return "Unknown"
    }

    fun getNetworkType(context: Context): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return "No Network"
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Unknown"

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile Data"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
            else -> "Unknown"
        }
    }

    fun getWifiInfo(context: Context): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo

        return """
        SSID: ${wifiInfo.ssid}
        BSSID: ${wifiInfo.bssid}
        RSSI: ${wifiInfo.rssi}
        Link Speed: ${wifiInfo.linkSpeed} Mbps
    """.trimIndent()
    }
    public fun Context.openSystemSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri: Uri = Uri.fromParts("package", packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = uri
        }
        startActivity(intent)
    }

    fun getMobileDataInfo(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return """
        Network Operator: ${telephonyManager.networkOperatorName}
        SIM Operator: ${telephonyManager.simOperatorName}
        Network Type: ${telephonyManager.networkType}
    """.trimIndent()
    }

/*    suspend fun getPublicIPAddress(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val scanner = Scanner(URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")
                if (scanner.hasNext()) scanner.next() else null
            } catch (e: Exception) {
                null
            }
        }
    }*/

    fun getAndroidID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    }
    /*

        fun isFingerprintHardwareAvailable(context: Context): Boolean {
            val biometricManager = BiometricManager.from(context)
            return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
        }
    */

    /*
        fun isDeviceTimeIncorrect(context: Context): Boolean {
            val ntpServer = "pool.ntp.org" // يمكنك استخدام أي خادم NTP آخر
            val timeDifferenceThreshold = 60000 // عتبة فرق الوقت المسموح به بالمللي ثانية (60 ثانية في هذا المثال)

            return runBlocking {
                withContext(Dispatchers.IO) {
                    try {
                        val ntpClient = NTPUDPClient()
                        ntpClient.defaultTimeout = 5000 // مهلة 5 ثوانٍ
                        val info: TimeInfo = ntpClient.getTime(InetAddress.getByName(ntpServer))
                        info.computeDetails()

                        val deviceTime = System.currentTimeMillis()
                        val ntpTime = info.message.transmitTimeStamp.time

                        val timeDifference = Math.abs(deviceTime - ntpTime)
                        timeDifference > timeDifferenceThreshold
                    } catch (e: Exception) {
                        // خطأ في الحصول على الوقت من خادم NTP، يمكن اعتبار الوقت غير صحيح
                        true
                    }
                }
            }
        }*/



}