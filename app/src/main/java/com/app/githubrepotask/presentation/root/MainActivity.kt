package com.app.githubrepotask.presentation.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.githubsearch.presentation.ui.navigation.AppNavGraph
import com.githubsearch.ui.theme.GithubSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubSearchTheme {
                AppNavGraph(rememberNavController())
            }
        }
    }
}

