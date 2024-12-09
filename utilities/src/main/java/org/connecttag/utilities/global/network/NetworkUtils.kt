package com.core.utils.utilities.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities



object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun getNetworkCapabilities(context: Context): NetworkCapabilities? {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    }



    fun isVpnActive(context: Context): Boolean {
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false
    }

    fun isNetworkInternetAvailable(context: Context): Boolean {
        val capabilities = getNetworkCapabilities(context) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

    }

    fun isInternetByWifiAvailable(context: Context): Boolean {
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }

    fun isInternetByDataAvailable(context: Context): Boolean {
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    }

    fun isInternetByEthernetAvailable(context: Context): Boolean {
        return getNetworkCapabilities(context)?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ?: false
    }
}