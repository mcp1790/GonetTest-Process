package mx.test.android.gonet.processlib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager

fun isNetworkAvailable(context: Context): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        cm?.activeNetwork?.let { actNet ->
            val nc = cm.getNetworkCapabilities(actNet)
            return nc?.let { netCap ->
                netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } ?: false
        }
    } else {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (info == null || !info.isConnected)
            return false //not connected
        if (info.type == ConnectivityManager.TYPE_WIFI)
            return true //"WIFI"
        if (info.type == ConnectivityManager.TYPE_MOBILE) {
            return when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN, //api<8 : replace by 11
                -> true //"2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, //api<9 : replace by 14
                TelephonyManager.NETWORK_TYPE_EHRPD,  //api<11 : replace by 12
                TelephonyManager.NETWORK_TYPE_HSPAP,  //api<13 : replace by 15
                -> true //"3G"
                TelephonyManager.NETWORK_TYPE_LTE,    //api<11 : replace by 13
                -> true //"4G"
                else -> false //"?"
            }
        }

        return false
    }

    return false
}