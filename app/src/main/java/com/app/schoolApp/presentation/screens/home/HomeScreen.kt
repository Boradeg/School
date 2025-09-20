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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.schoolApp.R
import com.app.schoolApp.presentation.components.CommonErrorContent
import com.app.schoolApp.presentation.components.CommonLoader
import com.app.schoolApp.presentation.screens.home.SchoolsViewModel
import com.app.schoolApp.presentation.state.UiState
import com.app.schoolApp.ui.theme.BgWhite
import com.app.schoolApp.ui.theme.dividerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SchoolsViewModel,
    onSchoolClick : () -> Unit
) {
    val schoolsState by viewModel.schoolsState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.schools)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BgWhite,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgWhite)
                .padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            when (val state = schoolsState) {
                is UiState.Loading -> CommonLoader()
                is UiState.Error -> CommonErrorContent(message = state.message)
                is UiState.Success -> {
                    if (state.data.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                            items(state.data) { school ->
                                school.school_name?.let {
                                    SchoolItem(
                                        schoolName = it,
                                        onClickItem = {

                                                viewModel.selectSchool(school)
                                                onSchoolClick()

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
}

@Composable
fun SchoolItem(
    schoolName: String,
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
                    onClickItem(schoolName)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = schoolName,
                style = MaterialTheme.typography.bodyMedium,
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


