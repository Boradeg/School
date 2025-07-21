package com.app.githubrepotask.data.remote

import com.app.githubrepotask.data.model.RepoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<List<RepoDto>>
}




