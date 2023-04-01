package com.dongeul.yogi.github_users.presentation.user_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dongeul.yogi.github_users.presentation.components.HeartIcon

@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Box(Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Box() {
                Image(
                    painter = rememberAsyncImagePainter(state.user?.avatarUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HeartIcon(user = state.user, onItemClick = {
                    viewModel.updateUser(user = state.user)
                })
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