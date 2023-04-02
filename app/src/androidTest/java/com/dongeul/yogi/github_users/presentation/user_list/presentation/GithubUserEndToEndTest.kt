package com.dongeul.yogi.github_users.presentation.user_list.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.dongeul.yogi.github_users.MainActivity
import com.dongeul.yogi.github_users.common.Constants
import com.dongeul.yogi.github_users.common.TestTags
import com.dongeul.yogi.github_users.di.AppModule
import com.dongeul.yogi.github_users.presentation.Screen
import com.dongeul.yogi.github_users.presentation.user_detail.UserDetailScreen
import com.dongeul.yogi.github_users.presentation.user_list.UserListScreen
import com.dongeul.yogi.ui.theme.YogiTheme
import com.dongeul.yogi.utils.waitUntilExists
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*

@HiltAndroidTest
@UninstallModules(AppModule::class)
class GithubUserEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            YogiTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.UserListScreen.route
                ) {
                    composable(route = Screen.UserListScreen.route) {
                        UserListScreen(navController = navController)
                    }
                    composable(route = Screen.UserDetailScreen.route + "?${Constants.PARAM_USER_ID}={${Constants.PARAM_USER_ID}}",
                        arguments = listOf(
                            navArgument(
                                name = Constants.PARAM_USER_ID
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

    @Test
    fun `유저목록에서_좋아요_누르고_상세화면진입후_해당유저이름이_보이는지체크_다시목록으로_돌아온후_좋아요_탭을_눌렀을때_좋아요목록에_존재하는가`() {
        composeRule.waitUntilExists(hasText("b"), timeoutMillis = 5000)
        composeRule.onNodeWithText("b").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.HEART_BUTTON + "1").assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.HEART_BUTTON + "1").performClick()

        composeRule.onNodeWithText("b").performClick()
        composeRule.onNodeWithText("b").assertIsDisplayed()

        Espresso.pressBack()

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        val nodes = composeRule.onAllNodes(hasTestTag(TestTags.LIKE_SORT))
        nodes[1].assertHasClickAction().performClick()

        composeRule.onNodeWithText("b").assertIsDisplayed()

        nodes[2].assertHasClickAction().performClick()

        nodes[1].assertHasClickAction().performClick()

        composeRule.onNodeWithText("b").assertIsDisplayed()

    }
}