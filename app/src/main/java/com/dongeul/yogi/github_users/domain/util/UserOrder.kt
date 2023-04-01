package com.dongeul.yogi.github_users.domain.util

sealed class UserOrder{
    object OrderLike : UserOrder()
    object OrderNormal : UserOrder()
    object OrderAll : UserOrder()
}