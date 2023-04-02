package com.dongeul.yogi.github_users.domain.util

sealed class UserOrder{
    object OrderAll : UserOrder()
    object OrderLike : UserOrder()
    object OrderNormal : UserOrder()
}