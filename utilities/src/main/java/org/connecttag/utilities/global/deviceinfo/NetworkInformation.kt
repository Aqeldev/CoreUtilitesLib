package org.connecttag.utilities.global.deviceinfoimport android.content.Contextimport android.net.ConnectivityManagerimport android.net.wifi.WifiInfoimport android.net.wifi.WifiManagerimport android.os.Buildimport java.net.Inet4Addressimport java.net.NetworkInterfaceclass NetworkInformation() {        var ipAddress: String? = "0"        var ip4Address: String? = null        var linkSpeedMbps: Int = 0        var networkID: Int? = 0        var bssID: String? = null        var maxSignalLevel: String? = null        private fun readDeviceInfo(context: Context) {            lateinit var connectivityManager: ConnectivityManager            lateinit var wifiManager: WifiManager            try {                ipAddress =                    connectivityManager.getLinkProperties(connectivityManager.activeNetwork)!!.linkAddresses[1].address.hostAddress!!            } catch (_: Exception) {            }            try {                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {                    maxSignalLevel = wifiManager.maxSignalLevel.toString()                }            } catch (_: Exception) {            }            try {                // Get the WiFi connection info                val wifiInfo: WifiInfo = wifiManager.connectionInfo                // Get the link speed in Mbps                linkSpeedMbps = wifiInfo.linkSpeed                networkID = wifiInfo.networkId                bssID = wifiInfo.bssid.toString()            } catch (_: Exception) {            }            try {                val en = NetworkInterface.getNetworkInterfaces()                while (en.hasMoreElements()) {                    val networkInterface = en.nextElement()                    val enu = networkInterface.inetAddresses                    while (enu.hasMoreElements()) {                        val inetAddress = enu.nextElement()                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {                            ip4Address = inetAddress.getHostAddress()                        }                    }                }            } catch (_: Exception) {            }        }    }