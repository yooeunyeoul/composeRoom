package com.dongeul.yogi.github_users.presentation.user_list

import com.dongeul.yogi.github_users.domain.model.User

data class UsersListState(
    val isLoading : Boolean = false,
    val users : List<User> = emptyList(),
    val error: String = ""
)
