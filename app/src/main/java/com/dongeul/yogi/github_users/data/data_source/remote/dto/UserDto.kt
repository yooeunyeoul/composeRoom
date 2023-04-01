package com.dongeul.yogi.github_users.data.data_source.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("avatar_url")
    val avatar_url: String,
    @SerialName("events_url")
    val eventsUrl: String?=null,
    @SerialName("followers_url")
    val followersUrl: String?=null,
    @SerialName("following_url")
    val followingUrl: String?=null,
    @SerialName("gists_url")
    val gistsUrl: String?=null,
    @SerialName("gravatar_id")
    val gravatarId: String?=null,
    @SerialName("html_url")
    val html_url: String,
    val id: Int,
    val login: String,
    @SerialName("node_id")
    val nodeId: String?=null,
    @SerialName("organizations_url")
    val organizationsUrl: String?=null,
    @SerialName("received_events_url")
    val receivedEventsUrl: String?=null,
    @SerialName("repos_url")
    val reposUrl: String?=null,
    val score: Double?=null,
    @SerialName("site_admin")
    val siteAdmin: Boolean?=null,
    @SerialName("starred_url")
    val starredUrl: String?=null,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String?=null,
    val type: String?=null,
    val url: String?=null
)