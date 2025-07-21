package com.githubsearch.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.githubrepotask.R
import com.app.githubrepotask.presentation.components.CommonErrorContent
import com.app.githubrepotask.presentation.components.CommonLoader
import com.app.githubrepotask.presentation.components.CommonTopBarSection
import com.app.githubrepotask.presentation.navigation.NavRoutes
import com.app.githubrepotask.presentation.screens.home.SharedViewModel
import com.app.githubrepotask.presentation.state.UiState
import com.app.githubrepotask.ui.theme.BgWhite
import com.app.githubrepotask.ui.theme.dividerColor

@Composable
fun HomeScreen(
    navController: NavController,
    viewmodel: SharedViewModel,
) {

    var searchQuery by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val repoState by viewmodel.searchHistoryState.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.fetchSearchHistory()
        searchQuery = ""
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgWhite)
    ) {
        CommonTopBarSection(
            backButtonRequired = false,
            navController = navController,
            searchQuery = searchQuery,
            context = context, submitButton = true, onQueryChanged = {
                searchQuery = it
            }, onClickSearch = {
                viewmodel.onUsernameSelected(it)
                searchQuery = ""
                navController.navigate(NavRoutes.RepoListScreen.route)
            })
        Spacer(modifier = Modifier.height(12.dp))
        when (val state = repoState) {
            is UiState.Loading -> CommonLoader()
            is UiState.Error -> CommonErrorContent(message = state.message)
            is UiState.Success -> {
                if (state.data.isNotEmpty()) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = stringResource(R.string.txt_recent_searches),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn {
                            items(state.data) { username ->
                                UsersItem(
                                    userName = username,
                                    onClickItem = {
                                        viewmodel.onUsernameSelected(username)
                                        navController.navigate(NavRoutes.RepoListScreen.route)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UsersItem(
    userName: String,
    onClickItem: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    onClickItem(userName)
                },
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = userName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = W400),
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_contry_selection),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 1.dp, color = dividerColor)
        Spacer(modifier = Modifier.height(20.dp))

    }
}
