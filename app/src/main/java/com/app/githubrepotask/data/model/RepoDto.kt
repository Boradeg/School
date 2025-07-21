package com.app.githubrepotask.data.model

import com.app.githubrepotask.data.local.entities.RepoEntity
import com.google.gson.annotations.SerializedName

data class RepoDto(
    val id: Int,
    val name: String,
    @SerializedName("html_url") val htmlUrl: String
) {
    fun toEntity(username: String): RepoEntity = RepoEntity(
        id = id,
        name = name,
        htmlUrl = htmlUrl,
        username = username
    )
}