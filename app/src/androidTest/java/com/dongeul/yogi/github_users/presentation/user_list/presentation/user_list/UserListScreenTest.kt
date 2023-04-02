package com.dongeul.yogi.github_users.presentation.user_list.presentation.user_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dongeul.yogi.github_users.MainActivity
import com.dongeul.yogi.github_users.common.TestTags
import com.dongeul.yogi.github_users.di.AppModule
import com.dongeul.yogi.github_users.presentation.Screen
import com.dongeul.yogi.github_users.presentation.user_list.UserListScreen
import com.dongeul.yogi.ui.theme.YogiTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class UserListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            YogiTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.UserListScreen.route
                ) {
                    composable(route = Screen.UserListScreen.route){
                        UserListScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun `정렬_버튼을_눌렀을때_정렬_목록들이_보여지는가`() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

}