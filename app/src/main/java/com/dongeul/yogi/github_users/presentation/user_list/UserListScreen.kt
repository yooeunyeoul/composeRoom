package com.dongeul.yogi.github_users.presentation.user_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.presentation.Screen
import com.dongeul.yogi.github_users.presentation.user_list.components.UserListItem

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Box(Modifier.fillMaxSize()) {

        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                Text(text = "header", style = MaterialTheme.typography.body1)
            }
            items(state.users) { user ->
                UserListItem(onItemClick = {
                    navController.navigate(Screen.UserDetailScreen.route + "/${user.id}")
                }, onHeartClick = {
                    viewModel.updateUser(user)
                }, user = user)
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}