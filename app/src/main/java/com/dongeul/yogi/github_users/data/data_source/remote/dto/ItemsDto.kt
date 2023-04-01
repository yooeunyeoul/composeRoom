package com.dongeul.yogi.github_users.data.data_source.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsDto(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<UserDto>,
    @SerialName("total_count")
    val totalCount: Int
)
