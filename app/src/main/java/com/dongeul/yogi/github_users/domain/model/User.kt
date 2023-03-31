package com.dongeul.yogi.github_users.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dongeul.yogi.github_users.data.remote.dto.UserDto

@Entity
data class User(
    val avatarUrl: String?=null,
    val htmlUrl: String?=null,
    val login: String?=null,
    var isLike :Boolean = false,
    @PrimaryKey val id: Int?=null
){

}

fun UserDto.toUser(): User {
    return User(
        avatarUrl = avatar_url,
        htmlUrl = html_url,
        login = login,
        id = id
    )
}