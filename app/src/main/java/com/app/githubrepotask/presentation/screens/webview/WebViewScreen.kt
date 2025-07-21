package com.app.githubrepotask.presentation.screens.webview

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.app.githubrepotask.R
import com.app.githubrepotask.presentation.components.CommonErrorContent
import com.app.githubrepotask.presentation.components.CommonLoader
import com.app.githubrepotask.presentation.components.CommonTopBar
import com.app.githubrepotask.presentation.screens.home.SharedViewModel
import com.app.githubrepotask.utils.isInternetAvailable

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(navController: NavController, viewModel: SharedViewModel) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val url by viewModel.url.collectAsState()
    val isOnline by remember {
        mutableStateOf(isInternetAvailable(context))
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            CommonTopBar(
                title = stringResource(R.string.txt_webview),
                navController = navController,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (isOnline) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                    isLoading = true
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isLoading = false
                                }
                            }
                            settings.javaScriptEnabled = true
                            loadUrl(url)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CommonErrorContent(message = stringResource(R.string.txt_no_internet_connection) )
            }
        }

        if (isLoading && isOnline) {
           CommonLoader()
        }
    }
}




