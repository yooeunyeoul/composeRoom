package com.dongeul.yogi.github_users.data.remote

import com.dongeul.yogi.github_users.data.remote.dto.ItemsDto
import retrofit2.http.GET

interface GithubApi {
    @GET("/search/users?q=shop")
    suspend fun getItem() : ItemsDto
}