package com.dongeul.yogi.github_users.presentation.user_list

import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.util.UserOrder

data class UsersListState(
    val users : List<User> = emptyList(),
    val isLoading : Boolean = false,
    val userOrder: UserOrder = UserOrder.OrderAll,
    val error: String = "",
    val isOrderSectionVisible:Boolean = false
)
