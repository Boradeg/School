package com.app.githubrepotask.presentation.navigation

import android.net.Uri

sealed class NavRoutes(val route: String) {
    object SearchScreen : NavRoutes("search")
    object RepoListScreen : NavRoutes("repos/{username}") {
        fun createRoute(username: String) = "repos/$username"
    }
    object WebViewScreen : NavRoutes("webview/{url}") {
        fun createRoute(url: String) = "webview/${Uri.encode(url)}"
    }
}
