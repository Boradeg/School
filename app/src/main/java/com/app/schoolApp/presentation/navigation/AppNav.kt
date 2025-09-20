package com.githubsearch.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.schoolApp.presentation.navigation.NavRoutes
import com.app.schoolApp.presentation.screens.home.SchoolsViewModel
import com.app.schoolApp.presentation.screens.SchoolDetailed.SchoolDetailScreen
import com.githubsearch.presentation.ui.search.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HomeScreen.route
    ) {
        // Home Screen
        composable(route = NavRoutes.HomeScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeScreen.route)
            }
            val sharedViewModel: SchoolsViewModel = hiltViewModel(parentEntry)

            HomeScreen(
                navController = navController,
                viewModel = sharedViewModel,
                onSchoolClick = {
                    // select the school in ViewModel
                    //sharedViewModel.selectSchool(it)
                    navController.navigate(NavRoutes.SchoolDetailScreen.route)
                }
            )
        }

        // School Detail Screen
        composable(route = NavRoutes.SchoolDetailScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.HomeScreen.route)
            }
            val sharedViewModel: SchoolsViewModel = hiltViewModel(parentEntry)

            SchoolDetailScreen(
                navController = navController,
                viewModel = sharedViewModel
            )
        }
    }
}
