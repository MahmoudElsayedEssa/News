package com.example.souhoolatask.data.remote.interceptors

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import com.example.souhoolatask.data.remote.exceptions.NetworkConnectionException
import com.example.souhoolatask.data.remote.exceptions.NetworkHostException
import com.example.souhoolatask.data.remote.exceptions.NetworkTimeoutException
import com.example.souhoolatask.data.remote.exceptions.NoConnectivityException
import okhttp3.Interceptor
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Interceptor for handling network connectivity
 */
class ConnectivityInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        if (!isNetworkAvailable()) {
            throw NoConnectivityException("No internet connection")
        }

        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> throw NetworkTimeoutException("Request timed out")
                is ConnectException -> throw NetworkConnectionException("Failed to connect to server")
                is UnknownHostException -> throw NetworkHostException("Unknown host")
                else -> throw e
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }
}
