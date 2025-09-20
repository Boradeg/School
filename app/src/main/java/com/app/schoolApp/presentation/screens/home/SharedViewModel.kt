package com.app.schoolApp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.data.model.SchoolDto
import com.app.schoolApp.domain.usecases.GetSatScoreUseCase
import com.app.schoolApp.domain.usecases.GetSchoolsUseCase
import com.app.schoolApp.data.state.Resource
import com.app.schoolApp.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val getSchoolsUseCase: GetSchoolsUseCase,
    private val getSatScoresUseCase: GetSatScoreUseCase
) : ViewModel() {

    private val _schoolsState = MutableStateFlow<UiState<List<SchoolDto>>>(UiState.Loading)
    val schoolsState: StateFlow<UiState<List<SchoolDto>>> = _schoolsState

    private val _satScoresState = MutableStateFlow<UiState<List<SatScoreDto>>>(UiState.Loading)
    val satScoresState: StateFlow<UiState<List<SatScoreDto>>> = _satScoresState

    private val _selectedSchool = MutableStateFlow<SchoolDto?>(null)
    val selectedSchool: StateFlow<SchoolDto?> = _selectedSchool

    private val _selectedSatScore = MutableStateFlow<UiState<SatScoreDto>>(UiState.Loading)
    val selectedSatScore: StateFlow<UiState<SatScoreDto>> = _selectedSatScore

    init {
        fetchSchools()
        fetchAllSatScores() // fetch all SAT once
    }

    private fun fetchSchools() {
        viewModelScope.launch {
            getSchoolsUseCase().collect { resource ->
                _schoolsState.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> UiState.Success(resource.data ?: emptyList())
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }

    private fun fetchAllSatScores() {
        viewModelScope.launch {
            getSatScoresUseCase().collect { resource ->
                _satScoresState.value = when (resource) {
                    is Resource.Loading -> UiState.Loading
                    is Resource.Success -> UiState.Success(resource.data ?: emptyList())
                    is Resource.Error -> UiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }


    fun selectSchool(school: SchoolDto) {
        _selectedSchool.value = school

        val satScore = (satScoresState.value as? UiState.Success<List<SatScoreDto>>)
            ?.data
            ?.firstOrNull { it.dbn == school.dbn }

        _selectedSatScore.value = satScore?.let { UiState.Success(it) }
            ?: UiState.Error("SAT data not available")
    }

}

