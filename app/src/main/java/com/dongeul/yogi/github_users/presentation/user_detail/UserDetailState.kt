package com.dongeul.yogi.github_users.presentation.user_detail

import com.dongeul.yogi.github_users.domain.model.User

data class UserDetailState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
) {
}