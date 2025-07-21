package com.app.githubrepotask.utils

import android.content.Context
import android.net.ConnectivityManager


// Internet connectivity helper
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected == true
}
