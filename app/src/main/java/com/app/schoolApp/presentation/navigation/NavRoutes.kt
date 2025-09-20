package com.app.schoolApp.presentation.navigation

sealed class NavRoutes(val route: String) {
    object HomeScreen : NavRoutes("HomeScreen")
    object SchoolDetailScreen : NavRoutes("SchoolDetailScreen")


}
