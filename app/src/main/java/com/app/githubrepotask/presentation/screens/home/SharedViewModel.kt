package com.app.githubrepotask.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.githubrepotask.data.model.Results
import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.domain.usecases.GetAllUsernamesUseCase
import com.app.githubrepotask.domain.usecases.GetUserReposUseCase
import com.app.githubrepotask.domain.usecases.InsertUsernameUseCase
import com.app.githubrepotask.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserReposUseCase,
    private val getAllUsernamesUseCase: GetAllUsernamesUseCase,
    private val insertUsernameUseCase: InsertUsernameUseCase,
) : ViewModel() {
    private val _repositories = MutableStateFlow<UiState<List<RepoEntity>>>(UiState.Loading)
    val repositories: StateFlow<UiState<List<RepoEntity>>> = _repositories.asStateFlow()

    private val _searchHistoryState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val searchHistoryState: StateFlow<UiState<List<String>>> = _searchHistoryState


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _url = MutableStateFlow("")
    val url: StateFlow<String> = _url

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onUsernameSelected(username: String) {
        _userName.value = username
    }

    fun setRepoUrl(url: String) {
        _url.value = url
    }


    @OptIn(FlowPreview::class)
    private val debouncedQuery = _searchQuery
        .debounce(300)
        .distinctUntilChanged()

    val filteredRepos: StateFlow<List<RepoEntity>> = combine(
        _repositories,
        debouncedQuery
    ) { state, query ->
        if (state is UiState.Success) {
            state.data.filter {
                it.name.contains(query, ignoreCase = true)
            }
        } else {
            emptyList()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        fetchSearchHistory()
    }

     fun fetchSearchHistory() {
        viewModelScope.launch {
            _searchHistoryState.value = UiState.Loading
            when (val result = getAllUsernamesUseCase()) {
                is Results.Success -> {
                    _searchHistoryState.value = UiState.Success(result.data)
                }
                is Results.Error -> _searchHistoryState.value = UiState.Error(result.message)
            }
        }
    }

    private fun insertUsername(username: String) {
        viewModelScope.launch {
            insertUsernameUseCase(username)
        }
    }
    fun fetchRepositoryList(username: String) {
        viewModelScope.launch {
            _repositories.value = UiState.Loading
            when (val result = getUserReposUseCase(username)) {
                is Results.Success -> {
                    insertUsername(username)
                    _repositories.value = UiState.Success(result.data)
                }
                is Results.Error -> {
                    _repositories.value = UiState.Error(result.message)
                }
            }
        }
    }

}

