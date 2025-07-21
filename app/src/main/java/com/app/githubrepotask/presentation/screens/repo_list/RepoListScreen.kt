package com.app.githubrepotask.presentation.screens.repo_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.githubrepotask.R
import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.presentation.components.CommonErrorContent
import com.app.githubrepotask.presentation.components.CommonLoader
import com.app.githubrepotask.presentation.components.CommonTopBarSection
import com.app.githubrepotask.presentation.navigation.NavRoutes
import com.app.githubrepotask.presentation.screens.home.SharedViewModel
import com.app.githubrepotask.presentation.state.UiState
import com.app.githubrepotask.ui.theme.BgWhite
import com.app.githubrepotask.ui.theme.Green500

@Composable
fun RepoListScreen(
    navController: NavController,
    viewModel: SharedViewModel,
) {
    val uiState by viewModel.repositories.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredRepos by viewModel.filteredRepos.collectAsState()
    val username by viewModel.userName.collectAsState()
    val context = LocalContext.current

    // Fetch repos once
    LaunchedEffect(key1 = username) {
        viewModel.fetchRepositoryList(username)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgWhite)
    ) {
        CommonTopBarSection(
            navController = navController,
            searchQuery = searchQuery, context = context,
            submitButton = false, onQueryChanged = {
            viewModel.onSearchQueryChanged(it)
        }, onClickSearch = {})

        when (uiState) {
            is UiState.Loading -> CommonLoader()
            is UiState.Error -> CommonErrorContent((uiState as UiState.Error).message)
            is UiState.Success -> {
                if (filteredRepos.isEmpty()) {
                    CommonErrorContent(stringResource(R.string.txt_no_repositories_found))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        items(filteredRepos) { repo ->
                            RepoListItem(
                                repo = repo,
                                onClick = { url ->
                                    viewModel.setRepoUrl(url)
                                    navController.navigate(NavRoutes.WebViewScreen.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepoListItem(
    repo: RepoEntity,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(repo.htmlUrl) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Green500,    // background color of the card
            contentColor = Color.White    // color of content inside the card
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.ic_arrow_contry_selection),
                contentDescription = null,
            )
        }
    }
}







