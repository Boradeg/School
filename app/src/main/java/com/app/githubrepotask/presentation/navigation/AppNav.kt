package com.githubsearch.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.githubrepotask.presentation.navigation.NavRoutes
import com.app.githubrepotask.presentation.screens.repo_list.RepoListScreen
import com.app.githubrepotask.presentation.screens.webview.WebViewScreen
import com.app.githubrepotask.presentation.screens.home.SharedViewModel
import com.githubsearch.presentation.ui.search.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SearchScreen.route
    ) {
        composable(route = NavRoutes.SearchScreen.route) {
            val  sharedViewModel : SharedViewModel = hiltViewModel()
            HomeScreen(
                navController,
                viewmodel = sharedViewModel,
            )
        }
        composable(route = NavRoutes.RepoListScreen.route) {
            val sharedViewModel: SharedViewModel = hiltViewModel(
                navController.getBackStackEntry(NavRoutes.SearchScreen.route)
            )
            RepoListScreen(
                navController,
                viewModel = sharedViewModel ,
            )
        }
        composable(route = NavRoutes.WebViewScreen.route) {
            val sharedViewModel: SharedViewModel = hiltViewModel(
                navController.getBackStackEntry(NavRoutes.SearchScreen.route)
            )
            WebViewScreen(navController,sharedViewModel)
        }
    }
}

