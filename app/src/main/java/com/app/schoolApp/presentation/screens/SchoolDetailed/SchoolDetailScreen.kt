package com.app.schoolApp.presentation.screens.SchoolDetailed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.schoolApp.R
import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.presentation.components.CommonErrorContent
import com.app.schoolApp.presentation.components.CommonLoader
import com.app.schoolApp.presentation.screens.home.SchoolsViewModel
import com.app.schoolApp.presentation.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolDetailScreen(
    navController: NavHostController,
    viewModel: SchoolsViewModel
) {
    val selectedSchool by viewModel.selectedSchool.collectAsState()
    val satScoreState by viewModel.selectedSatScore.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.school_details)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            selectedSchool?.let { school ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(
                            R.string.school_label,
                            school.school_name ?: stringResource(R.string.not_available)
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.borough_label,
                            school.boro ?: stringResource(R.string.not_available)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.phone_label,
                            school.phone_number ?: stringResource(R.string.not_available)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.website_label,
                            school.website ?: stringResource(R.string.not_available)
                        )
                    )
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.sat_scores_label),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))

                    when (satScoreState) {
                        is UiState.Loading -> CommonLoader()
                        is UiState.Error -> CommonErrorContent(
                            message = (satScoreState as UiState.Error).message
                        )

                        is UiState.Success -> {
                            val sat = (satScoreState as UiState.Success<SatScoreDto>).data

                            sat.let {
                                Column {
                                    Text(
                                        stringResource(
                                            R.string.math_avg_label,
                                            it.mathAvg ?: stringResource(R.string.not_available)
                                        )
                                    )
                                    Text(
                                        stringResource(
                                            R.string.reading_avg_label,
                                            it.readingAvg ?: stringResource(R.string.not_available)
                                        )
                                    )
                                    Text(
                                        stringResource(
                                            R.string.writing_avg_label,
                                            it.writingAvg ?: stringResource(R.string.not_available)
                                        )
                                    )
                                }
                            } ?: Text(stringResource(R.string.sat_not_available))
                        }
                    }
                }
            } ?: Text(
                stringResource(R.string.no_school_selected),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
