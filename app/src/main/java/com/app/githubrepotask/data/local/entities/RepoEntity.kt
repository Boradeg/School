package com.app.githubrepotask.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val htmlUrl: String,
    val username: String
)


