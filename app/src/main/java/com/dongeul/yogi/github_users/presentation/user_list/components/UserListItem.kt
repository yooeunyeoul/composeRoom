package com.dongeul.yogi.github_users.presentation.user_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.presentation.components.HeartIcon

@Composable
fun UserListItem(
    user: User? = null,
    onItemClick: (User?) -> Unit,
    onHeartClick: (User?) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(user) }
            .padding(22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(user?.avatarUrl)
                .crossfade(true).build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = user?.login ?: "이것은 텍스트", style = MaterialTheme.typography.body1)
            Text(
                text = user?.htmlUrl ?: "이것은 텍스트 입니다",
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.h1,
                fontSize = 16.sp
            )
        }

        HeartIcon(user = user, onItemClick = {
            onHeartClick(user)
        })
    }
}

@Preview
@Composable
fun PreviewUserItem() {
    UserListItem(user = null, onItemClick = {}, onHeartClick = {})
}