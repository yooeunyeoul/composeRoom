package com.dongeul.yogi.github_users.presentation

sealed class Screen(val route: String){
    object UserListScreen : Screen("user_list")
    object UserDetailScreen : Screen("user_detail_screen")
}
