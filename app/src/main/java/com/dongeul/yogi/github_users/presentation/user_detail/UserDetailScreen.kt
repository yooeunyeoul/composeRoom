package com.dongeul.yogi.github_users.presentation.user_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dongeul.yogi.github_users.presentation.components.DefaultLinkifyText
import com.dongeul.yogi.github_users.presentation.components.HeartIcon

@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()

    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(scrollState),
        ) {
            Box() {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(state.user?.avatarUrl)
                        .crossfade(true).build(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

                HeartIcon(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    user = state.user,
                    onItemClick = {
                        viewModel.updateUser(user = state.user)
                    })
            }

            Text(
                text = state.user?.login ?: "",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(8.dp))

            DefaultLinkifyText(
                modifier = Modifier,
                text = state.user?.htmlUrl ?: ""
            )
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