package com.dongeul.yogi.github_users

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dongeul.yogi.github_users.common.Constants.PARAM_USER_ID
import com.dongeul.yogi.github_users.presentation.Screen
import com.dongeul.yogi.github_users.presentation.user_detail.UserDetailScreen
import com.dongeul.yogi.github_users.presentation.user_list.UserListScreen
import com.dongeul.yogi.ui.theme.YogiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.UserListScreen.route
                    ) {
                        composable(route = Screen.UserListScreen.route) {
                            UserListScreen(navController = navController)
                        }
                        composable(route = Screen.UserDetailScreen.route + "?${PARAM_USER_ID}={${PARAM_USER_ID}}",
                            arguments = listOf(
                                navArgument(
                                    name = PARAM_USER_ID
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            UserDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
