package com.dongeul.yogi.github_users

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                // A surface container using the 'background' color from the theme
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
                        composable(route = Screen.UserDetailScreen.route+"/{userId}") {
                            UserDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
